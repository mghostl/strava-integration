import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.1.7.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
    kotlin("plugin.jpa") version "1.3.61"
    kotlin("kapt") version "1.3.61"

}

group = "com.mghostl"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8


object Version {
    const val springAdmin = "2.1.6"
    const val mapstruct = "1.2.0.Final"
    const val okhttp = "2.7.5"
    const val feignForm = "3.8.0"
    const val apachePoi = "3.17"
}

val developmentOnly: Configuration by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("http://repo.spring.io/milestone")
        name = "spring-milestone"
    }
    maven{
        url = uri("http://repo.spring.io/snapshot")
        name = "spring-snapshots"
    }
    maven {
        name = "Sonatype Nexus Snapshots"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
    maven {
        url = uri("https://repo.spring.io/libs-release-local")
        name = "Spring Release Repository"
    }
    maven {
        url = uri("https://repo.spring.io/libs-snapshot-local")
        name = "Spring Snapshot Repository"
    }
}

kapt {
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
    }
}


dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("io.github.openfeign.form:feign-form-spring:${Version.feignForm}")
    implementation("io.github.openfeign.form:feign-form:${Version.feignForm}")
    implementation("de.codecentric:spring-boot-admin-starter-server:${Version.springAdmin}")
    implementation("de.codecentric:spring-boot-admin-starter-client:${Version.springAdmin}")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // DB
    implementation("org.liquibase:liquibase-core")
    implementation("com.h2database:h2:1.4.200")
    implementation("org.postgresql:postgresql:42.2.12")

    implementation("io.swagger.core.v3:swagger-annotations:2.0.0")
    implementation("com.squareup.okhttp:okhttp:${Version.okhttp}")
    implementation("com.squareup.okhttp:logging-interceptor:${Version.okhttp}")
    implementation("com.google.code.gson:gson:2.8.1")
    implementation("io.gsonfire:gson-fire:1.8.3")
    implementation("org.threeten:threetenbp:1.3.5")

    // MapStruct
    implementation("org.mapstruct:mapstruct:${Version.mapstruct}")
    kapt("org.mapstruct:mapstruct-processor:${Version.mapstruct}")
    implementation("org.mapstruct:mapstruct-jdk8:${Version.mapstruct}")

    // apache POI
    api("org.apache.poi:poi:${Version.apachePoi}")
    api("org.apache.poi:poi-ooxml:${Version.apachePoi}")

    // Dev tools
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.compileJava{
    setDependsOn(listOf(tasks.processResources))
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
allprojects {
    group = "com.example"
    version = "1.0.0"

    tasks.withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }
}

configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:Greenwich.RELEASE")
    }
}
subprojects {
    buildscript {
        repositories {
            mavenCentral()
        }
    }
    dependencies {
        implementation("io.swagger.core.v3:swagger-annotations:2.0.0")
        implementation("com.squareup.okhttp:okhttp:2.7.5")
        implementation("com.squareup.okhttp:logging-interceptor:2.7.5")
        implementation("com.google.code.gson:gson:2.8.1")
        implementation("io.gsonfire:gson-fire:1.8.3")
        implementation("org.threeten:threetenbp:1.3.5")
    }
}
