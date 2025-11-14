package services

import (
	"os"
	"time"

	"github.com/golang-jwt/jwt/v5"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/repositories"
	"github.com/kchan139/intelligent-tutoring-system/identity-service/internal/utils"
)

type UserService struct {
	repo *repositories.UserRepository
}

func NewUserService(repo *repositories.UserRepository) *UserService {
	return &UserService{repo}
}

// func (s *UserService) Authenticate(email, password string) (*models.User, error) {
//     // user, err := s.repo.FindByEmail(email)
//     // if err != nil {
//     //     return nil, err
//     // }
//     // if !utils.CheckPassword(password, user.Password) {
//     //     return nil, errors.New("invalid password")
//     // }
//     // return user, nil
// }

func (s *UserService) Authenticate(email, password string) (string, error) {
	user, err := s.repo.CheckUser(email, password)
	if err != nil {
		return "", err
	}

	// making jwt
	claims := jwt.MapClaims{
        "id":    user.ID,
        "email": user.Email,
        "role":  user.RoleID,   // or user.Role.RoleName if preloaded
        "exp":   time.Now().Add(24 * time.Hour).Unix(), // expires in 24 hours
    }
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	jwt_key := []byte(os.Getenv("JWT_KEY"))
	signedToken, err := token.SignedString(jwt_key)
    if err != nil {
        return "", err
    }

	return signedToken, nil
}
func (s *UserService) CreateUser(email, fullname, password, role string) (error) {
	hash, err := utils.HashPassword(password)
	if err != nil {
		return err
	}
	err = s.repo.StoreUser(email, fullname, hash, role)
	if err != nil {
		return err
	}
	return nil
}