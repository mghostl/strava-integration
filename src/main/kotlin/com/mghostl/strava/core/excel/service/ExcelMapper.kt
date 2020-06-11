package com.mghostl.strava.core.excel.service

import com.mghostl.strava.core.model.Activity

interface ExcelMapper {
    fun map(activities: List<Activity>): List<Map<String, ExcelValue<out Any>>>
}