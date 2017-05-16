package com.mylab.embot.page.frame;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

public class CaptchaFrame extends HtmlElement {

    @FindBy(className = "recaptcha-checkbox-checkmark")
    private CheckBox notRobotCheckBox;

    public void proveHumaneness() {
        notRobotCheckBox.select();
    }
}
