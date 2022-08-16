# DISBURSEMENTS PROJECT
This project provides a possible solution to https://gist.github.com/francesc/33239117e4986459a9ff9f6ea64b4e80

# Requirements
- Java 8+
- Mongodb

## HOWTO RUN THE SERVICE
- Create the JAR with `./gradlew build`
- The database must be a MongoDB and have the following three collections: `merchants`, `orders` and `disbursements`
- The collections `orders` and `merchants` should have some merchants and orders persisted.
### With docker
- Build the docker image and run a container with it inside with: `docker-compose up`
- It should start the disbursements container and a mongodb container.
- Add the collections to the mongodb database.
### Without docker:
- Set the right URL to a MongoDB in `resources/.env-local.env`.
- Add the collections to the mongodb database.
- Run `java -jar build/libs/disbursements-1.0-SNAPSHOT.jar`

- Access the swagger docs here: http://localhost:8888/swagger-ui
- Provided endpoints:
  - GET `http://localhost:8888/v1/disbursements?week=1&year=2018`
    - Exposes the disbursements for a given merchant on a given week. If no merchant is provided return for all of them.
  - POST `http://localhost:8888/v1/disbursements/2022/33`
    - Calculates and persists the disbursements per merchant on a given week

# NOTES
## Architecture
**Hexagonal architecture** has been applied. It's the most flexible for these kind of projects
that can grow huge. Besides, it allows me to test the business logic without knowing any
details about the infrastructure.

## Use cases
For the sake of simplicity, both use cases (**calculate disbursements** and **API endpoint**) have been implemented in the same
microservice.

However, as the calculation of disbursements may be a heavy process, it should be deployed in its own microservice
(¿billing?) and be launched weekly with a jenkins task, cron... calling `/v1/disbursements/{year}/{week}`

A better solution would be to use a message broker. The billing process would be listening to a queue in an exchange.
Once a message with the year and week is published to the queue, the billing process (`DisbursementAppService.calculateDisbursements`)
would start running. Once it is completed, it should publish another message to another queue reporting the success/failure.

This billing process is idempotent in the sense that, if run several times, it will always give the same result.
Once it has been calculated, it is persisted, and running the process again won't do anything but retrieve the persisted data.
Some validations should be added depending on the desired logic:
- Does it make sense to persist the weekly disbursements before the week is finished? I assumed the answer is NO, so I
- throw an exception when trying to calculate a week in the future.

## Tests
- I'm aware that more tests are needed. I just added some basic ones. An important missing test is the one that guarantees that `OrderRepository.findCompletedByMerchantDateRange` JUST returns completed orders within the specified period
