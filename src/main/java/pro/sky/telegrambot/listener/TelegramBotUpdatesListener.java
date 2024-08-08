package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.exception.RepositoryOperationException;
import pro.sky.telegrambot.service.NotificationTaskService;

import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    /**
     * Demo project for learning Telegram Bot programming
     */
    private final NotificationTaskService notificationTaskService;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(NotificationTaskService notificationTaskService) {
        this.notificationTaskService = notificationTaskService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        /*
          Process input updates...
         */
        updates.forEach(update -> {
            try {
                logger.info("Processing update: {}", update);
                long chatId = update.message().chat().id();
                if (update.message().text().equals("/start")) {
                    SendMessage message = new SendMessage(chatId, "Привет, студент!");
                    logger.info("Message:{}", message);
                    telegramBot.execute(message);
                } else if (notificationTaskService.parse(update).isPresent()) {
                    notificationTaskService.write(notificationTaskService.parse(update).get());
                    SendMessage message = new SendMessage(chatId, "Принято: " + update.message().text());
                    logger.info("Message:{}", message);
                    SendResponse sR = telegramBot.execute(message);
                } else {
                    SendMessage message = new SendMessage(chatId, "Когда нужно выполнить задачу? Пришли дату и время!");
                    logger.info("Message:{}", message);
                    telegramBot.execute(message);
                }
            } catch (RuntimeException e) {
                throw new RepositoryOperationException("Ошибка обращения к подсистеме хранения данных");
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
