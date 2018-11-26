package controller;


/**
 * GradeController receives quest from dispatcher-servlet, then accesses data from
 * database and put the information into model and return this model to view. It
 * is a controller that deals with all the quests regarding with grades.
 *
 * Students can see grade for certain assignment.
 * Instructor can see grade for certain assignment and certain student.
 * Instructor can give grade for certain assignment and certain student.
 * Instructor can update grade for certain assignment and certain student.
 *
 * @author --- Jiwen Zhang ----
 */

@RestController
@RequestMapping("/grade")
public class GradeController {
    private GradeDAO gradeDAO

    @Autowired
    public void setGradeDAO(GradeDAO gradeDAO) {
        this.gradeDAO = gradeDAO;
    }

    /**
     * Query for the grade for certain user and certain assignment.
     *
     * @param userId, assignmentId.
     * @return mapModel.
     */
    @RequestMapping(value = "/gradePage/{userId}/{assignmentId}", method = RequestMethod.GET)
    public Map<String, Object> showGrade(???) {
        Map<String, Object> mapModel = new HashMap<>();

        try {
            Grade grade = gradeDAO.getGrade(userId, assignmentId);
            mapModel.put("status", "success");
            mapModel.put("grade", grade);
        } catch (Exception e) {
            mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }

    /**
     * Update the grade for certain user and certain assignment.
     *
     * @param userId, assignmentId, grade.
     * @return mapModel.
     */
    @RequestMapping(value = "/updateGrade/{userId}/{assignmentId}/{grade}", method
            = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateGrade(HttpServletRequest rq) {
        Map<String, Object> mapModel = new HashMap<>();
        JSONObject input = JSONHelper.readJSONObject(rq);

        try{
            gradeDAO.updateGrade(input.getInt("userId"), input.getInt("assignmentId"), input.getInt("grade"));
            mapModel.put("status", "success");
        } catch (Exception e){
            mapModel = POLSHelper.failureReturnConstructor(e.getMessage());
        }
        return mapModel;
    }
}
