package com.mylab.embot.scenario;

import com.mylab.embot.driver.CustomChromeDriver;
import com.mylab.embot.entity.Visitor;
import com.mylab.embot.page.MainPage;
import com.mylab.embot.page.RegisterPage;
import com.mylab.embot.skype.SkypeClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RegistrationScenario implements Scenario {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationScenario.class.getName());

    @Autowired
    private CustomChromeDriver driver;

    @Autowired
    private SkypeClient skypeClient;

    @Value("${skype.group.name}")
    private String slotsTopic;

    @Value("${scenario.entrypoint.address}")
    private String mainPageAddress;

    @Value("${scenario.response.timeout.seconds}")
    private long waitSecondsForResponce;

    private final Set<Visitor> visitors;

    public RegistrationScenario(Set<Visitor> visitors) {
        this.visitors = visitors;
    }

    @Override
    public void run() {
        try {
            driver.get(mainPageAddress);

            RegisterPage page = new MainPage(driver).register().proceedToRegistration();

            String slotsDay = page.getAvailableSlotDay(visitors.size());
            if(slotsDay != null) {
                skypeClient.sendGroupNotification(slotsTopic, "Slots available for " + slotsDay + "!!");
                LOGGER.info("Found slots!! Registering visitors");
                page.fillInVisitorsInfo(visitors);
                page.confirmRegistration();
            } else {
                LOGGER.info("Didn't find any slots...");
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred during slots checking", e);
        }
    }

    /*@PostConstruct
    public void setDriverWaits() {
        driver.setImplicitWait(waitSecondsForResponce, TimeUnit.SECONDS);
    }*/

    @PostConstruct
    public void connectSkype() {
        skypeClient.connectSkype();
    }

    @PreDestroy
    public void disconnectSkype() {
        skypeClient.disconnectSkype();
    }
}
