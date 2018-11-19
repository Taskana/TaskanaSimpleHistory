package acceptance;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.BeforeClass;

import configuration.DBWriter;
import configuration.TaskanaEngineConfigurationHelperClassTest;
import pro.taskana.configuration.TaskanaEngineConfiguration;
import pro.taskana.simplehistory.TaskanaHistoryEngine;
import pro.taskana.simplehistory.impl.TaskanaHistoryEngineImpl;

public class AbstractAccTest {

    protected static TaskanaEngineConfiguration taskanaEngineConfiguration;
    protected static TaskanaHistoryEngine taskanaHistoryEngine;
	
    @BeforeClass
    public static void setupTest() throws Exception {
        resetDb("TASKANA");
    }

    public static void resetDb(String schemaName) throws SQLException, IOException {
        DataSource dataSource = TaskanaEngineConfigurationHelperClassTest.getDataSource();
        DBWriter writer = new DBWriter();
        taskanaEngineConfiguration = new TaskanaEngineConfiguration(dataSource, false, schemaName);
        taskanaHistoryEngine = new TaskanaHistoryEngineImpl(taskanaEngineConfiguration);
        writer.clearDB(dataSource);
        writer.generateTestData(dataSource);
    }
}
