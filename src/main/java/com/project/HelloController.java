package com.project;

import com.project.entity.Education;
import com.project.mediator.Colleague;
import com.project.mediator.Mediator;
import com.project.parse.ParseEducation;
import com.project.repository.CrudDao;
import com.project.repository.CrudDaoImpl;
import com.project.role.Developer;
import com.project.role.Abiturient;
import com.project.role.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.net.URL;
import java.util.*;


//Разработка ПС Навигатор абитуриента, цель интерактивный справочник по специальностям
// и направлениям подготовки (пример с возможными данными https://cchgeu.ru/education/programms)
// + поиск по среднему баллу, названиям дисциплин, и пр.

public class HelloController implements Initializable, Mediator {
    @FXML
    private HBox parentHBox;
    @FXML
    private TextField role;  // роль
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
    private Colleague currentcolleague;
    private static final Logger logger = LogManager.getLogger(HelloController.class);
    private Map<String, Colleague> mapMembers = new HashMap<>();

    private ListView<String> listView  = new ListView<>();

    public HelloController() {
        mapMembers.put("Разработчик", new Developer(this));
        mapMembers.put("Абитуриент", new Abiturient(this));
        mapMembers.put("Преподаватель", new Teacher(this));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        createTable();
        logger.info("Начало работы");
    }

    @FXML
    public void onUpdateDataButton() {
        currentcolleague = mapMembers.get(role.getText());
        parseEducation = new ParseEducation();
        List<Education> listNewData = parseEducation.parse();
        List<Education> listDB = crudDao.getData();

        System.out.println("listNewData " + listNewData.size());
        System.out.println("listDB " + listDB.size());

        if (listNewData.size() == listDB.size()) {
            checkEqualsList(listDB, listNewData);
            crudDao.updateDate(listUpdate);
            logger.info("база обновлена, кол-во записей не изменилось, поля изменены ");
        } else if (listNewData.size() > listDB.size()) {
            addEducation(listDB, listNewData);
            crudDao.saveData(listAdd);
            logger.info("в базу добавлены записи");
        } else if (listNewData.size() < listDB.size()) {
            deleteEducation(listDB, listNewData);
            crudDao.deleteData(listDelete);
            logger.info("из базы удалены записи");
        }

        if (listUpdate != null) {
            System.out.println("Количество записей не поменялось, изменились их поля");
        } else {
            System.out.println("Количество записей изменилось");
        }

        currentcolleague.receive(crudDao.getData());
        currentcolleague.notifyColleague(currentcolleague.getReceivedMessage());

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
        currentcolleague = mapMembers.get(role.getText());
        List<Education> educationList = crudDao.getData(); // показывает таблицу по дефолту

        checkError();

        if (!specializationField.getText().isEmpty()) {
            educationList = choose.realizeEducationSpecialization(specializationField.getText(), middleScoreDiploma.getText(),
                    middleScoreExam.getText(), role.getText());
        } else if (!middleScoreDiploma.getText().isEmpty()) {
            educationList = choose.realizeEducationMiddleScoreDiploma(middleScoreDiploma.getText(), role.getText());
            logger.info(role.getText() + " поиск по баллам спо " + middleScoreDiploma.getText());
        } else if (!middleScoreExam.getText().isEmpty()) {
            educationList = choose.realizeEducationMiddleScoreExam(middleScoreExam.getText(), role.getText());
            logger.info(role.getText() + " поиск по баллам егэ " + middleScoreExam.getText());
        }

        currentcolleague.receive(educationList);
        currentcolleague.notifyColleague(currentcolleague.getReceivedMessage());

        educationObservableList = FXCollections.observableList(educationList);
        tableView.setItems(educationObservableList);
    }

    private void checkError() {
        if (!middleScoreDiploma.getText().isEmpty() && !middleScoreExam.getText().isEmpty()) {
            showError("Нельзя задавать средний балл по аттестату и экзаменам. Выберите одно! ");
            logger.error("ошибка при вводе 3 полей");
        }
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

//        первоначальное заполнение б.д
//        List<Education> educationList = parseEducation.parse();
//        crudDao.save(educationList);
    }

    @Override
    public void setView(List<Education> message) {
        createTable();
        parentHBox.getChildren().remove(listView);
    }

    @Override
    public void setViewL(List<String> message) {
        parentHBox.getChildren().remove(tableView);
        listView.setPrefWidth(750);

        ObservableList<String> items = FXCollections.observableArrayList(message);
        listView.setItems(items);

        parentHBox.getChildren().add(listView);
    }
}