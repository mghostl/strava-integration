package com.mghostl.strava.core

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope

@EnableFeignClients
@EnableAdminServer
@SpringBootApplication(scanBasePackages = ["com.mghostl"])
class CoreApplication

fun main(args: Array<String>) {
	runApplication<CoreApplication>(*args)
}

@Bean
@Scope("prototype")
fun logger(injectionPoint: InjectionPoint): Logger = LoggerFactory.getLogger(
	injectionPoint.methodParameter?.containingClass ?: injectionPoint.member.declaringClass)
