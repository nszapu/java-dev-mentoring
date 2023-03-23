drop table if exists events cascade;

create table events (
    id serial primary key,
    title varchar(30),
    date date,
	ticket_price numeric(20, 2)
);