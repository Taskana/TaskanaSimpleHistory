package pro.taskana.rest.simplehistory;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.SpringHandlerInstantiator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;

import pro.taskana.TaskanaEngine;
import pro.taskana.configuration.SpringTaskanaEngineConfiguration;
import pro.taskana.configuration.TaskanaEngineConfiguration;
import pro.taskana.simplehistory.impl.SimpleHistoryServiceImpl;

/**
 * Configuration for Taskana history REST service.
 */
@Configuration
@ComponentScan(basePackages={"pro.taskana.rest", "pro.taskana.rest.simplehistory"})
@EnableTransactionManagement
public class TaskHistoryRestConfiguration {

    @Bean
    public SimpleHistoryServiceImpl getSimpleHistoryService() {
        return new SimpleHistoryServiceImpl();
    }

}
