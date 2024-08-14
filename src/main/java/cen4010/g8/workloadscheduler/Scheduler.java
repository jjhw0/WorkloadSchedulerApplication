package cen4010.g8.workloadscheduler;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Scheduler {
    private static final Schedule schedule = new Schedule();

    public static boolean scheduleAssignment(Assignment assignment) {
        WorkBlock workBlock = schedule.findTimeSlot(assignment);
        if (workBlock == null) {
            return false;
        }
        else { // no available time slot
            schedule.addWorkBlock(workBlock);
        }
        return true;
    }

    public static void saveSchedule() {
        ArrayList<WorkBlock> blocks;
        blocks = schedule.getWorkBlocks();
        try {
            FileWriter writer = new FileWriter("src/main/resources/schedule.txt", false);
            BufferedWriter out = new BufferedWriter(writer);
            for (WorkBlock block: blocks) {
                String name = block.getAssignment().getName();
                LocalDateTime due = block.getAssignment().getDueDate();
                int time = block.getAssignment().getEstimatedTime();
                LocalDateTime start = block.getStartTime();
                LocalDateTime end = block.getEndTime();
                out.write(name +", " + due + ", " + time + ", " + start + ", " + end);
                out.newLine();
            }
            out.close();
        }
        catch(Exception e) {   System.out.println(e);   }
    }

    public static void addWorkBlock(WorkBlock workBlock) {
        schedule.addWorkBlock(workBlock);
    }

    public static String getScheduleString() {
        return schedule.toString();
    }

    public static Schedule getSchedule() {
        return schedule;
    }
}
