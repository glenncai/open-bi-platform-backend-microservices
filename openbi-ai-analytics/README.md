# Run in Docker

## Package

```bash
mvn clean install -DskipTests
```

## Build

```bash
docker build -t openbi/user-analytics:0.0.1 .
```

## Run

```bash
docker run -d -p8003:8003 -e CONFIG_SERVER_URL=host.docker.internal -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name openbi-analytics-service [image_id]
```