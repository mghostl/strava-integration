package com.mghostl.strava.core.excel.enums

import org.apache.poi.ss.usermodel.CellType

enum class ExcelValueType(val cellType: CellType) {
    DATE(CellType.NUMERIC),
    STRING(CellType.STRING),
    DOUBLE(CellType.NUMERIC)
}