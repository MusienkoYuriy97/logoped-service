CREATE TABLE IF NOT EXISTS logoped_category (
    logoped_id INT NOT NULL,
    category_id INT NOT NULL,
    FOREIGN KEY (logoped_id) REFERENCES logoped(id),
    FOREIGN KEY (category_id) REFERENCES category(id),
    UNIQUE(logoped_id, category_id));