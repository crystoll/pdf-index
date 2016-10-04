package fi.solita.arto;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PdfFileRepository extends ElasticsearchRepository<PdfFile, String>{
}
