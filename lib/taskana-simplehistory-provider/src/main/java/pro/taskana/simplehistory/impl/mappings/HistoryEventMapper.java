package pro.taskana.simplehistory.impl.mappings;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import pro.taskana.history.api.TaskanaHistory;
import pro.taskana.history.api.TaskanaHistoryEvent;
import pro.taskana.simplehistory.impl.HistoryEventImpl;

/**
 * This class is the mybatis mapping of workbaskets.
 */
public interface HistoryEventMapper {

    @Insert(
        "<script>INSERT INTO HISTORY_EVENTS (TYPE, USER_ID, CREATED, COMMENT, WORKBASKET_KEY, TASK_ID)"
            + " VALUES ( #{historyEvent.type}, #{historyEvent.userId}, #{historyEvent.created}, #{historyEvent.comment}, #{historyEvent.workbasketKey}, #{historyEvent.taskId}) "
            + "</script>")
    void insert(@Param("historyEvent") TaskanaHistoryEvent historyEvent);
}
