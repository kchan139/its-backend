package database

import (
	"log"
	"os"

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
		log.Fatalf("Error fetching ADMIN role: %v", err)
	}

	var teacherRole models.Role
	if err := db.Where("role_name = ?", "TEACHER").First(&teacherRole).Error; err != nil {
		log.Fatalf("Error fetching TEACHER role: %v", err)
	}

	var studentRole models.Role
	if err := db.Where("role_name = ?", "STUDENT").First(&studentRole).Error; err != nil {
		log.Fatalf("Error fetching STUDENT role: %v", err)
	}

	seedPassword := os.Getenv("SEED_PASSWORD")
	var adminUser models.User
	if err := db.Where("email = ?", "admin@its.edu").First(&adminUser).Error; err != nil {
		hashedPass, err := utils.HashPassword(seedPassword)
		if err != nil {
			log.Fatalf("Failed to hash admin password: %v", err)
		}
		adminUser := models.User{
			Email:    "admin@its.edu",
			Fullname: "Admin",
			Password: hashedPass,
			RoleID:   &adminRole.ID,
		}

		if err := db.Create(&adminUser).Error; err != nil {
			log.Printf("Failed to create admin user: %v", err)
		}
	}

	var teacherUser models.User
	if err := db.Where("email = ?", "teacher@its.edu").First(&teacherUser).Error; err != nil {
		hashedPass, err := utils.HashPassword(seedPassword)
		if err != nil {
			log.Fatalf("Failed to hash teacher password: %v", err)
		}
		teacherUser := models.User{
			Email:    "teacher@its.edu",
			Fullname: "Teacher",
			Password: hashedPass,
			RoleID:   &teacherRole.ID,
		}

		if err := db.Create(&teacherUser).Error; err != nil {
			log.Printf("Failed to create teacher user: %v", err)
		}
	}

	var studentUser models.User
	if err := db.Where("email = ?", "student@its.edu").First(&studentUser).Error; err != nil {
		hashedPass, err := utils.HashPassword(seedPassword)
		if err != nil {
			log.Fatalf("Failed to hash student password: %v", err)
		}
		studentUser := models.User{
			Email:    "student@its.edu",
			Fullname: "Student",
			Password: hashedPass,
			RoleID:   &studentRole.ID,
		}

		if err := db.Create(&studentUser).Error; err != nil {
			log.Printf("Failed to create student user: %v", err)
		}
	}

	return db
}
