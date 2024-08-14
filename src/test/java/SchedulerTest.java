import cen4010.g8.workloadscheduler.Assignment;
import cen4010.g8.workloadscheduler.Schedule;
import cen4010.g8.workloadscheduler.Scheduler;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


public class SchedulerTest {
    @Test
    void scheduleAssignmentTest() {
        LocalDateTime now = LocalDateTime.now();
        int month = now.plusMonths(1).getMonthValue(); // moves to next month to avoid scheduling a due date that already passed
        int day = now.getDayOfMonth();
        int year = now.getYear();
        LocalDateTime dueDate = LocalDateTime.of(year, month, day, 23, 59);

        Assignment assignment = new Assignment("test", dueDate, 90);
        Assignment assignment1 = new Assignment("test1", dueDate, 90);

        Schedule schedule = Scheduler.getSchedule();

        Scheduler.scheduleAssignment(assignment);
        Scheduler.scheduleAssignment(assignment1);
        System.out.println(schedule);
        Scheduler.saveSchedule();

    }
}
