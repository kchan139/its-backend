package services

import (
	"log"
	"os"
	"time"

	"github.com/golang-jwt/jwt/v5"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/models"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/repositories"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/utils"
)

type UserService struct {
	repo repositories.IUserRepository
}

func NewUserService(repo repositories.IUserRepository) *UserService {
	return &UserService{repo}
}

func (s *UserService) Authenticate(email, password string) (string, error) {
	user, err := s.repo.CheckUser(email, password)
	if err != nil {
		log.Printf("error from checking user existence in userService: %v", err)
		return "", err
	}

	// making jwt
	claims := jwt.MapClaims{
		"id":    user.ID,
		"email": user.Email,
		"role":  user.Role.RoleName,                    // or user.Role.RoleName if preloaded
		"exp":   time.Now().Add(24 * time.Hour).Unix(), // expires in 24 hours
	}
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	jwt_key := []byte(os.Getenv("JWT_KEY"))
	signedToken, err := token.SignedString(jwt_key)
	if err != nil {
		log.Printf("error from generating token in userService: %v", err)
		return "", err
	}

	return signedToken, nil
}
func (s *UserService) CreateUser(email, fullname, password, role string) error {
	hash, err := utils.HashPassword(password)
	if err != nil {
		log.Printf("error from hashing password in userService: %v", err)
		return err
	}
	err = s.repo.StoreUser(email, fullname, hash, role)
	if err != nil {
		log.Printf("error from storing new user in userService: %v", err)
		return err
	}
	return nil
}

func (s *UserService) GetAllUsers() ([]models.User, error) {
	return s.repo.GetAllUsers()
}
