package domain;

import java.util.Date;

public class Document {
    private Integer id;
    private Integer classId;
    private String filename;
    private String type;
    private String path;
    private Date createDate;
    private Boolean publish;

    public Integer getId() {
        return id;
    }

    public Integer getClassId() {
        return classId;
    }

    public String getFilename() {
        return filename;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Boolean getPublish() {
        return publish;
    }

    public static class DocumentBuilder{
        private Integer id;
        private Integer classId;
        private String filename;
        private String type;
        private String path;
        private Date createDate;
        private Boolean publish;

        public DocumentBuilder(Integer classId, String filename, String type) {
            this.classId = classId;
            this.filename = filename;
            this.type = type;
        }

        public DocumentBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public DocumentBuilder setClassId(Integer classId) {
            this.classId = classId;
            return this;
        }

        public DocumentBuilder setFilename(String filename) {
            this.filename = filename;
            return this;
        }

        public DocumentBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public DocumentBuilder setPath(String path) {
            this.path = path;
            return this;
        }

        public DocumentBuilder setCreateDate(Date createDate) {
            this.createDate = createDate;
            return this;
        }

        public DocumentBuilder setPublish(Boolean publish) {
            this.publish = publish;
            return this;
        }

        public Document build() {
            return new Document(this);
        }
    }

    private Document(DocumentBuilder documentBuilder) {
        this.id = documentBuilder.id;
        this.classId = documentBuilder.classId;
        this.filename = documentBuilder.filename;
        this.type = documentBuilder.type;
        this.path = documentBuilder.path;
        this.createDate = documentBuilder.createDate;
        this.publish = documentBuilder.publish;
    }

}
