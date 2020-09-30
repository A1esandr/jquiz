import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class QuizBot extends TelegramLongPollingBot {
    private static final Logger log = Logger.getLogger(QuizBot.class.getName());
    private final String token;
    private final String name;

    QuizBot (String token) {
        super();
        this.token = token;
        this.name = "QuizBot";
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
        List<String> options = Arrays.asList("Yes", "No");
        SendPoll sendPoll = new SendPoll(update.getMessage().getChatId(), "Are you human?", options);
        sendPoll.setAnonymous(false);
        sendPoll.setType("quiz");
        sendPoll.setCorrectOptionId(0);
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
