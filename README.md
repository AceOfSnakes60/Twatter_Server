
![Logo]((https://github.com/CodecoolGlobal/el-proyecte-grande-sprint-1-java-AceOfSnakes60/twatter-logo.png?raw=true))


# TWITTER CLONE (BACKEND)

This is a server application for the twitter clone site.
- Client application: https://github.com/CodecoolGlobal/epg-2-java-AceOfSnakes60


## Authors

- Piotr Maślanka [@AceOfSnakes60](https://github.com/AceOfSnakes60)


## Features

- Creating and storing users
- Authorization and Authentication of Users
- Adding posts (twatts)
- Creating groups


## Tech Stack

**Server:** Java, Hibernate, Spring Boot, Spring Security, REST API


## API Reference

### Twatts (posts) API

#### Get 10 latest posts by page number

```http
  GET /twatts
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `page`    |   `int`  | **Required**. Page number  |

#### Get post by id

```http
  GET /twatts/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long`   | **Required**. Id of item to fetch |

#### Get post by user

```http
  GET /twatts/user/${username}
```

| Parameter | Type     | Description                                    |
| :-------- | :------- | :--------------------------------------------- |
| `username`| `String` | **Required**. name of user the posts belong to |


#### Add new post

```http
  POST /twatts/
```

#### Delete post by id

```http
  DELETE /twatts/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Long`   | **Required**. Id of item to delete|

### User API

#### Get user by id

```http
  GET /user/${id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id`      |  `Long`  | **Required**. User id      |

#### Get current user by Access token

```http
  GET /user/me
```

#### Add a friend to given user

```http
  POST /{userId}/addFriend/{friendId}
```

#### Delete a friend from given user

```http
  DELETE /{userId}/addFriend/{friendId}
```

### Group API

#### Get all groups

```http
  GET /groups/
```

#### Add a group

```http
  POST /groups/
```

#### Get group by id

```http
  GET /groups/{groupId}
```

#### Delete a group by id

```http
  DELETE /groups/{groupId}
```

####Add a user to group

```http
  POST /{groupId}/addUser/{userId}
```

####Remove a user from group

```http
  DELETE /{groupId}/addUser/{userId}
```
## Used By

This project is used by its Frontend equivalent :

-  https://github.com/CodecoolGlobal/epg-2-java-AceOfSnakes60

