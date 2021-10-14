CREATE TABLE clients
(
    id   bigint       not null auto_increment primary key,
    name varchar(255) not null,
    dob  datetime     not null
);

CREATE TABLE accounts
(
    id        bigint         not null auto_increment primary key,
    number    varchar(20)    not null unique,
    balance   decimal(19, 4) not null default 0.0,
    client_id bigint         not null,
    foreign key (client_id) references clients (id)
);

CREATE TABLE cards
(
    id         bigint      not null auto_increment primary key,
    number     varchar(16) not null unique,
    confirmed  boolean     not null default false,
    account_id int         not null,
    foreign key (account_id) references accounts (id)
);

CREATE TABLE refills
(
    id         bigint                                          not null auto_increment primary key,
    amount     decimal(19, 4)                                  not null,
    account_id int                                             not null,
    status     enum ('waiting', 'confirmed', 'rejected') not null default ' waiting ',
    foreign key (account_id) references accounts (id)
);

CREATE TABLE partners
(
    id             bigint       not null auto_increment primary key,
    name           varchar(255) not null,
    inn            varchar(12)  not null,
    bik            varchar(9)   not null,
    account_number varchar(20)  not null,
    client_id      bigint       not null,
    foreign key (client_id) references clients (id)
);

CREATE TABLE payments
(
    id         bigint                                          not null auto_increment primary key,
    amount     decimal(19, 4)                                  not null,
    account_id int                                             not null,
    partner_id int                                             not null,
    purpose    varchar(500)                                    not null,
    status     enum ('waiting', 'confirmed', 'rejected') not null default ' waiting ',
    foreign key (account_id) references accounts (id),
    foreign key (partner_id) references partners (id)
);

insert into clients (id, name, dob)
values (1, 'Джордж Оруэлл', '1903 - 06 - 25 '),
       (2, 'Александр Грейам Белл', '1847 - 03 - 3 '),
       (3, 'Фрэнсис Бэкон', '1561 - 01 - 22 ');

insert into accounts (number, client_id)
VALUES ('60503441005177702114', 1),
       ('66993484894300321359', 1),
       ('73401302929236836862', 1),
       ('53949741505326033841', 2),
       ('65203725531042344549', 2),
       ('5014126846299463695', 2),
       ('78207572511996734597', 3),
       ('26731570625218542534', 3),
       ('61600582205750110968', 3);
