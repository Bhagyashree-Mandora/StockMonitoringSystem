package main.usu;

import lombok.Getter;

import java.util.Observable;

@Getter
public class Stock extends Observable {

    TickerMessage tickerMessage;

    public void update(TickerMessage tickerMessage) {
        this.tickerMessage = tickerMessage;
        setChanged();
        notifyObservers();
    }

}
