package handlers

import (
	"log"
	"net/http"

	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/services"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/utils"

	"github.com/gin-gonic/gin"
)

type UserHandler struct {
	service *services.UserService
}
type LoginRequest struct {
	Email    string `json:"email" binding:"required,email"`
	Password string `json:"password" binding:"required"`
}
type RegisterRequest struct {
	Email    string `json:"email" binding:"required,email"`
	Password string `json:"password" binding:"required"`
	FullName string `json:"fullname" binding:"required"`
	Role string `json:"role" binding:"required"`
}
func NewUserHandler(service *services.UserService) *UserHandler {
	return &UserHandler{service: service}
}
func (h *UserHandler) Login(c *gin.Context) {
	var req LoginRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	user_jwt, err := h.service.Authenticate(req.Email, req.Password)
	if err != nil {
		log.Printf("error from log in: %v", err)
		c.JSON(http.StatusUnauthorized, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{
		"jwt": string(user_jwt),
	})
}
func (h *UserHandler) Register(c *gin.Context) {
	var req RegisterRequest
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
	}
	if !utils.StrongPassword(req.Password) {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Passwords must be greater than 6 letters"})
		return
	}
	err := h.service.CreateUser(req.Email, req.FullName, req.Password, req.Role)
	if err != nil {
	    c.JSON(http.StatusUnauthorized, gin.H{"error": "invalid credentials"})
	    return
	}
	c.JSON(http.StatusOK, gin.H{"message": "Successfully register user."})
}
