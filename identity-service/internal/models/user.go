package models

import "time"

type User struct {
	ID        uint      `json:"id" gorm:"primaryKey"`
	Email     string    `json:"email" gorm:"unique"`
	Password  string    `json:"-"`
	Fullname  string    `json:"fullname"`
	CreatedAt time.Time `json:"created_at"`
	Avatar    string    `json:"avatar" gorm:"type:text"`
	RoleID    *uint     `json:"role_id"` // Foreign key column
	Role      Role      `json:"role" gorm:"foreignKey:RoleID;constraint:OnUpdate:CASCADE,OnDelete:SET NULL;"`
}
