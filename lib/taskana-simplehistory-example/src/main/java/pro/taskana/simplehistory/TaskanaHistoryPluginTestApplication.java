package pro.taskana.simplehistory;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TaskanaHistoryPluginConfiguration.class)
public class TaskanaHistoryPluginTestApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TaskanaHistoryPluginTestApplication.class).run(args);
    }
}
