import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
        PollBotStub stub = mock(PollBotStub.class);

        Update update = new Update();
        stub.onUpdateReceived(update);

        verify(stub, times(0)).execute(any(SendMessage.class));
    }


    private class PollBotStub extends PollBot {
        PollBotStub () {
            super("");
        }
    }
}
