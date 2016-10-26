//package fi.solita.indexer;
//
//
//import io.searchbox.client.JestClient;
//import io.searchbox.core.Index;
//import io.searchbox.core.Search;
//import io.searchbox.core.SearchResult;
//import io.searchbox.indices.CreateIndex;
//import io.searchbox.indices.DeleteIndex;
//import io.searchbox.indices.IndicesExists;
//import org.apache.poi.hslf.record.Document;
//import org.apache.tika.exception.TikaException;
//import org.apache.tika.metadata.Metadata;
//import org.apache.tika.metadata.TikaCoreProperties;
//import org.apache.tika.parser.AutoDetectParser;
//import org.apache.tika.parser.ParseContext;
//import org.apache.tika.sax.BodyContentHandler;
//
////import org.elasticsearch.index.query.MultiMatchQueryBuilder;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
////import org.springframework.data.elasticsearch.core.query.SearchQuery;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.xml.sax.SAXException;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
////import static org.elasticsearch.index.query.QueryBuilders.*;
//import static org.springframework.web.bind.annotation.RequestMethod.GET;
//
//@RestController
//@RequestMapping(value = "/api")
//public class SearchController {
//
//    private Logger logger = Logger.getLogger(this.getClass().getName());
//
//    @Autowired
//    private JestClient jestClient;
//
//
//    @Async
//    @RequestMapping(value = "/index", method = GET)
//    public void indexPdfs() throws IOException {
//        boolean indexExists = jestClient.execute(new IndicesExists.Builder("pdfs").build()).isSucceeded();
//        if (indexExists) {
//            logger.info("pdfs index already exists, deleting it..");
//            jestClient.execute(new DeleteIndex.Builder("pdfs").build());
//        }
//        logger.info("creating pdfs index to ElasticSearch");
//        jestClient.execute(new CreateIndex.Builder("pdfs").build());
//
//        String pdfPath = System.getenv("PDF_FOLDER");
//        if (pdfPath == null) pdfPath="./pdf-files";
//        logger.info(String.format("Using PDF_FOLDER %s", pdfPath));
//
//        Files.list(Paths.get(pdfPath))
//                .filter((f) -> f.getFileName().toString().endsWith(".pdf"))
//                .forEach(f -> storePdfFile(f));
//    }
//
//    @RequestMapping(value = "/search", method = GET, produces = "application/json")
//    public List<String> search(@RequestParam String query) throws IOException {
//        logger.info(String.format("Doing a search with keyword %s", query));
//
//
//        QueryBuilder esQuery = QueryBuilders.boolQuery()
//                .should(QueryBuilders.termQuery("title",query))
//                .should(QueryBuilders.termQuery("contentText",query));
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(esQuery);
//        Search.Builder searchBuilder = new Search.Builder(searchSourceBuilder.toString())
//                .addIndex("pdfs")
//                .addType("pdffile");
//
//
//
//        SearchResult result = jestClient.execute(searchBuilder.build());
//        logger.info("Got a result!" + result.getJsonString());
//        List names = new ArrayList<>();
//        result.getHits(PdfFile.class).stream().forEach(h-> {
//            System.out.println(h.source.getName() + " :" + h.index);
//            names.add(h.source.getName());
//        });
//
//
//        return names;
//    }
//
//    public void storePdfFile(Path file) {
//        logger.log(Level.INFO, "storePdfFile: " + file.getFileName().toString());
//        FileInputStream stream = null;
//        try {
//            stream = new FileInputStream(file.toFile());
//            BodyContentHandler handler = new BodyContentHandler(-1);
//            Metadata metadata = new Metadata();
//            AutoDetectParser parser = new AutoDetectParser();
//            ParseContext parseContext = new ParseContext();
//            parser.parse(stream, handler, metadata, parseContext);
//            Map<String, String> map = new HashMap<>();
//            map.put("name", file.getFileName().toString());
//            map.put("title", metadata.get(TikaCoreProperties.TITLE));
//            map.put("pageCount", metadata.get("xmpTPg:NPages"));
//            map.put("text", handler.toString().replaceAll("\n|\r|\t", " "));
//            PdfFile pdfFile = new PdfFile(file.getFileName().toString());
//            pdfFile.setTitle(metadata.get(TikaCoreProperties.TITLE));
//            pdfFile.setPageCount(metadata.get("xmpTPg:NPages"));
//            pdfFile.setContentText(handler.toString().replaceAll("\n|\r|\t", " "));
//            Index index = new Index.Builder(pdfFile).index("pdfs").type("pdffile").build();
//            jestClient.execute(index);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (TikaException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                stream.close();
//            } catch (IOException e) {}
//        }
//
//    }
//
//}
