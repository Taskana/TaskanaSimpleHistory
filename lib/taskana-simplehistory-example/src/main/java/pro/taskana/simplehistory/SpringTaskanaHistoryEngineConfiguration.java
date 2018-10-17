package pro.taskana.simplehistory;

import java.sql.SQLException;

import javax.sql.DataSource;

import pro.taskana.simplehistory.configuration.TaskanaHistoryEngineConfiguration;

public class SpringTaskanaHistoryEngineConfiguration extends TaskanaHistoryEngineConfiguration {

    public SpringTaskanaHistoryEngineConfiguration(DataSource dataSource, String schemaName) throws SQLException {
        super(dataSource, schemaName);
    }
}
