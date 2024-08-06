package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.telegrambot.model.NotificationTask;

import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

    @Query(value = "select * from telegram_bot.public.notification_task"
            + " where (current_timestamp - notification_scheduler) :::: interval"
            + " between INTERVAL'0 hours 0 minutes' and INTERVAL'0 hours 5 minutes'"
            , nativeQuery = true)
    List<NotificationTask> findByDateTime();

}
