mvn -f indexserver/pom.xml clean package
docker-compose build
docker-compose up -d
