# remarkable-desktop

I looked for a paperless solution and bought a [remarkable](https://remarkable.com/). But when i 
want to share some drawings i was frustrated about the quality of the SVG files and PDF files.  So this project started with the goal to get better exports through a JavaFX based application.

This application need [remarkable-api](https://github.com/matthiaw/remarkable-api) and is inspired by [astounding](https://github.com/jlarriba/astounding).

# Build
Building requires Java 11. OpenJFX is downloaded via Maven so no need to install the SDK.

 ```
 $ mvn clean package
 $ mvn javafx:run
 ```
