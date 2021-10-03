create table owner (
    owner_id uuid not null primary key,
    owner_type int not null,
    unique (owner_id, owner_type)
);

alter table company drop constraint UK_2vfxe8sd8nhvd1lpn6pvw35gx;
alter table company drop column creator_id;

alter table company add column owner_owner_id uuid not null;
alter table company add constraint UK_owner_id_unique unique (owner_owner_id);
alter table company add constraint FK_owner_id_to_owner FOREIGN KEY (owner_owner_id) references owner;