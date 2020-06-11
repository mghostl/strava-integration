package com.mghostl.strava.core.excel.service

import com.mghostl.strava.core.excel.enums.FieldType
import com.mghostl.strava.core.model.Activity
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@Service
class ExcelMapperImpl: ExcelMapper {
    override fun map(activities: List<Activity>) = activities
        .map {
            mapOf(
                FieldType.MOVING_TIME.fieldName to ExcelValueDouble(it.movingTime.toDouble()),
                FieldType.MAX_HEART_RATE.fieldName to ExcelValueDouble(it.maxHeartRate),
                FieldType.AVERAGE_HEART_RATE.fieldName to ExcelValueDouble(it.averageHeartRate),
                FieldType.MAX_SPEED.fieldName to ExcelValueDouble(it.maxSpeed),
                FieldType.AVERAGE_SPEED.fieldName to ExcelValueDouble(it.averageSpeed),
                FieldType.DISTANCE.fieldName to ExcelValueDouble(it.distance),
                FieldType.START_DATE_LOCAL.fieldName to ExcelValueDate(it.startDateLocal.toLocalDate().toDate()),
                FieldType.ZONE.fieldName to ExcelValueString(it.zone?.name),
                FieldType.NAME.fieldName to ExcelValueString("${it.athlete.lastName} ${it.athlete.firstName}"),
                FieldType.ACTIVITY_TYPE.fieldName to ExcelValueString(it.type.toString())
            )
        }
}

fun LocalDate.toDate(): Date = Date.from(atStartOfDay(ZoneId.systemDefault()).toInstant())