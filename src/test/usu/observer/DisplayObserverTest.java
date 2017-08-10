package test.usu.observer;

import main.usu.Stock;
import main.usu.models.TickerMessage;
import main.usu.observer.DisplayObserver;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class DisplayObserverTest {

    @Test
    public void testObserverIsCalledOnStockUpdate(){
        DisplayObserver observer = spy(new DisplayObserver());
        Stock stock = new Stock();
        stock.addObserver(observer);

        stock.update(new TickerMessage());

        verify(observer).update(any(), any());
    }
}