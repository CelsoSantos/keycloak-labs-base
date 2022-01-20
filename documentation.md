# Keycloak – Third-Party User Provider

This document describes how to use Keycloak – Third-Party User Provider and his features. Project contains a number of modules, here is a quick description of what each module does:

- ThirdParty User Provider - It allows us to hook up with Amx BE.

- MyDomain Protocol Mapper – We use MyDomain Protocol Mapper to modify JWT.

- MyDomain Theme – Style Keycloak login page to look like MyDomain login page.

- realm-export.json file - contains all necessary configuration for keycloak realm.

- pom.xml - The pom.xml file contains information of project and configuration information for the maven to build the project such as dependencies, build directory, source directory, test source directory, plugin, goals etc.

## Requirements:

- [Docker](https://www.docker.com/products/docker-desktop) and install it

## Start Keycloak Server:

- Search for Docker, and select Docker Desktop in the search results.

- Navigate to Keycloak root

- Run: docker-compose -f docker-compose.yml up

This will start a Keycloak server with all necessary configuration.
