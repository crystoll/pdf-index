package fi.solita.arto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;

@SpringBootApplication
public class PdfIndexerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfIndexerApplication.class, args);
	}
}
