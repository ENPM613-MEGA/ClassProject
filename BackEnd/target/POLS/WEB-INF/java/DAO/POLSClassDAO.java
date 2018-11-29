package DAO;

import domain.POLSClass;
import domain.POLSClassRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class POLSClassDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}


    private final String GET_CLASS_LIST_OF_USER = "SELECT Classes.id, Classes.instructor_id, Classes.class_name, Classes.start_date, Classes.end_date, Classes.description " +
                                                  "FROM Classes, User_Class " +
                                                  "WHERE Classes.id = User_Class.c_id AND User_Class.u_id = ?";
    /*
    * Get the class list of a user
    * */
    public List<Map<String, Object>> getClassList(int uId) {
        try{
            return jdbcTemplate.queryForList(GET_CLASS_LIST_OF_USER, new Object[]{uId});
        }catch (Exception e){
            throw e;
        }
    }


    private final String GET_CLASS_BY_ID = "SELECT id, instructor_id, class_name, start_date, end_date, description " +
                                            "FROM Classes WHERE id = ?";
    /*
    * get class by class Id
    * */
    public POLSClass getClassById(int cId) {
        try {
            return (POLSClass)jdbcTemplate.queryForObject(GET_CLASS_BY_ID, new Object[]{cId}, new POLSClassRowMapper());
        }catch (Exception e) {
            throw e;
        }
    }
}
