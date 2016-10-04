package fi.solita.arto;


import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value="/api/search")
public class SearchController {

    @Autowired
    private PdfFileRepository pdfFileRepository;



    @RequestMapping(method = GET, produces = "application/json")
    public List<String> getHello() throws IOException {
        pdfFileRepository.deleteAll();

        String pdfPath = "pdf-files";
        Files.list(Paths.get(pdfPath))
                .forEach(f->storePdfFile(f));

        List names = new ArrayList<>();

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("content","java"))
                .build();
        Iterable<PdfFile> results  = pdfFileRepository.search(searchQuery);
        results.forEach(p->names.add(p.getName()));
        return names;
    }

    public void storePdfFile(Path file)  {
        System.out.println(file.getFileName().toString());
        PdfFile pdfFile = new PdfFile(file.getFileName().toString());
        try {
            byte[] content = Files.readAllBytes(file);
            pdfFile.setContent(content);
            pdfFileRepository.save(pdfFile);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}
