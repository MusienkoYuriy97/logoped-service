CREATE TABLE IF NOT EXISTS logoped (
    id INT GENERATED BY DEFAULT AS IDENTITY,
    user_id INT NOT NULL,
    education VARCHAR (255),
    PRIMARY KEY (id),
    foreign key (user_id) references users(id));