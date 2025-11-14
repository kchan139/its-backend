# Intelligent Tutoring System

Software Architecture Project - Microservices-based tutoring platform.

## Architecture

- **identity-service**: Go + Gin (port 8080)
- **course-service**: Java 21 + Spring Boot (port 8081)
- **postgres-db**: PostgreSQL 18

All services communicate through Docker network `its_network`.

## Prerequisites

- Docker & Docker Compose (or Podman)
- For local Go development: Go 1.25
- For local Java development: Java 21

## Quick Start

### Clone and configure

```bash
git clone https://github.com/kchan139/intelligent-tutoring-system.git
cd intelligent-tutoring-system
cp .env.example .env
docker compose up -d
```

## API Documentation

- **Identity Service**: [http://localhost:8080/swagger/index.html](http://localhost:8080/swagger/index.html)
- **Course Service**: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

Edit `.env` if needed (defaults work fine for local dev).

## Local Development (Using Containers)

You don’t need Go or Java installed — only Docker or Podman.
All services are defined in `compose.yml`.

### Usage

Start all services:

```bash
docker compose up -d
```

**Start a specific service (and dependencies like the database):**

```bash
docker compose up -d identity
docker compose up -d course
```

Stop and remove containers, volumes, and network:

```bash
docker compose down -v
```

Rebuild after code or Dockerfile changes:

```bash
docker compose build
docker compose up -d
```

Follow logs:

```bash
docker compose logs -f
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

## Logs

All services:
```bash
docker compose logs -f
```

Specific service:
```bash
docker compose logs -f identity
docker compose logs -f course
docker compose logs -f postgres-db
```

## Database Access

```bash
docker exec -it its-postgres psql -U postgres-user -d postgres
```

## Troubleshooting

### Port conflicts

Check if ports are in use:
```bash
lsof -i :5432
lsof -i :8080
lsof -i :8081
```

Change ports in `.env` if needed.

### Database not ready

Wait for health check:
```bash
docker compose logs postgres-db
```

Look for "ready to accept connections".

### Services won't start
```bash
docker compose down
docker compose up -d
```

### If you're not sure

Clean rebuild:
```bash
docker compose down -v
docker compose build --no-cache
docker compose up -d
```

## Project Structure

```
.
├── compose.yml                 # Docker Compose configuration
├── .env.example                # Environment variables template
├── course-service/
│   ├── Dockerfile
│   ├── pom.xml                 # Maven dependencies
│   └── src/                    # Java source code
├── identity-service/
│   ├── Dockerfile
│   ├── go.mod                  # Go dependencies
│   ├── cmd/
│   │   └── lets.go             # Application entry point
│   └── internal/               # Internal packages
│       ├── config/
│       ├── handlers/
│       ├── models/
│       └── routes/
└── postgres-db/
    ├── Dockerfile
    └── init.sql                # Database initialization
```
