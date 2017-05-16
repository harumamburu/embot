package com.mylab.embot.page;

import com.mylab.embot.driver.Driver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Link;

public class PreRegistrationPage extends Page {

    @FindBy(xpath = "//div[not(@class)]/a[contains(@href, 'rct77')]")
    private Link registrationPageLink;

    public PreRegistrationPage(Driver driver) {
        super(driver);
    }

    public RegisterPage proceedToRegistration() {
        registrationPageLink.click();
        return new RegisterPage(driver);
    }
}
