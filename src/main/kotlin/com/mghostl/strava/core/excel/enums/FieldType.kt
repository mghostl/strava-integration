package com.mghostl.strava.core.excel.enums

enum class FieldType(val fieldName: String) {
    NAME("name"),
    DISTANCE("Distance"),
    MOVING_TIME("time"),
    START_DATE_LOCAL("date"),
    AVERAGE_SPEED("avspeed"),
    MAX_SPEED("maxspeed"),
    MAX_HEART_RATE("maxhr"),
    AVERAGE_HEART_RATE("avhr"),
    ACTIVITY_TYPE("type"),
    ZONE("zone")
}