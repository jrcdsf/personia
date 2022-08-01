# Personia API Demo

Author: Jose Roberto Filho (jrcdsf@gmail.com)
Date: August 1 2022

## Description

Personia API is a service designed to validate and setup a company hierarchy and generate employee hierarchy reports.

Before using the service you must create a valid user and login using the provided credentials since the access to the 
API is authenticated.


## Project setup

- Open project into IntelliJ IDE (optional)

- Run the startup script:

`$ bash startup.sh`

- Import the Postman DEV environment file present in folder ./postman/


    DEV.postman_environment.json


- Import the Postman collection file present in folder ./postman/


    Personia API - Security.postman_collection.json


- Use the option **Signup** to create a valid user:

POST http://localhost:8080/signup

Example body:
`{
"fullName": "Jose Roberto",
"email": "jose@fakeemail.com",
"password": "123456"
}`

- Next use the option **Login** to authenticate your user and receive a token:

POST http://localhost:8080/login

Example body:
`{
"email": "jose@fakeemail.com",
"password": "123456"
}`

NOTE: the token returned in the response will be automatically set for the next options due to Postman automation.

- Now you can use the option **Create hierarchy** to setup your company hierarchy:

POST http://localhost:8080/employees

Example body: 
`{
"Pete": "Nick",
"Barbara": "Nick",
"Nick": "Sophie",
"Sophie": "Jonas"
}`

header: 
`'Authorization: Bearer <token>`

- Finally use the option **Generate employee reports** to generate a full hierarchy report or a hierarchy report by employee:
  - Full hierarchy report:

    GET http://localhost:8080/employees
  
    header:`Authorization: Bearer <token>`
  - Employee hierarchy report

    GET http://localhost:8080/employees?name=<employee_name>

    header:`Authorization: Bearer <token>`

    
## Technologies

Java 11, Spring Boot, Spring Security, JWT, Postgresql, Gradle, Docker, Junit, Mockk


