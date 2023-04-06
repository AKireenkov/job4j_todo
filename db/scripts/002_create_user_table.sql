create TABLE users (
   id SERIAL PRIMARY KEY,
   name TEXT,
   login unique TEXT,
   password TEXT
);