package main.usu;

import com.google.common.collect.Lists;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

/**
 * Created by optimus-prime on 10/12/16.
 */
public class DisplayGraph extends DisplayObserver {

    private static final String FILE_NAME = "portfolio.txt";
    private JComboBox companiesList;
    private JPanel mainPanel;
    private JPanel portfolioPanel;
    private JPanel individualStockPricePanel;
    private JPanel volumeGraph;
    private JButton button1;
    private JButton button2;
    private JRadioButton radioButton2;
    private JCheckBox checkBox1;
    private JTable table1;
    private JButton showGraphButton;
    List<String> selectedSymbols = Lists.newArrayList();


    @Override
    public void update(Observable obs, Object arg) {
        super.update(obs, arg);
        if (obs instanceof Stock) {
            Stock stock = (Stock) obs;
            updatePortfolioTable(stock.getTickerMessage());
        }
    }

    private void updatePortfolioTable(TickerMessage tickerMessage) {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        boolean isRowPresent = false;
        for(int i=0; i< model.getRowCount(); i++) {
            if(model.getValueAt(i, 0).equals(tickerMessage.getSymbol())){
                model.setValueAt(tickerMessage.getCurrentPrice(),i,1);
                model.setValueAt(tickerMessage.getBidPrice(),i,2);
                model.setValueAt(tickerMessage.getAskPrice(),i,3);
                isRowPresent = true;
            }
        }
        if(selectedSymbols.contains(tickerMessage.getSymbol()) && !isRowPresent){
            List<String> rowData = Lists.newArrayList(tickerMessage.getSymbol(), String.valueOf(tickerMessage.getCurrentPrice()), String.valueOf(tickerMessage.getBidPrice()), String.valueOf(tickerMessage.getAskPrice()));
            model.addRow(rowData.toArray());
        }
    }

    private void createAndShowGui() {

        List<Integer> scores = new ArrayList<Integer>();
        Random random = new Random();
        int maxDataPoints = 16;
        int maxScore = 20;
        for (int i = 0; i < maxDataPoints ; i++) {
            scores.add(random.nextInt(maxScore));
        }
        DrawGraph graphPanel = new DrawGraph(scores);

        JFrame frame = new JFrame("DrawGraph");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(graphPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public DisplayGraph() {
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(800, 600));
        frame.setContentPane(mainPanel);
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.addColumn("Stock");
        model.addColumn("Current");
        model.addColumn("Bid");
        model.addColumn("Ask");
        frame.pack();
        frame.setVisible(true);

        populateBox();
        companiesList.setSize(new Dimension(400, 50));
        companiesList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String selectedSymbol = (String) cb.getSelectedItem();
                updatePortfolioPanel(selectedSymbol);
                System.out.println("Selected: " + selectedSymbol);
//                createAndShowGui();
            }
        });
        showGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAndShowGui();
            }
        });
    }

    private void updatePortfolioPanel(String selectedSymbol) {
        if(selectedSymbols.contains(selectedSymbol)){
            return;
        }
        selectedSymbols.add(selectedSymbol);
    }

    public static void main(String[] args) {
        DisplayGraph displayGraph = new DisplayGraph();
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(800, 600));
        frame.setContentPane(displayGraph.mainPanel);

        frame.pack();
        frame.setVisible(true);
    }


    public void populateBox() {
        String line;
        List<String> loadCompanies = Lists.newArrayList();
        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
//                String symbol = line.split(",")[0];
//                loadCompanies.add(symbol);
                loadCompanies.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        companiesList.setModel(new DefaultComboBoxModel(loadCompanies.toArray()));
    }

}