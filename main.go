package main

import (
	"kujiraapp/config"
	"kujiraapp/database"
	"kujiraapp/handlers"
	"kujiraapp/repository"
	"log"
	"os"

	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/fiber/v2/middleware/cors"
	"github.com/gofiber/fiber/v2/middleware/logger"
	"go.mongodb.org/mongo-driver/mongo"
)

var mongoClient *mongo.Client

func main() {
	port := config.Config("API_PORT")
	mongoURI := os.Getenv("MONGO_URI")
	dbName := config.Config("MONGO_DATABASE")

	if port == "" {
		port = "3000"
	}

	if err := database.ConnectDB(mongoURI, dbName); err != nil {
		log.Fatalf("Failed to connect to MongoDB: %v", err)
	}

	app := fiber.New()
	app.Use(logger.New())

	app.Use(cors.New(cors.Config{
		AllowOrigins: "*",
		AllowMethods: "GET,POST,PUT,DELETE,OPTIONS",
		AllowHeaders: "Origin, Content-Type, Accept, Authorization",
	}))

	userRepo := repository.NewUserRepository(database.DB)
	comicRepo := repository.NewComicRepository(database.DB)

	userHandler := handlers.NewUserHandler(userRepo)
	comicHandler := handlers.NewComicHandler(comicRepo)

	userHandler.SetupUserRoutes(app)
	comicHandler.SetupComicRoutes(app)

	app.Get("/", func(c *fiber.Ctx) error {
		return c.JSON(fiber.Map{"message": "Comics API built with Go and MongoDB"})
	})

	log.Printf("Server run in %s", port)
	log.Fatal(app.Listen(":" + port))
}
