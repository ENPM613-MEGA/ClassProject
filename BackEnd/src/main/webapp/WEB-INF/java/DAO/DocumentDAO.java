package DAO;

import domain.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;

@Repository
public class DocumentDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    };

    private final String INSER_A_DOCUMENT = "INSERT INTO Documents " +
                                            "(c_id, filename, type, path, create_date, publish)" +
                                            "VALUES (?, ?, ?, ?, ?, ?)";
    /*
    * create new Document record in db,
    * */
    public void createNewDocument(Document document) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Object[] inputs = new Object[6];
        inputs[0] = document.getClassId();
        inputs[1] = document.getFilename();
        inputs[2] = document.getType();
        inputs[3] = document.getPath();
        inputs[4] = df.format(document.getCreateDate());
        inputs[5] = document.getPublish() == null ? Boolean.TRUE : document.getPublish(); //default true

        try {
            jdbcTemplate.update(INSER_A_DOCUMENT, inputs);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}