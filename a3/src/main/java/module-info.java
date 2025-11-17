module ca.utoronto.utm.paint {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires javafx.base;
    requires javafx.graphics;


    opens ca.utoronto.utm.paint to javafx.fxml;
    exports ca.utoronto.utm.paint;
    exports ca.utoronto.utm.paint.model;
    opens ca.utoronto.utm.paint.model to javafx.fxml;
    exports ca.utoronto.utm.paint.view;
    opens ca.utoronto.utm.paint.view to javafx.fxml;
    exports ca.utoronto.utm.paint.controller;
    opens ca.utoronto.utm.paint.controller to javafx.fxml;
    exports ca.utoronto.utm.paint.persistence;
    opens ca.utoronto.utm.paint.persistence to javafx.fxml;
}