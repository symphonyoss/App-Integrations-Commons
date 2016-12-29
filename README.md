_Note that this project depends on internal Symphony infrastructure (repository.symphony.com), and therefore it can only be built by Symphony LLC employees/partners._

# Integrations Commons Documentation

This document provides a brief overview of Integration Commons components and how to build them from scratch.

# Overview

Integration Commons provides the common components required to build a new integration to Symphony Platform.

The third-party services that would like to post messages into a configurable set of streams should define the integration-parent as a parent of your application.

Example:

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>integration-parent</artifactId>
        <groupId>org.symphonyoss</groupId>
        <version>1.45.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>integration-test</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.symphonyoss</groupId>
            <artifactId>integration-webhook</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
</project>
```

If you want to create a new webhook integration you should define the __integration-webhook__ module as a dependency.

# Installation instructions for the Java developer

### What you’ll build
You’ll build a simple java library that provides some useful services to build new integration.

### What you’ll need
* JDK 1.7
* Maven 3.0.5+

### Build with maven
Integration Commons is compatible with Apache Maven 3.0.5 or above. If you don’t already have Maven installed you can follow the instructions at maven.apache.org.

To start from scratch, do the following:

1. Clone the source repository using Git:
   `git clone https://github.com/SymphonyOSF/App-Integrations-Commons.git`
2. cd into _App-Integrations-Commons_
3. Build using maven:
   `mvn clean install`