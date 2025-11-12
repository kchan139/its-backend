package repositories

import (
	"errors"

	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/models"
	"gorm.io/gorm"
)

type UserRepository struct {
	db *gorm.DB
}

func NewUserRepository(db *gorm.DB) *UserRepository {
	return &UserRepository{db}
}

func (r *UserRepository) FindByEmail(email string) (*models.User, error) {
	var user models.User
	result := r.db.Where("email = ?", email).First(&user)
	if result.Error != nil {
		return nil, result.Error
	}
	return &user, nil
}

func (r *UserRepository) StoreUser(email, fullname, hashed_password, role string) ( error) {
	roleMap := map[string]uint{
		"ADMIN":   1,
		"TEACHER": 2,
		"STUDENT": 3,
	}
	roleID, exists := roleMap[role]
	if !exists {
		return errors.New("invalid role: must be ADMIN, TEACHER, or STUDENT")
	}
	user := models.User{
		Email:    email,
		Fullname: fullname,
		Password: string(hashed_password),
		RoleID:   &roleID, // Assign pointer to roleID
	}
	if err := r.db.Create(&user).Error; err != nil {
		return err
	}
	return nil
}