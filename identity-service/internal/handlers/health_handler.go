package handlers

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

type HeadHandler struct {
	db *gorm.DB
}

func NewHeadHandler(db *gorm.DB) *HeadHandler {
	return &HeadHandler{db}
}

func (h *HeadHandler) Check(c *gin.Context) {
	sqlDB, err := h.db.DB()
        if err != nil {
            c.JSON(http.StatusInternalServerError, gin.H{
                "status": "down",
                "db":     "error getting sql DB handle",
                "error":  err.Error(),
            })
            return
        }

        if err := sqlDB.Ping(); err != nil {
            c.JSON(http.StatusServiceUnavailable, gin.H{
                "status": "down",
                "db":     "unreachable",
                "error":  err.Error(),
            })
            return
        }

        c.JSON(http.StatusOK, gin.H{
            "status": "up",
            "db":     "connected",
        })
}