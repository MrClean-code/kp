package com.project;

import com.project.entity.Education;
import com.project.repository.EducationDao;
import com.project.repository.EducationDaoImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Choose {
    private List<Education> educationList;
    private EducationDao educationDao;
    private static final Logger logger = LogManager.getLogger(Choose.class);



    public Choose() {
        educationList = new ArrayList<>();
    }

    public List<Education> realizeEducationSpecialization(String specializationField, String middleScoreDiploma,
                                                          String middleScoreExam, String role) {
        if (!specializationField.isEmpty() && !middleScoreExam.isEmpty()) {
            educationDao = new EducationDaoImpl();
            educationList = educationDao.findDataTwoParam(specializationField, middleScoreExam);
            logger.info(role + " поиск по специальности и баллу егэ " + specializationField + " " + middleScoreExam);
            return educationList;
        } else if (!specializationField.isEmpty() && !middleScoreDiploma.isEmpty()) {
            educationDao = new EducationDaoImpl();
            educationList = educationDao.findDataTwoParam(specializationField, middleScoreDiploma);
            logger.info(role + " поиск по специальности и баллу спо " + specializationField + " " + middleScoreDiploma);
            return educationList;
        } else if (!specializationField.isEmpty()) {
            educationDao = new EducationDaoImpl();
            educationList = educationDao.findDataOneParam(specializationField);
            logger.info(role + " поиск по специальности " + specializationField);
            return educationList;
        } else {
            return (List<Education>) new RuntimeException("ошибка метод realizeEducation");
        }
    }

    public List<Education> realizeEducationMiddleScoreExam(String middleScoreExam, String role) {
        educationDao = new EducationDaoImpl();
        educationList = educationDao.findDataOneParam(middleScoreExam);
        logger.info(role + " поиск по баллу за егэ " + middleScoreExam);
        return educationList;
    }

    public List<Education> realizeEducationMiddleScoreDiploma(String middleScoreDiploma, String role) {
        educationDao = new EducationDaoImpl();
        educationList = educationDao.findDataOneParam(middleScoreDiploma);
        logger.info(role + " поиск по баллу за диплом " + middleScoreDiploma);
        return educationList;
    }
}