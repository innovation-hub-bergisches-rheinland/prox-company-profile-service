alter table company add column facebook_handle varchar(255);
alter table company add column twitter_handle varchar(255);
alter table company add column instagram_handle varchar(255);
alter table company add column xing_handle varchar(255);
alter table company add column linked_in_handle varchar(255);

insert into company (id, facebook_handle) select c.company_id, c.account from company_social_media c where type = 0;
insert into company (id, twitter_handle) select c.company_id, c.account from company_social_media c where type = 1;
insert into company (id, instagram_handle) select c.company_id, c.account from company_social_media c where type = 2;
insert into company (id, xing_handle) select c.company_id, c.account from company_social_media c where type = 3;
insert into company (id, linked_in_handle) select c.company_id, c.account from company_social_media c where type = 4;

drop table company_social_media;