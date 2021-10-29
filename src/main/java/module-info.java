module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.json;
    requires javafx.media;

    opens com.example.javafx to javafx.fxml;
    opens com.example.solution;
    exports com.example.javafx;
}
