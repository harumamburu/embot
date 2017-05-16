package com.mylab.embot.page.element;

import org.springframework.beans.factory.annotation.Value;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

public class Calendar extends HtmlElement {

    @Value("element.calendar.locator://div[@id='ui-datepicker-div']")
    private String calendarLocator;

    @Value("element.calendar.locator://td[not(contains(@class, 'unselectable'))]")
    private String openDayLocator;

    public String getCalendarLocator() {
        return calendarLocator;
    }

    public String getOpenDayLocator() {
        return openDayLocator;
    }
}
