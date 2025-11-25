package models

type Role struct {
	ID       uint   `json:"id" gorm:"primaryKey"`
	RoleName string `json:"role_name"`
}
