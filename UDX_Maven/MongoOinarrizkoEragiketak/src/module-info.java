module MongoDBProiektua {
    requires javafx.fxml;
    requires javafx.controls;

    opens MongoDBProiektua to javafx.graphics;

    exports MongoDBProiektua;
}