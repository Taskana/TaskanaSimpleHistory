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
        "<script>INSERT INTO HISTORY_EVENTS (ID, TYPE, USER_ID, CREATED, COMMENT, WORKBASKET_KEY, TASK_ID)"
            + " VALUES (#{historyEvent.id}, #{historyEvent.type}, #{historyEvent.userId}, #{historyEvent.created}, #{historyEvent.comment}, #{historyEvent.workbasketKey}, #{historyEvent.taskId}) "
            + "</script>")
    @Options(keyProperty = "id", keyColumn = "ID")
    void insert(@Param("historyEvent") TaskanaHistoryEvent historyEvent);

    @Select(
        "<script>SELECT ID, TYPE, USER_ID, CREATED, COMMENT, WORKBASKET_KEY, TASK_ID "
            + "FROM HISTORY_EVENTS "
            + "WHERE ID = #{id} "
            + "</script>")
    @Results(value = {
        @Result(property = "id", column = "ID"),
        @Result(property = "type", column = "TYPE"),
        @Result(property = "userId", column = "USER_ID"),
        @Result(property = "created", column = "CREATED"),
        @Result(property = "comment", column = "COMMENT"),
        @Result(property = "workbasketKey", column = "WORKBASKET_KEY"),
        @Result(property = "taskId", column = "TASK_ID")
    })
    HistoryEventImpl findById(@Param("id") String id);
}
