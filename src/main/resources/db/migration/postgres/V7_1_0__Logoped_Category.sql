CREATE TABLE IF NOT EXISTS logoped_category (
    logoped_id INT NOT NULL ,
    category_id INT not null,
    foreign key (logoped_id) references logoped(id),
    foreign key (category_id) references category(id));