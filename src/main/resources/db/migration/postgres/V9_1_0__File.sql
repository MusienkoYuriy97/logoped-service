CREATE TABLE IF NOT EXISTS file (
    id INT GENERATED BY DEFAULT AS IDENTITY,
    file_name VARCHAR (255) NOT NULL,
    download_link VARCHAR (255) NOT NULL,
    logoped_id INT NOT NULL,
    user_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (logoped_id) REFERENCES logoped(id),
    FOREIGN KEY (user_id) REFERENCES users(id));