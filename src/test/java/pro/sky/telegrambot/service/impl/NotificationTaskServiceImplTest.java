package pro.sky.telegrambot.service.impl;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.NotificationTaskServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pro.sky.telegrambot.constants.NotificationTaskServiceImplTestConstants.*;

public class NotificationTaskServiceImplTest {

    NotificationTaskRepository notificationTaskRepository = Mockito.mock(NotificationTaskRepository.class);

    Message message = Mockito.mock(Message.class);
    Update update = Mockito.mock(Update.class);

    final NotificationTaskServiceImpl NotificationTaskServiceImplTest = new NotificationTaskServiceImpl(notificationTaskRepository);

    private NotificationTaskServiceImplTest() {
    }

    @Test
    public void shouldCheck() {

        //        Preparing...

        NotificationTask notificationTask1 = new NotificationTask(1L, 1L, TASK_MESSAGE1, LOCAL_DATE_TIME1, false);
        NotificationTask notificationTask2 = new NotificationTask(2L, 1L, TASK_MESSAGE2, LOCAL_DATE_TIME2, false);
        NotificationTask notificationTask3 = new NotificationTask(3L, 1L, TASK_MESSAGE3, LOCAL_DATE_TIME3, false);

        List<NotificationTask> notificationTasks = new ArrayList<>();
        notificationTasks.add(notificationTask1);
        notificationTasks.add(notificationTask2);
        notificationTasks.add(notificationTask3);

        Mockito.when(notificationTaskRepository.findByDateTime()
                        .stream()
                        .distinct()
                        .filter((n) -> !n.isSending())
                        .collect(Collectors.toList()))
                .thenReturn(notificationTasks);

        //        Testing...

        Assertions.assertEquals(notificationTasks, NotificationTaskServiceImplTest.check());
    }

    @Test
    public void shouldParse() {

        //        Preparing...

        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(update.message().text()).thenReturn(TASK_MESSAGE1);

        //        Testing...

        Assertions.assertNotNull(NotificationTaskServiceImplTest.parse(update));
    }

}
