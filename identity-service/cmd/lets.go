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

    // 3️⃣ Create Gin engine
    r := gin.Default()
	 r.GET("/hello", func(c *gin.Context) {
        c.JSON(200, gin.H{
            "message": "Hello World!",
        })
    })

    // 4️⃣ Register routes with db
    routes.RegisterRoutes(r, db)

    // 5️⃣ Start server
    r.Run(":8080")
}
