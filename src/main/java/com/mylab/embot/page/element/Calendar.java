package com.mylab.embot.page.element;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Value;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.util.Optional;

public class Calendar extends HtmlElement {

    @Value("element.calendar.locator://td[not(contains(@class, 'unselectable'))]")
    private String openDayLocator;

    @FindBy(className = "ui-datepicker-next")
    private Button nextMonthButton;

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
        if (openDayButton == null) {
            Optional.ofNullable(
                    Optional.ofNullable(findElement(By.xpath(openDayLocator)))
                            .orElseGet(() -> {
                                nextMonthButton.click();
                                return findElement(By.xpath(openDayLocator));
                            }))
                    .ifPresent(webElement -> openDayButton = new Button(webElement));
        }
        return openDayButton == null;
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
