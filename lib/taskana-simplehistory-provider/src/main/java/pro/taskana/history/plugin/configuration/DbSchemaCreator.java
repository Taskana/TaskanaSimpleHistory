package pro.taskana.history.plugin.configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbSchemaCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbSchemaCreator.class);
    private static final String SQL = "/sql";
    private static final String DB_SCHEMA = "/sql/taskana-history-schema.sql";
    private DataSource dataSource;
    private String schemaName;
    private StringWriter outWriter = new StringWriter();
    private PrintWriter logWriter = new PrintWriter(outWriter);
    private StringWriter errorWriter = new StringWriter();
    private PrintWriter errorLogWriter = new PrintWriter(errorWriter);

    public DbSchemaCreator(DataSource dataSource, String schema) throws SQLException {
        this.dataSource = dataSource;
        this.schemaName = schema;
        run();
    }

    /**
     * Run all db scripts.
     *
     * @throws SQLException
     *             will be thrown if there will be some incorrect SQL statements invoked.
     */
    public void run() throws SQLException {
        Connection connection = dataSource.getConnection();
        ScriptRunner runner = new ScriptRunner(connection);
        runner.setStopOnError(true);
        runner.setLogWriter(logWriter);
        runner.setErrorLogWriter(errorLogWriter);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass()
                .getResourceAsStream(DB_SCHEMA)));
            runner.runScript(reader);
        } finally {
            runner.closeConnection();
        }
        LOGGER.debug(outWriter.toString());
        if (!errorWriter.toString().trim().isEmpty()) {
            LOGGER.error(errorWriter.toString());
        }
    }

}
