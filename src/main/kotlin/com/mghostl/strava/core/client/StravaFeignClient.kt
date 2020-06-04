package com.mghostl.strava.core.client

import com.mghostl.strava.client.model.ActivityTotal
import com.mghostl.strava.client.model.SummaryAthlete
import com.mghostl.strava.client.model.SummaryClub
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "StravaFeignClient", url = "\${strava.requestUrl}")
interface StravaFeignClient {

    @GetMapping("/athlete")
    fun getAuthenticateAthlete(): SummaryAthlete

    @GetMapping("/activities/{id}")
    fun getActivity(): ActivityTotal

    @GetMapping("/athlete/clubs")
    fun getAthleteClubs(): List<SummaryClub>

}

