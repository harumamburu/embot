package com.mylab.embot.runner;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AsDaemonRunner extends AbstractRunner {

    @Value("${scenario.timer.repeatin.min}")
    private long periodInMinutes;

    @Override
    protected void runScenario() {
        Timer timer = new Timer(true);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                registrationScenario.run();
            }
        }, new Date(),periodInMinutes * 60 * 1000);

        while (true) {
        }
    }

}
