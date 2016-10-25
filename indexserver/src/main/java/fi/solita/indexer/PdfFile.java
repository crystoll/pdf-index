package fi.solita.indexer;

public class PdfFile {

    private String name;

    private String content;

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
