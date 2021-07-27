CREATE TABLE IF NOT EXISTS users_user_roles (
    user_id int8 NOT NULL ,
    user_roles_id int8 not null,
        foreign key (user_roles_id) references user_role(id),
        foreign key (user_id) references users(id));