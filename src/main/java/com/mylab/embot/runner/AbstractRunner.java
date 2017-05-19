package com.mylab.embot.runner;

import com.mylab.embot.scenario.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Optional;

public abstract class AbstractRunner {

    @Autowired
    protected Scenario registrationScenario;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = null;
        try {
            context = new ClassPathXmlApplicationContext("config.xml");
            context.registerShutdownHook();


            context.getBean("runner", AbstractRunner.class).runScenario();
        } finally {
            Optional.ofNullable(context).ifPresent(cont -> cont.close());
        }
    }

    protected abstract void runScenario();
}
