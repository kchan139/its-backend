package middleware

import (
	"fmt"
	"net/http"
	"os"
	"strings"

	"github.com/gin-gonic/gin"
	"github.com/golang-jwt/jwt/v5"
)

func AuthMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		authHeader := c.GetHeader("Authorization")

		if authHeader == "" {
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Authorization header required"})
			c.Abort()
			return
		}

		tokenString := strings.TrimPrefix(authHeader, "Bearer ")

		// Parse the token
		token, err := jwt.Parse(tokenString, func(token *jwt.Token) (interface{}, error) {
			if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
				return nil, fmt.Errorf("unexpected signing method")
			}
			jwt_key := []byte(os.Getenv("JWT_KEY"))
			return jwt_key, nil
		})

		if err != nil {
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Invalid token"})
			c.Abort()
			return
		}

		if !token.Valid {
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Token is not valid"})
			c.Abort()
			return
		}

		// Extract claims
		if claims, ok := token.Claims.(jwt.MapClaims); ok {
			// Store claims in context
			c.Set("userID", claims["id"])
			c.Set("email", claims["email"])
			c.Set("role", claims["role"])
			c.Set("claims", claims) // Store entire claims if needed
		} else {
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Invalid claims"})
			c.Abort()
			return
		}

		c.Next()
	}
}
