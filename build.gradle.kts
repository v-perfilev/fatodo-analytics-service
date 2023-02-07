import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.springframework.cloud.contract") version "4.0.1"
	id("org.graalvm.buildtools.native") version "0.9.18"
	id("jacoco")
	id("maven-publish")
	id("org.sonarqube") version "3.3"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("kapt") version "1.3.72"
}

group = "com.persoff68.fatodo"
description = "extendedmysqlkotlinskeleton"
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
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}


sourceSets {
	create("unitTest") {
		kotlin.srcDir("src/test/kotlin")
		kotlin.include("**/*Test.*")
		kotlin.exclude("**/*IT.*")
		kotlin.exclude("**/*Tests.*")
		kotlin.exclude("**/contract/*Test.*")
		resources.srcDir("src/test/kotlin")
		compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
		runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
	}

	create("integrationTest") {
		kotlin.srcDir("src/test/kotlin")
		kotlin.exclude("**/*Test.*")
		kotlin.include("**/*IT.*")
		kotlin.include("**/*Tests.*")
		kotlin.include("**/contract/*Test.*")
		resources.srcDir("src/test/kotlin")
		compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
		runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
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
	contractsDslDir.set(file("src/test/resources/contracts"))
	baseClassForTests.set("$contractPackage.ContractBase")
	testFramework.set(org.springframework.cloud.contract.verifier.config.TestFramework.JUNIT5)
}

publishing {
	publications {
		create<MavenPublication>("stubs") {
			artifactId = project.description
			artifact("${buildDir}/libs/${project.description}-${project.version}-stubs.jar")
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

