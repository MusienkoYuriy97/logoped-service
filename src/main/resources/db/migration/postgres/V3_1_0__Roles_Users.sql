CREATE TABLE IF NOT EXISTS users_user_role (
    user_id int8 NOT NULL ,
    user_role_id int8 not null,
        foreign key (user_role_id) references user_role(id),
        foreign key (user_id) references users(id));

INSERT INTO users_user_role (user_id, user_role_id)
VALUES (
           '1',
           (SELECT u.id FROM user_role as u  WHERE u.role_name='ROLE_USER')
       ),
       (
        '1',
        (SELECT u.id FROM user_role as u  WHERE u.role_name='ROLE_ADMIN')
        );

