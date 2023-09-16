# Run in Docker

## Package

```bash
mvn clean install
```

## Build

```bash
docker build -t openbi/service-registry:0.0.1 .
```

## Run

```bash
docker run -d -p8761:8761 --name oepnbi-service-registry [image_id]
```
