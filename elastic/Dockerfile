FROM elasticsearch:2.4

# Install HEAD plugin
RUN /usr/share/elasticsearch/bin/plugin install mobz/elasticsearch-head

# Install MAPPER ATTACHMENTS plugin
RUN /usr/share/elasticsearch/bin/plugin install mapper-attachments

EXPOSE 9200 9300
