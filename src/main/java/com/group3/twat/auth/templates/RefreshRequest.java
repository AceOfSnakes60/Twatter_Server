package com.group3.twat.auth.templates;

public record RefreshRequest(String refreshToken, String accessToken) {
}
