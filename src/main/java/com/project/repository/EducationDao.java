package com.project.repository;

import com.project.entity.Education;

import java.util.List;

public interface EducationDao {

    List<Education> findDataTwoParam(String param1, String param2);

    List<Education> findDataOneParam(String param1);
}
