package models

import "go.mongodb.org/mongo-driver/bson/primitive"

type User struct {
	ID       primitive.ObjectID `bson:"_id,omitempty" json:"id"`
	Username string
	Email    string
	Password string
}

type LoginRequest struct {
	Email    string
	Password string
}

type RegisterRequest struct {
	Username        string
	Email           string
	Password        string
	ConfirmPassword string
}
