package fi.solita.indexer;


import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/api")
public class SearchController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private PdfFileRepository pdfFileRepository;

    @Async
    @RequestMapping(value = "/index", method = GET)
    public void indexPdfs() throws IOException {
        pdfFileRepository.deleteAll();
        String pdfPath = System.getenv("PDF_FOLDER");
        if (pdfPath == null) pdfPath="pdf-files";
        logger.info(String.format("Using PDF_FOLDER %s", pdfPath));

        Files.list(Paths.get(pdfPath))
                .filter((f) -> f.getFileName().toString().endsWith(".pdf"))
                .forEach(f -> storePdfFile(f));
    }

    @RequestMapping(value = "/search", method = GET, produces = "application/json")
    public List<String> search(@RequestParam String query) throws IOException {
        List names = new ArrayList<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(boolQuery().should(
                        regexpQuery("title", String.format(".*%s.*", query)).boost(2.0f)
                ).should(
                        regexpQuery("contentText", String.format(".*%s.*", query))
                ).should(
                        regexpQuery("content", String.format(".*%s.*", query))
                ))
                .build();

        Iterable<PdfFile> results = pdfFileRepository.search(searchQuery);
        results.forEach(p -> names.add(p.getName()));
        return names;
    }

    public void storePdfFile(Path file) {
        logger.log(Level.INFO, "storePdfFile: " + file.getFileName().toString());
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file.toFile());
            BodyContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            AutoDetectParser parser = new AutoDetectParser();
            ParseContext parseContext = new ParseContext();
            parser.parse(stream, handler, metadata, parseContext);
            Map<String, String> map = new HashMap<>();
            map.put("name", file.getFileName().toString());
            map.put("title", metadata.get(TikaCoreProperties.TITLE));
            map.put("pageCount", metadata.get("xmpTPg:NPages"));
            map.put("text", handler.toString().replaceAll("\n|\r|\t", " "));
            PdfFile pdfFile = new PdfFile(file.getFileName().toString());
//          logger.log(Level.INFO,"Reading file as byte array...");
//            byte[] content = Files.readAllBytes(file);
//          logger.log(Level.INFO,"File was read");
//            pdfFile.setContent(org.elasticsearch.common.Base64.encodeBytes(content));
//          logger.log(Level.INFO,"Content was base64 encoded, size " + pdfFile.getContent().length());
            pdfFile.setTitle(metadata.get(TikaCoreProperties.TITLE));
            pdfFile.setPageCount(metadata.get("xmpTPg:NPages"));
            pdfFile.setContentText(handler.toString().replaceAll("\n|\r|\t", " "));
            pdfFileRepository.save(pdfFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

}
