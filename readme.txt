Please download, unzip and run the api.

On the boot up the in-memory H2 db will load the initial records.

The api can run on a docker container by running the following 3 commands in Intellij terminal.

mvnw spring-boot:build-image
docker build -t payment-card-cost .
docker run -p 8080:8080 payment-card-cost

or just click the run button in Intellij ide. 

Navigate to http://localhost:8080/docs and test the endpoints.

