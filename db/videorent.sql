CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE', 'OTHER');

CREATE TABLE customers(
    customer_id SERIAL PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    gender gender_enum NOT NULL,
    address_id INT REFERENCES addresses(address_id)
);

CREATE TABLE addresses(
    address_id SERIAL PRIMARY KEY,
    street VARCHAR(100) NOT NULL,
    house_number VARCHAR(10) NOT NULL,
    postal_code VARCHAR(10) NOT NULL,
    city VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL
);

CREATE TABLE movies(
    movie_id SERIAL PRIMARY KEY,
    title VARCHAR(100) UNIQUE NOT NULL,
    genre VARCHAR(100)
);

CREATE TABLE customer_movies(
    PRIMARY KEY (movie_id, customer_id),
    movie_id INT REFERENCES movies(movie_id),
    customer_id INT REFERENCES customers(customer_id)
);
