package database

import (
	"log"

	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/models"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func Connect(dbUrl string) *gorm.DB {
	db, err := gorm.Open(postgres.Open(dbUrl), &gorm.Config{})
	if err != nil {
		log.Fatalf("failed to connect to database: %v", err)
	}
	err = db.AutoMigrate(&models.Role{}, &models.User{})
	if err != nil {
    	log.Fatalf("Migration error: %v", err)
	}
	db.Create(&models.Role{RoleName: "ADMIN"})
	db.Create(&models.Role{RoleName: "TEACHER"})
	db.Create(&models.Role{RoleName: "STUDENT"})



	log.Println(" ✔ Connected to database")
	return db
}
