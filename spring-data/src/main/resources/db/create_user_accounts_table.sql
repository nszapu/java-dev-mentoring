drop table if exists user_accounts;

create table user_accounts (
	id serial primary key,
	user_id integer,
	balance numeric(20, 2),
	foreign key (user_id) references users (id)
);