# Description #

Secured restful API to create and list articles

# Prerequisites #

* Java 17;
* Apache Maven 3;
* Docker 20.10

# How to build #

```
mvn package
```

# How to start #

```
docker compose up
```

# Example #

**Get admin user:**
```
curl --request GET 'http://localhost:8080/user/3dccddc9-a5dd-459d-b476-cd7e42cba8eb' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' 
```

**Register new user:**
```
curl --request POST 'http://localhost:8080/user/registration' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "writer",
    "password": "writer",
    "passwordConfirm": "writer"
}'
```

**Save article:**
```
curl --request POST 'http://localhost:8080/article/' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "test article",
    "content": "the best new article",
    "datePublishing": "2022-10-04T15:45Z"
}'
```

**Get article:**
```
curl --request GET 'http://localhost:8080/user/<article-id>' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' 
```

**Update article:**
```
curl --request PUT 'http://localhost:8080/article/<article-id>' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "updated name of article",
    "content": "updated content",
    "datePublishing": "2022-10-04T15:50Z"
}'
```

**Delete article:**
```
curl --request DELETE 'http://localhost:8080/user/<article-id>' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' 
```

**Get page:**
```
curl --request GET 'http://localhost:8080/article/?page=1&size=1' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' 
```

**Get statistic:**
```
curl --request GET 'http://localhost:8080/statistic/' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--header 'Content-Type: application/json' \
--data-raw '"2017-09-17T11:45Z"'
```


