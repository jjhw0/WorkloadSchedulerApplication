package cen4010.g8.workloadscheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WorkBlock implements Comparable<WorkBlock> {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Assignment assignment;

    public static int count = 0;
    private int id;

    public WorkBlock(LocalDateTime startTime, LocalDateTime endTime, Assignment assignment) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.assignment = assignment;
        this.id = count++;
    }

    public int getId() {   return id;   }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public int compareTo(WorkBlock other) {
        return this.startTime.compareTo(other.startTime);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE LLL dd, yyyy h:mma");
        return String.format("(id:%s)%s: %s to %s\n", getId(), (getAssignment() == null ? null : getAssignment().getName()), getStartTime().format(formatter), getEndTime().format(formatter) );
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;

        WorkBlock other = (WorkBlock) obj;
        if (other.getAssignment() == null && other.getStartTime() == null && other.getEndTime() == null) {
            if (getAssignment() == null && getStartTime() == null && getEndTime() == null) {
                return true;
            }
        }
        return (getStartTime().equals(other.getStartTime())) && (getEndTime().equals(other.getEndTime())) && (getAssignment().equals(other.getAssignment()));
    }
}
