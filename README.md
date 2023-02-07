# Fatodo extended-mysql-kotlin-skeleton

Spring app that is used for development of complex microservices with kotlin and mysql.

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
./gradlew contracts 
    -Pcontracts
    -Dmaven.repo.url=URL
    -Dmaven.repo.username=USERNAME
    -Dmaven.repo.password=PASSWORD
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
