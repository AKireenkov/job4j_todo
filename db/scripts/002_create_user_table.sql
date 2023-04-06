create TABLE users (
   id SERIAL PRIMARY KEY,
   name TEXT,
   login TEXT unique,
   password TEXT
);