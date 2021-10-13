create table clients (
                         id bigint not null auto_increment primary key,
                         name varchar(255) not null,
                         dob datetime not null
);

create table accounts (
                          id bigint not null auto_increment primary key,
                          number varchar(20) not null unique,
                          balance bigint not null,
                          client_id bigint not null,
                          foreign key (client_id) references clients(id)
);

CREATE TABLE  cards (
                        id bigint not null auto_increment primary key,
                        number varchar(16) not null unique,
                        confirmed boolean not null default false,
                        account_id int not null,
                        foreign key (account_id) references accounts(id)
);

CREATE TABLE refills (
                         id bigint not null auto_increment primary key,
                         amount bigint not null,
                         account_id int not null,
                         status enum('waiting', 'confirmed', 'rejected') default 'waiting',
                         foreign key (account_id) references accounts(id)
);

CREATE TABLE partners (
                          id bigint not null auto_increment primary key,
                          name varchar(255) not null,
                          inn varchar(12) not null,
                          bik varchar(9) not null,
                          account_number varchar(20) not null,
                          client_id bigint not null,
                          foreign key (client_id) references clients(id)
);

CREATE TABLE payments (
                          id bigint not null auto_increment primary key,
                          amount bigint not null,
                          account_id int not null,
                          partner_id int not null,
                          purpose varchar(500) not null,
                          status enum('waiting', 'confirmed', 'rejected') default 'waiting',
                          foreign key (account_id) references accounts(id),
                          foreign key (partner_id) references partners(id)
);

insert into clients (name, dob) values ('Джордж Оруэлл', '1903-06-25');
insert into accounts (number, balance, client_id) VALUES ('6566666912345678910', 500, 1);
insert into cards (number, account_id) VALUES ('7887', 1);

drop table ACCOUNTS, cards, clients;
drop table refills;
drop table partners;

select * from cards;
select * from accounts;
select * from clients;
select * from partners;

update cards SET confirmed = false where confirmed = true;