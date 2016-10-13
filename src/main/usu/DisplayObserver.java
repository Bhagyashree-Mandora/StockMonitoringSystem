package main.usu;

import java.util.Observable;
import java.util.Observer;

public class DisplayObserver implements Observer {

//    Observable observable;

    public void watch(Stock stock){
        stock.addObserver(this);
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof Stock) {
            Stock stock = (Stock) obs;
//            System.out.println(stock.getTickerMessage().getSymbol());
            System.out.println(stock.getTickerMessage());
//            System.out.println(stock.getTickerMessage().getSymbol() + " " + stock.getCurrentPriceHistory());
        }
    }
}