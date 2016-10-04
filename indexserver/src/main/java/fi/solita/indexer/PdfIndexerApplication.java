package fi.solita.indexer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PdfIndexerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(PdfIndexerApplication.class, args);
	}
}
