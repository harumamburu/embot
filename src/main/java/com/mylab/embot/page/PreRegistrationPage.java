package com.mylab.embot.page;

import com.mylab.embot.driver.CustomChromeDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Link;

public class PreRegistrationPage extends Page {

    @FindBy(xpath = "//div[not(@class)]/a[contains(@href, 'rct77')]")
    private Link registrationPageLink;

    public PreRegistrationPage(CustomChromeDriver driver) {
        super(driver);
    }

    public RegisterPage proceedToRegistration() {
        driver.waitForVisibilityOf(registrationPageLink);
        registrationPageLink.click();
        return new RegisterPage(driver);
    }
}
