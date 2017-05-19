package com.mylab.embot.driver;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class CustomChromeDriver extends ChromeDriver {

    private static final Logger LOGGER = LogManager.getLogger(CustomChromeDriver.class.getName());

    @Value("${driver.reload.attempts:3}")
    private int attemptsToLoad;

    @Value("${driver.chrome.emptypage.address:chrome://newtab}")
    private String emptyPageAddress;
    private long elementWaitTimeoutSeconds = 6;

    public CustomChromeDriver() {
    }

    public CustomChromeDriver(Capabilities capabilities) {
        super(capabilities);
    }

    public CustomChromeDriver(ChromeOptions options) {
        super(options);
    }

    public long getElementWaitTimeoutSeconds() {
        return elementWaitTimeoutSeconds;
    }

    public void setElementWaitTimeoutSeconds(long elementWaitTimeoutSeconds) {
        this.elementWaitTimeoutSeconds = elementWaitTimeoutSeconds;
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

    public void getScreenshot() {
        try {
            FileUtils.copyFile(getScreenshotAs(OutputType.FILE),
                    new File(String.format("./screenshots/%1$tm.%1$td.%1$ty_%1$tH-%1$tM-%1$tS.png", new Date())));
        } catch (IOException e) {
            LOGGER.error("Failed to make a screenshot", e);
        }
    }

    public void setPageLoadTimeout(long value, TimeUnit timeUnit) {
        manage().timeouts().pageLoadTimeout(value, timeUnit);
    }

    public void waitForVisibilityOf(WebElement element) {
        waitForVisibilityOf(element, 1);
    }

    public void waitForVisibilityOf(WebElement element, int attempt) {
        while (attempt <= attemptsToLoad) {
            try {
                new WebDriverWait(this, elementWaitTimeoutSeconds).until(ExpectedConditions.visibilityOf(element));
            } catch (Exception e) {
                navigate().refresh();
                waitForVisibilityOf(element, ++attempt);
            }
        }
        throw new NoSuchElementException(
                String.format("Wasn't able to find %s in %d %d sec attempts",
                        element.toString(), elementWaitTimeoutSeconds, attemptsToLoad));
    }

    @PreDestroy
    public void shutDown() {
        quit();
    }
}
