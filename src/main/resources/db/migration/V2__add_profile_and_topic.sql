create table profile
(
    id           uuid primary key,
    user_name    varchar(100) not null,
    about_myself varchar(250),
    avatar       varchar(255),
    user_id      uuid references users (id) on delete cascade
);

create table topic
(
    id          uuid primary key,
    topic_title varchar(255)  not null
);

create table profile_topic
(
    profile_id uuid references profile (id) on delete cascade,
    topic_id uuid references topic (id) on delete cascade,
    constraint pk_profile_topic primary key (profile_id, topic_id)
);