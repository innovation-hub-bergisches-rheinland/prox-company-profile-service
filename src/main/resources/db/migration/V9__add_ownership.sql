alter table company add column owner_id uuid not null;
alter table company add constraint UK_owner_id_unique unique (owner_id);

insert into company (id, owner_id) select id, creator_id from company;

alter table company drop constraint UK_2vfxe8sd8nhvd1lpn6pvw35gx;
alter table company drop column creator_id;