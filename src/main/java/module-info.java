module Steam.workshop.showcase.tool {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    opens workshopshowcasetool to javafx.fxml;
    exports workshopshowcasetool;
}