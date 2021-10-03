create table user_profile
(
    id           serial       not null
        constraint user_profile_pkey
            primary key,
    username     varchar(50)  not null,
    email        varchar(100) not null,
    fullname     varchar(255),
    password     varchar(200) not null,
    avatar       varchar(200),
    sex          varchar(45)  not null,
    created_time timestamp default now(),
    birthday     varchar(255)
);

alter table user_profile
    owner to kggkgqslnzulem;

create table friend
(
    id           serial      not null
        constraint friend_pkey
            primary key,
    sender_id    integer     not null
        constraint friend_sender_id
            references user_profile,
    receiver_id  integer     not null
        constraint friend_receiver_id
            references user_profile,
    status       varchar(45) not null,
    created_time timestamp default now()
);

alter table friend
    owner to kggkgqslnzulem;

create index friend_receiver_id_idx
    on friend (receiver_id);

create index friend_sender_id_idx
    on friend (sender_id);

create table message
(
    id           serial      not null
        constraint message_pkey
            primary key,
    sender_id    integer     not null
        constraint message_sender_id
            references user_profile,
    receiver_id  integer     not null
        constraint message_receiver_id
            references user_profile,
    type         varchar(45) not null,
    content      text,
    status       varchar(45),
    created_time timestamp default now()
);

alter table message
    owner to kggkgqslnzulem;

create index message_receiver_id_idx
    on message (receiver_id);

create index message_sender_id_idx
    on message (sender_id);

