package pro.taskana.simplehistory.impl;

import java.sql.SQLException;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.taskana.simplehistory.HistoryEvent;
import pro.taskana.simplehistory.HistoryService;
import pro.taskana.simplehistory.TaskanaHistoryEngine;
import pro.taskana.simplehistory.impl.mappings.HistoryEventMapper;

/**
 * This is the implementation of HistoryService.
 */
public class HistoryServiceImpl implements HistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryServiceImpl.class);
    private TaskanaHistoryEngineImpl taskanaHistoryEngine;
    private HistoryEventMapper historyEventMapper;

    HistoryServiceImpl(TaskanaHistoryEngine taskanaHistoryEngine, HistoryEventMapper historyEventMapper) {
        this.taskanaHistoryEngine = (TaskanaHistoryEngineImpl) taskanaHistoryEngine;
        this.historyEventMapper = historyEventMapper;
    }

    @Override
    public HistoryEvent newHistoryEvent(String eventType, String taskId, String workbasketKey) {
        HistoryEventImpl historyEvent = new HistoryEventImpl();
        historyEvent.setEventType(eventType);
        historyEvent.setTaskId(taskId);
        historyEvent.setWorkbasketKey(workbasketKey);
        return historyEvent;
    }

    @Override
    public HistoryEvent createHistoryEvent(HistoryEvent newHistoryEvent) {
        HistoryEventImpl historyEvent = (HistoryEventImpl) newHistoryEvent;
        try {
            taskanaHistoryEngine.openConnection();
            historyEvent.setId("some auto generated ID");
            Instant now = Instant.now();
            historyEvent.setCreated(now);
            historyEventMapper.insert(historyEvent);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            taskanaHistoryEngine.returnConnection();
        }
        return historyEvent;
    }

    @Override
    public HistoryEvent getHistoryEvent(String id) {
        HistoryEvent historyEvent = null;

        try {
            taskanaHistoryEngine.openConnection();
            historyEvent = historyEventMapper.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            taskanaHistoryEngine.returnConnection();
        }
        return historyEvent;

    }
}
