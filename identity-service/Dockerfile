# IDENTITY SVC DOCKERFILE

# --- STAGE 1: BUILD ---
FROM golang:1.25-alpine AS build

ENV CGO_ENABLED=0

# install build dependencies
RUN apk add --no-cache git ca-certificates tzdata

WORKDIR /app

# install swag cli
RUN go install github.com/swaggo/swag/cmd/swag@latest

COPY go.mod go.sum ./
RUN go mod download

COPY cmd ./cmd
COPY internal ./internal

RUN swag init -g cmd/lets.go --output docs --parseDependency

# build the binary
RUN GOOS=linux GOARCH=amd64 go build \
    -ldflags='-w -s' \
    -o /app/identity-service \
    ./cmd/lets.go

# --- STAGE 2: RUNTIME ---
FROM alpine:3.19 AS final

# for https
RUN apk --no-cache add ca-certificates

RUN addgroup -S appgroup && \
    adduser -S -G appgroup appuser

WORKDIR /app

# Copy from build stage
COPY --from=build /app/identity-service .
COPY --from=build /app/docs ./docs

RUN chown -R appuser:appgroup .

USER appuser

EXPOSE 8080

ENTRYPOINT ["./identity-service"]
