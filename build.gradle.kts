import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.springframework.cloud.contract") version "4.0.1"
    id("org.graalvm.buildtools.native") version "0.9.18"
    id("jacoco")
    id("maven-publish")
    id("org.sonarqube") version "3.3"
    id("org.jlleitschuh.gradle.ktlint") version "11.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

group = "com.persoff68.fatodo"
description = "extended-mysql-kotlin-skeleton"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.spring.io/snapshot")
    }
    maven {
        url = uri("https://repo.spring.io/milestone")
    }
    maven {
        url = uri("https://repo.spring.io/release")
    }
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        val springCloudVersion: String by project
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.2")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.hibernate.orm:hibernate-core:6.1.7.Final")
    implementation("mysql:mysql-connector-java:8.0.32")
    implementation("org.liquibase:liquibase-core:4.19.0")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("io.micrometer:micrometer-registry-influx:1.10.3")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.14")
    runtimeOnly("com.github.danielwegener:logback-kafka-appender:0.2.0-RC2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
    testImplementation("org.springframework.cloud:spring-cloud-contract-stub-runner")
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("com.h2database:h2:2.1.214")
}

sourceSets {
    create("unitTest") {
        kotlin.srcDir("src/test/kotlin")
        kotlin.exclude("**/*IT.*")
        kotlin.exclude("**/*Tests.*")
        kotlin.exclude("**/contract/*Test.*")
        resources.srcDir("src/test/resources")
        compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
        runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
    }

    create("integrationTest") {
        kotlin.srcDir("src/test/kotlin")
        kotlin.exclude("**/*Test.*")
        resources.srcDir("src/test/resources")
        compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
        runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
    }

    getByName("contractTest") {
        resources.srcDir("src/test/resources")
    }
}

sonarqube {
    properties {
        property("sonar.sources", "src/main/")
        property("sonar.tests", "src/test/")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}

contracts {
    val contractPackage: String by project
    failOnNoContracts.set(false)
    contractsDslDir.set(file("src/test/resources/contracts"))
    baseClassForTests.set("$contractPackage.ContractBase")
    testFramework.set(org.springframework.cloud.contract.verifier.config.TestFramework.JUNIT5)
}

publishing {
    publications {
        create<MavenPublication>("stubs") {
            artifactId = project.description
            artifact("$buildDir/libs/${project.description}-${project.version}-stubs.jar")
        }
    }
    repositories {
        val mavenRepoUrl: String by project
        val mavenRepoUsername: String by project
        val mavenRepoPassword: String by project

        maven {
            name = "fatodo"
            url = uri(mavenRepoUrl)
            isAllowInsecureProtocol = true
            credentials {
                username = mavenRepoUsername
                password = mavenRepoPassword
            }
        }
    }
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("fatodo")
            buildArgs.add("-J-Xmx3G")
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register<Test>("unitTest") {
    description = "Runs the unit tests"
    group = "verification"
    testClassesDirs = sourceSets["unitTest"].output.classesDirs
    classpath = sourceSets["unitTest"].runtimeClasspath
}

tasks.register<Test>("integrationTest") {
    description = "Runs the integration tests"
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
}

tasks.jacocoTestReport {
    executionData.setFrom(
        fileTree(project.projectDir) {
            setIncludes(setOf("**/**/*.exec"))
        }
    )
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    dependsOn(tasks.getByPath("unitTest"))
    dependsOn(tasks.getByPath("integrationTest"))
}
