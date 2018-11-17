package domain;

import java.util.Date;

public class POLSClass {

    private Integer id;
    private String classname;
    private String description;
    private Date startDate;
    private Date endDate;
    private Account instructor;

    public Integer getId() {
        return id;
    }

    public String getClassname() {
        return classname;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Account getInstructor() {
        return instructor;
    }

    public static class POLSClassBuilder {
        private Integer id;
        private String classname;
        private String description;
        private Date startDate;
        private Date endDate;
        private Account instructor;

        public POLSClassBuilder(){};

        public POLSClassBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public POLSClassBuilder setClassname(String classname) {
            this.classname = classname;
            return this;
        }

        public POLSClassBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public POLSClassBuilder setStartDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public POLSClassBuilder setEndDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public POLSClassBuilder setInstructor(Account instructor) {
            this.instructor = instructor;
            return this;
        }

        public POLSClass build() {
            return new POLSClass(this);
        }
    }

    public POLSClass(POLSClassBuilder polsClassBuilder) {
        this.id = polsClassBuilder.id;
        this.classname = polsClassBuilder.classname;
        this.description = polsClassBuilder.description;
        this.startDate = polsClassBuilder.startDate;
        this.endDate = polsClassBuilder.endDate;
        this.instructor = polsClassBuilder.instructor;
    }
}
