package pro.taskana.simplehistory.impl;

import java.sql.SQLException;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.taskana.history.api.TaskanaHistory;
import pro.taskana.history.api.TaskanaHistoryEvent;
import pro.taskana.impl.util.IdGenerator;
import pro.taskana.simplehistory.TaskanaHistoryEngine;
import pro.taskana.simplehistory.impl.mappings.HistoryEventMapper;

/**
 * This is the implementation of HistoryService.
 */
public class HistoryServiceImpl implements TaskanaHistory {
    private static final String ID_PREFIX_HISTORY_EVENT = "HEI";
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryServiceImpl.class);
    private TaskanaHistoryEngineImpl taskanaHistoryEngine;
    private HistoryEventMapper historyEventMapper;

    HistoryServiceImpl(TaskanaHistoryEngine taskanaHistoryEngine, HistoryEventMapper historyEventMapper) {
        this.taskanaHistoryEngine = (TaskanaHistoryEngineImpl) taskanaHistoryEngine;
        this.historyEventMapper = historyEventMapper;
    }


    public HistoryEventImpl getHistoryEvent(String id) {
        HistoryEventImpl historyEvent = null;

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

    @Override
    public void create(TaskanaHistoryEvent event) {
        try {
            taskanaHistoryEngine.openConnection();
            event.setId(IdGenerator.generateWithPrefix(ID_PREFIX_HISTORY_EVENT));
            Instant now = Instant.now();
            event.setCreated(now);
            historyEventMapper.insert(event);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            taskanaHistoryEngine.returnConnection();
        }
    }
}
