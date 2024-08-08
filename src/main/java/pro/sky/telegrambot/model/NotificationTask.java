package pro.sky.telegrambot.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification_task")
public class NotificationTask {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "notification_scheduler")
    private LocalDateTime notificationScheduler;

    @Column(name = "sending")
    private boolean sending;

    public NotificationTask() {
    }

    public NotificationTask(Long chatId, String messageText
            , LocalDateTime notificationScheduler, boolean sending) {
        this.chatId = chatId;
        this.messageText = messageText;
        this.notificationScheduler = notificationScheduler;
        this.sending = sending;
    }

    public NotificationTask(Long id, Long chatId, String messageText
            , LocalDateTime notificationScheduler, boolean sending) {
        this.id = id;
        this.chatId = chatId;
        this.messageText = messageText;
        this.notificationScheduler = notificationScheduler;
        this.sending = sending;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getNotificationScheduler() {
        return notificationScheduler;
    }

    public void setNotificationScheduler(LocalDateTime notificationScheduler) {
        this.notificationScheduler = notificationScheduler;
    }

    public boolean isSending() {
        return sending;
    }

    public void setSending(boolean sending) {
        this.sending = sending;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return sending == that.sending && Objects.equals(id, that.id)
                && Objects.equals(chatId, that.chatId)
                && Objects.equals(messageText, that.messageText)
                && Objects.equals(notificationScheduler, that.notificationScheduler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, messageText, notificationScheduler, sending);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", messageText='" + messageText + '\'' +
                ", notificationScheduler=" + notificationScheduler +
                ", sending=" + sending +
                '}';
    }

}
