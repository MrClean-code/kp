package com.project.mediator;


import com.project.entity.Education;

import java.util.ArrayList;
import java.util.List;


public abstract class Colleague {
    protected Mediator mediator;
    private List<Education> receivedMessage;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }

    public abstract void notifyColleague(List<Education> message);

    public void receive(List<Education> message) {
        this.receivedMessage = message;
    }

    public List<Education> getReceivedMessage() {
        return this.receivedMessage;
    }
}
