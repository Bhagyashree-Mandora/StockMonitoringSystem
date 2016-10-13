package main.usu.observer;

import com.google.common.collect.Lists;
import main.usu.Stock;
import main.usu.models.TickerMessage;
import main.usu.observer.DisplayObserver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Observable;

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
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(tickerMessage.getSymbol())) {
                model.setValueAt(tickerMessage.getCurrentPrice(), i, 1);
                model.setValueAt(tickerMessage.getBidPrice(), i, 2);
                model.setValueAt(tickerMessage.getAskPrice(), i, 3);
                isRowPresent = true;
            }
        }
        if (selectedSymbols.contains(tickerMessage.getSymbol()) && !isRowPresent) {
            List<String> rowData = Lists.newArrayList(tickerMessage.getSymbol(), String.valueOf(tickerMessage.getCurrentPrice()), String.valueOf(tickerMessage.getBidPrice()), String.valueOf(tickerMessage.getAskPrice()));
            model.addRow(rowData.toArray());

        }
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
            }
        });
    }

    private void updatePortfolioPanel(String selectedSymbol) {
        if (selectedSymbols.contains(selectedSymbol)) {
            return;
        }
        selectedSymbols.add(selectedSymbol);
    }

    public void populateBox() {
        String line;
        List<String> loadCompanies = Lists.newArrayList();
        try {
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                loadCompanies.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        companiesList.setModel(new DefaultComboBoxModel(loadCompanies.toArray()));
    }
}