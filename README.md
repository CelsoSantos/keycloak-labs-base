# Keycloak – Custom User Provider

This document describes how to use Keycloak – Custom User Provider and his features. Project contains a number of modules, here is a quick description of what each module does:

- OtherCompany User Provider - It allows us to hook up with OtherCompany BE.

- Custom Protocol Mapper – We use Custom Protocol Mapper to modify JWT.

- MyCompany Theme – Style Keycloak login page to look like MyCompany login page.

- realm-export.json file - contains all necessary configuration for keycloak realm.

- pom.xml - The pom.xml file contains information of project and configuration information for the maven to build the project such as dependencies, build directory, source directory, test source directory, plugin, goals etc.

## Requirements:

- [Docker](https://www.docker.com/products/docker-desktop) and install it

## Optional:

- Replace the certificates data if required

## Start Keycloak Server:

- Run: docker-compose -f docker-compose.yml up

This will start a Keycloak server with all necessary configuration.
