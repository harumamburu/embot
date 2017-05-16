package com.mylab.embot.driver;

public class ChromeOptions extends org.openqa.selenium.chrome.ChromeOptions {

    public void setArguments(String... arguments) {
        super.addArguments(arguments);
    }
}
