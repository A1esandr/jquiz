import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

}
