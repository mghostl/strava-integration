package com.mghostl.strava.core.controller

import com.mghostl.strava.core.client.StravaFeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(
    private val stravaFeignClient: StravaFeignClient) {

    @GetMapping
    fun test() {
        val result = stravaFeignClient.getAthleteClubs()
        print(result)
    }
}