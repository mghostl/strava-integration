package com.mghostl.strava.core.excel

import com.mghostl.strava.core.excel.service.ExcelService
import com.mghostl.strava.core.services.StravaService
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reports")
class ReportController(
    private val stravaService: StravaService,
    private val excelService: ExcelService
) {

    /**
     * Возвращает активности пользователя в формате xlsx
     */
    @GetMapping("/excel")
    fun getExcelActivities(): ResponseEntity<Resource> {
        val activities = stravaService.getAllActivities()
        val resource = ByteArrayResource(excelService.export(activities))
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=activities.xlsx")
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource)
    }
}