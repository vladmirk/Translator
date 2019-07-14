drop table TransLog if exists;
create table TransLog(Id bigint auto_increment, TransDate timestamp,  Client varchar(255), FromText nvarchar(255),
  FromLang varchar(2),  ToLang varchar(2), Translation nvarchar(255));