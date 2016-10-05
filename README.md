# pdf-index
Elastic Search PDF indexer with docker compose image

Ideally you could just do
cd indexserver
mvn clean package
cd ..
docker-compose up

But development-time it might be convenient to separately run elasticsearch with pdf plugin, and manually run spring boot maven
There are some convenience scripts provided in elastic-folder
indexer is just self-contained maven spring boot project, and refers to elasticsearch cluster at localhost:9300

- Containers are now able to see eachothers ports using docker-compose v2 networking
- Containers are now mounting local folders under indexserver and elastic projects to persist data and find pdfs to index (elastic/esdata, indexserver/pdf-files)


## Issues

- Note: At least on windows 10, you have to enable drive sharing if you want to have the mounted volumes to work. This seems to be current issue with Docker for Windows.
https://github.com/docker/docker/issues/23992
https://github.com/docker/docker/issues/23900
https://github.com/docker/docker/issues/22981
