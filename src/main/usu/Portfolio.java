package main.usu;

import lombok.Getter;
import main.usu.models.TickerMessage;
import main.usu.observer.DisplayGraph;
import main.usu.observer.DisplayObserver;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Portfolio {

    private static final String FILE_NAME = "portfolio.txt";
    @Getter
    private HashMap<String,Stock> stocks = new HashMap<>();
    private DisplayObserver displayObserver = new DisplayGraph();

    public Portfolio(){
        List<String> symbols = getSymbolsfromFile();
        for (String symbol : symbols){
            addSymbol(symbol);
        }
    }

    public void saveToFile() {
        PrintWriter writer;
        try {
            writer = new PrintWriter(FILE_NAME, "UTF-8");
            for (String symbol : stocks.keySet()) {
                writer.println(symbol);
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSymbolsfromFile() {
        String line;
        List<String> loadSymbols = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                loadSymbols.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadSymbols;
    }

    public void addSymbol(String ticker) {
        Stock stock = new Stock();
        stock.addObserver(displayObserver);
        stocks.put(ticker, stock);
    }

    public void update(TickerMessage tickerMessage){
        if(stocks.containsKey(tickerMessage.getSymbol())){
            stocks.get(tickerMessage.getSymbol()).update(tickerMessage);
        }
    }
}