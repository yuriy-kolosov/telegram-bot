package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import pro.sky.telegrambot.model.NotificationTask;

import java.util.List;
import java.util.Optional;

public interface NotificationTaskService {

    List<NotificationTask> check();

    Optional<NotificationTask> parse(Update update);

    void update(NotificationTask notificationTask);

    void write(NotificationTask notificationTask);

}
