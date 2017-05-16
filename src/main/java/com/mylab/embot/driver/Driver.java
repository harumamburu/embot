package com.mylab.embot.driver;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PreDestroy;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Driver<T extends WebDriver> extends RemoteWebDriver {

    private final T driver;

    @Value("${driver.reload.attempts:3}")
    private int attemptsToLoad;

    public Driver(T driver) {
        this.driver = driver;
    }

    public T getWrappedDriver() {
        return driver;
    }

    @Override
    public void get(String url) {
        Optional<Throwable> result = get(url, 1);
        result.ifPresent(throwable -> { throw new RuntimeException(throwable); });
    }

    private Optional<Throwable> get(String url, int attempt) {
        while (attempt <= attemptsToLoad) {
            try {
                driver.get(url);
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
                String.format("Wasn't able to load %s in %d attempts", url, attemptsToLoad)));
    }

    public void setPageLoadTimeout(long value, TimeUnit timeUnit) {
        driver.manage().timeouts().pageLoadTimeout(value, timeUnit);
    }

    public void setImplicitWait(long value, TimeUnit timeUnit) {
        driver.manage().timeouts().implicitlyWait(value, timeUnit);
    }

    @PreDestroy
    public void quit() {
        driver.quit();
    }
}
