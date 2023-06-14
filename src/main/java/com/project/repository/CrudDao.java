package com.project.repository;

import com.project.entity.Education;

import java.util.List;

public interface CrudDao {
    void updateDate(List<Education> listUpdate);
    void saveData(List<Education> listAdd);
    List<Education> getData();
    void deleteData(List<Education> listDelete);
}
