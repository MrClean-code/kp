package com.project.mediator;

import com.project.entity.Education;
import com.project.repository.CrudDao;

import java.util.List;

public interface Mediator {
    default void notifyColleague(Colleague colleague, CrudDao message) {}
    void setView(List<Education> message);
    void setViewL(List<String> message);
}