# java-filmorate
Template repository for Filmorate project.
![diagram](https://github.com/EvgenyBelykh/java-filmorate/blob/main/filmorate%20(6).png)

## Code from dbdiagram.io:



  ``` 
  Project filmorate {
  database_type: 'H2'
}

Table users as U{
  id int [pk, increment]
  name varchar
  email varchar [unique]
  login varchar [not null]
  birthday timestamp
}

Table films as F {
  id int [pk, increment]
  name varchar
  description varchar
  release_date timestamp
  duration int
  rate int
  mpa varchar
}
  
Table film_genres {
  id_film int [pk]
  id_genre int [pk]
}

Table genres {
  id int [pk]
  name genre
}

Table mpa {
  id int [pk]
  name mpa
}

Table likes {
  id_user int [pk]
  id_film int [pk]
}

Table users_friends{
  id_user_one int [pk]
  id_user_two int [pk]
}

Ref: U.id < likes.id_user
Ref: F.id < likes.id_film
Ref: U.id < users_friends.id_user_one
Ref: U.id < users_friends.id_user_two
Ref: genres.id < film_genres.id_genre
Ref: F.id < film_genres.id_film
Ref: mpa.id > F.id
   ```
