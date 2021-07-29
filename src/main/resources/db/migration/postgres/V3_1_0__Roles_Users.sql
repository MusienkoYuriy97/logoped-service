CREATE TABLE IF NOT EXISTS users_user_role (
    user_id INT NOT NULL ,
    user_role_id INT NOT NULL ,
        FOREIGN KEY (user_role_id) REFERENCES user_role(id),
        FOREIGN KEY (user_id) REFERENCES users(id),
        UNIQUE(user_id, user_role_id));

INSERT INTO users_user_role (user_id, user_role_id)
VALUES (
           '1',
           (SELECT u.id FROM user_role as u  WHERE u.role_name='ROLE_USER')
       ),
       (
        '1',
        (SELECT u.id FROM user_role as u  WHERE u.role_name='ROLE_ADMIN')
        );

