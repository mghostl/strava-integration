package com.mghostl.strava.core.excel.service

import com.mghostl.strava.core.excel.enums.ExcelValueType
import com.mghostl.strava.core.excel.enums.FieldType
import com.mghostl.strava.core.model.Activity
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.util.IOUtils
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.util.Date

@Service
class ExcelServiceImpl(
    private val excelMapper: ExcelMapper
): ExcelService {
    override fun export(activities: List<Activity>): ByteArray {
        val (workBook, sheet) = initExcel()

        writeDataActivity(excelMapper.map(activities), workBook, sheet)
        return workBook.toBytes()
    }

    private fun initExcel(): Pair<XSSFWorkbook, XSSFSheet> {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet()
        return workbook to sheet
    }

    private fun writeDataActivity(listOfDataMaps: List<Map<String, ExcelValue<out Any>>>, workBook: XSSFWorkbook, sheet: XSSFSheet) {
        val startIndex = sheet.lastRowNum
        createRows(sheet, startIndex, listOfDataMaps.size + startIndex)

        val columnHeaders = createHeadersForActivities(workBook, sheet, startIndex)

        //Заполняем таблицу
        var rowIndex = startIndex + 1
        listOfDataMaps.forEach{ rowMap ->
            columnHeaders.forEachIndexed { columnIndex, column ->
                sheet.getRow(rowIndex)
                    .createCell(columnIndex)
                    .apply {
                        rowMap[column.fieldName]?.let {
                            setCellValue(it, workBook, true)
                        }
                    }
            }
            rowIndex++
        }
        // Autosize columns
        columnHeaders.forEachIndexed{ index, _ -> sheet.autoSizeColumn(index)}
    }

    private fun createHeadersForActivities(workBook: XSSFWorkbook, sheet: XSSFSheet, rowNumPosition: Int = 0): List<FieldType> {
        val columnHeaders = listOf(
            FieldType.NAME,
            FieldType.START_DATE_LOCAL,
            FieldType.DISTANCE,
            FieldType.MOVING_TIME,
            FieldType.AVERAGE_SPEED,
            FieldType.MAX_SPEED,
            FieldType.AVERAGE_HEART_RATE,
            FieldType.MAX_HEART_RATE,
            FieldType.ACTIVITY_TYPE,
            FieldType.ZONE
        )

        val headerStyle = getTableHeaderStyle(workBook, true)
        val headerRow = sheet.getRow(rowNumPosition)
        columnHeaders.forEachIndexed { index, column ->
            headerRow.createCell(index)
                .apply {
                    setCellValue(column.fieldName)
                    setCellType(CellType.STRING)
                    cellStyle = headerStyle
                }
        }
        headerRow.heightInPoints = 52.5f
        return columnHeaders
    }

    private fun createRows(sheet: XSSFSheet, from: Int, to: Int) {
        for(i in from..to) {
            sheet.createRow(i)
        }
    }

}

fun XSSFWorkbook.toBytes(): ByteArray {
    val byteArrayOS = ByteArrayOutputStream()
    IOUtils.writeAndClose(this, byteArrayOS)
    return byteArrayOS.toByteArray()
}

fun XSSFCell.setCellValue(value: ExcelValue<out Any>, workBook: XSSFWorkbook, withBorder: Boolean = true) {
    when(value.type){
        ExcelValueType.DOUBLE -> setCellValue(value.value as Double)
        ExcelValueType.STRING -> setCellValue(value.value as String)
        ExcelValueType.DATE -> setCellValue(value.value as Date)
    }
    setCellType(value.type.cellType)
    cellStyle = value.getStyle(workBook, withBorder)
}