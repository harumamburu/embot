package com.mylab.embot.page;

import com.mylab.embot.driver.Driver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;

abstract class Page {

    final Driver driver;

    Page(Driver driver) {
        PageFactory.initElements(new HtmlElementDecorator(driver), this);
        this.driver = driver;
    }
}
