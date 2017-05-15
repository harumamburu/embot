package com.mylab.embot.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Link;

public class PreRegistrationPage extends Page {

    @FindBy(xpath = "//div[not(@class)]/a[contains(@href, 'rct77')]")
    private Link registrationPageLink;

    public PreRegistrationPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage proceedToRegistration(int numberOfVisitors) {
        registrationPageLink.click();
        return new RegisterPage(driver, numberOfVisitors);
    }
}
