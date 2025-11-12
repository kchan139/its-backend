package models

type Role struct {
	ID       uint   `json:"id" gorm:"primaryKey"`
	RoleName string `json:"role_name"`
	Users    []User `gorm:"foreignKey:RoleID"` // optional back-reference
}
