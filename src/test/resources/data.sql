INSERT INTO users (first_name, last_name, email, phone_number, password, user_status)
VALUES (
           'Юрий',
           'Мусиенко',
           '97musienko@gmail.com',
           '+375298344491',
           '$2y$12$QKQ2d03.6SCPEQhLlaZusOJT9Q/ODqnvFHT6JCdpqvxKeXHa7KviG',
           'ACTIVE'
       );

INSERT INTO user_role (role_name)
VALUES (
           'ROLE_USER'
       ),
       (
           'ROLE_LOGOPED'
       ),
       (
           'ROLE_ADMIN'
       );

INSERT INTO users_user_role (user_id, user_role_id)
VALUES (
           '1',
           (SELECT u.id FROM user_role as u  WHERE u.role_name='ROLE_USER')
       ),
       (
           '1',
           (SELECT u.id FROM user_role as u  WHERE u.role_name='ROLE_ADMIN')
       );
