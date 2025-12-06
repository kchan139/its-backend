package repositories

import "github.com/kchan139/intelligent-tutoring-system/identity-service/internal/models"

type IUserRepository interface {
	FindByEmail(email string) (*models.User, error)
	StoreUser(email, fullname, hashed_password, role string) error
	CheckUser(email, password string) (*models.User, error)
	GetAllUsers() ([]models.User, error)
}
