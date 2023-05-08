create table client
(
    user_id     int auto_increment
        primary key,
    username    varchar(30)                  not null,
    password    varchar(255)                 not null,
    activated   varchar(15) default 'ACTIVE' not null,
    cash        double      default 0        null,
    role        varchar(30) default 'USER'   not null,
    status_type varchar(15) default 'ACTIVE' not null comment 'ACTIVE, BANNED',
    constraint user_username_key
        unique (username)
)
    engine = InnoDB;

create table card
(
    card_id     int auto_increment
        primary key,
    card_cash   float default 0 null,
    user_id     int             null,
    cv          int             null,
    card_number varchar(16)     null,
    constraint card_client_null_fk
        foreign key (user_id) references client (user_id)
)
    engine = InnoDB;

create table chat
(
    chat_id int auto_increment
        primary key,
    user_id int      not null,
    message longtext not null,
    date    datetime null,
    constraint chat_client_null_fk
        foreign key (user_id) references client (user_id)
            on delete cascade
)
    engine = InnoDB;

create table company
(
    company_id        int auto_increment
        primary key,
    company_name      varchar(50)                   not null,
    company_info      longtext                      not null,
    stock_price       double                        not null,
    counter_of_stocks int                           not null,
    owner_id          int                           null,
    company_status    varchar(30) default 'WAITING' null,
    constraint company_pk
        unique (company_name),
    constraint company_client_null_fk
        foreign key (owner_id) references client (user_id)
            on update cascade on delete cascade
)
    engine = InnoDB;

create table transaction
(
    transaction_id     int auto_increment
        primary key,
    transactional_type varchar(50) not null,
    user_id            int         not null,
    company_id         int         not null,
    amount             int         not null,
    date               datetime    not null,
    price              float       not null,
    constraint transaction_company_null_fk
        foreign key (company_id) references company (company_id),
    constraint transaction_company_null_null_fk
        foreign key (user_id) references client (user_id)
)
    engine = InnoDB;

create table wallet
(
    wallet_id     int auto_increment
        primary key,
    user_id       int             not null,
    currency_type varchar(30)     not null,
    amount        float default 0 not null,
    constraint wallet_client__fk
        foreign key (user_id) references client (user_id)
)
    engine = InnoDB;