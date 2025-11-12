package services
import (
    "github.com/kchan139/intelligent-tutoring-system/identity-service/internal/repositories"
    // "github.com/kchan139/intelligent-tutoring-system/identity-service/internal/models"

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
    return "haha", nil
}
