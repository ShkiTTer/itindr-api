create table chats
(
    id             uuid primary key,
    first_user_id  uuid references users (id) on delete cascade,
    second_user_id uuid references users (id) on delete cascade
);

create table messages
(
    id         uuid primary key,
    "text"     text,
    created_at bigint not null,
    chat_id    uuid references chats (id) on delete cascade,
    user_id    uuid references users (id) on delete cascade
);

create table attachments
(
    id         uuid primary key,
    "file"     varchar(150) not null,
    message_id uuid references messages (id) on delete cascade
);