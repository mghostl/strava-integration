package com.mghostl.strava.core.config

import feign.RequestInterceptor
import feign.RequestTemplate
import feign.Retryer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class StravaFeignConfiguration  {

    @Value("\${strava.authorizationToken}")
    lateinit var authorizationToken: String

    @Bean
    fun requestInterceptor() =
        BearerAuthRequestInterceptor()
            .also{
                it.authorizationToken = authorizationToken
            }

    @Bean
    fun retryer(): Retryer = Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 2)

}

class BearerAuthRequestInterceptor: RequestInterceptor {


    var authorizationToken: String = ""

    companion object {
        const val AUTHORIZATION = "Authorization"
        const val BEARER_AUTH_PREFIX = "Bearer "

    }

    override fun apply(template: RequestTemplate) {
        template.header(AUTHORIZATION, BEARER_AUTH_PREFIX + authorizationToken)
    }
}