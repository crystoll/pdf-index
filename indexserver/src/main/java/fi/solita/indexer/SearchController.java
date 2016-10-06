package fi.solita.indexer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/api")
public class SearchController {

    @Autowired
    private PdfFileRepository pdfFileRepository;

    @Async
    @RequestMapping(value = "/index", method = GET)
    public void indexPdfs() throws IOException {
        pdfFileRepository.deleteAll();
        String pdfPath = "pdf-files";
        Files.list(Paths.get(pdfPath))
                .forEach(f -> storePdfFile(f));
    }

    @RequestMapping(value="/search", method = GET, produces = "application/json")
    public List<String> search(@RequestParam String query) throws IOException {
        List names = new ArrayList<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(regexpQuery("content", String.format(".*%s.*", query)))
                .build();
        Iterable<PdfFile> results = pdfFileRepository.search(searchQuery);
        results.forEach(p -> names.add(p.getName()));
        return names;
    }

    public void storePdfFile(Path file) {
        System.out.println(file.getFileName().toString());
        PdfFile pdfFile = new PdfFile(file.getFileName().toString());
        try {
            System.out.println("Reading file as byte array...");
            byte[] content = Files.readAllBytes(file);
            System.out.println("File was read");
            pdfFile.setContent(org.elasticsearch.common.Base64.encodeBytes(content));
            System.out.println("Content was base64 encoded, size " + pdfFile.getContent().length());
            pdfFileRepository.save(pdfFile);
            System.out.println("File was saved");
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}
