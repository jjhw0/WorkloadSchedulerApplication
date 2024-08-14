import cen4010.g8.workloadscheduler.Assignment;
import cen4010.g8.workloadscheduler.Schedule;
import cen4010.g8.workloadscheduler.WorkBlock;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScheduleTest {
    @Test
    void addWorkBlockTest() {
        Schedule actual = new Schedule();
        Schedule expected = new Schedule();

        LocalDateTime now = LocalDateTime.now();
        int month = now.plusMonths(1).getMonthValue(); // moves to next month to avoid scheduling a due date that already passed
        int day = now.getDayOfMonth();
        int year = now.getYear();
        LocalDateTime start1 = LocalDateTime.of(year, month,day,9, 0);
        LocalDateTime start2 = LocalDateTime.of(year, month,day,13, 0);
        LocalDateTime start3 = LocalDateTime.of(year, month,day,15, 0);
        LocalDateTime dueDate = LocalDateTime.of(year, month, day,14, 0);

        Assignment a1 = new Assignment("1", dueDate, 60);
        Assignment a2 = new Assignment("2", dueDate, 60);
        Assignment a3 = new Assignment("3", dueDate, 60);

        WorkBlock w1 = new WorkBlock(start1, start1.plusMinutes(a1.getEstimatedTime()), a1);
        WorkBlock w2 = new WorkBlock(start2, start2.plusMinutes(a2.getEstimatedTime()), a2);
        WorkBlock w3 = new WorkBlock(start3, start3.plusMinutes(a2.getEstimatedTime()), a3);

        actual.addWorkBlock(w1);
        actual.addWorkBlock(w2);
        actual.addWorkBlock(w3);

        // change order to test sorted insertion
        expected.addWorkBlock(w2);
        expected.addWorkBlock(w3);
        expected.addWorkBlock(w1);

        assertEquals(actual, expected);
    }

    @Test
    void getWorkBlockTest() {
        LocalDateTime now = LocalDateTime.now();
        WorkBlock workBlock = new WorkBlock(now, now, new Assignment("test", 60));
        Schedule schedule = new Schedule();
        schedule.addWorkBlock(workBlock);
        WorkBlock expected  = schedule.getWorkBlock(0);
        WorkBlock nullBlock = schedule.getWorkBlock(100);
        assertEquals(workBlock, expected);
        assertEquals(nullBlock, new WorkBlock(null, null, null));
    }

    @Test
    void removeWorkBlockTest() {
        LocalDateTime now = LocalDateTime.now();
        WorkBlock workBlock = new WorkBlock(now, now, new Assignment("test", 60));
        Schedule schedule = new Schedule();
        schedule.addWorkBlock(workBlock);

        schedule.removeWorkBlock(workBlock);
        Schedule.WorkBlockList workBlockList = schedule.getWorkBlocks();

        assertTrue(workBlockList.isEmpty());
    }

    @Test
    void findTimeSlotTest() {
        LocalDateTime fiveMinsAgo = LocalDateTime.now().minusMinutes(5);
        LocalDateTime inFiveMins = LocalDateTime.now().plusMinutes(5);
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        Schedule schedule = new Schedule();
        Assignment futureAssignment = new Assignment("future", inFiveMins, 60);
        Assignment pastAssignment = new Assignment("past", fiveMinsAgo, 60);
        schedule.addWorkBlock(new WorkBlock(inFiveMins, tomorrow, futureAssignment));
        WorkBlock pastBlock = schedule.findTimeSlot(pastAssignment);
        Assignment tomorrowAssignment = new Assignment("tomorrow", tomorrow, 60);
        WorkBlock tomorrowBlock = schedule.findTimeSlot(tomorrowAssignment);
    }
    @Test
    void toStringTest() {

    }

}
