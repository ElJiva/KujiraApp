package handlers

import (
	"kujiraapp/models"
	"kujiraapp/repository"
	"time"

	"github.com/gofiber/fiber/v2"
)

type ComicHandler struct {
	repo *repository.ComicRepository
}

func NewComicHandler(repo *repository.ComicRepository) *ComicHandler {
	return &ComicHandler{repo: repo}
}

func (h *ComicHandler) CreateComic(c *fiber.Ctx) error {
	comic := new(models.Comic)
	if err := c.BodyParser(comic); err != nil {
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"error": "Failed to parse comic",
		})
	}

	if comic.Title == "" || comic.Category == "" || comic.Editorial == "" || comic.Author == "" {
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"error": "Title, category, editorial and author are required",
		})
	}

	newComic, err := h.repo.CreateComic(comic)
	if err != nil {
		return c.Status(fiber.StatusInternalServerError).JSON(fiber.Map{"error": err.Error()})
	}

	return c.Status(fiber.StatusCreated).JSON(newComic)
}

func (h *ComicHandler) GetAllComics(c *fiber.Ctx) error {
	comics, err := h.repo.GetAllComics()
	if err != nil {
		return c.Status(fiber.StatusInternalServerError).JSON(fiber.Map{"error": err.Error()})
	}
	return c.Status(fiber.StatusOK).JSON(comics)
}

func (h *ComicHandler) GetComicByID(c *fiber.Ctx) error {
	id := c.Params("id")
	comic, err := h.repo.GetComicById(id)
	if err != nil {
		return c.Status(fiber.StatusNotFound).JSON(fiber.Map{"error": err.Error()})
	}

	return c.Status(fiber.StatusOK).JSON(comic)
}

func (h *ComicHandler) UpdateComic(c *fiber.Ctx) error {
	id := c.Params("id")

	comic := new(models.Comic)
	if err := c.BodyParser(comic); err != nil {
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"error": "Failed to parse comic",
		})
	}

	if comic.Title == "" || comic.Category == "" || comic.Editorial == "" || comic.Author == "" {
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"error": "Title, category, editorial and author are required",
		})
	}

	updatedComic, err := h.repo.UpdateComic(id, comic)
	if err != nil {
		if err.Error() == "Comic not found" {
			return c.Status(fiber.StatusNotFound).JSON(fiber.Map{"error": err.Error()})
		}
		return c.Status(fiber.StatusInternalServerError).JSON(fiber.Map{"error": err.Error()})
	}

	return c.Status(fiber.StatusOK).JSON(updatedComic)
}

func (h *ComicHandler) DeleteComic(c *fiber.Ctx) error {
	id := c.Params("id")
	err := h.repo.DeleteComic(id)

	if err != nil {
		if err.Error() == "Comic not found" || err.Error() == "Invalid comic ID" {
			return c.Status(fiber.StatusNotFound).JSON(fiber.Map{"error": err.Error()})
		}

		return c.Status(fiber.StatusInternalServerError).JSON(fiber.Map{"error": err.Error()})
	}

	return c.Status(fiber.StatusOK).JSON(fiber.Map{
		"message": "Comic successfully deleted",
	})
}

func (h *ComicHandler) AddComment(c *fiber.Ctx) error {
	comicId := c.Params("id")

	type CommentRequest struct {
		Username string `json:"username"`
		Text     string `json:"text"`
	}

	req := new(CommentRequest)
	if err := c.BodyParser(req); err != nil {
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"error": "Failed to parse commentary",
		})
	}

	if req.Username == "" || req.Text == "" {
		return c.Status(fiber.StatusBadRequest).JSON(fiber.Map{
			"error": "Username and text are required",
		})
	}

	comment := models.Comment{
		Username:  req.Username,
		Text:      req.Text,
		CreatedAt: time.Now(),
	}

	err := h.repo.AddCommentToComic(comicId, comment)
	if err != nil {
		return c.Status(fiber.StatusNotFound).JSON(fiber.Map{"error": err.Error()})
	}

	return c.Status(fiber.StatusOK).JSON(fiber.Map{
		"message": "Commentary succesfully added",
	})
}

func (h *ComicHandler) SetupComicRoutes(app *fiber.App) {
	app.Post("/comics", h.CreateComic)
	app.Get("/comics", h.GetAllComics)
	app.Get("/comics/:id", h.GetComicByID)
	app.Put("/comics/:id", h.UpdateComic)
	app.Delete("/comics/:id", h.DeleteComic)
	app.Post("/comics/:id/comment", h.AddComment)
}
