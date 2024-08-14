module cen4010.g8.workloadscheduler {
    requires javafx.controls;
    requires javafx.fxml;


    opens cen4010.g8.workloadscheduler to javafx.fxml;
    exports cen4010.g8.workloadscheduler;
}