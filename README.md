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




