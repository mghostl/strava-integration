package com.mghostl.strava.core.excel.service

import com.mghostl.strava.core.excel.enums.ExcelValueType
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.util.Date

abstract class ExcelValue<T>(
    val value: T?,
    val type: ExcelValueType
) {
    open fun getStyle(workbook: XSSFWorkbook, withBorder: Boolean = true): XSSFCellStyle = workbook.createCellStyle()
        .apply {
            if(withBorder) {
                setBorder()
            }
            setCenterAlignment()
        }
}

class ExcelValueDate(date: Date?): ExcelValue<Date>(date, ExcelValueType.DATE) {
    override fun getStyle(workbook: XSSFWorkbook, withBorder: Boolean): XSSFCellStyle {
        return super.getStyle(workbook, withBorder)
            .apply {
                dataFormat = workbook.creationHelper.createDataFormat().getFormat("dd.mm.yyyy")
            }
    }
}

class ExcelValueString(str: String?): ExcelValue<String>(str, ExcelValueType.STRING)

class ExcelValueDouble(value: Double?): ExcelValue<Double>(value, ExcelValueType.DOUBLE)