CREATE TABLE clients (
                         id bigint not null auto_increment primary key,
                         name varchar(255) not null,
                         dob datetime not null
);

CREATE TABLE accounts (
                          id bigint not null auto_increment primary key,
                          number varchar(20) not null unique,
                          balance decimal(19, 4) not null,
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
                         amount decimal(19, 4) not null,
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
                          amount decimal(19, 4) not null,
                          account_id int not null,
                          partner_id int not null,
                          purpose varchar(500) not null,
                          status enum('waiting', 'confirmed', 'rejected') default 'waiting',
                          foreign key (account_id) references accounts(id),
                          foreign key (partner_id) references partners(id)
);