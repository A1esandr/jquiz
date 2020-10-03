import java.util.List;

public class Question {
    private String text;
    private List<String> options;
    private int answerIndex;

    Question(String text, List<String> options, int answerIndex) {
        this.text = text;
        this.options = options;
        this.answerIndex = answerIndex;
    }

    public String getText() {
        return text;
    }
}
