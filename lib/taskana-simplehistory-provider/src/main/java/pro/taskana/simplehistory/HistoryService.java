package pro.taskana.simplehistory;

/**
 * The History Service manages all operations on history events.
 */
public interface HistoryService {

    /**
     * Returns a not persisted instance of {@link HistoryEvent}.
     *
     * @param eventType
     *            the type of the event
     * @param taskId
     *            the task id of the event
     * @param workbasketKey
     *            the workbasket key of the event
     * @return an empty new HistoryEvent
     */
    HistoryEvent newHistoryEvent(String eventType, String taskId, String workbasketKey);

    /**
     * Persists a not persisted HistoryEvent which does not exist already.
     *
     * @param newHistoryEvent
     *            the transient historyEvent object to be persisted
     * @return the created and persisted HistoryEvent
     * @return a {@link HistoryEvent}
     */
    HistoryEvent createHistoryEvent(HistoryEvent newHistoryEvent);

    /**
     * Get the details of a history event by Id.
     *
     * @param id
     *            the id of the history event
     * @return the HistoryEvent
     */
    HistoryEvent getHistoryEvent(String id);

}