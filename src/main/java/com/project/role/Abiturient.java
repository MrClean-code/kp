package com.project.role;

import com.project.entity.Education;
import com.project.mediator.Colleague;
import com.project.mediator.Mediator;

import java.util.List;

public class Abiturient extends Colleague {
    public Abiturient(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void notifyColleague(List<Education> message) {
        mediator.setView(message);
    }

    @Override
    public void receive(List<Education> message) {
        super.receive(message);
    }

    @Override
    public List<Education> getReceivedMessage() {
        return super.getReceivedMessage();
    }
}
