package com.mghostl.strava.core.model

import com.fasterxml.jackson.annotation.JsonProperty

data class OauthResponse (
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("expires_at")
    val expiresAt: Int,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    @JsonProperty("refresh_token")
    val refreshToken: String
)