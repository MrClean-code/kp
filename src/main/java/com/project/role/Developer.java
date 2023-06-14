package com.project.role;

import com.project.HelloController;
import com.project.entity.Education;
import com.project.mediator.Colleague;
import com.project.mediator.Mediator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Developer extends Colleague {

    private static final Logger logger = LogManager.getLogger(HelloController.class);

    public Developer(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void notifyColleague(List<Education> message) {
        List<String> list = new ArrayList<>();
        String logFilePath = "C:\\Users\\nuran\\OneDrive\\Рабочий стол\\intJava\\nandsbook2\\app.log";

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediator.setViewL(list);
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
