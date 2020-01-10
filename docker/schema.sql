create table pet
(
	id int auto_increment,
	created_at datetime null,
	updated_at datetime null,
	deleted_at datetime null,
	name varchar(100) null,
	s3_url varchar(200) null,
	constraint pet_pk
		primary key (id)
);