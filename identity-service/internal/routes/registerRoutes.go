package routes

import (
	"github.com/gin-gonic/gin"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/handlers"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/repositories"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/services"
	"gorm.io/gorm"
)

func RegisterRoutes(r *gin.Engine, db *gorm.DB) {
	userRepo := repositories.NewUserRepository(db)
	userService := services.NewUserService(userRepo)
	userHandler := handlers.NewUserHandler(userService)

	api := r.Group("/api/v1")
	{
		api.POST("/login", userHandler.Login)
		api.POST("/register", userHandler.Register)
	}
	r.GET("/hello", func(c *gin.Context) {
		c.JSON(200, gin.H{
			"message": "Hello haha!",
		})
	})
}
