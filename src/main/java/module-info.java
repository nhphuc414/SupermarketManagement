module com.ktpm.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires org.controlsfx.controls;
    
    opens com.ktpm.app to javafx.fxml;
    exports com.ktpm.app;
    exports com.ktpm.pojo;
    exports com.ktpm.services;
    exports com.ktpm.services.impl;
    exports com.ktpm.utils;
}
