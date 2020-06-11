package com.mghostl.strava.core.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.mghostl.strava.client.model.ActivityType
import java.math.BigInteger
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Activity(
    @Id
    val id: BigInteger,
    @ManyToOne(targetEntity = Athlete::class)
    @JoinColumn(name = "ATHLETE_ID")
    val athlete: Athlete,
    val distance: Double,
    @JsonProperty("moving_time")
    val movingTime: Int,
    @Column(name = "activity_type", length = 40)
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
    val averageHeartRate: Double?,
    @Enumerated(EnumType.STRING)
    var zone: Zone?
)

@Entity
data class
Athlete(
    @JsonValue
    @Id
    val id: Long,
    @JsonProperty("firstname")
    val firstName: String? = null,
    @JsonProperty("lastname")
    val lastName: String? = null,
    val age: Int? = null,
    @Enumerated(EnumType.STRING)
    val gender: Gender? = null
)

enum class ActivityType {
    Run, Ride
}

enum class Zone {
    WHITE, BLUE, GREEN, YELLOW, RED
}
enum class Gender {
    F, M
}