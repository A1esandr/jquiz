import java.util.List;

public class Question {
    private final String text;
    private final List<String> options;
    private final int answerIndex;

    Question(String text, List<String> options, int answerIndex) {
        this.text = text;
        this.options = options;
        this.answerIndex = answerIndex;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }
}
