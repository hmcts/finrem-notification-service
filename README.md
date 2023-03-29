# Financial Remedy Notification Service

The purpose of this application is to support Financial Remedy with GOV.UK Notify integration.

[![Build Status](https://travis-ci.org/hmcts/finrem-notification-service.svg?branch=master)](https://travis-ci.org/hmcts/finrem-notification-service)

## Building and deploying the application
## This repo will be de-commision soon
### Building the application

The project uses [Gradle](https://gradle.org) as a build tool. It already contains
`./gradlew` wrapper script, so there's no need to install gradle.

To build the project execute the following command:

```bash
./gradlew build
```

To get the project to build in IntelliJ IDEA, you have to:

 - Install the Lombok plugin: Preferences -> Plugins
 - Enable Annotation Processing: Preferences -> Build, Execution, Deployment -> Compiler -> Annotation Processors

### Running

You can run the application by executing following command:

```bash
./gradlew bootRun
```

The application will start locally on `http://localhost:8086`

### Mutation tests

To run all mutation tests execute the following command:

```bash
./gradlew pitest
```

### Running additional tests in the Jenkins PR Pipeline

1. Add one or more appropriate labels to your PR in GitHub. Valid labels are:

- ```enable_fortify_scan```

2. Trigger a build of your PR in Jenkins.  Fortify scans will take place asynchronously as part of the Static Checks/Container Build step.
- Check the Blue Ocean view for live monitoring, and review the logs once complete for any issues.
- As Fortify scans execute during the Static Checks/Container Build step, you will need to ensure this is triggered by making a minor change to the PR, such as bumping the chart version.

### API documentation

API documentation is provided with Swagger:
 - `http://localhost:8086/swagger-ui.html` - UI to interact with the API resources

## Versioning

We use [SemVer](http://semver.org/) for versioning.
For the versions available, see the tags on this repository.

## Standard API

We follow [RESTful API standards](https://hmcts.github.io/restful-api-standards/).

## License


This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
