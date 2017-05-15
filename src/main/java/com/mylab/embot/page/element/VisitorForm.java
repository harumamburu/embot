package com.mylab.embot.page.element;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.TextInput;

public class VisitorForm extends HtmlElement {

    @FindBy(xpath = "//input[contains(@id, 'visitCategory')]")
    private TextInput nameInput;

    @FindBy(xpath = "//input[contains(@id, 'embassy')]")
    private TextInput surnameInput;

    @FindBy(xpath = "//input[contains(@id, 'visitorsCount')]")
    private TextInput emailInput;

    @FindBy(xpath = "//input[contains(@id, 'visitDatetime')]")
    private TextInput passId;

    @FindBy(xpath = "//select[@name='undefined']")
    private Select representationSelect;
}
