package com.mylab.embot.page;

import com.mylab.embot.driver.CustomChromeDriver;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

abstract class Page {

    final CustomChromeDriver driver;

    Page(CustomChromeDriver driver) {
        HtmlElementLoader.populatePageObject(this, driver);
        this.driver = driver;
    }
}
