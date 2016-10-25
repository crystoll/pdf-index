# pdf-index
Elastic Search PDF indexer with docker compose image

## How to run

Ideally you could just do

mvn -f indexserver/pom.xml clean package
docker-compose up

But development-time it might be convenient to separately run elasticsearch with pdf plugin, and manually run spring boot maven
There are some convenience scripts provided in elastic-folder
indexer is just self-contained maven spring boot project, and refers to elasticsearch cluster at localhost:9300

- Containers are now able to see eachothers ports using docker-compose v2 networking
- Containers are now mounting local folders under indexserver and elastic projects to persist data and find pdfs to index (elastic/esdata, indexserver/pdf-files)

To stop running, you can do:

docker-compose stop && docker-compose rm

Note that when running in Docker, it will seek files from the mounted volume only, so use docker-compose.yml, or override the mount with command line variables to use another folder.

## How to use

This package serves REST APIs for testing, at 

- http://localhost:8080/api/index
- http://localhost:8080/api/search?query=angular

First URL will run indexing for any pdf files at PDF_FILES environment path, by default it's folder pdf-files under current project. Note that indexing is asynchronous process so it will keep running once you trigger it, for some time.
Second url will query and return names of files that match given keyword.


## Issues

- Note: At least on windows 10, you have to enable drive sharing if you want to have the mounted volumes to work. This seems to be current issue with Docker for Windows.
https://github.com/docker/docker/issues/23992
https://github.com/docker/docker/issues/23900
https://github.com/docker/docker/issues/22981


