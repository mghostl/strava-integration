package com.mghostl.strava.core.repository

import com.mghostl.strava.core.model.Activity
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigInteger

interface ActivityRepository: JpaRepository<Activity, BigInteger> {
}