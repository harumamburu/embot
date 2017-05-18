package com.mylab.embot.runner;

public class AsProgramRunner extends AbstractRunner {

    public static void main(String[] args) {
        getRegistrationScenarioFromContext().run();
        closeSpringContext();
    }
}
