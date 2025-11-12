package handlers

import (
	"net/http"

	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/services"

	"github.com/gin-gonic/gin"
)

type UserHandler struct {
	service *services.UserService
}
type LoginRequest struct {
	Email    string `json:"email" binding:"required,email"`
	Password string `json:"password" binding:"required"`
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
	// user, err := h.service.Authenticate(req.Email, req.Password)
	// if err != nil {
	//     c.JSON(http.StatusUnauthorized, gin.H{"error": "invalid credentials"})
	//     return
	// }
	c.JSON(http.StatusOK, gin.H{
		"message": "Hello World! from user handler",
	})
}
