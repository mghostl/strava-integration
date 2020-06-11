package com.mghostl.strava.core.repository

import com.mghostl.strava.core.model.Athlete
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigInteger

interface AthleteRepository: JpaRepository<Athlete, BigInteger> {
}