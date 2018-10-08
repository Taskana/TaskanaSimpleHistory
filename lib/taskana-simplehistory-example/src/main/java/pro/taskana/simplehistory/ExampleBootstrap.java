package pro.taskana.history.plugin;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ExampleBootstrap {

    @Autowired
    private HistoryService historyService;


    @PostConstruct
    public void test() {
        HistoryEvent newEvent = historyService.newHistoryEvent("", "", "");
        historyService.createHistoryEvent(newEvent);
        HistoryEvent insertedEvent = historyService.getHistoryEvent(newEvent.getId());
        System.out.println("---------------------------> event inserted: " + insertedEvent.getId());
    }
}
