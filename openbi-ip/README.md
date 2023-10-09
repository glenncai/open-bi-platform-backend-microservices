# Run in Docker

## Package

```bash
mvn clean install -DskipTests
```

## Build

```bash
docker build -t openbi/ip-service:0.0.1 .
```

## Run

```bash
docker run -d -p8002:8002 -e CONFIG_SERVER_URL=host.docker.internal -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name openbi-ip-service [image_id]
```