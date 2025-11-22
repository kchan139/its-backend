package utils

import (
	"fmt"
	"net/url"
)

func GenerateAvatar(name string) string {
	return fmt.Sprintf("https://ui-avatars.com/api/?name=%s&background=random&size=200",
		url.QueryEscape(name))
}
