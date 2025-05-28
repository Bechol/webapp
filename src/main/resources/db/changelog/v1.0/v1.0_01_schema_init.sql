-- liquibase formatted sql

-- changeset id:01_1_user author:bo
create table if not exists ${prefix}_user(
    telegram_id bigint not null,
    last_name varchar(255),
    first_name varchar(255),
    user_name varchar(255),
    last_login timestamp,
    language_code varchar(255),
    primary key(telegram_id)
);