package fi.solita.arto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "pdfs", type = "pdffile", shards = 1, replicas = 0, refreshInterval = "-1")
public class PdfFile {



    @Id
    private String name;

    private byte[] content;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public PdfFile(String name) {
        this.name = name;
    }

    public PdfFile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PdfFile{" +
                "name='" + name + '\'' +
                '}';
    }
}
