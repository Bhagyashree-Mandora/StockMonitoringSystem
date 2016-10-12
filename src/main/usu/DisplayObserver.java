package main.usu;

import java.util.Observable;
import java.util.Observer;

public class DisplayObserver implements Observer {

    Observable observable;

    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof Stock) {
            Stock stock = (Stock) obs;
//            System.out.println(stock.getTickerMessage().getSymbol());
            System.out.println(stock.getTickerMessage());
        }
    }
}
