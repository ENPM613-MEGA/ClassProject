package domain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Problem implements Serializable {
    private Integer number;
    private String content;
    private Integer grade;
    private List<String> choices;
    private String Solution;
    private String type;

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public String getSolution() {
        return Solution;
    }

    public void setSolution(String solution) {
        Solution = solution;
    }

    public Problem() {}

    public Problem(JSONObject jsonObject) {
        this.number = jsonObject.getInt("number");
        this.content = jsonObject.getString("content");
        JSONArray choicesArray = jsonObject.getJSONArray("choices");
        this.grade = jsonObject.getInt("grade");
        this.choices = new ArrayList<>();
        for (int i = 0; i < choicesArray.length(); i++) {
            this.choices.add(choicesArray.getString(i));
        }
        this.type = jsonObject.getString("type");
        this.Solution = jsonObject.getString("solution");
    }
}
