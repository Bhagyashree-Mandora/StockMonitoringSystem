package test.usu;

import main.usu.Portfolio;
import main.usu.TickerMessage;
import org.junit.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PortfolioTest {
    private static final String FILE_NAME = "portfolio.txt";

    @Before
    public void setUp(){
        File file = new File(FILE_NAME);
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void savesToFile() {
        List<String> testSymbols = new ArrayList<>();
        testSymbols.add("test");
        testSymbols.add("test1");

        Portfolio portfolio = new Portfolio();
        for(String symbol : testSymbols) {
            portfolio.addSymbol(symbol);
        }

        portfolio.saveToFile();

        List<String> actual = new ArrayList<>();
        BufferedReader br;
        try {
            String line;
            br = new BufferedReader(new FileReader(FILE_NAME));
            while ((line = br.readLine()) != null) {
                actual.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(actual.equals(testSymbols));
    }

    @Test
    public void loadsFromFile() {
        List<String> testCompanies = new ArrayList<>();
        testCompanies.add("test");
        testCompanies.add("test1");
        Portfolio portfolio = new Portfolio();
        PrintWriter writer;
        try {
            writer = new PrintWriter(FILE_NAME, "UTF-8");
            for (String company : testCompanies) {
                writer.println(company);
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<String> actual;
        actual = portfolio.getSymbolsfromFile();

        Assert.assertTrue(actual.equals(testCompanies));
    }

    @AfterClass
    public static void tearDown(){
        List<String> demoSymbols = new ArrayList<>();
        demoSymbols.add("AIR");
        demoSymbols.add("AAN");
        demoSymbols.add("MMM");
        PrintWriter writer;
        try {
            writer = new PrintWriter(FILE_NAME, "UTF-8");
            for (String company : demoSymbols) {
                writer.println(company);
            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}