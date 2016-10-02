docker run -d -p 9200:9200 -p 9300:9300 elasticsearch:latest
rem docker run --rm -p 9200:9200 -p 9300:9300 --name=es elasticsearch:latest -Des.network.host=0.0.0.0


rem volumes:
rem ./elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml
