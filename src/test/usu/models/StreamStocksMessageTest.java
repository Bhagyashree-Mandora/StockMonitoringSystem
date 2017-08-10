package test.usu.models;

import main.usu.models.StreamStocksMessage;
import org.junit.Test;

import static org.junit.Assert.*;

public class StreamStocksMessageTest {


    /*********************************************************
     *                     Count & Add
     *********************************************************/

    @Test
    public void shouldHaveNoSymbolsInitially() {
        StreamStocksMessage message = new StreamStocksMessage();
        assertNull(message.getSymbols());
    }

    @Test
    public void shouldHaveCountZeroInitially() {
        StreamStocksMessage message = new StreamStocksMessage();
        assertEquals(0, message.Count());
    }

    @Test
    public void shouldHaveInsertedSymbolInMessage() {
        StreamStocksMessage message = new StreamStocksMessage();
        message.Add("AAN");
        assertEquals("AAN", message.getSymbols().get(0));
    }

    @Test
    public void shouldUpdateCountOnSymbolAddition() {
        StreamStocksMessage message = new StreamStocksMessage();
        message.Add("AAN");
        assertEquals(1, message.Count());
    }

    @Test
    public void shouldHaveInsertedMultipleSymbolInMessage() {
        StreamStocksMessage message = new StreamStocksMessage();
        message.Add("AAN");
        message.Add("AIR");
        assertTrue(message.getSymbols().contains("AAN"));
        assertTrue(message.getSymbols().contains("AIR"));
    }

    @Test
    public void shouldUpdateCountOnMultipleSymbolAddition() {
        StreamStocksMessage message = new StreamStocksMessage();
        message.Add("AAN");
        message.Add("AIR");
        assertEquals(2, message.Count());
    }

    @Test
    public void shouldNotInsertNullSymbolInMessage() {
        StreamStocksMessage message = new StreamStocksMessage();
        message.Add(null);
        assertTrue(message.getSymbols().isEmpty());
    }

    @Test
    public void shouldNotInsertEmptySymbolInMessage() {
        StreamStocksMessage message = new StreamStocksMessage();
        message.Add("");
        assertTrue(message.getSymbols().isEmpty());
    }

    @Test
    public void shouldNotInsertBlankSymbolInMessage() {
        StreamStocksMessage message = new StreamStocksMessage();
        message.Add("     ");
        assertTrue(message.getSymbols().isEmpty());
    }

    @Test
    public void shouldNotInsertTabsSymbolInMessage() {
        StreamStocksMessage message = new StreamStocksMessage();
        message.Add("\t");
        assertTrue(message.getSymbols().isEmpty());
    }


    /*********************************************************
     *                     Encode
     *********************************************************/

    @Test
    public void messageShouldBeEncodedCorrectly() {
        byte[] bytesInSymbol = {0x00, 0x01, 0x41, 0x41, 0x4e, 0x20, 0x20, 0x20};
        StreamStocksMessage message = new StreamStocksMessage();
        message.Add("AAN");
        byte[] symbolArray = message.Encode();
        assertArrayEquals(bytesInSymbol, symbolArray);
    }

    @Test
    public void nullMessageShouldBeEncodedCorrectly() {
        byte[] bytesInSymbol = {0x00, 0x00};
        StreamStocksMessage message = new StreamStocksMessage();
        message.Add("");
        byte[] symbolArray = message.Encode();
        assertArrayEquals(bytesInSymbol, symbolArray);
    }

    @Test
    public void multipleMessagesShouldBeEncodedCorrectly() {
        byte[] bytesInSymbolOne = {0x00, 0x02, 0x41, 0x41, 0x4e, 0x20, 0x20, 0x20, 0x41, 0x49, 0x52, 0x20, 0x20, 0x20};
        StreamStocksMessage message = new StreamStocksMessage();
        message.Add("AAN");
        message.Add("AIR");
        byte[] symbolArray = message.Encode();
//        for (byte b : symbolArray)
//            System.out.println(Integer.toHexString(b));
        assertArrayEquals(bytesInSymbolOne, symbolArray);
    }


    /*********************************************************
     *                     Decode
     *********************************************************/

    @Test
    public void shouldReturnNullIfNullBytesArray() {
        assertNull(StreamStocksMessage.Decode(null));
    }

    @Test
    public void shouldReturnNullIfByteArrayEmpty() {
        byte [] input = {};
        assertNull(StreamStocksMessage.Decode(input));
    }

    @Test
    public void shouldReturnNullIfByteArraySizeLessThanTwo() {
        byte [] input = {0x00};
        assertNull(StreamStocksMessage.Decode(input));
    }

    @Test
    public void shouldReturnNullIfCountOneAndSymbolOfInvalidLength() {
        byte [] input = {0x00, 0x01, 0x41, 0x41};
        assertNull(StreamStocksMessage.Decode(input));
    }

    @Test
    public void shouldReturnNullIfCountNonZeroAndAnySymbolOfInvalidLength() {
        byte [] input = {0x00, 0x02, 0x41, 0x41, 0x4e, 0x20, 0x20, 0x20, 0x41, 0x49, 0x52};
        assertNull(StreamStocksMessage.Decode(input));
    }

    @Test
    public void shouldReturnNullIfFirstSymbolIsEmpty() {
        byte [] input = {0x00, 0x01, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20};
        assertNull(StreamStocksMessage.Decode(input));
    }

    @Test
    public void shouldReturnNullIfAnySymbolIsEmpty() {
        byte [] input = {0x00, 0x02, 0x41, 0x41, 0x4e, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20};
        assertNull(StreamStocksMessage.Decode(input));
    }

    @Test
    public void shouldReturnNullIfCountMoreThanActualSymbols() {
        byte [] input = {0x00, 0x02, 0x41, 0x41, 0x4e, 0x20, 0x20, 0x20};
        assertNull(StreamStocksMessage.Decode(input));
    }

    @Test
    public void shouldReturnValidMesageWithZeroSymbolsWhenEncodedCountIsZero() {
        byte [] input = {0x00, 0x00};
        StreamStocksMessage message = StreamStocksMessage.Decode(input);
        assertNotNull(message);
        assertEquals(0, message.Count());
    }

    @Test
    public void shouldReturnValidMessageWithOneSymbol() {
        byte [] input = {0x00, 0x01, 0x41, 0x41, 0x4e, 0x20, 0x20, 0x20};
        StreamStocksMessage message = StreamStocksMessage.Decode(input);
        assertNotNull(message);
        assertEquals(1, message.Count());
        assertEquals("AAN", message.getSymbols().get(0));
    }

    @Test
    public void shouldReturnValidMessageWithMultipleSymbols() {
        byte [] input = {0x00, 0x02, 0x41, 0x41, 0x4e, 0x20, 0x20, 0x20, 0x41, 0x49, 0x52, 0x20, 0x20, 0x20};
        StreamStocksMessage message = StreamStocksMessage.Decode(input);
        assertNotNull(message);
        assertEquals(2, message.Count());
        assertEquals("AAN", message.getSymbols().get(0));
        assertEquals("AIR", message.getSymbols().get(1));
    }

    @Test
    public void shouldReturnMessageEvenIfCountLessThanActualSymbols() {
        byte [] input = {0x00, 0x01, 0x41, 0x41, 0x4e, 0x20, 0x20, 0x20, 0x41, 0x49, 0x52, 0x20, 0x20, 0x20};
        StreamStocksMessage message = StreamStocksMessage.Decode(input);
        assertNotNull(message);
        assertEquals(1, message.Count());
        assertEquals("AAN", message.getSymbols().get(0));
    }
}