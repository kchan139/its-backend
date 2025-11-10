# IDENTITY SVC DOCKERFILE

# --- STAGE 1: BUILD ---
FROM golang:1.25-alpine AS build

# install build dependencies
RUN apk add --no-cache git ca-certificates tzdata

WORKDIR /app

COPY go.mod go.sum ./
RUN go mod download

COPY cmd ./cmd
COPY internal ./internal

# build the binary
RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build \
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

# Copy binary from build stage
COPY --from=build /app/identity-service .
RUN chown appuser:appgroup identity-service

USER appuser

EXPOSE 8080

ENTRYPOINT ["./identity-service"]
