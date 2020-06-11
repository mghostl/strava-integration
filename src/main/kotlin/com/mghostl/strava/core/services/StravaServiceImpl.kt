package com.mghostl.strava.core.services

import com.mghostl.strava.core.client.StravaFeignClient
import com.mghostl.strava.core.config.BearerAuthRequestInterceptor
import com.mghostl.strava.core.model.Activity
import com.mghostl.strava.core.model.Gender
import com.mghostl.strava.core.model.Zone
import com.mghostl.strava.core.repository.ActivityRepository
import com.mghostl.strava.core.repository.AthleteRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class StravaServiceImpl(
    // private val logger: Logger
    private val stravaFeignClient: StravaFeignClient,
    private val activityRepository: ActivityRepository,
    private val athleteRepository: AthleteRepository,
    private val interceptor: BearerAuthRequestInterceptor
): StravaService {

    @Transactional
    override fun getActivitiesWithHeartRate(authorizationToken: String?): List<Activity> {
        if(authorizationToken != null) {
            interceptor.authorizationToken = authorizationToken
        }
        saveAthlete()
        val result = mutableListOf<Activity>()
        var response = getHeartRatedActivities(1).apply {
            forEach {
                activityRepository.save(it)
            }
        }
        result.addAll(response)
        var k = 2
        while(response.isNotEmpty()) {
            response = getHeartRatedActivities(k)
                .apply { forEach{
                    activityRepository.save(it)
                } }

            result.addAll(response)
            k++
        }
        return result
    }

    fun saveAthlete() {
        val athlete = stravaFeignClient.getAuthenticateAthlete()
        athleteRepository.save(athlete)
    }

    @Transactional
    override fun getAllActivities(): List<Activity> {
       return activityRepository.findAll()
           .sortedBy { it.startDate }
           .apply {
               forEach {
                   it.apply { it.calculateZone() }
               }
               activityRepository.saveAll(this)
           }
    }

    private fun getHeartRatedActivities(page: Int) = stravaFeignClient.getAuthenticateAthleteActivities(perPage = 100, page = page)
        .filter { it.hasHeartRate }
        .filter { it.distance > 0 }

    private fun Activity.calculateZone() {
        val maxHR = when(athlete.gender) {
            Gender.F -> 209 - 0.9 * athlete.age!!
            Gender.M -> 214 - 0.8 * athlete.age!!
            else -> throw IllegalArgumentException()
        }
        zone = when {
            averageHeartRate!! < 0.6 * maxHR -> Zone.WHITE
            averageHeartRate >= 0.6 * maxHR && averageHeartRate < 0.7 * maxHR -> Zone.BLUE
            averageHeartRate >= 0.7 * maxHR && averageHeartRate < 0.8 * maxHR -> Zone.GREEN
            averageHeartRate >= 0.8 * maxHR && averageHeartRate < 0.9 * maxHR -> Zone.YELLOW
            else -> Zone.RED
        }
     }
}