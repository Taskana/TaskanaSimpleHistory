package pro.taskana.history.plugin.impl;

import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;

import pro.taskana.history.plugin.HistoryService;
import pro.taskana.history.plugin.TaskanaHistoryEngine;
import pro.taskana.history.plugin.configuration.TaskanaHistoryEngineConfiguration;
import pro.taskana.history.plugin.impl.mappings.HistoryEventMapper;

/**
 * This is the implementation of TaskanaHistoryEngine.
 */
public class TaskanaHistoryEngineImpl implements TaskanaHistoryEngine {

    private static final String DEFAULT = "default";

    TaskanaHistoryEngineConfiguration taskanaHistoryEngineConfiguration;
    protected SqlSessionManager sessionManager;
    protected TransactionFactory transactionFactory;
    protected java.sql.Connection connection = null;
    protected static ThreadLocal<Deque<SqlSessionManager>> sessionStack = new ThreadLocal<>();

    public TaskanaHistoryEngineImpl(TaskanaHistoryEngineConfiguration taskanaHistoryEngineConfiguration) {
        this.taskanaHistoryEngineConfiguration = taskanaHistoryEngineConfiguration;
        createTransactionFactory(true);
        this.sessionManager = createSqlSessionManager();
    }

    public static TaskanaHistoryEngine createTaskanaEngine(
        TaskanaHistoryEngineConfiguration taskanaHistoryEngineConfiguration) {
        return new TaskanaHistoryEngineImpl(taskanaHistoryEngineConfiguration);
    }

    @Override
    public HistoryService getTaskanaHistoryService() {
        return new HistoryServiceImpl(this, sessionManager.getMapper(HistoryEventMapper.class));
    }

    /**
     * Open the connection to the database. to be called at the begin of each Api call that accesses the database
     *
     */
    void openConnection() throws SQLException {
        initSqlSession();
        this.sessionManager.getConnection().setSchema(taskanaHistoryEngineConfiguration.getSchemaName());
    }

    /**
     * Returns the database connection into the pool. In the case of nested calls, simply pops the latest session from
     * the session stack. Closes the connection if the session stack is empty. In mode AUTOCOMMIT commits before the
     * connection is closed. To be called at the end of each Api call that accesses the database
     */
    void returnConnection() {
        popSessionFromStack();
        if (getSessionStack().isEmpty()
            && this.sessionManager != null && this.sessionManager.isManagedSessionStarted()) {
            try {
                this.sessionManager.commit();
            } catch (Exception e) {
            }
            this.sessionManager.close();
        }
    }

    /**
     * Initializes the SqlSessionManager.
     */
    void initSqlSession() {
        this.sessionManager.startManagedSession();
    }

    /**
     * creates the MyBatis transaction factory.
     *
     * @param useManagedTransactions
     */
    private void createTransactionFactory(boolean useManagedTransactions) {
        if (useManagedTransactions) {
            this.transactionFactory = new ManagedTransactionFactory();
        } else {
            this.transactionFactory = new JdbcTransactionFactory();
        }
    }

    protected SqlSessionManager createSqlSessionManager() {
        Environment environment = new Environment(DEFAULT, this.transactionFactory,
            taskanaHistoryEngineConfiguration.getDataSource());
        Configuration configuration = new Configuration(environment);

        // add mappers
        configuration.addMapper(HistoryEventMapper.class);
        SqlSessionFactory localSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return SqlSessionManager.newInstance(localSessionFactory);
    }

    protected static void pushSessionToStack(SqlSessionManager session) {
        getSessionStack().push(session);
    }

    protected static void popSessionFromStack() {
        Deque<SqlSessionManager> stack = getSessionStack();
        if (!stack.isEmpty()) {
            stack.pop();
        }
    }

    /**
     * With sessionStack, we maintain a Stack of SqlSessionManager objects on a per thread basis. SqlSessionManager is
     * the MyBatis object that wraps database connections. The purpose of this stack is to keep track of nested calls.
     * Each external API call is wrapped into taskanaEngineImpl.openConnection(); .....
     * taskanaEngineImpl.returnConnection(); calls. In order to avoid duplicate opening / closing of connections, we use
     * the sessionStack in the following way: Each time, an openConnection call is received, we push the current
     * sessionManager onto the stack. On the first call to openConnection, we call sessionManager.startManagedSession()
     * to open a database connection. On each call to returnConnection() we pop one instance of sessionManager from the
     * stack. When the stack becomes empty, we close the database connection by calling sessionManager.close()
     *
     * @return Stack of SqlSessionManager
     */
    protected static Deque<SqlSessionManager> getSessionStack() {
        Deque<SqlSessionManager> stack = sessionStack.get();
        if (stack == null) {
            stack = new ArrayDeque<>();
            sessionStack.set(stack);
        }
        return stack;
    }

    protected static SqlSessionManager getSessionFromStack() {
        Deque<SqlSessionManager> stack = getSessionStack();
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }
}