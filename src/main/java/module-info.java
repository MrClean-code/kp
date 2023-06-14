module com.project.nandsbook2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jsoup;
    requires org.apache.logging.log4j;


    opens com.project to javafx.fxml;
    exports com.project;

    opens com.project.repository to javafx.fxml;
    exports com.project.repository;

    opens com.project.entity to javafx.base;
    exports com.project.entity;

    opens com.project.db to javafx.fxml;
    exports com.project.db;
}