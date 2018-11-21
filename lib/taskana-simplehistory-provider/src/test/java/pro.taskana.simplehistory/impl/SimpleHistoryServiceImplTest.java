package pro.taskana.simplehistory.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSessionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import pro.taskana.configuration.TaskanaEngineConfiguration;
import pro.taskana.simplehistory.impl.mappings.HistoryEventMapper;

/**
 * Unit Test for SimpleHistoryServiceImplTest.
 *
 * @author MMR
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TaskanaHistoryEngineImpl.class)
@PowerMockIgnore("javax.management.*")
public class SimpleHistoryServiceImplTest {

    @InjectMocks
    private SimpleHistoryServiceImpl cutSpy;

    @Mock
    private HistoryEventMapper historyEventMapperMock;

    @Mock
    private TaskanaHistoryEngineImpl taskanaHistoryEngineMock;

    @Mock
    private TaskanaEngineConfiguration taskanaEngineConfiguration;

    @Mock
    private SqlSessionManager sqlSessionManagerMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInitializeSimpleHistoryService() throws SQLException {

        doReturn(historyEventMapperMock).when(sqlSessionManagerMock).getMapper(any());
        doReturn(sqlSessionManagerMock).when(taskanaHistoryEngineMock).getSqlSession();
        PowerMockito.mockStatic(TaskanaHistoryEngineImpl.class);
        Mockito.when(TaskanaHistoryEngineImpl.createTaskanaEngine(taskanaEngineConfiguration)).thenReturn(taskanaHistoryEngineMock);
        cutSpy.initialize(taskanaEngineConfiguration);

        verify(sqlSessionManagerMock, times(1)).getMapper(any());
        verify(taskanaHistoryEngineMock, times(1)).getSqlSession();
        PowerMockito.verifyStatic();
    }

    @Test
    public void testCreateEvent() throws SQLException {
        HistoryEventImpl expectedWb = createHistoryEvent("wbKey1", "taskId1", "type1", "Some comment", "wbKey2");
        doNothing().when(historyEventMapperMock).insert(expectedWb);

        cutSpy.create(expectedWb);
        verify(taskanaHistoryEngineMock, times(1)).openConnection();
        verify(historyEventMapperMock, times(1)).insert(expectedWb);
        verify(taskanaHistoryEngineMock, times(1)).returnConnection();
        assertTrue(expectedWb.getCreated() != null);

    }

    HistoryEventImpl createHistoryEvent(String workbasketKey, String taskId, String type, String comment, String  previousWorkbasketId) {
        HistoryEventImpl historyEvent = new HistoryEventImpl();
        historyEvent.setWorkbasketKey(workbasketKey);
        historyEvent.setTaskId(taskId);
        historyEvent.setType(type);
        historyEvent.setComment(comment);
        historyEvent.setOldValue(previousWorkbasketId);
        return historyEvent;
    }

}
