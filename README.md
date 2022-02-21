# Keycloak – Third-Party User Provider

## Project structure

This project consists of 3 containers:

- Backend - A simple backend that contains movie information
- Frontend - A simple Angular-Keycloak frontend to the backend and our Keycloak client
- Keycloak - Custom User Provider implementation.

### Keycloak

This document describes how to use Keycloak – Third-Party User Provider and his features. Project contains a number of modules, here is a quick description of what each module does:

- ThirdParty User Provider - It allows us to hook up with a Third-Party BE that replicates/mocks a real API/BE behaviour & data.

- MyDomain Theme – Style Keycloak login page to look like MyDomain login page.

- realm-export.json file - contains all necessary configuration for keycloak realm.

- pom.xml - The pom.xml file contains information of project and configuration information for the maven to build the project such as dependencies, build directory, source directory, test source directory, plugin, goals etc.

## Requirements:

- [Docker](https://www.docker.com/products/docker-desktop) and install it
- Add an entry to your `hosts` file with the following: `127.0.0.1   keycloak`. 
  - Alternatively, modify the configuration/code to suit your environment.

## Configured users

- Keycloak Administration:
  - user: "admin"
  - password: "password"
- Frontend:
  - user: "some.user@thirdparty.com"
  - password: any non-blank password (no enforcement, just mock data)

## Start the project:

At the root of the project, run the following commands:

- Build containers: `docker compose build --no-cache`
- Start stack: `./start.sh` (or use `docker compose up`)

To stop the project:

- Stop and destroy containers, volumes and networks: `./down.sh` (or `docker compose down -v`)

This will start a Keycloak server with all necessary configuration plus the Backend and Frontend projects, after keycloak starts

## Known issues

This implementation suffers from the following issues:

- The very first token that is issued, does not comply with the expected format/fields as some of them are missing. However, a page refresh will produce the accessToken in the expected format.

Check [LOGS.md](LOGS.md) for a capture of the logs/tokens produced by keycloak/

## References

- [Angular-Keycloak](https://github.com/mauriciovigolo/keycloak-angular) - The code for the Frontend and the Backend have been mostly copied from this repo, in their sample projects.
