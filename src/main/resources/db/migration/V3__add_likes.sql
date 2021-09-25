create table likes
(
    id     uuid primary key,
    type   varchar(10) not null,
    "from" uuid references users (id) on delete cascade,
    "to"   uuid references users (id) on delete cascade
);