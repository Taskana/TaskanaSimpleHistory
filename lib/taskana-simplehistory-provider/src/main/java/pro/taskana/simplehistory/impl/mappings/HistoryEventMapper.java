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
        "<script>INSERT INTO HISTORY_EVENTS (BUSINESS_PROCESS_ID, PARENT_BUSINESS_PROCESS_ID, TASK_ID,"
            + " EVENT_TYPE, CREATED, USER_ID, DOMAIN, WORKBASKET_KEY, POR_COMPANY, POR_SYSTEM, POR_INSTANCE,"
            + " POR_TYPE, POR_VALUE, TASK_CLASSIFICATION_KEY, TASK_CLASSIFICATION_CATEGORY, ATTACHMENT_CLASSIFICATION_KEY, "
            + " COMMENT, OLD_VALUE, NEW_VALUE, CUSTOM_1, CUSTOM_2, CUSTOM_3, CUSTOM_4, OLD_DATA, NEW_DATA)"
            + " VALUES ( #{historyEvent.businessProcessId}, #{historyEvent.parentBusinessProcessId}, #{historyEvent.taskId},"
            + " #{historyEvent.type}, #{historyEvent.created}, #{historyEvent.userId}, #{historyEvent.domain}, #{historyEvent.workbasketKey},"
            + " #{historyEvent.porCompany}, #{historyEvent.porSystem}, #{historyEvent.porInstance}, #{historyEvent.porType},"
            + " #{historyEvent.porValue}, #{historyEvent.taskClassificationKey}, #{historyEvent.taskClassificationCategory},"
            + " #{historyEvent.attachmentClassificationKey}, #{historyEvent.comment}, #{historyEvent.oldValue}, #{historyEvent.newValue},"
            + " #{historyEvent.custom1}, #{historyEvent.custom2}, #{historyEvent.custom3}, #{historyEvent.custom4},"
            + " #{historyEvent.oldData}, #{historyEvent.newData}) "
            + "</script>")
    void insert(@Param("historyEvent") TaskanaHistoryEvent historyEvent);
}
