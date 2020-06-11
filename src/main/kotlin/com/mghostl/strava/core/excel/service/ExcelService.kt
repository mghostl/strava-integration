package com.mghostl.strava.core.excel.service

import com.mghostl.strava.core.model.Activity

interface ExcelService {
    /**
     * экспорт в excel активностей пользователя
     */
    fun export(activities: List<Activity>): ByteArray
}