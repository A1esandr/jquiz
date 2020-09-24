import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PollBotTest {
    @Test
    public void WhenPollBot_Construct_GetTokenFromArg() {
        PollBot pollBot = new PollBot("1");

        assertEquals("1", pollBot.getBotToken());
    }

    @Test
    public void WhenPollBot_Construct_SetName() {
        PollBot pollBot = new PollBot("1");

        assertEquals("PollBot", pollBot.getBotUsername());
    }
}
