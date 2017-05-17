package com.mylab.embot.page.element;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

public class Calendar extends HtmlElement {

    @FindBy(className = "ui-datepicker-next")
    private Button nextMonthButton;

    @FindBy(xpath = "//td[not(contains(@class, 'unselectable'))]")
    private Button openDayButton;

    public boolean chooseAvailableDay() {
        if (isSlotAvailable()) {
            openDayButton.click();
            return true;
        }
        return false;
    }

    public String getAvailableDayText() {
        if (isSlotAvailable()) {
            return openDayButton.getWrappedElement().getText();
        }
        return null;
    }

    public boolean isSlotAvailable() {
        if (!openDayButton.exists()) {
            nextMonthButton.click();
            return openDayButton.exists();
        }
        return true;
    }

    @Override
    public WebElement findElement(By by) {
        try {
            return super.findElement(by);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
