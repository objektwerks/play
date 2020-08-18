drop table todo if exists;
create table todo (id long primary key auto_increment, task varchar(128) not null);