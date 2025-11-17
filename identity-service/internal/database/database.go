package database

import (
	"log"

	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/models"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/utils"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func Connect(dbUrl string) *gorm.DB {
	db, err := gorm.Open(postgres.Open(dbUrl), &gorm.Config{})
	if err != nil {
		log.Fatalf("failed to connect to database: %v", err)
	}
	log.Println(" ✔ Connected to database")

	err = db.AutoMigrate(&models.Role{}, &models.User{})
	if err != nil {
    	log.Fatalf("Migration error: %v", err)
	}
	db.Create(&models.Role{RoleName: "ADMIN"})
	db.Create(&models.Role{RoleName: "TEACHER"})
	db.Create(&models.Role{RoleName: "STUDENT"})
	// CREATE SEED USER
	var adminRole models.Role
    if err := db.Where("role_name = ?", "ADMIN").First(&adminRole).Error; err != nil {
        log.Fatalf("ADMIN role not found: %v", err)
    }

	hashedPass, _ := utils.HashPassword("1234567")
	adminUser := models.User{
        Email:    "admin@example.com",
        Fullname: "Administrator",
        Password: string(hashedPass),
        RoleID:   &adminRole.ID,
    }

    db.Create(&adminUser)

	return db
}
