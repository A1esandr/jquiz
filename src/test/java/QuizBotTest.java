import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class QuizBotTest {
    private QuizBot quizBot;

    @Before
    public void setUp() {
        quizBot = new QuizBot("1");
    }

    @Test
    public void WhenQuizBot_Construct_GetTokenFromArg() {
        assertEquals("1", quizBot.getBotToken());
    }

    @Test
    public void WhenQuizBot_Construct_SetName() {
        assertEquals("QuizBot", quizBot.getBotUsername());
    }

    @Test
    public void WhenQuizBot_ReceiveEmptyUpdate_NotCallExecute() {
        QuizBot bot = mock(QuizBot.class);
        Update update = new Update();
        doCallRealMethod().when(bot).onUpdateReceived(update);

        bot.onUpdateReceived(update);

        verify(bot, times(0)).actualExecute(any(SendPoll.class));
    }

    @Test
    public void WhenQuizBot_ReceiveNotEmptyUpdate_CallExecute() {
        QuizBot bot = mock(QuizBot.class);
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        doReturn(true).when(update).hasMessage();
        doReturn(true).when(message).hasText();
        doReturn("test").when(message).getText();
        doReturn(1L).when(message).getChatId();
        doReturn(message).when(update).getMessage();
        doCallRealMethod().when(bot).onUpdateReceived(update);
        doCallRealMethod().when(bot).getSendPoll(update);

        bot.onUpdateReceived(update);

        verify(bot, times(1)).actualExecute(any(SendPoll.class));
    }

    @Test
    public void WhenQuizBot_GetSendPoll() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        doReturn(true).when(update).hasMessage();
        doReturn(true).when(message).hasText();
        doReturn("test").when(message).getText();
        doReturn(1L).when(message).getChatId();
        doReturn(message).when(update).getMessage();

        SendPoll result = quizBot.getSendPoll(update);

        assertEquals("1", result.getChatId());
        assertEquals("quiz", result.getType());
        assertFalse(result.getAnonymous());
    }
}
