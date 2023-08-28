import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat

plugins {
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.graalvm.buildtools.native") version "0.9.24"
    id("com.google.cloud.tools.jib") version "3.3.2"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.allopen") version "1.6.21"
}

group = "com.istio-test"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

jib{
    from {
//        image = java.lang.System.getenv("JIB_FROM_IMAGE")
        image = "openjdk:17-jdk-alpine"
    }
    to {
//        image = System.getenv("JIB_TO_IMAGE")
        image = "564810568.dkr.ecr.ap-northeast-2.amazonaws.com/"
        setCredHelper("ecr-login")
        tags = setOf("latest", SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())) // 2개의 태그에 모두 push
    }
    container {
        jvmFlags = listOf(
            "-XX:+UseContainerSupport",
            "-XX:MaxRAMPercentage=85.0"

        )
    }
}

allOpen {
    annotation("org.springframework.data.relational.core.mapping.Table")
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.1"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs")
    implementation("org.postgresql:r2dbc-postgresql")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    runtimeOnly("io.r2dbc:r2dbc-postgresql:0.8.13.RELEASE")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
