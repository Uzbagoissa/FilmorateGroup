CREATE TABLE IF NOT EXISTS directors
(
    id       INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name     VARCHAR(64) NOT NULL,
    CONSTRAINT   director_is_blank CHECK (name NOT LIKE ' ' AND name NOT LIKE '')
);

CREATE TABLE IF NOT EXISTS mpa
(
    id       INTEGER PRIMARY KEY,
    name     VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS genres
(
    id     INTEGER PRIMARY KEY,
    name   VARCHAR(64) NOT NULL
);

CREATE ALIAS IF NOT EXISTS getDate AS
    'java.util.Date getDate() {
    return new java.util.Date();
    }';

CREATE TABLE IF NOT EXISTS users
(
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         VARCHAR(64),
    email        VARCHAR(64) UNIQUE NOT NULL,
    login        VARCHAR(64) NOT NULL,
    birthday     TIMESTAMP,
    CONSTRAINT   email_ CHECK (email LIKE '%@%'),
    CONSTRAINT   login_is_blank CHECK (login NOT LIKE ' ' AND login NOT LIKE ''),
    CONSTRAINT   birthday_not_in_future CHECK (CAST(birthday AS DATE) <= CAST(getDate() AS DATE))
);

CREATE TABLE IF NOT EXISTS films
(
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         VARCHAR(64),
    description  VARCHAR(256),
    release_date TIMESTAMP,
    duration     INTEGER,
    rate         INTEGER,
    mpa          VARCHAR(64),
    CONSTRAINT   length_duration
                 CHECK (duration > 0),
    CONSTRAINT   length_description
                 CHECK (LENGTH(description) <= 200),
    CONSTRAINT   name_is_blank
                 CHECK (name NOT LIKE ' ' AND NOT NULL),
    CONSTRAINT   after_first_film
                 CHECK (CAST (release_date AS DATE) > (CAST('1895-12-28' AS DATE))),
    CONSTRAINT   mpas FOREIGN KEY (mpa) REFERENCES mpa (id)
);

CREATE TABLE IF NOT EXISTS likes
(
    id_user      INTEGER NOT NULL,
    id_film      INTEGER NOT NULL,
    CONSTRAINT   likes_id_user FOREIGN KEY (id_user) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT   likes_id_film FOREIGN KEY (id_film) REFERENCES films (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY  (id_user, id_film)
);

CREATE TABLE IF NOT EXISTS users_friends
(
    id_user_one  INTEGER NOT NULL,
    id_user_two  INTEGER NOT NULL,
    CONSTRAINT   friend_id_user FOREIGN KEY (id_user_one) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT   friend_id_user_two FOREIGN KEY (id_user_two) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY  (id_user_one, id_user_two)
);

CREATE TABLE IF NOT EXISTS film_genres
(
    id_film      INTEGER NOT NULL,
    id_genre     INTEGER NOT NULL,
    CONSTRAINT   films FOREIGN KEY (id_film) REFERENCES films (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT   genres FOREIGN KEY (id_genre) REFERENCES genres (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (id_film, id_genre)
);

<<<<<<< HEAD
CREATE TABLE IF NOT EXISTS reviews (
    review_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id INTEGER NOT NULL,
    film_id INTEGER NOT NULL,
    content TEXT NOT NULL,
    is_positive BOOLEAN NOT NULL DEFAULT TRUE,
    useful INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT fk_film_id FOREIGN KEY (film_id) REFERENCES films(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS review_like (
    review_id INTEGER,
    user_id INTEGER,
    CONSTRAINT fk_review_like_review_id FOREIGN KEY (review_id) REFERENCES reviews(review_id) ON DELETE CASCADE,
    CONSTRAINT fk_review_like_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS review_dislike (
    review_id INTEGER,
    user_id INTEGER,
    CONSTRAINT fk_review_dislike_review_id FOREIGN KEY (review_id) REFERENCES reviews(review_id) ON DELETE CASCADE,
    CONSTRAINT fk_review_dislike_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE

CREATE TABLE IF NOT EXISTS film_directors
(
    id_film      INTEGER NOT NULL,
    id_director  INTEGER NOT NULL,
    CONSTRAINT   films_dir FOREIGN KEY (id_film) REFERENCES films (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT   directors_dir FOREIGN KEY (id_director) REFERENCES directors (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (id_film, id_director)

);
