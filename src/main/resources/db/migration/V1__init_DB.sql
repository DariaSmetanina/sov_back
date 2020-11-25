create table access (
id_access bigint not null,
organization bigint,
user bigint,
primary key (id_access)) engine=InnoDB

create table account (
id_account bigint not null,
amount float,
comment varchar(255),
date varchar(255),
number varchar(255),
organization bigint,
status varchar(255),
primary key (id_account)) engine=InnoDB

create table hibernate_sequence (next_val bigint) engine=InnoDB

create table news (
id_news bigint not null,
date varchar(255),
files varchar(255),
importance varchar(255),
main_part varchar(255),
text varchar(255),
title varchar(255),
primary key (id_news)) engine=InnoDB

create table notification (
id_notification bigint not null,
date varchar(255),
importance varchar(255),
organization bigint,
text varchar(255),
primary key (id_notification)) engine=InnoDB

create table organization (
id_organization bigint not null,
director varchar(255),
inn varchar(255),
name varchar(255),
primary key (id_organization)) engine=InnoDB

create table user (
id_user bigint not null,
email varchar(255),
name varchar(255),
password varchar(255),
role varchar(255),
username varchar(255),
primary key (id_user)) engine=InnoDB
