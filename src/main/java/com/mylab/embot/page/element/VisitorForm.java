package com.mylab.embot.page.element;

import com.mylab.embot.entity.User;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Select;
import ru.yandex.qatools.htmlelements.element.TextInput;

import java.util.Optional;

@FindBy(xpath = ".//div[contains(@id, 'visitors_')]")
public class VisitorForm extends HtmlElement {

    @FindBy(xpath = ".//input[contains(@id, 'firstname')]")
    private TextInput nameInput;

    @FindBy(xpath = ".//input[contains(@id, 'lastname')]")
    private TextInput lastnameInput;

    @FindBy(xpath = ".//input[contains(@id, 'phone')]")
    private TextInput phoneInput;

    @FindBy(xpath = ".//input[contains(@id, 'email')]")
    private TextInput emailInput;

    @FindBy(xpath = ".//input[contains(@id, 'document')]")
    private TextInput passId;

    @FindBy(xpath = ".//select[contains(@id, 'representation')]")
    private Select representationSelect;

    public void fillInUserInfo(User user) {
        nameInput.sendKeys(user.getName());
        lastnameInput.sendKeys(user.getLastName());
        Optional.ofNullable(user.getPhoneNumber()).ifPresent(number -> phoneInput.sendKeys(number));
        emailInput.sendKeys(user.getEmail());
        passId.sendKeys(user.getPassId());
        representationSelect.selectByValue(String.valueOf(user.getRepresentation().getValueIndex()));
    }
}
