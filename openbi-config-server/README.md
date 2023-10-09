# Run in Docker

## Package

```bash
mvn clean install -DskipTests
```

## Build

```bash
docker build -t openbi/config-server:0.0.1 .
```

## Run

```bash
docker run -d -p8080:8080 -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name openbi-config-server [image_id]
```
