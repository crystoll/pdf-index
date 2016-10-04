package fi.solita.arto;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value="/api/search")
public class SearchController {


    @RequestMapping(method = GET, produces = "application/json")
    public List<String> getHello() throws IOException {
//        pdfFileRepository.deleteAll();
//
//        String pdfPath = "pdf-files";
//        Files.list(Paths.get(pdfPath))
//                .forEach(f->storePdfFile(f));
//
//        List names = new ArrayList<>();
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchQuery("content","java"))
//                .build();
//        Iterable<PdfFile> results  = pdfFileRepository.search(searchQuery);
//        results.forEach(p->names.add(p.getName()));
//        return names;
        return Collections.emptyList();
    }

//    public void storePdfFile(Path file)  {
//        System.out.println(file.getFileName().toString());
//        PdfFile pdfFile = new PdfFile(file.getFileName().toString());
//        try {
//            byte[] content = Files.readAllBytes(file);
//            pdfFile.setContent(content);
//            pdfFileRepository.save(pdfFile);
//        } catch (IOException ioe) {
//            throw new RuntimeException(ioe);
//        }
//    }

}
