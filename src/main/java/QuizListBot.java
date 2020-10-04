import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class QuizListBot extends TelegramLongPollingBot {
    private static final Logger log = Logger.getLogger(QuizBot.class.getName());
    private final String token;
    private final String name;
    private List<Question> questions;

    QuizListBot (String token) {
        super();
        this.token = token;
        this.name = "QuizListBot";
        this.questions = new ArrayList<>();
        this.questions.add(new Question("Select method of Object in Java", Arrays.asList("copy", "getClass()"), 1));
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
        Question question = this.questions.get(Integer.parseInt(update.getMessage().getText()));
        SendPoll sendPoll = new SendPoll(update.getMessage().getChatId(), question.getText(), question.getOptions());
        sendPoll.setAnonymous(false);
        sendPoll.setType("quiz");
        sendPoll.setCorrectOptionId(question.getAnswerIndex());
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
