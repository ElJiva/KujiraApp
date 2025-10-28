package config

import (
	"log"
	"os"

	"github.com/joho/godotenv"
)

func Config(key string) string {
	err := godotenv.Load(".env")
	if err != nil {
		log.Printf("Warning: Could not load .env file: %v", err)
	}

	value := os.Getenv(key)
	if value == "" {
		log.Printf("Warning: Enviroment vairable %s is not set", key)
	}
	return value
}
