package com.project.entity;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

/**
 * Класс образовательные программы со свойствами
 * code, specialization, level, faculty, middleScoreExam, middleScoreDiploma.
 * @autor Бабаев Джавид
 * @version 1.1
 */
public class Education {
    /** Уникальный id */
    private SimpleIntegerProperty id;
    /** код специальности */
    private SimpleStringProperty code;
    /** название специальности специальности */
    private SimpleStringProperty specialization;
    /** уровень образования	 */
    private SimpleStringProperty level;
    /** факультет */
    private SimpleStringProperty faculty;
    /** ср. балл для бакалавра и магистратуры */
    private SimpleStringProperty middleScoreExam;
    /** минимальный . балл для спо */
    private SimpleStringProperty middleScoreDiploma;

    public Education(int id, String code, String specialization, String level, String faculty, String middleScoreExam,
                     String middleScoreDiploma) {
        this.id = new SimpleIntegerProperty(id);
        this.code = new SimpleStringProperty(code);
        this.specialization = new SimpleStringProperty(specialization);
        this.level = new SimpleStringProperty(level);
        this.faculty = new SimpleStringProperty(faculty);
        this.middleScoreExam = new SimpleStringProperty(middleScoreExam);
        this.middleScoreDiploma = new SimpleStringProperty(middleScoreDiploma);
    }

    public Education() {
        this.id = new SimpleIntegerProperty(2);
        this.code = new SimpleStringProperty("testCode3");
        this.specialization = new SimpleStringProperty("testSpecialization3");
        this.level = new SimpleStringProperty("testLevel3");
        this.faculty = new SimpleStringProperty("testFaculty3");
        this.middleScoreExam = new SimpleStringProperty("100");
        this.middleScoreDiploma = new SimpleStringProperty("testD3");
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getCode() {
        return code.get();
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getSpecialization() {
        return specialization.get();
    }

    public SimpleStringProperty specializationProperty() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization.set(specialization);
    }

    public String getLevel() {
        return level.get();
    }

    public SimpleStringProperty levelProperty() {
        return level;
    }

    public void setLevel(String level) {
        this.level.set(level);
    }

    public String getFaculty() {
        return faculty.get();
    }

    public SimpleStringProperty facultyProperty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty.set(faculty);
    }

    public String getMiddleScoreExam() {
        return middleScoreExam.get();
    }

    public SimpleStringProperty middleScoreExamProperty() {
        return middleScoreExam;
    }

    public void setMiddleScoreExam(String middleScoreExam) {
        this.middleScoreExam.set(middleScoreExam);
    }

    public String getMiddleScoreDiploma() {
        return middleScoreDiploma.get();
    }

    public SimpleStringProperty middleScoreDiplomaProperty() {
        return middleScoreDiploma;
    }

    public void setMiddleScoreDiploma(String middleScoreDiploma) {
        this.middleScoreDiploma.set(middleScoreDiploma);
    }

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id +
                ", code=" + code +
                ", specialization=" + specialization +
                ", level=" + level +
                ", faculty=" + faculty +
                ", middleScoreExam=" + middleScoreExam +
                ", middleScoreDiploma=" + middleScoreDiploma +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Education education = (Education) o;
        return Objects.equals(id, education.id) && Objects.equals(code, education.code) && Objects.equals(specialization, education.specialization) && Objects.equals(level, education.level) && Objects.equals(faculty, education.faculty) && Objects.equals(middleScoreExam, education.middleScoreExam) && Objects.equals(middleScoreDiploma, education.middleScoreDiploma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, specialization, level, faculty, middleScoreExam, middleScoreDiploma);
    }
}
