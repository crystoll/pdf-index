package fi.solita.indexer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PdfIndexerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(PdfIndexerApplication.class, args);
	}
}
