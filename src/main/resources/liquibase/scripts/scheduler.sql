-- liquibase formatted sql

-- changeset yuriy-kolosov:1
CREATE TABLE notification_task
    (
    id                      SERIAL          PRIMARY KEY,    -- автоинкрементируемый идентификатор пользователя
    chat_id                 int8            NOT NULL,       -- идентификатор чата
    message_text            varchar(100)    NOT NULL,       -- текст сообщения пользователю
    notification_scheduler  TIMESTAMP       NOT NULL,       -- дата и время для отправки уведомления
    sending                 boolean         DEFAULT false   -- флаг подтверждения отправки уведомления
    );
