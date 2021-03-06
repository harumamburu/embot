package com.mylab.embot.skype;

import fr.delthas.skype.Presence;
import fr.delthas.skype.Skype;

import java.io.IOException;

public class SkypeClient {

    private final Skype skype;

    public SkypeClient(String username, String password) {
        skype = new Skype(username, password);
    }

    public void connectSkype() {
        try {
            skype.connect(Presence.HIDDEN);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendGroupNotification(String groupName, String notification) {
        skype.getGroups().stream()
                .filter(group -> group.getTopic().equals(groupName))
                .findFirst()
                .ifPresent(group -> group.sendMessage(notification));
    }

    public void disconnectSkype() {
        skype.disconnect();
    }
}
