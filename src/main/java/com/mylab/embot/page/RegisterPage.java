package com.mylab.embot.page;

import com.mylab.embot.driver.CustomChromeDriver;
import com.mylab.embot.entity.Visitor;
import com.mylab.embot.page.element.Calendar;
import com.mylab.embot.page.element.VisitorForm;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.qatools.htmlelements.element.*;

import java.util.List;
import java.util.Set;

public class RegisterPage extends Page {

    @FindBy(xpath = "//select[contains(@id, 'visitCategory')]")
    private Select visitPurposeSelect;

    @FindBy(xpath = "//select[contains(@id, 'embassy')]")
    private Select embSelect;

    @FindBy(xpath = "//select[contains(@id, 'visitorsCount')]")
    private Select numberOfVisitorsSelect;

    @FindBy(xpath = "//input[contains(@id, 'visitDatetime')]")
    private TextInput dateInput;

    @FindBy(xpath = "//div[@id='ui-datepicker-div']")
    private Calendar calendar;

    @FindBy(xpath = "//input[@type='radio' and @value='09:30']")
    private Radio timeRadio;

    @FindBy(xpath = "//iframe[@name='undefined']")
    private HtmlElement capchaFrame;

    @FindBy(className = "recaptcha-checkbox-checkmark")
    private CheckBox notRobotCheckBox;

    @FindBy(xpath = "//button[contains(@id, 'visit_submit')]")
    private Button registerButton;

    private List<VisitorForm> visitorForms;

    @Value("@{scenario.registerpage.select.purpose.value:6}")
    private String visitPurposeSelectValue;

    @Value("@{scenario.registerpage.select.emb.value:3}")
    private String embSelectValue;

    @Value("@{scenario.registerpage.select.time.value:16}")
    private String timeSelectValue;

    private boolean filledIn;

    public RegisterPage(CustomChromeDriver driver) {
        super(driver);
    }

    private void fillInEmbInfo(int numberOfVisitors) {
        if (!filledIn) {
            visitPurposeSelect.selectByValue(visitPurposeSelectValue);
            embSelect.selectByValue(embSelectValue);
            numberOfVisitorsSelect.selectByValue(String.valueOf(numberOfVisitors));
            filledIn = true;
        }
    }

    /**
     * Check if there are slots available and return first available day if there are, null otherwise
     * @param numberOfVisitors
     * @return available slot day or null
     */
    public String getAvailableSlotDay(int numberOfVisitors) {
        fillInEmbInfo(numberOfVisitors);
        dateInput.getWrappedElement().click();
        return calendar.getAvailableDayText();
    }

    public void registerVisitors(Set<Visitor> visitors) {
        fillInEmbInfo(visitors.size());
        dateInput.getWrappedElement().click();

        if (!calendar.isSlotAvailable()) {
            return;
        }

        calendar.chooseAvailableDay();
        calendar.getAvailableDayText();
        timeRadio.selectByValue(timeSelectValue);

        for (int i = 0; i < visitors.size(); i++) {
            visitorForms.get(i).fillInUserInfo(visitors.iterator().next());
        }

        driver.switchTo().frame(capchaFrame);
        notRobotCheckBox.select();
        driver.switchTo().defaultContent();

        registerButton.click();
    }
}
