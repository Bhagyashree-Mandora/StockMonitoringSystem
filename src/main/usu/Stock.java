package main.usu;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

@Getter
public class Stock extends Observable {

    TickerMessage tickerMessage;
    List<Integer> currentPriceHistory = Lists.newArrayList();


    public void update(TickerMessage tickerMessage) {
        this.tickerMessage = tickerMessage;
        currentPriceHistory.add(tickerMessage.getCurrentPrice());
        setChanged();
        notifyObservers();
    }

}
