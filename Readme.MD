## Readme

## To run separately:

#1-st microservice:
```bash
java -jar \
/Users/Rage/IdeaProjects/Learn/myapp/build/libs/myapp.jar \
--spring.profiles.active=dev \
--server.port=8080 \
--spring.application.name=MyApp-1
```

2-nd microservice:
```bash
java -jar \
/Users/Rage/IdeaProjects/Learn/myapp/build/libs/myapp.jar \
--spring.profiles.active=dev \
--server.port=8090 \
--spring.application.name=MyApp-2
```

## How to build
Build all sub projects.

```bash
$ ./gradlew build
```

Build `common` project only.
```bash
$ ./gradlew :common:build
```

Build a specific task in a sub project. For example `app` project has a task to build a Fat jar.
```bash
$ ./gradlew :app:customFatJar
$ java -jar app/build/libs/executable-1.0.jar
17:20:42.309 [main] INFO base.MyBase - called doSomeProcess in Base
17:20:42.310 [main] INFO common.MyLibrary - do some process
17:20:42.311 [main] INFO base.MyBase - log floor: 3
17:20:42.316 [main] INFO base.MyBase - f.getCumPct('Z'): 0.5
```

## How to test
Run test for all sub projects.

```bash
$ ./gradlew --info cleanTest test
```

