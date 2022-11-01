# java-filmorate
Template repository for Filmorate project.
![diagram](https://github.com/EvgenyBelykh/java-filmorate/blob/main/filmorate%20(4).png)

## Code from dbdiagram.io:



  ``` 
  Project filmorate {
  database_type: 'H2'
}

Table users as U{
  id_user int [pk, increment]
  name varchar
  email email [unique]
  login varchar [not null]
  birthday date
}

Table films as F {
  id_film int [pk, increment]
  name varchar
  description varchar
  release_date date
  duration int
  id_rating int
}

enum genre{
    COMEDY
    DRAMA
    CARTOON
    THRILLER
    DOCUMENTARY
    ACTION
  }
  
Table film_genres {
  id_film int [pk]
  id_genre int [pk]
}

Table genres {
  id_genre int [pk]
  genre genre
}

enum rating{
    G
    PG
    PG_13
    R
    NC_17
}

Table likes {
  id_user int [pk]
  id_film int [pk]
}

Table users_friends{
  id_user_one int [pk]
  id_user_two int [pk]
}

Ref: U.id_user < likes.id_user
Ref: F.id_film < likes.id_film
Ref: U.id_user < users_friends.id_user_one
Ref: U.id_user < users_friends.id_user_two
Ref: genres.id_genre > film_genres.id_genre
Ref: F.id_film < film_genres.id_film
   ```
