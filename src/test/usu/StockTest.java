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
}
