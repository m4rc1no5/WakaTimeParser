# WakaTimeParser

[![Build Status](https://travis-ci.org/m4rc1no5/WakaTimeParser.svg?branch=master)](https://travis-ci.org/m4rc1no5/WakaTimeParser)
[![Build Status](https://sonarcloud.io/api/project_badges/measure?project=m4rc1no5_WakaTimeParser&metric=alert_status)](https://sonarcloud.io/dashboard?id=m4rc1no5_WakaTimeParser)

Parse your WakaTime free account and store data in own database

Run test worker for login into WakaTime
---------------------------------------

To login into WakaTime and fetch data you must do two things:

1. Create `auth.json` (in api\src\test\resources\config) in based on `auth.json.default` with your authentication data (login and password to WakaTime).
2. Launch test `work()` in class `ProcessWorker`.

Run application on Docker
-------------------------

```bash
cd docker
./create_network.sh
./up.sh -d
```

sonarcloud
----------

Push project into sonarcloud is possible via command:

```
clean install sonar:sonar -Dsonar.projectKey=m4rc1no5_WakaTimeParser -Dsonar.organization=m4rc1no5-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=...
```
