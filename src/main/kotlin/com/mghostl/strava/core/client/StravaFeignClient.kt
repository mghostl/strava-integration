package com.mghostl.strava.core.client

import com.mghostl.strava.client.model.ActivityTotal
import com.mghostl.strava.client.model.SummaryAthlete
import com.mghostl.strava.client.model.SummaryClub
import com.mghostl.strava.core.model.Activity
import com.mghostl.strava.core.model.Athlete
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "StravaFeignClient", url = "\${strava.requestUrl}")
interface StravaFeignClient {

    @GetMapping("/athlete")
    fun getAuthenticateAthlete(): Athlete

    @GetMapping("/athlete/activities")
    fun getAuthenticateAthleteActivities(@RequestParam("page") page: Int = 1,
        @RequestParam("per_page") perPage: Int = 30): List<Activity>

    @GetMapping("/activities/{id}")
    fun getActivity(@PathVariable("id") activityId: Long): ActivityTotal

    @GetMapping("/athlete/clubs")
    fun getAthleteClubs(): List<SummaryClub>

    @GetMapping("/clubs/{id}/members")
    fun getClubAthletes(@PathVariable("id") clubId: Long): List<Any>

    @GetMapping("/clubs/{id}/activities")
    fun getClubActivities(@PathVariable("id") clubId: Long): List<Any>
    // можно взять расстояние, имя с первой буквой фамилией атлета и время пробежки


}

