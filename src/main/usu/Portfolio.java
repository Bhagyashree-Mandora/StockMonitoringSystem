package main.usu;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Portfolio {

    private static final String FILE_NAME = "portfolio.txt";
    private List<String> companies = new ArrayList<>();
    private List<String> symbols = new ArrayList<>();
    private HashMap<String,Stock> stocks = new HashMap<>();
    DisplayObserver displayObserver = new DisplayObserver();

    public Portfolio(){
        this.symbols = getSymbols();
        for (String symbol : symbols){
            addCompany(symbol);
        }
    }

    public void saveToFile() {
        PrintWriter writer;
        try {
            writer = new PrintWriter(FILE_NAME, "UTF-8");
            for (String company : companies) {
                writer.println(company);
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSymbols() {
        String line;
        List<String> loadCompanies = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                loadCompanies.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadCompanies;
    }

    public void addCompany(String ticker) {
        Stock stock = new Stock();
        stock.addObserver(displayObserver);
        stocks.put(ticker, stock);
    }

    public void update(TickerMessage tickerMessage){
        if(stocks.containsKey(tickerMessage.getSymbol())){
            stocks.get(tickerMessage.getSymbol()).update(tickerMessage);
        }
    }

    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio();
        portfolio.addCompany("AAN");
        TickerMessage tickerMessage = new TickerMessage();
        tickerMessage.setSymbol("AAN");
        tickerMessage.setCurrentPrice(12);
        portfolio.update(tickerMessage);
//        portfolio.update("AMZN");
//        portfolio.update("AMZN1");
    }

}