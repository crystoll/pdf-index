#docker run -d  --name es -p 9200:9200 -p 9300:9300 -e ES_HEAP_SIZE=1g pdfindex_elasticsearch -Des.network.host=0.0.0.0
docker run -d  --name es -p 9200:9200 -p 9300:9300 pdfindex_elasticsearch -Des.network.host=0.0.0.0
