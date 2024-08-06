package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class NotificationTaskServiceImpl implements NotificationTaskService {

    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskServiceImpl(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    Logger logger = LoggerFactory.getLogger(NotificationTaskServiceImpl.class);

    @Override
    public List<NotificationTask> check() {
        return notificationTaskRepository.findByDateTime()
                .stream()
                .distinct()
                .filter((n) -> !n.isSending())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NotificationTask> parse(Update update) {

        String regex = "(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(update.message().text());

        if (!matcher.matches()) {
            return Optional.empty();
        } else {
            String updateDate = matcher.group(1);
            String updateText = matcher.group(3);
            LocalDateTime localDateTime = LocalDateTime
                    .parse(updateDate, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

            return Optional.of(new NotificationTask(update.message().chat().id()
                    , updateText
                    , localDateTime
                    , false));
        }
    }

    @Override
    public void update(NotificationTask notificationTask) {
        logger.info("\"Update\" notificationTask method was invoke...");
        notificationTaskRepository.save(notificationTask);
    }

    @Override
    public void write(NotificationTask notificationTask) {
        logger.info("\"Write\" notificationTask method was invoke...");
        notificationTask.setId(null);
        notificationTaskRepository.save(notificationTask);
    }

}
