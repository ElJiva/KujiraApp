package main

import (
	"context"
	"log"
	"os"
	"time"

	"github.com/gofiber/fiber/v2"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

var mongoClient *mongo.Client

func main() {
	mongoURI := os.Getenv("MONGO_URI")
	if mongoURI == "" {
		mongoURI = "mongodb://admin:admin123@localhost:27017/kujiradb?authSource=admin"
	}

	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	client, err := mongo.Connect(ctx, options.Client().ApplyURI(mongoURI))
	if err != nil {
		log.Fatal(err)
	}
	defer func() {
		if err := client.Disconnect(ctx); err != nil {
			log.Println("Error al desconectar MongoDB:", err)
		}
	}()

	err = client.Ping(ctx, nil)
	if err != nil {
		log.Fatal("No se pudo conectar a MongoDB:", err)
	}
	log.Println("✅ Conectado a MongoDB exitosamente")

	mongoClient = client

	app := fiber.New()

	app.Get("/", func(c *fiber.Ctx) error {
		return c.JSON(fiber.Map{
			"message": "Kujira API funcionando",
			"status":  "ok",
		})
	})

	port := os.Getenv("API_PORT")
	if port == "" {
		port = "3000"
	}

	log.Printf("🚀 Servidor corriendo en http://localhost:%s", port)
	log.Fatal(app.Listen("0.0.0.0:" + port))
}
