package services

import "github.com/kchan139/intelligent-tutoring-system/identity-service/internal/models"

type IUserService interface {
	Authenticate(email, password string) (string, error)
	CreateUser(email, fullname, password, role string) error
	GetAllUsers() ([]models.User, error)
}
