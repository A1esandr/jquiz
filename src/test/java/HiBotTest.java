import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HiBotTest {
    private HiBot hiBot;

    @Before
    public void setUp() {
        hiBot = new HiBot("1");
    }

    @Test
    public void WhenHiBot_Construct_GetTokenFromArg() {
        assertEquals("1", hiBot.getBotToken());
    }

    @Test
    public void WhenHiBot_Construct_SetName() {
        assertEquals("HiBot", hiBot.getBotUsername());
    }

    @Test
    public void WhenHiBot_ReceiveEmptyUpdate_NotCallExecute() throws TelegramApiException {
        HiBot bot = mock(HiBot.class);
        Update update = new Update();
        doCallRealMethod().when(bot).onUpdateReceived(update);

        bot.onUpdateReceived(update);

        verify(bot, times(0)).actualExecute(any(SendPhoto.class));
    }

    @Test
    public void WhenHiBot_ReceiveNotEmptyUpdate_CallExecute() throws TelegramApiException {
        HiBot bot = mock(HiBot.class);
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        doReturn(true).when(update).hasMessage();
        doReturn(true).when(message).hasText();
        doReturn("test").when(message).getText();
        doReturn(1L).when(message).getChatId();
        doReturn(message).when(update).getMessage();
        doCallRealMethod().when(bot).onUpdateReceived(update);

        bot.onUpdateReceived(update);

        verify(bot, times(1)).actualExecute(any(SendPhoto.class));
    }
}
