
# TABLE of CONTENTS
* [Application info](#Application_info)
* [Clone project from Github](#Clone_project_from_Github)
* [Test application](#Test_application)
* [Run project on Windows machine](#Run_project_on_local_machine)
* [Additional links](#Additional_links)

## Application info <a name="Application_info"/>
Application works with file that contains HTTP web proxy log messages with details of company computer network
HTTP activity. Each line contains information about an HTTP access which includes: date, time, client IP,
host, path, query, user agent and more. Application extracts all host values and count how many times every host
appears in the file and then prints result to console ordere by frequency in descending order.

## Clone project from Github <a name="Clone_project_from_Github"/>

`
git clone https://github.com/DmitryRyzhikov/log-parser
`

## Test application<a name="Test_application"/>
* To test application go to application ROOT folder and run

`
mvn clean test
`
All necessary parameters are pre-configured in maven-surefire-plugin in pom.xml

## Run project on Windows machine <a name="Run project on local machine"/>
* Copy [APP_ROOT]/config to any place necessary (or just leave it untouched). This folder contains application
configs and this configs are separated from application (not on classpath). Path to this folder and also to
log folder should be passed as command params
* Check files [APP_ROOT]/config/application.yml. This file contains path to file that should be parsed,
name of parsing strategy and name of data handling strategy. It can be changes easily when new parsing
and handling strategies will be added.
* Execute command
`
mvn clean clean spring-boot:run -Dspring.config.location=D:\work\Examples\@springboot\log-parser\config\ -Dlog.folder=D:\work\Examples\@springboot\log-parser\target\logs\
`
Just specify you own correct config folder and log folder locations.


## Additional links <a name="Additional_links"/>
* [Github link](https://github.com/DmitryRyzhikov/log-parser)

