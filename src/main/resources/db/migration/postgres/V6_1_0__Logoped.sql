CREATE TABLE IF NOT EXISTS logoped (
    id INT GENERATED BY DEFAULT AS IDENTITY,
    user_id INT NOT NULL,
    education VARCHAR (255),
    work_place VARCHAR (255),
    work_experience INT,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id));