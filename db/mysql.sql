create table user_profile
(
    id           int auto_increment
        primary key,
    username     varchar(50)                        not null,
    email        varchar(100)                       not null,
    fullname     varchar(255)                       null,
    password     varchar(200)                       not null,
    avatar       varchar(200)                       null,
    sex          varchar(45)                        not null,
    created_time datetime default CURRENT_TIMESTAMP null,
    birthday     datetime                           null
);

create table friend
(
    id           int auto_increment
        primary key,
    sender_id    int                                not null,
    receiver_id  int                                not null,
    status       varchar(45)                        not null,
    created_time datetime default CURRENT_TIMESTAMP null,
    constraint friend_receiver_id
        foreign key (receiver_id) references user_profile (id),
    constraint friend_sender_id
        foreign key (sender_id) references user_profile (id)
);

create index friend_receiver_id_idx
    on friend (receiver_id);

create index friend_sender_id_idx
    on friend (sender_id);

create table message
(
    id           int auto_increment
        primary key,
    sender_id    int                                not null,
    receiver_id  int                                not null,
    type         varchar(45)                        not null,
    content      text                               null,
    status       varchar(45)                        null,
    created_time datetime default CURRENT_TIMESTAMP null,
    constraint message_receiver_id
        foreign key (receiver_id) references user_profile (id),
    constraint message_sender_id
        foreign key (sender_id) references user_profile (id)
);

create index message_receiver_id_idx
    on message (receiver_id);

create index message_sender_id_idx
    on message (sender_id);

