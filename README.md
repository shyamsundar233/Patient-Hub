# Patient-Hub

# Docker

# Steps to be followed

docker pull --platform linux/x86_64 mysql:5.7

docker network create patienthub-mysql-net-1

docker run --name mysqldb2 --network patienthub-mysql-net-1 -e MYSQL_ROOT_PASSWORD=Bornjuly@2001 -e MYSQL_DATABASE=proddb -e MYSQL_PASSWORD=Bornjuly@2001 -d mysql:5.7

docker ps

docker exec -it #container-id# bash

docker build -t patienthubmysql-2 .

docker run --network patienthub-mysql-net-1 --name patienthubdb-container-2 -p 8084:8084 -d patienthubmysql-2
