package com.mylab.embot.runner;

import com.mylab.embot.scenario.RegistrationScenario;
import com.mylab.embot.scenario.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class AbstractRunner {

    @Autowired
    protected Scenario registrationScenario;

    private static ConfigurableApplicationContext context;

    protected static Scenario getRegistrationScenarioFromContext() {
        context = new ClassPathXmlApplicationContext("config.xml");
        context.registerShutdownHook();
        return context.getBean("registrationScenario", RegistrationScenario.class);
    }

    protected static void closeSpringContext() {
        context.close();
    }
}
