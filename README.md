
# Mintyn-Task Consumer Documentation

&nbsp;

### Language :

	Java 11

### Framework :

	Springboot 2.7.4

### Database :

    Mysql Database
&nbsp;

### How to run :

#### Option 1 (Run in docker container)

If you have docker installed, bring up your terminal, navigate to the root directory of the project  and follow the steps below

* To build the code run the command below--

  docker build -t mintynconsumer .

* To run the code run the command below--

  docker run -it -p 1837:81 mintyconsumer

#### Option 2 (Run from IDE)

open the project in Intellij-IDEA and run or follow the guide in the link below for better explanation
https://www.youtube.com/watch?v=kQ6Zkb6s6mM

&nbsp;

### Link to Postman collection

https://we.tl/t-ZPdn9VyaQk

### Link to Swagger documentation
http://localhost:1837/swagger-ui.html#/


### Things I would have done better if I had more time

1. I would add an index to the product table as this would reduce the time spent in retrieving data.
2. I would write more test for the service class and improve test coverage of the controller class.