create table clients (
                         id int not null auto_increment primary key,
                         name varchar(255) not null,
                         dob datetime not null
);

create table accounts (
                          id int not null auto_increment primary key,
                          number varchar(20) not null unique,
                          balance bigint not null,
                          client_id int not null,
                          foreign key (client_id) references clients(id)
);

CREATE TABLE  cards (
                        id int not null auto_increment primary key,
                        number varchar(16) not null unique,
                        confirmed boolean not null default false,
                        account_id int not null,
                        foreign key (account_id) references accounts(id)
);

CREATE TABLE put_operations (
                                id int not null auto_increment primary key,
                                amount bigint not null,
                                account_id int not null,
                                status enum('waiting', 'confirmed', 'rejected') default 'waiting',
                                foreign key (account_id) references accounts(id)
);


insert into clients (name, dob) values ('Джордж Оруэлл', '1903-06-25');
insert into accounts (number, balance, client_id) VALUES ('6566666912345678910', 500, 1);
insert into cards (number, account_id) VALUES ('7887', 1);

drop table ACCOUNTS, cards, clients;
drop table put_operations;

select * from cards;
select * from accounts;
select card0_.id as id1_1_0_, card0_.number as number2_1_0_ from cards card0_ where card0_.id=1;

update cards SET confirmed = true where confirmed = false;