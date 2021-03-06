CREATE TABLE IF NOT EXISTS users (
    id INT GENERATED BY DEFAULT AS IDENTITY,
    first_name VARCHAR (255) NOT NULL,
    last_name VARCHAR (255) NOT NULL,
    email VARCHAR (255) UNIQUE NOT NULL,
    phone_number VARCHAR (255) UNIQUE NOT NULL,
    password VARCHAR (255) NOT NULL,
    user_status VARCHAR (255) NOT NULL,
    PRIMARY KEY (id));


INSERT INTO users (first_name, last_name, email, phone_number, password, user_status)
VALUES (
        'Юрий',
        'Мусиенко',
        '97musienko@gmail.com',
        '+375298344491',
        '$2y$12$QKQ2d03.6SCPEQhLlaZusOJT9Q/ODqnvFHT6JCdpqvxKeXHa7KviG',
        'ACTIVE'
       );