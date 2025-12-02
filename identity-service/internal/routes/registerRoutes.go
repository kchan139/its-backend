package routes

import (
	"github.com/gin-gonic/gin"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/handlers"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/middleware"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/repositories"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/services"
	"gorm.io/gorm"
)

func RegisterRoutes(r *gin.Engine, db *gorm.DB) {
	userRepo := repositories.NewUserRepository(db)
	userService := services.NewUserService(userRepo)
	userHandler := handlers.NewUserHandler(userService)
	headHandler := handlers.NewHeadHandler(db)

	api := r.Group("/api/v1")
	{
		api.POST("/login", userHandler.Login)
	}
	auth := r.Group("/api/v1/auth")
	auth.Use(middleware.AuthMiddleware())
	{
		auth.GET("/users", userHandler.GetAllUsers)
		auth.POST("/register", userHandler.Register)

	}

	r.GET("/health", headHandler.Check)

}
