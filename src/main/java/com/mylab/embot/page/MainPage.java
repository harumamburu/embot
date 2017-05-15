package com.mylab.embot.page;

import com.mylab.embot.driver.Driver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Link;

public class MainPage extends Page {

    @FindBy(xpath = "//a[contains(@href, 'visit')]")
    private Link preRegistrationPageLink;

    public MainPage(Driver driver) {
        super(driver);
    }

    public PreRegistrationPage register() {
        preRegistrationPageLink.click();
        return new PreRegistrationPage(driver);
    }
}
