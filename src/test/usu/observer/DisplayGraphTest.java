package test.usu.observer;

import main.usu.models.TickerMessage;
import main.usu.observer.DisplayGraph;
import org.junit.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static org.mockito.Mockito.*;

public class DisplayGraphTest {

    @Test
    public void shouldNotUpdateRowIfNonePresent() {
        DisplayGraph observer = new DisplayGraph();
        JTable jTableMock = mock(JTable.class);
        observer.setTable1(jTableMock);

        DefaultTableModel model = mock(DefaultTableModel.class);
        doReturn(model).when(jTableMock).getModel();
        doReturn(0).when(model).getRowCount();

        observer.updatePortfolioTable(new TickerMessage());

        verify(model, never()).addRow((Object[]) any());
    }

    @Test
    public void shouldNotAddRowIfAlreadyPresent() {
        DisplayGraph observer = new DisplayGraph();
        observer.selectedSymbols.add("AAN");
        JTable jTableMock = mock(JTable.class);
        observer.setTable1(jTableMock);

        DefaultTableModel model = mock(DefaultTableModel.class);
        doReturn(model).when(jTableMock).getModel();
        doReturn(1).when(model).getRowCount();
        doReturn("AAN").when(model).getValueAt(0, 0);

        TickerMessage message = new TickerMessage();
        message.setSymbol("AAN");

        observer.updatePortfolioTable(message);

        verify(model, never()).addRow((Object[]) any());
    }

    @Test
    public void shouldNotAddRowIfSymbolNotInSelectedList() {
        DisplayGraph observer = new DisplayGraph();
        observer.selectedSymbols.add("AIR");
        JTable jTableMock = mock(JTable.class);
        observer.setTable1(jTableMock);

        DefaultTableModel model = mock(DefaultTableModel.class);
        doReturn(model).when(jTableMock).getModel();
        doReturn(0).when(model).getRowCount();

        TickerMessage message = new TickerMessage();
        message.setSymbol("AAN");

        observer.updatePortfolioTable(message);

        verify(model, never()).addRow((Object[]) any());
    }

    @Test
    public void shouldAddRowIfSymbolInSelectedListAndNotInTable() {
        DisplayGraph observer = new DisplayGraph();
        observer.selectedSymbols.add("AAN");
        JTable jTableMock = mock(JTable.class);
        observer.setTable1(jTableMock);

        DefaultTableModel model = mock(DefaultTableModel.class);
        doReturn(model).when(jTableMock).getModel();
        doReturn(0).when(model).getRowCount();

        TickerMessage message = new TickerMessage();
        message.setSymbol("AAN");

        observer.updatePortfolioTable(message);

        verify(model).addRow((Object[]) any());
    }
}