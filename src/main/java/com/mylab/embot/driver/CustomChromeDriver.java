package com.mylab.embot.driver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class CustomChromeDriver extends ChromeDriver {

    @Value("${driver.reload.attempts:3}")
    private int attemptsToLoad;

    @Value("${driver.chrome.emptypage.address:chrome://newtab}")
    private String emptyPageAddress;

    public CustomChromeDriver() {
    }

    public CustomChromeDriver(Capabilities capabilities) {
        super(capabilities);
    }

    public CustomChromeDriver(ChromeOptions options) {
        super(options);
    }

    @Override
    public void get(String url) {
        Optional<Throwable> result = get(url, 1);
        result.ifPresent(throwable -> { throw new RuntimeException(throwable); });
    }

    public void navigateToEmptyPage() {
        navigate().to(emptyPageAddress);
    }

    private Optional<Throwable> get(String url, int attempt) {
        while (attempt <= attemptsToLoad) {
            try {
                super.get(url);
                return Optional.empty();
            } catch (Exception e) {
                if (attempt == attemptsToLoad) {
                    return Optional.of(e);
                } else {
                    return get(url, ++attempt);
                }
            }
        }
        return Optional.of(new TimeoutException(
                String.format("Wasn't able to load {%s} in %d attempts", url, attemptsToLoad)));
    }

    @Override
    public WebElement findElement(By by) {
        return findElement(by, 1);
    }

    private WebElement findElement(By by, int attempt) {
        while (attempt <= attemptsToLoad) {
            try {
                return super.findElement(by);
            } catch (NoSuchElementException e) {
                navigate().refresh();
                return findElement(by, ++attempt);
            }
        }
        throw new NoSuchElementException(
                String.format("Wasn't able to find %s in %d attempts", by.toString(), attemptsToLoad));
    }

    public void getScreenshot() {
        try {
            FileUtils.copyFile(getScreenshotAs(OutputType.FILE),
                    new File(String.format("./screenshots/%1$tm.%1$td.%1$ty_%1$tH-%1$tM-%1$tS.png", new Date())));
        } catch (IOException e) {
            // TODO replace with logging
            e.printStackTrace();
        }
    }

    public void setPageLoadTimeout(long value, TimeUnit timeUnit) {
        manage().timeouts().pageLoadTimeout(value, timeUnit);
    }

    public void setImplicitWait(long value, TimeUnit timeUnit) {
        manage().timeouts().implicitlyWait(value, timeUnit);
    }

    @PreDestroy
    public void shutDown() {
        quit();
    }
}
