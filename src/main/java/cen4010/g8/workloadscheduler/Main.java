package cen4010.g8.workloadscheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("startScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 570);
        stage.setTitle("Workload Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        try{
            File saveFile = new File("src/main/resources/schedule.txt");
            Scanner s = new Scanner(saveFile);
            while(s.hasNext()) {
                String line = s.nextLine();
                String[] details = line.split(", ");
                Assignment a;
                if(details[1].equals("null")) {
                    a = new Assignment(details[0], Integer.parseInt(details[2]));
                }
                else {
                    a = new Assignment(details[0], LocalDateTime.parse(details[1]), Integer.parseInt(details[2]));
                }
                WorkBlock wb = new WorkBlock(LocalDateTime.parse(details[3]), LocalDateTime.parse(details[4]), a);
                Scheduler.addWorkBlock(wb);
            }
        }
        catch(Exception e) {    System.out.println(e);    }

        launch();
    }
}