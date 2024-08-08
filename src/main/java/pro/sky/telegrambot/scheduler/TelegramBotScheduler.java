package pro.sky.telegrambot.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.exception.DatabaseOperationException;
import pro.sky.telegrambot.service.NotificationTaskService;

@Component
public class TelegramBotScheduler {
    /**
     * Demo project for learning Telegram Bot programming
     */
    @Autowired
    private NotificationTaskService notificationTaskService;

    private Logger logger = LoggerFactory.getLogger(TelegramBotScheduler.class);

    @Autowired
    private TelegramBot telegramBot;

    @Scheduled(cron = "0 0/1 * * * *")
    public void scheduler() {
         /*
          Generate output messages
         */
        try {
            notificationTaskService.check()
                    .forEach(n -> {
                        SendMessage message = new SendMessage(n.getChatId()
                                , "Внимание: наступил срок исполнения: " + n.getMessageText());
                        logger.info("Message:{}", message);
                        SendResponse sR = telegramBot.execute(message);
                        n.setSending(true);
                        notificationTaskService.update(n);
                    });
        } catch (RuntimeException e) {
            throw new DatabaseOperationException("Ошибка базы данных");
        }
    }

}
