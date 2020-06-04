package com.mghostl.strava.core.services

import com.mghostl.strava.core.model.Activity

interface StravaService {
    fun getActivitiesWithHeartRate(): List<Activity>
}