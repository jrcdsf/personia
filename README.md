# Personia Demo

Author: Jose Roberto Filho (jrcdsf@gmail.com)
Date: August 1 2022

## Description

Personia API is designed to validate and setup a company hierarchy and generate employee hierarchy reports.


## Project setup

- Open project into IntelliJ IDE (optional)

- Run the startup script:

`$ bash ./startup.sh`

- Import the Postman collection present in folder ./postman/ 

- Use the option **Signup** to create a valid user:

POST http://localhost:8080/signup

JSON body:
`{
"fullName": "Jose Roberto",
"email": "jose@fakeemail.com",
"password": "123456"
}`

- Next use the option **Login** to authenticate your user and receive a token:

POST http://localhost:8080/login

JSON body:
`{
"email": "jose@fakeemail.com",
"password": "123456"
}`

NOTE: the token will be automatically set for the next options due to Postman automation

- Now you can use the option **Create hierarchy** to setup your company hierarchy:

POST http://localhost:8080/employees

JSON body: 
`{
"Pete": "Nick",
"Barbara": "Nick",
"Nick": "Sophie",
"Sophie": "Jonas"
}`

header: 
`'Authorization: Bearer <token>`

- Finally use the option **Generate employee reports** to generate a full hierarchy report or a report by employee:


GET http://localhost:8080/employees

header:
`'Authorization: Bearer <token>`

GET http://localhost:8080/employees?name=Pete

header:
`'Authorization: Bearer <token>`



## Technologies

Java 11, Spring Boot, Spring Security, JWT, Postgresql, Gradle, Docker, Junit


