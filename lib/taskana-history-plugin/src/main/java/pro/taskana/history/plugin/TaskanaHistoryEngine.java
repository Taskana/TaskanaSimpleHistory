package pro.taskana.history.plugin;

/**
 * The TaskanaHistoryEngine represents an overall set of all needed services.
 */
public interface TaskanaHistoryEngine {
    /**
     * The HistoryService can be used for operations on all history events.
     *
     * @return the HistoryService
     */
    HistoryService getTaskanaHistoryService();
}