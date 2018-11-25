package domain;

import java.io.Serializable;
import java.util.Date;

public class Assignment implements Serializable {
    private Integer id;
    private Integer classId;
    private String name;
    private String path;
    private Date createDate;
    private Date dueDate;
    private Boolean publish;

    public Integer getId() {
        return id;
    }

    public Integer getClassId() {
        return classId;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Boolean getPublish() {
        return publish;
    }

    public static class AssignmentBuilder{
        private Integer id;
        private Integer classId;
        private String name;
        private String path;
        private Date createDate;
        private Date dueDate;
        private Boolean publish = true; // default

        public AssignmentBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public AssignmentBuilder setClassId(Integer classId) {
            this.classId = classId;
            return this;
        }

        public AssignmentBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AssignmentBuilder setPath(String path) {
            this.path = path;
            return this;
        }

        public AssignmentBuilder setCreateDate(Date createDate) {
            this.createDate = createDate;
            return this;
        }

        public AssignmentBuilder setDueDate(Date dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public AssignmentBuilder setPublish(Boolean publish) {
            this.publish = publish;
            return this;
        }

        public Assignment build(){
            return new Assignment(this);
        }

        /*
        * Constructor [classId, name] required
        * */
        public AssignmentBuilder(int classId, String name) {
            this.classId = classId;
            this.name = name;
        }
    }

    private Assignment(AssignmentBuilder assignmentBuilder) {
        this.id = assignmentBuilder.id;
        this.classId = assignmentBuilder.classId;
        this.name = assignmentBuilder.name;
        this.path = assignmentBuilder.path;
        this.createDate = assignmentBuilder.createDate;
        this.dueDate = assignmentBuilder.dueDate;
        this.publish = assignmentBuilder.publish;
    }
}
