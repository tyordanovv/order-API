# Order Processing API

## Overview of the application

Application simplifies the e-commerce order process taking the responsibility for:
* Checking for product availability 
* Product management **(add, edit and delete)**
* Order accepting and returning
* Tracking of delivery status
* Generating Invoices and Shipping tickets
* Sending confirmation and informational email to the customer

## Structure and tech stack

The application is built as a set of microservices to provide simple management 
and easy improvement of different features compared to monolith. Reactive
microservice architecture with asynchronous communication between the elements
ensures elastic, resilient and responsive work.