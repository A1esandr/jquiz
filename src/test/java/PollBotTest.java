import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PollBotTest {
    private PollBot pollBot;

    @Before
    public void setUp() {
        pollBot = new PollBot("1");
    }

    @Test
    public void WhenPollBot_Construct_GetTokenFromArg() {
        assertEquals("1", pollBot.getBotToken());
    }

    @Test
    public void WhenPollBot_Construct_SetName() {
        assertEquals("PollBot", pollBot.getBotUsername());
    }

    @Test
    public void WhenPollBot_ReceiveEmptyUpdate_NotCallExecute() throws TelegramApiException {
        PollBot bot = mock(PollBot.class);
        Update update = new Update();
        doCallRealMethod().when(bot).onUpdateReceived(update);

        bot.onUpdateReceived(update);

        verify(bot, times(0)).execute(any(SendMessage.class));
    }

    @Test
    public void WhenPollBot_ReceiveNotEmptyUpdate_CallExecute() throws TelegramApiException {
        PollBot bot = mock(PollBot.class);
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        doReturn(true).when(update).hasMessage();
        doReturn(true).when(message).hasText();
        doReturn("test").when(message).getText();
        doReturn(1L).when(message).getChatId();
        doReturn(message).when(update).getMessage();
        doCallRealMethod().when(bot).onUpdateReceived(update);

        bot.onUpdateReceived(update);

        verify(bot, times(1)).execute(any(SendMessage.class));
    }
}
