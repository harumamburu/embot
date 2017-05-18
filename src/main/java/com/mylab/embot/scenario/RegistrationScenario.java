package com.mylab.embot.scenario;

import com.mylab.embot.driver.CustomChromeDriver;
import com.mylab.embot.entity.User;
import com.mylab.embot.page.MainPage;
import com.mylab.embot.page.RegisterPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public class RegistrationScenario implements Scenario {

    @Autowired
    private CustomChromeDriver driver;

    @Value("scenario.entrypoint.address")
    private String mainPageAddress;

    private Set<User> visitors;

    public RegistrationScenario(Set<User> visitors) {
        this.visitors = visitors;
    }

    @Override
    public void run() {
        driver.get(mainPageAddress);

        RegisterPage page = new MainPage(driver).register().proceedToRegistration();

        String slotsDay = page.getAvailableSlotDay(visitors.size());
        if(slotsDay != null) {
            page.registerVisitors(visitors);
        } else {

        }
    }
}
