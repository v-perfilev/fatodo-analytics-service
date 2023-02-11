# Fatodo analytics-service

Analytics service for Fatodo based on extended mysql kotlin skeleton.

## Tests

### Unit tests

```
./gradlew unitTest
```

### Integration tests

```
./gradlew integrationTest
```

### Contract tests

```
./gradlew contractTest
```

### Publish contract stubs to repo

```
./gradlew verifierStubsJar publishStubsPublicationToFatodoRepository 
    -PmavenRepoUrl=MAVEN_URL 
    -PmavenRepoUsername=MAVEN_USERNAME 
    -PmavenRepoPassword=MAVEN_PASSWORD
```

### Sonar report

```
./gradlew jacocoTestReport sonarqube
    -Dsonar.host.url=SONAR_URL
    -Dsonar.login=SONAR_TOKEN
```

## CI/CD pipeline

### The following environment variables must be set:

Common for the project:

```
SONAR_URL
SONAR_TOKEN

MAVEN_URL
MAVEN_USERNAME
MAVEN_PASSWORD

SERVER_URL
SERVER_USER
SERVER_IP
SERVER_ID_RSA

DOCKER_COMMON_PARAMS
```

Unique for the app:

```
APP_NAME

DOCKER_APP_PARAMS
```
