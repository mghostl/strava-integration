package com.mghostl.strava.core.excel.service

import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook

fun getTableHeaderStyle(workbook: XSSFWorkbook, withBorder: Boolean = true): XSSFCellStyle = workbook.createCellStyle()
    .apply {
        setFont(
            workbook.createFont()
                .apply {
                    bold = true
                }
        )
        setCenterAlignment()
        if(withBorder) {
            setBorder()
        }
    }

fun XSSFCellStyle.setBorder() {
    setBorderBottom(BorderStyle.MEDIUM)
    setBorderLeft(BorderStyle.MEDIUM)
    setBorderRight(BorderStyle.MEDIUM)
    setBorderTop(BorderStyle.MEDIUM)
}

fun XSSFCellStyle.setCenterAlignment() {
    setAlignment(HorizontalAlignment.CENTER)
    setVerticalAlignment(VerticalAlignment.CENTER)
}