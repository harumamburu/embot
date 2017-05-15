package com.mylab.embot.page;

import com.mylab.embot.driver.Driver;
import com.mylab.embot.page.element.VisitorForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.TextInput;

import java.util.List;

public class RegisterPage extends Page {

    @FindBy(xpath = "//select[contains(@id, 'visitCategory')]")
    private Select visitPurposeSelect;

    @FindBy(xpath = "//select[contains(@id, 'embassy')]")
    private Select embSelect;

    @FindBy(xpath = "//select[contains(@id, 'visitorsCount')]")
    private Select numberOfVisitorsSelect;

    @FindBy(xpath = "//input[contains(@id, 'visitDatetime')]")
    private TextInput dateInput;

    @FindBy(xpath = "//iframe[@name='undefined']")
    private HtmlElement capchaFrame;

    private final int numberOfVisitors;
    private List<VisitorForm> visitorForms;

    public RegisterPage(Driver driver, int numberOfVisitors) {
        super(driver);
        this.numberOfVisitors = numberOfVisitors;
    }
}
