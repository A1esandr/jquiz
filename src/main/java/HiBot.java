import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.logging.Logger;

public class HiBot extends TelegramLongPollingBot {
    private static final Logger log = Logger.getLogger(HiBot.class.getName());
    private final String token;
    private final String name;

    HiBot (String token) {
        super();
        this.token = token;
        this.name = "HiBot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info(String.format("Get message: %s", update.getMessage().getText()));
            SendPhoto sendPhotoRequest = new SendPhoto();
            // Set destination chat id
            sendPhotoRequest.setChatId(update.getMessage().getChatId());
            // Set the photo file as a new photo
            sendPhotoRequest.setPhoto(new File("src/main/resources/hi.jpg"));
            sendPhotoRequest.setCaption("Hey, I know you!");
            actualExecute(sendPhotoRequest);
        }
    }

    protected void actualExecute(SendPhoto sendPhotoRequest) {
        try {
            // Execute the method
            execute(sendPhotoRequest);
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
