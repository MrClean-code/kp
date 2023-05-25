package com.project;

import com.project.entity.Education;
import com.project.parse.ParseEducation;
import com.project.repository.CrudDao;
import com.project.repository.CrudDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//Разработка ПС Навигатор абитуриента, цель интерактивный справочник по специальностям
// и направлениям подготовки (пример с возможными данными https://cchgeu.ru/education/programms)
// + поиск по среднему баллу, названиям дисциплин, и пр.

public class HelloController implements Initializable {
    @FXML
    private TextField specializationField;  // название направления
    @FXML
    private TextField middleScoreExam;  // средняя оценка по экзамену у бакалавра и магистратуры (int)
    @FXML
    private TextField middleScoreDiploma;  // средняя оценка по диплому у спо (float)
    @FXML
    public TableView tableView;
    private ObservableList<Education> educationObservableList;
    private Choose choose = new Choose();
    private CrudDao crudDao = new CrudDaoImpl();
    private ParseEducation parseEducation;
    private List<Education> listUpdate;
    private List<Education> listAdd;
    private List<Education> listDelete;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTable();
    }

    @FXML
    public void onUpdateDataButton() {
        parseEducation = new ParseEducation();
        List<Education> listNewData = parseEducation.parse();
        List<Education> listDB = crudDao.getData();

        System.out.println("listNewData " + listNewData.size());
        System.out.println("listDB " + listDB.size());

        if (listNewData.size() == listDB.size()) {
            checkEqualsList(listDB, listNewData);
            crudDao.updateDate(listUpdate);
        } else if (listNewData.size() > listDB.size()) {
            addEducation(listDB, listNewData);
            crudDao.saveData(listAdd);
        } else if (listNewData.size() < listDB.size()) {
            deleteEducation(listDB, listNewData);
            crudDao.deleteData(listDelete);
        }

        if (listUpdate != null) {
            System.out.println("Количество записей не поменялось, изменились их поля");
        } else {
            System.out.println("Количество записей изменилось");
        }

        educationObservableList = FXCollections.observableList(crudDao.getData());
        tableView.setItems(educationObservableList);
    }
// DB 385    NewData 384
    private List<Education> deleteEducation(List<Education> listDB, List<Education> listNewData) {
        listDelete = new ArrayList<>();
        for (int i = listNewData.size()+1; i <= listDB.size(); i++) {
            listDelete.add(listDB.get(i-1));
        }
        return listDelete;
    }
// Example: DB 380  ->  NewData385
    private List<Education> addEducation(List<Education> listDB, List<Education> listNewData) {
        listAdd = new ArrayList<>();
        for (int i = listDB.size()+1; i <= listNewData.size(); i++) {
            listAdd.add(listNewData.get(i-1));
        }
        return listAdd;
    }

    private List<Education> checkEqualsList(List<Education> listDB, List<Education> listNewData) {
        listUpdate = new ArrayList<>();
        for (int i = 0; i < listDB.size(); i++) {
            if (!listNewData.get(i).equals(listDB.get(i))) {
                listUpdate.add(listNewData.get(i));
            }
        }
        return listUpdate;
    }

    @FXML
    public void onFindDataButton() {
        List<Education> educationList = crudDao.getData(); // показывает таблицу по дефолту

        checkError();

        if (!specializationField.getText().isEmpty()) {
            educationList = choose.realizeEducationSpecialization(specializationField.getText(), middleScoreDiploma.getText(),
                    middleScoreExam.getText());
        } else if (!middleScoreDiploma.getText().isEmpty()) {
            educationList = choose.realizeEducationMiddleScoreDiploma(middleScoreDiploma.getText());
        } else if (!middleScoreExam.getText().isEmpty()) {
            educationList = choose.realizeEducationMiddleScoreExam(middleScoreExam.getText());
        }

        educationObservableList = FXCollections.observableList(educationList);
        tableView.setItems(educationObservableList);
    }

    private void checkError() {
        if (!middleScoreDiploma.getText().isEmpty() && !middleScoreExam.getText().isEmpty()) {
            showError("Нельзя задавать средний балл по аттестату и экзаменам. Выберите одно! ");
        }
//        else if (middleScoreDiploma.getText().isEmpty() && middleScoreExam.getText().isEmpty() &&
//                specializationField.getText().isEmpty()) {
//            showError("Все поля пустые");
//        }
    }

    private void showError(String e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error alert");
        alert.setHeaderText(e);
        alert.showAndWait();
    }

    private void createTable() {
        TableColumn Col0 = new TableColumn("№");
        Col0.setMinWidth(5);
        Col0.setCellValueFactory(new PropertyValueFactory<Education, Integer>("id"));

        TableColumn Col1 = new TableColumn("Код");
        Col1.setMinWidth(15);
        Col1.setCellValueFactory(new PropertyValueFactory<Education, String>("code"));

        TableColumn Col2 = new TableColumn("Факультет");
        Col2.setMinWidth(30);
        Col2.setCellValueFactory(new PropertyValueFactory<Education, String>("faculty"));

        TableColumn Col3 = new TableColumn("Уровень образования");
        Col3.setMinWidth(100);
        Col3.setCellValueFactory(new PropertyValueFactory<Education, String>("level"));

        TableColumn Col4 = new TableColumn("Название образовательной программы");
        Col4.setMinWidth(150);
        Col4.setCellValueFactory(new PropertyValueFactory<Education, String>("specialization"));

        TableColumn Col5 = new TableColumn("Ср. балл за экзамен");
        Col5.setMinWidth(150);
        Col5.setCellValueFactory(new PropertyValueFactory<Education, String>("middleScoreExam"));

        TableColumn Col6 = new TableColumn("Минимальный балл по диплому");
        Col6.setMinWidth(150);
        Col6.setCellValueFactory(new PropertyValueFactory<Education, String>("middleScoreDiploma"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(Col0, Col1, Col2, Col3, Col4, Col5, Col6);

        educationObservableList = FXCollections.observableList(crudDao.getData());
        tableView.setItems(educationObservableList);

        // первоначальное заполнение б.д
//        List<Education> educationList = parseEducation.parse();
//        crudDao.save(educationList);
    }
}