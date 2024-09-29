create table articles
(
    id           bigserial
        primary key,
    author       varchar(255),
    title        varchar(255)             not null,
    description  text                     not null,
    url          text                     not null,
    source       varchar(255)             not null,
    image        text,
    category     varchar(100)             not null,
    language     varchar(10)              not null,
    country      varchar(10)              not null,
    published_at timestamp with time zone not null,
    saved_at     timestamp with time zone default now()
);

alter table articles
    owner to postgres;

create table category
(
    id   integer default nextval('tags_id_seq'::regclass) not null
        constraint tags_pkey
            primary key,
    name varchar(255)                                     not null
);

alter table category
    owner to postgres;

create table user_category
(
    user_id bigint not null
        constraint user_tags_user_id_fkey
            references users
            on delete cascade,
    tag_id  bigint not null
        constraint user_tags_tag_id_fkey
            references category
            on delete cascade,
    constraint user_tags_pkey
        primary key (user_id, tag_id)
);

alter table user_category
    owner to postgres;

create table users
(
    id           bigserial
        primary key,
    email        varchar(255) not null
        unique,
    full_name    varchar(255),
    language     varchar(10)  not null,
    last_sent_at timestamp with time zone
);

alter table users
    owner to postgres;

