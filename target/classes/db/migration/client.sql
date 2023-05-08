CREATE TABLE IF NOT EXISTS client
(
    user_id   int auto_increment           NOT NULL PRIMARY KEY,
    username  VARCHAR(30)                  NOT NULL,
    password  VARCHAR(255)                 NOT NULL,
    activated BOOLEAN     default (0)  NOT NULL,
    cash      double      default (0),
    role      varchar(30) default ('USER') NOT NULL,
    constraint user_username_key
        unique (username)
);

