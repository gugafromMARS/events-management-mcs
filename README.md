# events-management-mcs

![Logo](https://www.moretimepa.co.uk/wp-content/uploads/shutterstock_378811030.jpg)

Welcome to my events management backend app, this is an app with microservices architecture, with Spring Cloud Open Feign communication, Spring Cloud Netflix Eureka Server for service registry with load balancer, Spring Cloud Api Gateway, Spring Cloud Circuit Breaker pattern and Retry pattern!
This app allow to create an event, create tickets for that event, create a ticket hub and manage the ticket sells for users request.

## Architecture

![ArchitectureImg](https://github.com/gugafromMARS/events-management-mcs/assets/116969206/65adf39b-a374-49e8-a3b8-848efad0e8ea)


## Technology

Here are some technology's used on this project.

* Java version 17

## Services

Services used.

* Github

## Getting started

1 - Run all this commands for build your docker containers and get all connections for any db

1.1 - Docker container for Ticket Hub db
```shell script
docker run -d -p 3307:3306 --name tickethub-mcs-db -v $(pwd)/tickethubsdata:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=tickethubdb mysql:latest
```
1.2 - Docker container for Tickets db
```shell script
docker run -d -p 3308:3306 --name tickets-mcs-db -v $(pwd)/ticketsdata:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=ticketsdb mysql:latest
```
1.3 - Docker container for Events db
```shell script
docker run -d -p 3309:3306 --name events-mcs-db -v $(pwd)/eventsdata:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=eventsdb mysql:latest
```
1.4 - Docker container for Users db
```shell script
 docker run -d -p 3310:3306 --name users-mcs-db -v $(pwd)/usersdata:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=usersdb mysql:latest
```

2- Now run your microservices

2.1 - Run Service Registry first!
2.2 - Run all other microservices

## App functionalitys

In this app you have several options :

* **Create**

  You can create an Event, tickets for the event, a ticket hub who going to sells the tickets for users, and create Users.

  In this api you can register your glucose, and the total of carbohydrates in grams you are going to eat, and the app is going to calculate the insulin indices you put on your diabetic details and calculate the total of insulin you need to give!
* **Get**

  Get the all events available, get event by id and get ticket if available.
 
## Authors

**gugafromMars**

[Github-gugafromMars](https://github.com/gugafromMARS)

Thanks to visiting and happy coding!