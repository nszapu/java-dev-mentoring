create table if not exists customers (
    name varchar(255) primary key,
    address varchar(255),
    phone_number varchar(255)
);
create table if not exists orders (
	id bigserial primary key,
	customer_name varchar(255),
	pizza varchar(255),
	comment varchar(255),
	status varchar(255),
	date date,
	foreign key (customer_name) references customers (name)
);