package main

import (
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/config"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/database"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/routes"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	_ "github.com/kchan139/intelligent-tutoring-system/identity-service/docs" //nolint
	swaggerFiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"
)

// @title Identity Service API
// @version 1.0
// @description API for Identity Service in the Intelligent Tutoring System - HCMUT
// @termsOfService http://swagger.io/terms/

// @contact.name API Support
// @contact.email deo-co-dau-cu@fuckyou.com

// @license.name MIT
// @license.url https://opensource.org/licenses/MIT

// @host localhost:8080
// @BasePath /api/v1
// @schemes http https

// @securityDefinitions.apikey BearerAuth
// @in header
// @name Authorization
// @description Type "Bearer" followed by a space and JWT token.

func main() {
	cfg := config.Load()

	db := database.Connect(cfg.DBUrl)

	//  Create Gin engine
	r := gin.Default()

	// NOTE: very relaxed cors
	// local development only
	r.Use(cors.New(cors.Config{
		AllowOrigins: []string{
			"http://localhost:5173",
			"http://its-edu:5173",
		},
		AllowMethods:     []string{"*"},
		AllowHeaders:     []string{"*"},
		ExposeHeaders:    []string{"*"},
		AllowCredentials: true,
	}))

	//  Swagger endpoint
	r.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))

	//  Register routes with db
	routes.RegisterRoutes(r, db)

	//  Start server
	r.Run(":" + cfg.Port)
}
