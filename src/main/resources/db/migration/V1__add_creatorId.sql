alter table company add column creator_id uuid not null;
alter table company add constraint UK_2vfxe8sd8nhvd1lpn6pvw35gx unique (creator_id);
