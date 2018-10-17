package pro.taskana.simplehistory.impl;

import pro.taskana.history.api.TaskanaHistoryEvent;

/**
 * This entity contains the most important information about a history event.
 */
public class HistoryEventImpl extends TaskanaHistoryEvent {
    private String workbasketKey;
    private String taskId;

    public HistoryEventImpl() {
    }

    public String getWorkbasketKey() {
        return workbasketKey;
    }

    public void setWorkbasketKey(String workbasketKey) {
        this.workbasketKey = workbasketKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
