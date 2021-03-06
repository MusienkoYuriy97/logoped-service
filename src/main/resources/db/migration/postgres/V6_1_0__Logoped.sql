CREATE TABLE IF NOT EXISTS logoped (
    id INT GENERATED BY DEFAULT AS IDENTITY,
    user_id INT NOT NULL UNIQUE,
    education VARCHAR (255) NOT NULL,
    work_place VARCHAR (255) NOT NULL,
    work_experience INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id));