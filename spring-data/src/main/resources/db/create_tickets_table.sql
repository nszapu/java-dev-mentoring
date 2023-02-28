drop type if exists category cascade;

create type category as enum('STANDARD', 'PREMIUM', 'BAR');

drop table if exists tickets;

create table tickets (
	id serial primary key,
	event_id integer,
	user_id integer,
	category category,
	place integer,
	foreign key (event_id) references events (id),
	foreign key (user_id) references users (id)
);