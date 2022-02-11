# Keycloak – Third-Party User Provider

## Project structure

This project consists of 3 containers:

- Backend - A simple backend that contains movie information
- Frontend - A simple Angular-Keycloak frontend to the backend and our Keycloak client
- Keycloak - Custom User Provider implementation.

### Keycloak

This document describes how to use Keycloak – Third-Party User Provider and his features. Project contains a number of modules, here is a quick description of what each module does:

- ThirdParty User Provider - It allows us to hook up with a Third-Party BE.

- MyDomain Theme – Style Keycloak login page to look like MyDomain login page.

- realm-export.json file - contains all necessary configuration for keycloak realm.

- pom.xml - The pom.xml file contains information of project and configuration information for the maven to build the project such as dependencies, build directory, source directory, test source directory, plugin, goals etc.

## Requirements:

- [Docker](https://www.docker.com/products/docker-desktop) and install it
- Add an entry to your `hosts` file with the following: `127.0.0.1   keycloak`. 
  - Alternatively, modify the configuration/code to suit your environment.

## Start the project:

At the root of the project, run the following commands:

- Run: docker compose build
- Run: docker compose up

This will start a Keycloak server with all necessary configuration plus the Backend and Frontend projects

## References

- [Angular-Keycloak](https://github.com/mauriciovigolo/keycloak-angular) - The code for the Frontend and the Backend have been mostly copied from this repo, in their sample projects.
