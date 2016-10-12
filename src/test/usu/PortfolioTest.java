//package test.usu;
//
//import main.usu.Portfolio;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PortfolioTest {
//    private static final String FILE_NAME = "portfolio.txt";
//
//    @Test
//    public void savesToFile() {
//        List<String> testCompanies = new ArrayList<>();
//        testCompanies.add("test");
//        testCompanies.add("test1");
//
//        Portfolio portfolio = new Portfolio();
//        portfolio.setCompanies(testCompanies);
//
//        portfolio.saveToFile();
//
//        List<String> actual = new ArrayList<>();
//        BufferedReader br;
//        try {
//            String line;
//            br = new BufferedReader(new FileReader(FILE_NAME));
//            while ((line = br.readLine()) != null) {
//                actual.add(line);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Assert.assertTrue(actual.equals(testCompanies));
//    }
//
//    @Test
//    public void loadsFromFile() {
//        List<String> testCompanies = new ArrayList<>();
//        testCompanies.add("test");
//        testCompanies.add("test1");
//        Portfolio portfolio = new Portfolio();
//        PrintWriter writer;
//        try {
//            writer = new PrintWriter(FILE_NAME, "UTF-8");
//            for (String company : testCompanies) {
//                writer.println(company);
//            }
//            writer.close();
//        } catch (FileNotFoundException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        List<String> actual;
//        actual = portfolio.getSymbols();
//
//        Assert.assertTrue(actual.equals(testCompanies));
//        Assert.assertTrue(actual.equals(portfolio.getCompanies()));
//    }
//}