package com.mghostl.strava.core.model

import com.fasterxml.jackson.annotation.JsonProperty

data class OauthRequest(
    @JsonProperty("client_id")
    val clientId: String,
    @JsonProperty("client_secret")
    val clientSecret: String,
    @JsonProperty("grant_type")
    val grantType: GrantType = GrantType.refresh_token,
    @JsonProperty("refresh_token")
    val refreshToken: String
)

@Suppress("EnumEntryName")
enum class GrantType {
    refresh_token
}