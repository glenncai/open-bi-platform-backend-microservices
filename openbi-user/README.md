# Run in Docker

## Package

```bash
mvn clean install -DskipTests
```

## Build

```bash
docker build -t openbi/user-service:0.0.1 .
```

## Run

```bash
docker run -d -p8001:8001 -e CONFIG_SERVER_URL=host.docker.internal -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name openbi-user-service [image_id]
```