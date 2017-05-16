package com.mylab.embot.scenario;

import com.mylab.embot.driver.CustomChromeDriver;
import com.mylab.embot.entity.User;
import com.mylab.embot.page.MainPage;
import com.mylab.embot.page.RegisterPage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public class RegistrationScenario implements Scenario {

    @Autowired
    private CustomChromeDriver driver;

    @Value("scenario.entrypoint.address")
    private String mainPageAddress;

    private Set<User> users;

    public RegistrationScenario(Set<User> users) {
        this.users = users;
    }

    @Override
    public void run() {
        driver.get(mainPageAddress);

        RegisterPage page = new MainPage(driver).register().proceedToRegistration();
        if (page.checkSlots(users.size())) {
            page.registerVisitors(users);
        } else {

        }
    }
}
