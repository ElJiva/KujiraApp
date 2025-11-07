package models

import (
	"time"

	"go.mongodb.org/mongo-driver/bson/primitive"
)

type Comment struct {
	Username  string
	Text      string
	CreatedAt time.Time
}

type Comic struct {
	ID        primitive.ObjectID `bson:"_id,omitempty" json:"id"`
	Title     string
	Imagen    string
	Category  string
	Editorial string
	Rating    float64
	Author    string
	VideoLink string
	BuyLink   string

	Comments []Comment `bson:"comments" json:"comments"`
}
