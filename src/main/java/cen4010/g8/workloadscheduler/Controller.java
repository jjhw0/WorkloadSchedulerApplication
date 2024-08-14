package cen4010.g8.workloadscheduler;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.*;
import java.util.*;

public class Controller {
    @FXML
    private Pane schedulePane;
    @FXML
    private Pane workBlockPane;
    @FXML
    private Pane assignmentPane;
    @FXML
    private Pane editAssignmentPane;
    @FXML
    private Pane editWorkBlockPane;
    @FXML
    private Label workblockWarningLabel;
    @FXML
    private Label assignmentWarningLabel;
    @FXML
    private TextField assignmentNameTextField;
    @FXML
    private TextField estimatedCompletetionTextField;
    @FXML
    private DatePicker workblockDatePicker;
    @FXML
    private DatePicker assignmentDatePicker;
    @FXML
    private TextArea schedulerTextArea;
    @FXML
    private ComboBox<String> assignmentHour;
    @FXML
    private ComboBox<String> assignmentMinute;
    @FXML
    private ComboBox<String> assignmentAMorPM;
    @FXML
    private ComboBox<String> workBlockStartHour;
    @FXML
    private ComboBox<String> workBlockStartMinute;
    @FXML
    private ComboBox<String> workBlockEndHour;
    @FXML
    private ComboBox<String> workBlockEndMinute;
    @FXML
    private ComboBox<String> workBlockStartAMorPM;
    @FXML
    private ComboBox<String> workBlockEndAMorPM;
    @FXML
    private TextField workBlockNameTextField;

    @FXML
    private CheckBox repeatedWorkBlock;

    @FXML
    private DatePicker repeatUntilDate;
    @FXML
    private ChoiceBox assignmentsPicker;
    @FXML
    private ChoiceBox workBlocksPicker;
    @FXML
    private Button cancelAssignment;
    @FXML
    private Button cancelWorkblock;

    @FXML
    protected void onStartButtonClick(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Scheduler.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 1000, 570);
        stage.setScene(scene);
        stage.show();

        //IDK how to make this work and show the schedule on startup :P
        //schedulerTextArea.setText(Scheduler.getScheduleString());
    }

    @FXML
    protected void onAddAssignmentButtonClick(ActionEvent event) throws IOException {
        schedulePane.setVisible(false);
        editAssignmentPane.setVisible(false);
        assignmentPane.setVisible(true);
    }

    @FXML
    protected void onAddWorkblockButtonClick(ActionEvent event) throws IOException {
        schedulePane.setVisible(false);
        editWorkBlockPane.setVisible(false);
        workBlockPane.setVisible(true);
        repeatedWorkBlock.setVisible(true);
    }

    @FXML
    protected void onEditWorkblockButtonClick(ActionEvent event) throws IOException {
        schedulePane.setVisible(false);
        ArrayList<WorkBlock> workBlockList = Scheduler.getSchedule().getWorkBlocks();
        ArrayList<String> workBlocks = new ArrayList<>();
        for (WorkBlock wb: workBlockList) {
            workBlocks.add(wb.toString());
        }
        workBlocksPicker.getItems().clear();
        workBlocksPicker.getItems().addAll(workBlocks);
        editWorkBlockPane.setVisible(true);
    }

    @FXML
    protected void onEditAssignmentButtonClick(ActionEvent event) throws IOException {
        schedulePane.setVisible(false);
        ArrayList<WorkBlock> workBlocks = Scheduler.getSchedule().getWorkBlocks();
        ArrayList<String> assignments = new ArrayList<>();
        for(WorkBlock wb: workBlocks) {
            if(wb.getAssignment().getDueDate() == null) {   /*nothing*/   }
            else {   assignments.add(wb.toString());   }
        }
        assignmentsPicker.getItems().clear();
        assignmentsPicker.getItems().addAll(assignments);
        editAssignmentPane.setVisible(true);
    }

    @FXML
    protected void onDeleteAssignment(ActionEvent event) throws IOException {
        //System.out.println(assignmentsPicker.getValue().toString());
        if (assignmentsPicker.getValue() != null) {
            String temp = assignmentsPicker.getValue().toString().split(":")[1];
            //System.out.println(temp);
            int id = Integer.parseInt(temp.substring(0, temp.indexOf(")")));
            WorkBlock wb = Scheduler.getSchedule().getWorkBlock(id);
            Scheduler.getSchedule().removeWorkBlock(wb);
            assignmentsPicker.getItems().remove(assignmentsPicker.getValue());
            Scheduler.saveSchedule();
            schedulerTextArea.setText(Scheduler.getScheduleString());
            assignmentsPicker.setValue(null);
        }
    }

    @FXML
    protected void onDeleteWorkBlock(ActionEvent event) throws IOException {
        //System.out.println(workBlocksPicker.getValue().toString());
        if (workBlocksPicker.getValue() != null) {
            String temp = workBlocksPicker.getValue().toString().split(":")[1];
            //System.out.println(temp);
            int id = Integer.parseInt(temp.substring(0, temp.indexOf(")")));
            WorkBlock wb = Scheduler.getSchedule().getWorkBlock(id);
            Scheduler.getSchedule().removeWorkBlock(wb);
            workBlocksPicker.getItems().remove(workBlocksPicker.getValue());
            Scheduler.saveSchedule();
            schedulerTextArea.setText(Scheduler.getScheduleString());
            workBlocksPicker.setValue(null);
        }
    }

    @FXML
    protected void onEditAssignment(ActionEvent event) throws IOException {
        String temp = assignmentsPicker.getValue().toString().split(":")[1];
        int id = Integer.parseInt(temp.substring(0, temp.indexOf(")")));
        WorkBlock wb = Scheduler.getSchedule().getWorkBlock(id);

        Assignment assignment = wb.getAssignment();
        assignmentNameTextField.setText(assignment.getName());
        LocalDate date = assignment.getDueDate().toLocalDate();
        LocalTime time = assignment.getDueDate().toLocalTime();
        assignmentDatePicker.setValue(date);
        if(time.getHour() > 12) {
            assignmentHour.setValue(String.valueOf(time.getHour() - 12));
            assignmentAMorPM.setValue("PM");
        }
        else if(time.getHour() == 12) {
            assignmentAMorPM.setValue("PM");
        }
        else {
            assignmentHour.setValue(String.valueOf(time.getHour()));
            assignmentAMorPM.setValue("AM");
        }
        assignmentMinute.setValue(String.valueOf(time.getMinute()));
        estimatedCompletetionTextField.setText(String.valueOf(assignment.getEstimatedTime()));
        onAddAssignmentButtonClick(event);
    }

    @FXML
    protected void onEditWorkBlock(ActionEvent event) throws IOException {
        String temp = workBlocksPicker.getValue().toString().split(":")[1];
        int id = Integer.parseInt(temp.substring(0, temp.indexOf(")")));
        WorkBlock wb = Scheduler.getSchedule().getWorkBlock(id);

        workBlockNameTextField.setText(wb.getAssignment().getName());
        LocalDate date = wb.getStartTime().toLocalDate();
        LocalTime startTime = wb.getStartTime().toLocalTime();
        LocalTime endTime = wb.getEndTime().toLocalTime();
        workblockDatePicker.setValue(date);
        if(startTime.getHour() > 12) {
            workBlockStartHour.setValue(String.valueOf(startTime.getHour() - 12));
            workBlockStartAMorPM.setValue("PM");
        }
        else if (startTime.getHour() == 12) {
            workBlockStartAMorPM.setValue("PM");
        }
        else {
            workBlockStartHour.setValue(String.valueOf(startTime.getHour()));
            workBlockStartAMorPM.setValue("AM");
        }
        workBlockStartMinute.setValue(String.valueOf(startTime.getMinute()));

        if(endTime.getHour() > 12) {
            workBlockEndHour.setValue(String.valueOf(endTime.getHour() - 12));
            workBlockEndAMorPM.setValue("PM");
        }
        else if (endTime.getHour() == 12) {
            workBlockEndAMorPM.setValue("PM");
        }
        else {
            workBlockEndHour.setValue(String.valueOf(endTime.getHour()));
            workBlockEndAMorPM.setValue("AM");
        }
        workBlockEndMinute.setValue(String.valueOf(endTime.getMinute()));
        onAddWorkblockButtonClick(event);
        repeatedWorkBlock.setSelected(false);
        repeatedWorkBlock.setVisible(false);
    }

    @FXML
    protected  void onReturnSchedule(ActionEvent event) throws IOException {
        assignmentPane.setVisible(false);
        workBlockPane.setVisible(false);
        editAssignmentPane.setVisible(false);
        editWorkBlockPane.setVisible(false);
        schedulePane.setVisible(true);
    }
//    @FXML
//    protected void onCancelWorkblock(ActionEvent event) throws IOException {
//        workBlockPane.setVisible(false);
//        schedulePane.setVisible(true);
//    }
//
//    @FXML
//    protected void onCancelAssignment(ActionEvent event) throws IOException {
//        assignmentPane.setVisible(false);
//        schedulePane.setVisible(true);
//    }

    @FXML
    protected void onAssignmentSubmitButton(ActionEvent event){
        String name;
        LocalDateTime dueDate;
        int estimatedTime;

        try{
            name = assignmentNameTextField.getText();
            LocalDate date = assignmentDatePicker.getValue();
            int dueHour = Integer.parseInt(assignmentHour.getValue());
            int dueMinute = Integer.parseInt(assignmentMinute.getValue());
            LocalTime dueTime = LocalTime.of(dueHour, dueMinute);
            // if user selects PM, increase time 12 hours
            if (assignmentAMorPM.getValue().equals("PM")) {
                dueTime = dueTime.plusHours(12);
            }
            dueDate = date.atTime(dueTime);
            estimatedTime = Integer.parseInt(estimatedCompletetionTextField.getText());

            if (name.isEmpty()) {
                throw new RuntimeException("Name is empty");
            }
            Assignment assignment = new Assignment(name, dueDate, estimatedTime);
            if (!Scheduler.scheduleAssignment(assignment)) {
                throw new DateTimeException("No available timeslot");
            }

            onDeleteAssignment(event);
            Scheduler.saveSchedule();

            assignmentPane.setVisible(false);
            schedulePane.setVisible(true);
            schedulerTextArea.setText(Scheduler.getScheduleString());
            assignmentWarningLabel.setText("");
        }
        catch (DateTimeException e) {
            assignmentWarningLabel.setText(e.getMessage());
        }
        catch(Exception e){
            assignmentWarningLabel.setText("Invalid. Please enter all assignment information before submitting!");
        }
    }

    @FXML
    protected void onRepeatWeekly(ActionEvent event) {
        repeatUntilDate.setVisible(!repeatUntilDate.isVisible());
    }

    @FXML
    protected void onWorkBlockSubmitButton(ActionEvent event) {
        try{
            String name;
            LocalDate date;
            LocalTime startTime;
            LocalTime endTime;
            name = workBlockNameTextField.getText();
            date = workblockDatePicker.getValue();

            // error checking
            if (name.isEmpty()) {
                throw new RuntimeException("Name is empty");
            }

            // create start and end dates
            int startHour = Integer.parseInt(workBlockStartHour.getValue());
            int startMinute = Integer.parseInt(workBlockStartMinute.getValue());
            startTime = LocalTime.of(startHour, startMinute);
            // if user selects PM, increase time 12 hours
            if (workBlockStartAMorPM.getValue().equals("PM")) {
                startTime = startTime.plusHours(12);
            }

            int endHour = Integer.parseInt(workBlockEndHour.getValue());
            int endMinute = Integer.parseInt(workBlockEndMinute.getValue());
            endTime = LocalTime.of(endHour, endMinute);
            // if user selects PM, increase time 12 hours
            if (workBlockEndAMorPM.getValue().equals("PM")) {
                endTime = endTime.plusHours(12);
            }

            LocalDateTime startDateTime = date.atTime(startTime);
            LocalDateTime endDateTime = date.atTime(endTime);


            // create assignment and schedule workblock
            Assignment assignment = new Assignment(name, (int) Duration.between(startTime, endTime).toMinutes());
            WorkBlock workBlock = new WorkBlock(startDateTime, endDateTime, assignment);
            Scheduler.addWorkBlock(workBlock);

            if(repeatedWorkBlock.isSelected()) {
                System.out.println(date);
                System.out.println(date.getDayOfWeek());
                date = date.plusDays(7);
                System.out.println(date);
                //ArrayList<WorkBlock> repeatWorkBlock = new ArrayList<>();
                LocalDate endRepeat = repeatUntilDate.getValue();
                System.out.println(date.compareTo(endRepeat));
                while(date.compareTo(endRepeat) < 1) {
                    startDateTime = startDateTime.plusDays(7);
                    endDateTime = endDateTime.plusDays(7);
                    //repeatWorkBlock.add(new WorkBlock(startDateTime, endDateTime, assignment));
                    Scheduler.addWorkBlock(new WorkBlock(startDateTime, endDateTime, assignment));
                    date = date.plusDays(7);
                }
//                for(int i = 0; i < repeatWorkBlock.size(); i++) {
//                    Scheduler.addWorkBlock(repeatWorkBlock.get(i));
//                }
            }

            onDeleteWorkBlock(event);
            Scheduler.saveSchedule();

            workblockWarningLabel.setText("");


            schedulePane.setVisible(true);
            workBlockPane.setVisible(false);
            schedulerTextArea.setText(Scheduler.getScheduleString());
        }
        catch (NumberFormatException e) {
            workblockWarningLabel.setText("Select date and time");
        }
        catch(Exception e){
            workblockWarningLabel.setText(e.getMessage());
        }
    }


}