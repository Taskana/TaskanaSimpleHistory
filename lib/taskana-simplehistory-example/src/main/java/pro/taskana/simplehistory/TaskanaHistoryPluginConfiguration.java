package pro.taskana.simplehistory;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import pro.taskana.history.api.TaskanaHistory;
import pro.taskana.simplehistory.configuration.TaskanaHistoryEngineConfiguration;
import pro.taskana.simplehistory.impl.TaskanaHistoryEngineImpl;

public class TaskanaHistoryPluginConfiguration {

    @Value("${taskana.schemaName:TASKANA}")
    private String schemaName;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "customdb.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource dataSource(DataSourceProperties properties) {
        DataSource dataSource = properties.initializeDataSourceBuilder().build();
        return dataSource;
    }

    @Bean
    public TaskanaHistoryEngineConfiguration taskanaHistoryEngineConfiguration(DataSource dataSource) throws SQLException {
        TaskanaHistoryEngineConfiguration taskanaHistoryEngineConfiguration = new TaskanaHistoryEngineConfiguration(
            dataSource,
            schemaName);
        return taskanaHistoryEngineConfiguration;
    }

    @Bean
    public TaskanaHistoryEngine taskanaHistoryEngine(TaskanaHistoryEngineConfiguration taskanaHistoryEngineConfiguration) {
        TaskanaHistoryEngine taskanaHistoryEngine = new TaskanaHistoryEngineImpl(taskanaHistoryEngineConfiguration);
        return taskanaHistoryEngine;
    }

    @Bean
    public TaskanaHistory historyService(TaskanaHistoryEngine taskanaHistoryEngine) {
        return taskanaHistoryEngine.getTaskanaHistoryService();
    }

    @Bean
    public ExampleBootstrap exampleBootstrap() {
        return new ExampleBootstrap();
    }
}
