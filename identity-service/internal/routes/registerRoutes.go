package routes
import (
    "gorm.io/gorm"
    "github.com/kchan139/intelligent-tutoring-system/identity-service/internal/repositories"
    "github.com/kchan139/intelligent-tutoring-system/identity-service/internal/services"
    "github.com/kchan139/intelligent-tutoring-system/identity-service/internal/handlers"
	"github.com/gin-gonic/gin"


)
func RegisterRoutes(r *gin.Engine, db *gorm.DB) {
    userRepo := repositories.NewUserRepository(db)
    userService := services.NewUserService(userRepo)
    userHandler := handlers.NewUserHandler(userService)

    api := r.Group("/api/v1")
    {
        api.POST("/login", userHandler.Login)
    }

}
