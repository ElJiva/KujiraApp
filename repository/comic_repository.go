package repository

import (
	"context"
	"errors"
	"kujiraapp/models"
	"time"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type ComicRepository struct {
	collection *mongo.Collection
	timeout    time.Duration
}

func NewComicRepository(db *mongo.Database) *ComicRepository {
	return &ComicRepository{
		collection: db.Collection("comics"),
		timeout:    5 * time.Second,
	}
}

func (r *ComicRepository) CreateComic(comic *models.Comic) (*models.Comic, error) {
	ctx, cancel := context.WithTimeout(context.Background(), r.timeout)
	defer cancel()

	comic.Comments = []models.Comment{}

	result, err := r.collection.InsertOne(ctx, comic)
	if err != nil {
		return nil, errors.New("Failed to create comic")
	}

	comic.ID = result.InsertedID.(primitive.ObjectID)
	return comic, nil
}

func (r *ComicRepository) GetAllComics() ([]models.Comic, error) {
	ctx, cancel := context.WithTimeout(context.Background(), r.timeout)
	defer cancel()

	var comics []models.Comic
	cursor, err := r.collection.Find(ctx, bson.M{})
	if err != nil {
		return nil, err
	}
	defer cursor.Close(ctx)

	if err = cursor.All(ctx, &comics); err != nil {
		return nil, err
	}

	return comics, nil
}

func (r *ComicRepository) GetComicById(id string) (*models.Comic, error) {
	objId, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		return nil, errors.New("Invalid comic ID")
	}

	ctx, cancel := context.WithTimeout(context.Background(), r.timeout)
	defer cancel()

	var comic models.Comic
	if err := r.collection.FindOne(ctx, bson.M{"_id": objId}).Decode(&comic); err != nil {
		return nil, errors.New("Comic not found")
	}

	return &comic, nil
}

func (r *ComicRepository) UpdateComic(id string, comic *models.Comic) (*models.Comic, error) {
	objID, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		return nil, errors.New("Invalid comic ID")
	}

	ctx, cancel := context.WithTimeout(context.Background(), r.timeout)
	defer cancel()

	filter := bson.M{"_id": objID}

	update := bson.M{
		"$set": bson.M{
			"Title":     comic.Title,
			"Imagen":    comic.Imagen,
			"Category":  comic.Category,
			"Editorial": comic.Editorial,
			"Rating":    comic.Rating,
			"VideoLink": comic.VideoLink,
			"BuyLink":   comic.BuyLink,
		},
	}

	opts := options.FindOneAndUpdate().SetReturnDocument(options.After)

	var updatedComic models.Comic

	err = r.collection.FindOneAndUpdate(ctx, filter, update, opts).Decode(&updatedComic)

	if err != nil {
		if err == mongo.ErrNoDocuments {
			return nil, errors.New("Comic not found")
		}
		return nil, errors.New("Failed to update comic")
	}

	return &updatedComic, nil
}

func (r *ComicRepository) DeleteComic(id string) error {
	objID, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		return errors.New("Invalid comic ID")
	}

	ctx, cancel := context.WithTimeout(context.Background(), r.timeout)
	defer cancel()

	filter := bson.M{"_id": objID}

	result, err := r.collection.DeleteOne(ctx, filter)
	if err != nil {
		return errors.New("Failed to delete comic")
	}

	if result.DeletedCount == 0 {
		return errors.New("Comic not found")
	}

	return nil
}

func (r *ComicRepository) AddCommentToComic(comicId string, comment models.Comment) error {
	objId, err := primitive.ObjectIDFromHex(comicId)
	if err != nil {
		return errors.New("Invalid comic ID")
	}

	ctx, cancel := context.WithTimeout(context.Background(), r.timeout)
	defer cancel()

	update := bson.M{
		"$push": bson.M{"comments": comment},
	}

	result, err := r.collection.UpdateOne(ctx, bson.M{"_id": objId}, update)
	if err != nil {
		return err
	}

	if result.MatchedCount == 0 {
		return errors.New("Comic not found to add comment")
	}
	return nil
}
