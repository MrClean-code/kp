package com.project;

import com.project.entity.Education;
import com.project.repository.EducationDao;
import com.project.repository.EducationDaoImpl;

import java.util.ArrayList;
import java.util.List;

public class Choose {
    private List<Education> educationList;
    private EducationDao educationDao;

    public Choose() {
        educationList = new ArrayList<>();
    }

    public List<Education> realizeEducationSpecialization(String specializationField, String middleScoreDiploma, String middleScoreExam) {
        if (!specializationField.isEmpty() && !middleScoreExam.isEmpty()) {
            educationDao = new EducationDaoImpl();
            educationList = educationDao.findDataTwoParam(specializationField, middleScoreExam);
            return educationList;
        } else if (!specializationField.isEmpty() && !middleScoreDiploma.isEmpty()) {
            educationDao = new EducationDaoImpl();
            educationList = educationDao.findDataTwoParam(specializationField, middleScoreDiploma);
            return educationList;
        } else if (!specializationField.isEmpty()) {
            educationDao = new EducationDaoImpl();
            educationList = educationDao.findDataOneParam(specializationField);
            return educationList;
        } else {
            return (List<Education>) new RuntimeException("ошибка метод realizeEducation");
        }
    }

    public List<Education> realizeEducationMiddleScoreExam(String middleScoreExam) {
        educationDao = new EducationDaoImpl();
        educationList = educationDao.findDataOneParam(middleScoreExam);
        return educationList;
    }

    public List<Education> realizeEducationMiddleScoreDiploma(String middleScoreDiploma) {
        educationDao = new EducationDaoImpl();
        educationList = educationDao.findDataOneParam(middleScoreDiploma);
        return educationList;
    }
}