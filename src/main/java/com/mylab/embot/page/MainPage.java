package com.mylab.embot.page;

import com.mylab.embot.driver.CustomChromeDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Link;

public class MainPage extends Page {

    @FindBy(xpath = "//a[contains(@href, 'visit')]")
    private Link preRegistrationPageLink;

    public MainPage(CustomChromeDriver driver) {
        super(driver);
    }

    public PreRegistrationPage register() {
        driver.waitForVisibilityOf(preRegistrationPageLink);
        preRegistrationPageLink.click();
        return new PreRegistrationPage(driver);
    }
}
