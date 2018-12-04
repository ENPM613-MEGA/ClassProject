package DAO;

import domain.Document;
import domain.DocumentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Repository
public class DocumentDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    };

    private final String INSER_A_DOCUMENT = "INSERT INTO Documents " +
                                            "(c_id, filename, type, path, create_date, publish) " +
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
            throw e;
        }
    }


    private final String GET_DOC_BY_ID = "SELECT id, c_id, filename, type, path, create_date, publish " +
                                         "FROM Documents " +
                                         "WHERE id = ?";
    /*
    * get document by fileId
    * */
    public Document getDocumentByFID(int fId) {

        try{
            return (Document)jdbcTemplate.queryForObject(GET_DOC_BY_ID, new Object[]{fId}, new DocumentRowMapper());
        }catch (Exception e) {
            throw e;
        }
    }


    private final String DELETE_FILE_BY_FID = "DELETE FROM Documents WHERE id = ?";

    /*
    * delete a document record by id
    * */
    public void deleteFileByID(int fId) {
        try {
            jdbcTemplate.update(DELETE_FILE_BY_FID, new Object[]{fId});
        }catch (Exception e) {
            throw e;
        }
    }


    private final String UPDATE_FILE_PUBLISH_STATUS = "UPDATE Documents " +
                                                      "SET publish = ? " +
                                                      "WHERE id = ?";
    /*
    * update document's publish status
    * */
    public void updateDocument(int fId, boolean newPublishStatus) {
        try{
            jdbcTemplate.update(UPDATE_FILE_PUBLISH_STATUS, new Object[]{newPublishStatus, fId});
        }catch (Exception e) {
            throw e;
        }
    }


    private final String GET_CLASS_FILES = "SELECT id, filename, publish, type " +
                                           "FROM Documents WHERE c_id = ? AND (type = 'file' OR type = 'syllabus')";

    /*
    * get the raw list of files of specific class
    * */
    public List<Map<String, Object>> getClassFileList(int cId) {

        try {
            return jdbcTemplate.queryForList(GET_CLASS_FILES, new Object[]{cId});
        }catch (Exception e){
            throw e;
        }
    }
}