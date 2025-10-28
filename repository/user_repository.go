package repository

import (
	"context"
	"errors"
	"kujiraapp/models"
	"time"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

type UserRepository struct {
	collection *mongo.Collection
	timeout    time.Duration
}

func NewUserRepository(db *mongo.Database) *UserRepository {
	return &UserRepository{
		collection: db.Collection("users"),
		timeout:    5 * time.Second,
	}
}

func (r *UserRepository) withContext() (context.Context, context.CancelFunc) {
	return context.WithTimeout(context.Background(), r.timeout)
}

func (r *UserRepository) CreateUser(user *models.User) (*models.User, error) {
	ctx, cancel := r.withContext()
	defer cancel()

	result, err := r.collection.InsertOne(ctx, user)
	if err != nil {
		if mongo.IsDuplicateKeyError(err) {
			return nil, errors.New("Email or username is already taken")
		}
		return nil, errors.New("Failed to create user")
	}

	user.ID = result.InsertedID.(primitive.ObjectID)
	return user, nil
}

func (r *UserRepository) CheckLogin(email, password string) (*models.User, error) {
	ctx, cancel := r.withContext()
	defer cancel()

	var user models.User
	err := r.collection.FindOne(ctx, bson.M{"email": email}).Decode(&user)
	if err != nil {
		if err == mongo.ErrNoDocuments {
			return nil, errors.New("user not found")
		}
		return nil, err
	}

	if user.Password != password {
		return nil, errors.New("incorrect password")
	}

	return &user, nil
}
