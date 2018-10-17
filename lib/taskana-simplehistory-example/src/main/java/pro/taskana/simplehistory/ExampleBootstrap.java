package pro.taskana.simplehistory;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pro.taskana.history.api.TaskanaHistory;
import pro.taskana.simplehistory.impl.HistoryEventImpl;
import pro.taskana.simplehistory.impl.HistoryServiceImpl;

@Component
@Transactional
public class ExampleBootstrap {

    @Autowired
    private TaskanaHistory historyService;


    @PostConstruct
    public void test() {
        HistoryEventImpl historyEvent = new HistoryEventImpl();
        historyEvent.setTaskId("some task Id");
        historyEvent.setType("Some Type");
        historyService.create(historyEvent);
        HistoryServiceImpl historyServiceImpl =  (HistoryServiceImpl)historyService;

        System.out.println("---------------------------> event inserted Id: " + historyServiceImpl.getHistoryEvent(historyEvent.getId()).getId());
    }
}
