# Intelligent Tutoring System
Software Architecture Project - Microservices-based tutoring platform.

## Architecture
- **identity-service**: Go + Gin (port 8080)
- **course-service**: Java 21 + Spring Boot (port 8081)
- **postgres-db**: PostgreSQL 18
- **minio**: Object storage (API: 9000, Web UI: 9001)

All services communicate through Docker network `its_network`.

## Prerequisites
- Docker & Docker Compose (or Podman)
- For local Go development: Go 1.25
- For local Java development: Java 21

## Quick Start
```bash
git clone https://github.com/kchan139/intelligent-tutoring-system.git
cd intelligent-tutoring-system
cp .env.example .env
docker compose up -d
```

## Service URLs
- **Identity Service API Docs**: [http://localhost:8080/swagger/index.html](http://localhost:8080/swagger/index.html)
- **Course Service API Docs**: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
- **MinIO API**: [http://localhost:9000](http://localhost:9000) (S3-compatible endpoint)
- **MinIO Web Console**: [http://localhost:9001](http://localhost:9001) (login: `minio` / `minio-password`)

## Docker Commands

Start all services:
```bash
docker compose up -d
```

Start specific service:
```bash
docker compose up -d identity
docker compose up -d course
docker compose up -d minio
```

Stop and clean:
```bash
docker compose down -v
```

Rebuild after changes:
```bash
docker compose build
docker compose up -d
```

View logs:
```bash
docker compose logs -f
docker compose logs -f identity
docker compose logs -f course
docker compose logs -f minio
```

## Local Development (Native)

### identity-service (Go)
```bash
source .env
cd identity-service
go mod download
go run cmd/lets.go
```

### course-service (Java)
```bash
source .env
cd course-service
./mvnw clean package
java -jar target/course-service-0.0.1-SNAPSHOT.jar
```

## Database Access
```bash
docker exec -it its-postgres psql -U postgres-user -d postgres
```

## Troubleshooting

Check port conflicts:
```bash
lsof -i :5432
lsof -i :8080
lsof -i :8081
lsof -i :9000
lsof -i :9001
```

Clean rebuild:
```bash
docker compose down -v
docker compose build --no-cache
docker compose up -d
```

## Project Structure
```
.
├── compose.yml
├── course-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── identity-service/
│   ├── Dockerfile
│   ├── go.mod
│   ├── cmd/lets.go
│   └── internal/
└── postgres-db/
    ├── Dockerfile
    └── init.sql
```

