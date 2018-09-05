package pro.taskana.history.plugin.impl;

import java.time.Instant;
import pro.taskana.history.plugin.HistoryEvent;

/**
 * This entity contains the most important information about a history event.
 */
public class HistoryEventImpl implements HistoryEvent {
    private String id;
    private String businessProcessId;
    private String parentBusinessProcessId;
    private String taskId;
    private String eventType;
    private Instant created;
    private String userId;
    private String domain;
    private String workbasketKey;

    public HistoryEventImpl() {
    }

    @Override
    public String getId() {
        return this.id;
    }

    void setId(String id) {
        this.id = id;
    }

    @Override
    public String getBusinessProcessId() {
        return this.businessProcessId;
    }

    void setBusinessProcessId(String businessProcessId) {
        this.businessProcessId = businessProcessId;
    }

    @Override
    public String getParentBusinessProcessId() {
        return this.parentBusinessProcessId;
    }

    void setParentBusinessProcessId(String parentBusinessProcessId) {
        this.parentBusinessProcessId = parentBusinessProcessId;
    }

    @Override
    public String getTaskId() {
        return this.taskId;
    }

    void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String getEventType() {
        return this.eventType;
    }

    @Override
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public Instant getCreated() {
        return this.created;
    }

    void setCreated(Instant created) {
        this.created = created;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getDomain() {
        return this.domain;
    }

    void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String getWorkbasketKey() {
        return this.workbasketKey;
    }

    void setWorkbasketKey(String workbasketKey) {
        this.workbasketKey = workbasketKey;
    }
}
