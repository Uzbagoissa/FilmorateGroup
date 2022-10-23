# java-filmorate
Template repository for Filmorate project.
![diagram](https://github.com/EvgenyBelykh/java-filmorate/blob/main/filmorate%20(1)%20(1).png)

### Code from dbdiagram.io: ###

< Project filmorate {
  database_type: 'PostgreSQL'
}

Table users as U{
  id_user int [pk, increment]
  name varchar
  email email [unique]
  login varchar [not null]
  birthday date
  last_update datetime
}

Table films as F {
  id_film int [pk, increment]
  name varchar
  description varchar
  releaseDate date
  duration int
  id_genre int
  id_rating int
  last_update datetime
}

enum genre{
    COMEDY
    DRAMA
    CARTOON
    THRILLER
    DOCUMENTARY
    ACTION
  }
  
Table genre {
  id_genre int [pk]
  genre genre
  last_update datetime
}

enum rating{
    G
    PG
    PG13
    R
    NC17
}

Table rating {
  id_rating int [pk]
  rating rating
  last_update datetime
}

Table likes {
  id_user int [pk]
  id_film int [pk]
  last_update datetime
}

Table users_friends{
  id_user_one int [pk]
  id_user_two int [pk]
  id_requester int [note:'Кто запрашивал']
  id_status int
  last_update datetime
}

enum status{
  request
  confirmation
  rejected
  block
}

Table status{
  id_status int [pk]
  status status
  last_update datetime
}

Ref: F.id_genre < genre.id_genre 
Ref: F.id_rating < rating.id_rating
Ref: U.id_user < likes.id_user
Ref: F.id_film < likes.id_film
Ref: users_friends.id_status < status.id_status
Ref: U.id_user < users_friends.id_user_one
Ref: U.id_user < users_friends.id_user_two
>
