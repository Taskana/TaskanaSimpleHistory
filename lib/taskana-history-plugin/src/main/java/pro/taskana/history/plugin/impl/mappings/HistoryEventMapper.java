package pro.taskana.history.plugin.impl.mappings;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import pro.taskana.history.plugin.impl.HistoryEventImpl;

/**
 * This class is the mybatis mapping of workbaskets.
 */
public interface HistoryEventMapper {

    @Insert(
        "<script>INSERT INTO HISTORY_EVENTS (ID, BUSINESSPROCESSID, TASKID, EVENTTYPE, CREATED, USERID, DOMAIN, WORKBASKETKEY)"
            + " VALUES (#{historyEvent.id}, #{historyEvent.businessProcessId}, #{historyEvent.taskId}, #{historyEvent.eventType}, #{historyEvent.created}, #{historyEvent.userId}, #{historyEvent.domain}, #{historyEvent.workbasketKey}) "
            + "</script>")
    @Options(keyProperty = "id", keyColumn = "ID")
    void insert(@Param("historyEvent") HistoryEventImpl historyEvent);

    @Select(
        "<script>SELECT ID, BUSINESSPROCESSID, TASKID, EVENTTYPE, CREATED, USERID, DOMAIN, WORKBASKETKEY "
            + "FROM HISTORY_EVENTS "
            + "WHERE ID = #{id} "
            + "</script>")
    @Results(value = {
        @Result(property = "id", column = "ID"),
        @Result(property = "businessProcessId", column = "BUSINESSPROCESSID"),
        @Result(property = "taskId", column = "TASKID"),
        @Result(property = "eventType", column = "EVENTTYPE"),
        @Result(property = "created", column = "CREATED"),
        @Result(property = "userId", column = "USERID"),
        @Result(property = "domain", column = "DOMAIN"),
        @Result(property = "workbasketKey", column = "WORKBASKETKEY")
    })
    HistoryEventImpl findById(@Param("id") String id);
}
