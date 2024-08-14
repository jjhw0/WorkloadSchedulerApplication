import cen4010.g8.workloadscheduler.Assignment;
import cen4010.g8.workloadscheduler.WorkBlock;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AssignmentTest {

    @Test
    void toStringTest() {
        Assignment a1 = new Assignment("test1", LocalDateTime.now(), 60);
        Assignment a2 = new Assignment("test1", LocalDateTime.now(), 60);
        Assignment a3 = new Assignment("test1", LocalDateTime.now(), 60);

        String s1 = String.format("%s due: %s", a1.getName(), a1.getDueDate());
        String s2 = String.format("%s due: %s", a2.getName(), a2.getDueDate());
        String s3 = String.format("%s due: %s", a3.getName(), a3.getDueDate());

        assertEquals(s1, a1.toString());
        assertEquals(s2, a2.toString());
        assertEquals(s3, a3.toString());
    }

    @Test
    void getNameTest() {
        Assignment assignment = new Assignment("test", 60);
        String expected = "test";
        String actual = assignment.getName();
        assertEquals(expected, actual);
    }

    @Test
    void setNameTest() {
        Assignment assignment = new Assignment("test", 60);
        assignment.setName("New Name");
        String expected = "New Name";
        String actual = assignment.getName();
        assertEquals(actual, expected);
    }

    @Test
    void getDueDateTest() {
        LocalDateTime now = LocalDateTime.now();
        Assignment assignment = new Assignment("test1", now, 60);
        LocalDateTime actual = assignment.getDueDate();
        assertEquals(actual, now);
    }

    @Test
    void setDueDateTest() {
        LocalDateTime now = LocalDateTime.now();
        Assignment assignment = new Assignment("test", 60);
        assignment.setDueDate(now);
        LocalDateTime actual = assignment.getDueDate();
        assertEquals(actual, now);
    }
    @Test
    void getEstimatedTimeTest() {
        Assignment assignment = new Assignment("test1", 60);
        int expected = 60;
        int actual = assignment.getEstimatedTime();
        assertEquals(actual, expected);
    }

    @Test
    void setEstimatedTime() {
        Assignment assignment = new Assignment("test1", 60);
        assignment.setEstimatedTime(90);
        int expected = 90;
        int actual = assignment.getEstimatedTime();
        assertEquals(actual, expected);
    }

    @Test
    void equalsTest() {
        LocalDateTime now = LocalDateTime.now();
        Assignment assignment = new Assignment("test1", 60);
        Assignment assignment1 = new Assignment("test1", 60);
        Assignment assignment2 = new Assignment("test1", now, 60);
        Assignment assignment3 = new Assignment("test1", now, 60);
        WorkBlock workBlock = new WorkBlock(now, LocalDateTime.MAX, assignment);
        assertEquals(assignment, assignment1);
        assertNotEquals(assignment, workBlock);
        assertEquals(assignment2, assignment3);
    }
}
