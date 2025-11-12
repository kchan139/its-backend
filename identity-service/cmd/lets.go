package main

import (
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/config"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/database"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/routes"

	"github.com/gin-gonic/gin"
)

func main() {
	cfg := config.Load()

	db := database.Connect(cfg.DBUrl)

	//  Create Gin engine
	r := gin.Default()


	//  Register routes with db
	routes.RegisterRoutes(r, db)

	//  Start server
	r.Run(":" + cfg.Port)
}
