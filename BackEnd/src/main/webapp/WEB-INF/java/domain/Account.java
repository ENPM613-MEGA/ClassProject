package domain;



import java.io.Serializable;
import java.util.Date;


public class Account implements Serializable {
    private final Integer id;
    private final String username;
    private final String passwd;
    private final String gender;
    private final String role;
    private final Date birth;
    private final Integer points;
    private final String address;
    private final String email;
    private final Boolean colorBlind;


    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }

    public Date getBirth() {
        return birth;
    }

    public Integer getPoints() {
        return points;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getColorBlind() {
        return colorBlind;
    }

    /*
    * AccountBuilder
    * [username], [gender], [role] are required
    * */
    public static class AccountBuilder {
        private String username;
        private String gender;
        private String role;
        private Integer id;
        private String passwd;
        private Date birth;
        private Integer points;
        private String address;
        private String email;
        private boolean colorBlind;

        public  AccountBuilder() {
            this.username = null;
            this.gender = null;
            this.role = null;
        }

        public AccountBuilder(String username, String gender, String role) {
            this.username = username;
            this.gender = gender;
            this.role = role;
        }
        /*
        * Must keep the setters of required fields for query turn back;
        * */
        public AccountBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public AccountBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public AccountBuilder setRole (String role) {
            this.role = role;
            return this;
        }

        public AccountBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public AccountBuilder setPasswd(String passwd) {
            this.passwd = passwd;
            return this;
        }

        public AccountBuilder setBirth(Date birth) {
            this.birth = birth;
            return this;
        }

        public AccountBuilder setPoints(Integer points) {
            this.points = points;
            return this;
        }

        public AccountBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public AccountBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AccountBuilder setColorBlind(boolean colorBlind) {
            this.colorBlind = colorBlind;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }

    private Account(AccountBuilder accountBuilder) {
        this.id = accountBuilder.id;
        this.username = accountBuilder.username;
        this.gender = accountBuilder.gender;
        this.birth = accountBuilder.birth;
        this.passwd = accountBuilder.passwd;
        this.points = accountBuilder.points;
        this.address = accountBuilder.address;
        this.email = accountBuilder.email;
        this.role = accountBuilder.role;
        this.colorBlind = accountBuilder.colorBlind;
    }
}
