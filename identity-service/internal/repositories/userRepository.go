package repositories
import (
    "gorm.io/gorm"
    "github.com/kchan139/intelligent-tutoring-system/identity-service/internal/models"
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
    return &user, result.Error
}
