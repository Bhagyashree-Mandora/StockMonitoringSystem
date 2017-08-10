package test.usu.models;

import main.usu.models.TickerMessage;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TickerMessageTest {
    TickerMessage tickerMessageOne = new TickerMessage();

    byte[] bytesInMessageOne = {0x41, 0x41, 0x4e, 0x20, 0x20, 0x20, 0x0, 0x0, 0x1, 0x5d, 0x60, 0x38, 0x6e, 0xffffff88,
            0x0, 0x0, 0x0, 0xffffffb6, 0x0, 0x0, 0x0, 0xffffffb0, 0x0, 0x0, 0x0, 0xffffffcb, 0x0, 0x0, 0x0,
            0xffffffc3, 0x0, 0x0, 0x0, 0xffffffd2, 0x0, 0x0, 0x7, 0xffffffd0, 0x0, 0x0, 0x7, 0x8};

    @Before
    public void setUp() {
        tickerMessageOne.setSymbol("AAN");
        tickerMessageOne.setMessageTimestamp(1500557897352L);
        tickerMessageOne.setOpeningPrice(182);
        tickerMessageOne.setPreviousClosingPrice(176);
        tickerMessageOne.setCurrentPrice(203);
        tickerMessageOne.setBidPrice(195);
        tickerMessageOne.setAskPrice(210);
        tickerMessageOne.setCurrentVolume(2000);
        tickerMessageOne.setAverageVolume(1800);
    }

    /*********************************************************
     *                     Encode
     *********************************************************/

    @Test
    public void shouldEncodeMessageCorrectly() throws IOException {
        byte[] messageArray = tickerMessageOne.Encode();
        assertArrayEquals(bytesInMessageOne, messageArray);
    }

    @Test
    public void shouldConstructBlankByteArrayForEmptyMessage() throws IOException {
        byte[] emptyBytesArray = {0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        TickerMessage tickerMessage = new TickerMessage();
        byte[] messageArray = tickerMessage.Encode();
        assertArrayEquals(emptyBytesArray, messageArray);
    }

    @Test
    public void shouldHaveBlankSymbolIfNull() throws IOException {
        byte[] emptyBytesSymbol = {0x20, 0x20, 0x20, 0x20, 0x20, 0x20};
        TickerMessage tickerMessage = new TickerMessage();
        tickerMessage.setSymbol(null);
        byte[] messageArray = tickerMessage.Encode();
        byte[] symbolInBytes = Arrays.copyOfRange(messageArray,0,6);
        assertArrayEquals(emptyBytesSymbol, symbolInBytes);
    }

    @Test
    public void shouldHaveBlankSymbolIfEmptyString() throws IOException {
        byte[] emptyBytesSymbol = {0x20, 0x20, 0x20, 0x20, 0x20, 0x20};
        TickerMessage tickerMessage = new TickerMessage();
        tickerMessage.setSymbol("");
        byte[] messageArray = tickerMessage.Encode();
        byte[] symbolInBytes = Arrays.copyOfRange(messageArray,0,6);
        assertArrayEquals(emptyBytesSymbol, symbolInBytes);
    }

    @Test
    public void shouldHaveBlankSymbolIfConsistsOfWhiteSpaces() throws IOException {
        byte[] emptyBytesSymbol = {0x20, 0x20, 0x20, 0x20, 0x20, 0x20};
        TickerMessage tickerMessage = new TickerMessage();
        tickerMessage.setSymbol("      \t");
        byte[] messageArray = tickerMessage.Encode();
        byte[] symbolInBytes = Arrays.copyOfRange(messageArray,0,6);
        assertArrayEquals(emptyBytesSymbol, symbolInBytes);
    }


    /*********************************************************
     *                     Decode
     *********************************************************/

    @Test
    public void shouldDecodeBytesAndReturnValidMessage() throws IOException {
        TickerMessage message = TickerMessage.Decode(bytesInMessageOne);
        assertNotNull(message);
        assertEquals("AAN", message.getSymbol());
        assertEquals(1500557897352L, message.getMessageTimestamp());
        assertEquals(182, message.getOpeningPrice());
        assertEquals(176, message.getPreviousClosingPrice());
        assertEquals(203, message.getCurrentPrice());
        assertEquals(195, message.getBidPrice());
        assertEquals(210, message.getAskPrice());
        assertEquals(2000, message.getCurrentVolume());
        assertEquals(1800, message.getAverageVolume());
    }

}