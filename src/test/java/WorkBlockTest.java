import cen4010.g8.workloadscheduler.Assignment;
import cen4010.g8.workloadscheduler.WorkBlock;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkBlockTest {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime later = now.plusMinutes(60);
    @Test
     void getIdTest() {
        WorkBlock workBlock = new WorkBlock(now, later, new Assignment("test", 60));
        int id = workBlock.getId();
        assertEquals(--WorkBlock.count, id);
    }
    @Test
    void getStartTimeTest() {
        WorkBlock workBlock = new WorkBlock(now, later, new Assignment("test", 60));
        assertEquals(now, workBlock.getStartTime());
    }
    @Test
    void setStartTimeTest() {
        WorkBlock workBlock = new WorkBlock(now, later, new Assignment("test", 60));
        workBlock.setStartTime(now.plusMinutes(5));
        assertEquals(now.plusMinutes(5), workBlock.getStartTime());
    }
    @Test
    void getEndTimeTest() {
        WorkBlock workBlock = new WorkBlock(now, later, new Assignment("test", 60));
        assertEquals(later, workBlock.getEndTime());
    }
    @Test
    void setEndTimeTest() {
        WorkBlock workBlock = new WorkBlock(now, later, new Assignment("test", 60));
        workBlock.setEndTime(later.plusMinutes(5));
        assertEquals(later.plusMinutes(5), workBlock.getEndTime());
    }
    @Test
    void getAssignmentTest() {
        Assignment assignment = new Assignment("test", 60);
        WorkBlock workBlock = new WorkBlock(now, later, assignment);
        assertEquals(assignment, workBlock.getAssignment());
    }
    @Test
    void setAssignmentTest() {
        Assignment assignment = new Assignment("test", 60);
        WorkBlock workBlock = new WorkBlock(now, later, null);
        workBlock.setAssignment(assignment);
        assertEquals(assignment, workBlock.getAssignment());
    }

    @Test
    void compareToTest()  {
        WorkBlock workBlock = new WorkBlock(now, later, new Assignment("test", 60));
        WorkBlock workBlock1 = new WorkBlock(now, later, new Assignment("test", 60));
        assertEquals(workBlock.compareTo(workBlock1), 0);

    }

}
