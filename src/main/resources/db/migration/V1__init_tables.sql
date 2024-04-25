-- create author table

CREATE TABLE author (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255) NOT NULL, -- Assuming a max length of 255 for the name
    biography TEXT -- Assuming a biography can be of variable length, we use TEXT
);

-- create book table

CREATE TABLE book (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR NOT NULL,
    author_id uuid,
    isbn VARCHAR NOT NULL UNIQUE,
    publication_year INT NOT NULL,

    FOREIGN KEY (author_id)
        REFERENCES author (id)
);

-- create rental table

CREATE TABLE rental (
    book_id uuid NOT NULL,
    renter_name VARCHAR(255), -- Assuming a max length of 255 for the renter's name
    rental_date DATE,
    return_date DATE,

    PRIMARY KEY (book_id),
    FOREIGN KEY (book_id)
        REFERENCES book (id)
);
