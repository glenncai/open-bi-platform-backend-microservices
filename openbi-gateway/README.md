# Run in Docker

## Package

```bash
mvn clean install -DskipTests
```

## Build

```bash
docker build -t openbi/gateway:0.0.1 .
```

## Run

```bash
docker run -d -p8000:8000 -e CONFIG_SERVER_URL=host.docker.internal -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name openbi-gateway [image_id]
```

# Gateway Module

Add `-Dreactor.netty.http.server.accessLogEnabled=true` to VM options to enable access logging for better debugging
experience.