package com.mghostl.strava.core.services

import com.mghostl.strava.core.client.StravaFeignClient
import com.mghostl.strava.core.model.Activity
import org.springframework.stereotype.Service

@Service
class StravaServiceImpl(
    // private val logger: Logger
    private val stravaFeignClient: StravaFeignClient
): StravaService {
    override fun getActivitiesWithHeartRate(): List<Activity> {
        val result = mutableListOf<Activity>()
        var response = getHeartRatedActivities(1)
        result.addAll(response)
        var k = 2
        while(response.isNotEmpty()) {
            response = getHeartRatedActivities(k)
            result.addAll(response)
            k++
        }
        return result
    }

    private fun getHeartRatedActivities(page: Int) = stravaFeignClient.getAuthenticateAthleteActivities(perPage = 100, page = page)
        .filter { it.hasHeartRate }
}