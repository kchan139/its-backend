package repositories

import (
	"errors"

	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/models"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/utils"
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

func (r *UserRepository) StoreUser(email, fullname, hashed_password, role string) error {
	var dbRole models.Role
	result := r.db.Where("role_name = ?", role).First(&dbRole)
	if result.Error != nil {
		if errors.Is(result.Error, gorm.ErrRecordNotFound) {
			return errors.New("invalid role: role does not exist")
		}
		return result.Error
	}
	user := models.User{
		Email:    email,
		Fullname: fullname,
		Password: string(hashed_password),
		Avatar:   utils.GenerateAvatar(fullname),
		RoleID:   &dbRole.ID, // Assign pointer to roleID
	}
	if err := r.db.Create(&user).Error; err != nil {
		return err
	}
	return nil
}

func (r *UserRepository) CheckUser(email, password string) (*models.User, error) {
	var user models.User
	result := r.db.Preload("Role").Where("email = ?", email).First(&user)
	if result.Error != nil {
		if errors.Is(result.Error, gorm.ErrRecordNotFound) {
			return nil, errors.New("invalid credentials")
		}
		return nil, result.Error
	}
	valid := utils.CheckPassword(password, user.Password)
	if !valid {
		return nil, errors.New("wrong username or password")
	}

	// 3. OK
	return &user, nil
}

func (r *UserRepository) GetAllUsers() ([]models.User, error) {
	var users []models.User
	result := r.db.Preload("Role").Find(&users)
	if result.Error != nil {
		return nil, result.Error
	}
	return users, nil
}
