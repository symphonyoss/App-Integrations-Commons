[![Symphony Software Foundation - Incubating](https://cdn.rawgit.com/symphonyoss/contrib-toolbox/master/images/ssf-badge-incubating.svg)](https://symphonyoss.atlassian.net/wiki/display/FM/Incubating)
[![Build Status](https://travis-ci.org/symphonyoss/App-Integrations-Commons.svg?branch=dev)](https://travis-ci.org/symphonyoss/App-Integrations-Commons)
[![Dependencies](https://www.versioneye.com/user/projects/58efec768fa4273d16f6d1da/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/58efec768fa4273d16f6d1da)
[![Validation Status](https://scan.coverity.com/projects/12821/badge.svg?flat=1)](https://scan.coverity.com/projects/symphonyoss-app-integrations-commons)
[![codecov](https://codecov.io/gh/symphonyoss/App-Integrations-Commons/branch/dev/graph/badge.svg)](https://codecov.io/gh/symphonyoss/App-Integrations-Commons)
[![Code Quality: Java](https://img.shields.io/lgtm/grade/java/g/symphonyoss/App-Integrations-Commons.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/symphonyoss/App-Integrations-Commons/context:java)
[![Total Alerts](https://img.shields.io/lgtm/alerts/g/symphonyoss/App-Integrations-Commons.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/symphonyoss/App-Integrations-Commons/alerts)

# Integrations Commons Documentation

This document provides a brief overview of Integration Commons components and how to build them from scratch.

# Overview

Integration Commons provides the common components required to build a new integration to Symphony Platform, as the common base class for any WebHook based integration, Symphony authentication utility classes, metrics tools, and many other utility classes.

The third-party services that would like to post messages into a configurable set of streams should define the integration-parent as a parent of your maven project.

Example:

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>integration-parent</artifactId>
        <groupId>org.symphonyoss</groupId>
        <version>0.10.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>integration-test</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.symphonyoss.symphony.integrations</groupId>
            <artifactId>integration-webhook</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
</project>
```

If you want to create a new webhook integration you should define the __integration-webhook__ module as a dependency and start by extending the [WebHookIntegration](integration-webhook/src/main/java/org/symphonyoss/integration/webhook/WebHookIntegration.java) class, as it is the super class of every other integration.

# Installation instructions for the Java developer

### What you’ll build
You’ll build a simple java library that provides some useful services to build new integrations.

### What you’ll need
* JDK 1.8
* Maven 3.0.5+

### Build with maven
Integration Commons is compatible with Apache Maven 3.0.5 or above. If you don’t already have Maven installed you can follow the instructions at maven.apache.org.

To start from scratch, do the following:

1. Clone the source repository using Git: `git clone git@github.com:symphonyoss/App-Integrations-Commons.git`
2. cd into _App-Integrations-Commons_
3. Build using maven: `mvn clean install`

#### Creating a bundle

In order to distribute and/or deploy an Integration, the Maven build provides a `-Pbundle` profile which creates a `target/bundle` folder containing:
- an `integration.jar` artifact including all Java logic needed.
- an `application.yaml` file that configures the Spring Boot application; the file must be located in the project's root folder.
- a `run.sh` that is able to run the integration on different platforms (locally and remotely); the file must be located in the project's root folder.
