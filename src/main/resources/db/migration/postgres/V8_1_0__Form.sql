CREATE TABLE IF NOT EXISTS form (
    id INT GENERATED BY DEFAULT AS IDENTITY,
    logoped_id INT NOT NULL,
    phone_number VARCHAR (255) NOT NULL,
    description VARCHAR (255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (logoped_id) REFERENCES logoped(id));