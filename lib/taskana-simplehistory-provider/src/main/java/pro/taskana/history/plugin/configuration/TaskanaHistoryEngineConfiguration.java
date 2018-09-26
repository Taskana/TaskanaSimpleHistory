package pro.taskana.history.plugin.configuration;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.taskana.history.plugin.TaskanaHistoryEngine;
import pro.taskana.history.plugin.impl.TaskanaHistoryEngineImpl;

public class TaskanaHistoryEngineConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskanaHistoryEngineConfiguration.class);
    // TASKANA_SCHEMA_VERSION
    private static final String DEFAULT_SCHEMA_NAME = "TASKANA";

    // Taskana datasource configuration
    protected DataSource dataSource;
    protected DbSchemaCreator dbSchemaCreator;
    protected String schemaName;

    public TaskanaHistoryEngineConfiguration(DataSource dataSource, String schemaName) throws SQLException {
        this.dataSource = dataSource;
        this.schemaName = schemaName;
        dbSchemaCreator = new DbSchemaCreator(this.dataSource, this.schemaName);
    }

    public String getSchemaName() {
        return schemaName;
    }

    public TaskanaHistoryEngine buildTaskanaHistoryEngine() {
        return TaskanaHistoryEngineImpl.createTaskanaEngine(this);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
