drop table if exists events cascade;

create table events (
    id serial primary key,
    title varchar(30),
    date date,
	ticket_price numeric(20, 2)
);

insert into events (title, date, ticket_price) values ('event1', now()::date, 50);

select * from events;

drop table if exists users;

create table users (
	id serial primary key,
	name varchar(30),
	email varchar(30)
);

insert into users (name, email) values ('Albert', 'albert@email.com');

select * from users;

drop table if exists tickets cascade;

create table tickets (
	id serial primary key,
	event_id integer,
	user_id integer,
	category varchar(10),
	place integer,
	foreign key (event_id) references events (id), 
	foreign key (user_id) references users (id)
);

insert into tickets (event_id, user_id, category, place) values (1, 1, 'PREMIUM', 1);

select * from tickets;

drop table if exists user_accounts;

create table user_accounts (
	id serial primary key,
	user_id integer,
	balance numeric(20, 2),
	foreign key (user_id) references users (id)
);

insert into user_accounts (user_id, balance) values (1, 1000);

select * from user_accounts;