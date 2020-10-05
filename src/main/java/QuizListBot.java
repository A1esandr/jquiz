import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class QuizListBot extends TelegramLongPollingBot {
    private static final Logger log = Logger.getLogger(QuizBot.class.getName());
    private final String token;
    private final String name;
    private final List<Question> questions;

    QuizListBot (String token) {
        super();
        this.token = token;
        this.name = "QuizListBot";
        String methodsOfObject = "Select method of Object in Java";
        this.questions = Arrays.asList(
            new Question(methodsOfObject, Arrays.asList("copy()", "getClass()"), 1),
            new Question(methodsOfObject, Arrays.asList("notify()", "interrupt()"), 0),
            new Question(methodsOfObject, Arrays.asList("wait()", "getLock()"), 0),
            new Question(methodsOfObject, Arrays.asList("notifyAll()", "getInstance()"), 0),
            new Question(methodsOfObject, Arrays.asList("id()", "toString()"), 1),
            new Question(methodsOfObject, Arrays.asList("wait(long timeoutMillis)", "wait(int timeoutMillis)"), 0),
            new Question(methodsOfObject, Arrays.asList("wait(long timeoutMillis, int nanos)", "wait(int timeoutMillis, int nanos)"), 0),
            new Question(methodsOfObject, Arrays.asList("finalize()", "final()"), 0),
            new Question(methodsOfObject, Arrays.asList("int hashCode()", "long hashCode()"), 0),
            new Question(methodsOfObject, Arrays.asList("equalsTo(Object obj)", "equals(Object obj)"), 1)
        );
    }

    protected List<Question> getQuestions() {
        return questions;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info(String.format("Get message: %s", update.getMessage().getText()));
            SendPoll sendPoll = getSendPoll(update);
            actualExecute(sendPoll);
        }
    }

    protected SendPoll getSendPoll(Update update) {
        SendPoll sendPoll = new SendPoll();
        sendPoll.setChatId(update.getMessage().getChatId());
        int index = Integer.parseInt(update.getMessage().getText());
        if(index >= 0 && index < this.questions.size()) {
            Question question = this.questions.get(Integer.parseInt(update.getMessage().getText()));
            sendPoll.setQuestion(question.getText());
            sendPoll.setOptions(question.getOptions());
            sendPoll.setCorrectOptionId(question.getAnswerIndex());
        }
        sendPoll.setAnonymous(false);
        sendPoll.setType("quiz");
        return sendPoll;
    }

    protected void actualExecute(SendPoll sendPoll) {
        try {
            // Execute the method
            execute(sendPoll);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return this.name;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }
}
