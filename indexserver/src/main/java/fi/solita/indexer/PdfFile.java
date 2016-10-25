package fi.solita.indexer;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "pdfs", type = "pdffile")
public class PdfFile {

    @Id
    private String name;

    @Field(type = FieldType.Attachment)
    private String content;

    @Field(type = FieldType.Attachment)
    private String contentText;

    private String title;

    private String pageCount;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentText() {
        return contentText;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public PdfFile(String name) {
        this.name = name;
    }

    public PdfFile() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
