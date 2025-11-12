package config

import (
    "log"
    "os"
)

type Config struct {
    DBUrl string
    Port  string
}

func Load() *Config {
    dbUrl := os.Getenv("DATABASE_URL")
    if dbUrl == "" {
        log.Fatal("DATABASE_URL not set")
    }

    port := os.Getenv("IDENTITY_SVC_PORT")
    if port == "" {
        port = "8080"
    }

    return &Config{
        DBUrl: dbUrl,
        Port:  port,
    }
}
