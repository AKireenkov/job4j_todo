create TABLE task_and_category (
        task_id int not null REFERENCES tasks(id),
        category_id int not null REFERENCES category(id)
        );