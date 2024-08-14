package cen4010.g8.workloadscheduler;

import java.time.LocalDateTime;

public class Assignment {
    private String name;
    private LocalDateTime dueDate;
    private int estimatedTime; // in minutes

    public Assignment(String name, LocalDateTime dueDate, int estimatedTime) {
        this.name = name;
        this.dueDate = dueDate;
        this.estimatedTime = estimatedTime;
    }
    public Assignment(String name, int estimatedTime) {
        this.name = name;
        this.estimatedTime = estimatedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    @Override
    public String toString() {
        return String.format("%s due: %s", getName(), getDueDate());
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;

        Assignment other = (Assignment) obj;

        if(getDueDate() == null) {
            return (getName().equals(other.getName())) && (getEstimatedTime() == other.getEstimatedTime());
        }
        return (getName().equals(other.getName())) && (getDueDate().equals(other.getDueDate())) && (getEstimatedTime() == other.getEstimatedTime());
    }
}
