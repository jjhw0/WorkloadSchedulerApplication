package cen4010.g8.workloadscheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

// A Schedule is simply a sorted list of WorkBlocks. By sorting the list, we are able
// to easily search for available time slots to schedule WorkBlocks for new Assignments.
public class Schedule {
    private final WorkBlockList workBlocks;

    // Constructor
    public Schedule() {
        workBlocks = new WorkBlockList();
    }

    // Adds a WorkBlock to the schedule
    public void addWorkBlock(WorkBlock workBlock) {
        workBlocks.add(workBlock);
    }

    public WorkBlock getWorkBlock(int id) {
        for (WorkBlock wb: workBlocks) {
            if(wb.getId() == id) {
                return wb;
            }
        }
        return new WorkBlock(null, null, null);
    }

    public void removeWorkBlock(WorkBlock workBlock) {
        System.out.println(workBlocks);
        workBlocks.remove(workBlock);
        System.out.println(workBlocks);
    }

    // returns null if there is no available time slot or due date has passed
    public WorkBlock findTimeSlot(Assignment assignment) {
        LocalDateTime now = LocalDateTime.now();
        int estimatedTime = assignment.getEstimatedTime();
        LocalDateTime dueDate = assignment.getDueDate();
        if (dueDate.isBefore(LocalDateTime.now())) {
            return null;
        }
        if (workBlocks.isEmpty()) {
            if (now.plusMinutes(estimatedTime).isAfter(dueDate)) {
                return null;
            }
            return new WorkBlock(now, now.plusMinutes(estimatedTime), assignment);
        }
        for (int i = 0; i < workBlocks.size() - 1; ++i) {
            LocalDateTime current = workBlocks.get(i).getEndTime();
            LocalDateTime next = workBlocks.get(i + 1).getStartTime();
            if (current.isBefore(now)) { // if workblock is in the past, ignore
                continue;
            }
            if (current.isAfter(dueDate)) { // if due date has passed, return null
                return null;
            }
            Duration inBetweenTime = Duration.between(current, next);
            if (inBetweenTime.toMinutes() > estimatedTime) {
                return new WorkBlock(current, current.plusMinutes(estimatedTime), assignment);
            }
        }
        LocalDateTime lastEndTime = workBlocks.get(workBlocks.size() - 1).getEndTime();
        if (lastEndTime.isAfter(dueDate))
            return null;
        if (lastEndTime.isBefore(now)) {
            if (now.plusMinutes(estimatedTime).isAfter(dueDate)) {
                return null;
            }
            return new WorkBlock(now, now.plusMinutes(estimatedTime), assignment);
        }
        return new WorkBlock(lastEndTime, lastEndTime.plusMinutes(estimatedTime), assignment); // adds to end of schedule
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (WorkBlock workBlock : workBlocks) {
            stringBuilder.append(workBlock);
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;

        Schedule other = (Schedule) obj;
        if (workBlocks.size() != other.workBlocks.size())
            return false;

        int i = 0;
        for (WorkBlock workBlock : workBlocks) {
            if (!workBlock.equals(other.workBlocks.get(i)))
                return false;

            i++;
        }
        return true;
    }

    public WorkBlockList getWorkBlocks() {
        return workBlocks;
    }

    // WorkBlock list which keeps WorkBlocks sorted as we insert them.
    public static class WorkBlockList extends ArrayList<WorkBlock> {
        public WorkBlockList() {
            super();
        }
        @Override
        public boolean add(WorkBlock workBlock) {
            int index = Collections.binarySearch(this, workBlock);
            if (index < 0) {
                index = ~index;
            }
            super.add(index, workBlock);
            return true;
        }
    }

}
