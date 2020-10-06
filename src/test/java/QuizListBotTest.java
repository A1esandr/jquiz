import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

public class QuizListBotTest {
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
    public void WhenQuizBot_ReceiveNotEmptyUpdate_CallExecuteWithSendPoll() {
        QuizBotSpy bot = new QuizBotSpy("1");
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        doReturn(true).when(update).hasMessage();
        doReturn(true).when(message).hasText();
        doReturn("0").when(message).getText();
        doReturn(1L).when(message).getChatId();
        doReturn(message).when(update).getMessage();

        bot.onUpdateReceived(update);

        SendPoll result = bot.getPoll();
        assertEquals("1", result.getChatId());
        assertEquals("quiz", result.getType());
        assertFalse(result.getAnonymous());
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

    @Test
    public void WhenQuizBot_ReceiveUpdateWithNumber_ReturnFirstQuiz() {
        QuizBotSpy bot = new QuizBotSpy("1");
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        doReturn(true).when(update).hasMessage();
        doReturn(true).when(message).hasText();
        doReturn("0").when(message).getText();
        doReturn(1L).when(message).getChatId();
        doReturn(message).when(update).getMessage();

        bot.onUpdateReceived(update);

        SendPoll result = bot.getPoll();
        assertEquals("1", result.getChatId());
        assertEquals("quiz", result.getType());
        assertFalse(result.getAnonymous());
        List<Question> questions = bot.getQuestions();
        assertEquals(questions.get(0).getText(), result.getQuestion());
    }

    @Test
    public void WhenQuizBot_ReceiveUpdateWithWrongNumber_ReturnNothing() {
        QuizBotSpy bot = new QuizBotSpy("1");
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        doReturn(true).when(update).hasMessage();
        doReturn(true).when(message).hasText();
        doReturn("10").when(message).getText();
        doReturn(1L).when(message).getChatId();
        doReturn(message).when(update).getMessage();

        bot.onUpdateReceived(update);

        SendPoll result = bot.getPoll();
        assertEquals("1", result.getChatId());
        assertEquals("quiz", result.getType());
        assertFalse(result.getAnonymous());
        assertNull(result.getQuestion());
    }

    @Test
    public void WhenQuizBot_ReceiveUpdateWithNegativeNumber_ReturnNothing() {
        QuizBotSpy bot = new QuizBotSpy("1");
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        doReturn(true).when(update).hasMessage();
        doReturn(true).when(message).hasText();
        doReturn("-1").when(message).getText();
        doReturn(1L).when(message).getChatId();
        doReturn(message).when(update).getMessage();

        bot.onUpdateReceived(update);

        SendPoll result = bot.getPoll();
        assertEquals("1", result.getChatId());
        assertEquals("quiz", result.getType());
        assertFalse(result.getAnonymous());
        assertNull(result.getQuestion());
    }

    private class QuizBotSpy extends QuizListBot {
        private SendPoll sendPoll;

        QuizBotSpy(String token) {
            super(token);
        }

        @Override
        protected void actualExecute(SendPoll sendPoll) {
            this.sendPoll = sendPoll;
        }

        public SendPoll getPoll() {
            return this.sendPoll;
        }
    }
}
