package com.mylab.embot.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Link;

public class MainPage extends Page {

    @FindBy(xpath = "//a[contains(@href, 'visit')]")
    private Link preRegistrationPageLink;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public PreRegistrationPage register() {
        preRegistrationPageLink.click();
        return new PreRegistrationPage(driver);
    }
}
