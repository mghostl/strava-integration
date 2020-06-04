package com.mghostl.strava.core.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.mghostl.strava.client.model.ActivityType
import java.time.ZonedDateTime

data class Activity(
    val athlete: Athlete,
    val distance: Double,
    @JsonProperty("moving_time")
    val movingTime: Int,
    val type: ActivityType,
    @JsonProperty("start_date")
    val startDate: ZonedDateTime,
    @JsonProperty("start_date_local")
    val startDateLocal: ZonedDateTime,
    @JsonProperty("average_speed")
    val averageSpeed: Double,
    @JsonProperty("max_speed")
    val maxSpeed: Double,
    @JsonProperty("has_heartrate")
    val hasHeartRate: Boolean,
    @JsonProperty("max_heartrate")
    val maxHeartRate: Double?,
    @JsonProperty("average_heartrate")
    val averageHeartRate: Double?
)

data class Athlete(
    @JsonValue
    val id: Long
)

enum class ActivityType {
    Run
}