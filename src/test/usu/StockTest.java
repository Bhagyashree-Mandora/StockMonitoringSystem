package test.usu;

import main.usu.Stock;
import main.usu.models.TickerMessage;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.spy;

public class StockTest {

    @Test
    public void testObserversAreNotified(){
        Stock stock = spy(new Stock());
        stock.update(new TickerMessage());

        Mockito.verify(stock).notifyObservers();
    }

    @Test
    public void testTickerMessageIsSet(){
        Stock stock = new Stock();
        stock.update(new TickerMessage());

        Assert.assertNotNull(stock.getTickerMessage());
    }

    @Test
    public void shouldUdatePriceHistoryWithNewTickerMessage() {
        Stock stock = new Stock();
        TickerMessage message = new TickerMessage();
        message.setCurrentPrice(100);

        TickerMessage messageTwo = new TickerMessage();
        messageTwo.setCurrentPrice(101);

        stock.update(message);
        stock.update(messageTwo);

        Assert.assertEquals(2, stock.getCurrentPriceHistory().size());
        Assert.assertEquals(new Integer(100), stock.getCurrentPriceHistory().get(0));
        Assert.assertEquals(new Integer(101), stock.getCurrentPriceHistory().get(1));
    }
}