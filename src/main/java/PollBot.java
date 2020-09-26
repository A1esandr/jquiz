import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Logger;

public class PollBot extends TelegramLongPollingBot {
    private static final Logger log = Logger.getLogger(PollBot.class.getName());
    private String token;
    private String name;

    PollBot (String token) {
        super();
        this.token = token;
        this.name = "PollBot";
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info(String.format("Get message: %s", update.getMessage().getText()));
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText(update.getMessage().getText());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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
