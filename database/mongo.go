package database

import (
	"context"
	"log"
	"time"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"go.mongodb.org/mongo-driver/mongo/readpref"
)

var (
	Client *mongo.Client
	DB     *mongo.Database
)

func ConnectDB(uri, dbName string) error {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)
	defer cancel()

	clientOptions := options.Client().ApplyURI(uri)
	client, err := mongo.Connect(ctx, clientOptions)
	if err != nil {
		return err
	}

	ctxPing, cancelPing := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancelPing()
	if err := client.Ping(ctxPing, readpref.Primary()); err != nil {
		return err
	}

	log.Println("Connected to mongoDB")

	Client = client
	DB = client.Database(dbName)

	go CreateUserIndexes()

	return nil
}

func CreateUserIndexes() {
	userCollection := DB.Collection("users")

	emailIndex := mongo.IndexModel{
		Keys:    map[string]interface{}{"email": 1},
		Options: options.Index().SetUnique(true),
	}

	usernameIndex := mongo.IndexModel{
		Keys:    map[string]interface{}{"username": 1},
		Options: options.Index().SetUnique(true),
	}

	_, err := userCollection.Indexes().CreateMany(context.Background(), []mongo.IndexModel{emailIndex, usernameIndex})
	if err != nil {
		log.Printf("Error creating user indexes: %v", err)
	} else {
		log.Println("User indexes (email/username) created/verified")
	}
}
