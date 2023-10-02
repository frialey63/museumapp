# Museum App

This project can be used as a starting point to create your own Vaadin application with Spring Boot.
It contains all the necessary configuration and some placeholder files to get you started.

## TODO

Audio announcements, PA

"This Day in Aviation History", https://www.thisdayinaviation.com/

Alternative languages

Accessibility (deaf)

Flight Simulator Self-Service

Monetisation
- Adverts
- Raffles
- Membership

Quiz of the Day

Exhibit mechanisation / animations

## DONE

Change all QR codes to use app download as the base URL
   https://rafmanston-museumapp.azurewebsites.net/?tailNumber=WT205

Links to web site for museum and cafe

General Information for Visitor
- Opening hours
- Shop
- Cafe
- Toilets
- Map

Survey
- Visit feedback
- Behaviour stats
- Demographics

Kids Stuff
- Teddy Hunt
- Quizes
- Games

Notifications of museum closing (automatic)

Only display yellow "connect" message if not actually connected to the museum wifi

Audio Guide

## Running the application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any
Maven project. Read more on [how to import Vaadin projects to different IDEs](https://vaadin.com/docs/latest/flow/guide/step-by-step/importing) (Eclipse, IntelliJ IDEA, NetBeans, and VS Code).

# Access by Android in Development Mode

[Accessing the Android phone's camera in development mode via HTTP](https://stackoverflow.com/questions/52759992/how-to-access-camera-and-microphone-in-chrome-without-https)

> This worked for me. Although it's for Testing purpose only.
>
> To ignore Chrome’s secure origin policy, follow these steps. Navigate to `chrome://flags/#unsafely-treat-insecure-origin-as-secure` in Chrome.
>
> Find and enable the Insecure origins treated as secure section (see below). Add any addresses you want to ignore the secure origin policy for. Remember to include the port number too (if required). Save and restart Chrome.
>
> Remember this is for dev purposes only. The live working app will need to be hosted on https for users to be able to use their microphone or camera.

## Deploying to Production

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/museumapp-1.0-SNAPSHOT.jar`

# Deploying to Azure

    az login
    az acr login -n rafmanstoncontainerregistry
    mvn -Pproduction compile jib:build

## Project structure

- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/components/vaadin-app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.

## Useful links

- Read the documentation at [vaadin.com/docs](https://vaadin.com/docs).
- Follow the tutorials at [vaadin.com/tutorials](https://vaadin.com/tutorials).
- Watch training videos and get certified at [vaadin.com/learn/training](https://vaadin.com/learn/training).
- Create new projects at [start.vaadin.com](https://start.vaadin.com/).
- Search UI components and their usage examples at [vaadin.com/components](https://vaadin.com/components).
- View use case applications that demonstrate Vaadin capabilities at [vaadin.com/examples-and-demos](https://vaadin.com/examples-and-demos).
- Discover Vaadin's set of CSS utility classes that enable building any UI without custom CSS in the [docs](https://vaadin.com/docs/latest/ds/foundation/utility-classes).
- Find a collection of solutions to common use cases in [Vaadin Cookbook](https://cookbook.vaadin.com/).
- Find Add-ons at [vaadin.com/directory](https://vaadin.com/directory).
- Ask questions on [Stack Overflow](https://stackoverflow.com/questions/tagged/vaadin) or join our [Discord channel](https://discord.gg/MYFq5RTbBn).
- Report issues, create pull requests in [GitHub](https://github.com/vaadin/platform).
