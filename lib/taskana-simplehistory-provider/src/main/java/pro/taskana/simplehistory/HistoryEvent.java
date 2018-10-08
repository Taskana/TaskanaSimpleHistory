package pro.taskana.simplehistory;

import java.time.Instant;

/**
 * Interface for HistoryEvent. This model-object contains information about HistoryEvents structure.
 */
public interface HistoryEvent {

    /**
     * Gets the id of the historyEvent.
     *
     * @return eventId
     */
    String getId();

    /**
     * Gets the business process id of the historyEvent.
     *
     * @return businessProcessId
     */
    String getBusinessProcessId();

    /**
     * Gets the parent business process id of the historyEvent.
     *
     * @return parentBusinessProcessId
     */
    String getParentBusinessProcessId();

    /**
     * Gets the task id of the historyEvent.
     *
     * @return taskId
     */
    String getTaskId();

    /**
     * Gets the event type of the historyEvent.
     *
     * @return eventType
     */
    String getEventType();

    void setEventType(String historyEvent);

    /**
     * Returns the date when the history event was created.
     *
     * @return created as Instant
     */
    Instant getCreated();

    /**
     * Gets the user id of the historyEvent.
     *
     * @return userId
     */
    String getUserId();

    /**
     * Gets the domain of the historyEvent.
     *
     * @return domain
     */
    String getDomain();

    /**
     * Gets the workbasket key of the historyEvent.
     *
     * @return workbasketKey
     */
    String getWorkbasketKey();
}
