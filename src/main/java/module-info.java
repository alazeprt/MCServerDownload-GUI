module com.alazeprt {
    requires javafx.base;
    requires javafx.controls;
    requires org.jsoup;
    requires com.google.gson;
    requires javafx.fxml;

    opens com.alazeprt to javafx.fxml;
    exports com.alazeprt;
    exports com.alazeprt.http.utils;
    opens com.alazeprt.http.utils to javafx.fxml;
    exports com.alazeprt.http.servers;
    opens com.alazeprt.http.servers to javafx.fxml;
    exports com.alazeprt.servers;
    opens com.alazeprt.servers to javafx.fxml;
    exports com.alazeprt.http.servers.utils;
    opens com.alazeprt.http.servers.utils to javafx.fxml;
}