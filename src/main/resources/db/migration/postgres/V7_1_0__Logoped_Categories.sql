CREATE TABLE IF NOT EXISTS logoped_categories (
    logoped_id INT NOT NULL ,
    categories_id INT not null,
    foreign key (logoped_id) references logoped(id),
    foreign key (categories_id) references category(id));