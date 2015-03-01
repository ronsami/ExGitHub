/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thoth_lib_m.guiclass;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.table.*;
import thoth_lib_m.dataclass.CopyTable;
/**
 *Сортировка для строк основной таблицы,
 * содержащей сведения об экземплярах
 * @author Sirota Dmitry
 */
public class SortFilterModel extends AbstractTableModel{
    
    private TableCopiesModel model = null;
    private int sortColumn;
    private Row[] rows;
    private boolean flagSort;
    
    public SortFilterModel(TableCopiesModel m){
        this.model = m;
        this.flagSort = false;
        setRows();
    }
    
    private void setRows(){
        int i; //for loop
        if(this.model != null){
            rows = new Row[model.getRowCount()];
            for(i = 0; i < rows.length; i++){
                rows[i] = new Row();
                rows[i].index = i;
            }
        }
    }
    
    public void setRowsM(){
        setRows();
    }
    
    /**
     *Сортировка строк "от Хорстманна Кея и Корнелла Гари" 
     * @param numColumn - Столбец, значения которого подлежат сортировке
     */
    public void sort(int numColumn){
        this.sortColumn = numColumn;
        Arrays.sort(rows);
        fireTableDataChanged();
    }
    
    /**
     *Сортировка строк в обратном порядке
     * @param numColumn - Столбец, значения которого подлежат сортировке
     */
    public void reverseSort(int numColumn){
        this.sortColumn = numColumn;
        Arrays.sort(rows, Collections.reverseOrder());
        fireTableDataChanged();
    }
    
    public boolean getFlagSort(){
        return this.flagSort;
    }
    
    public void setFlagSort(boolean flag){
        this.flagSort = flag;
    }
    
    @Override
    public Object getValueAt(int row, int column){
        return model.getValueAt(this.rows[row].index, column);
    }
    
    @Override
    public boolean isCellEditable(int row, int column){
        return model.isCellEditable(this.rows[row].index, column);
    }
    
    @Override
    public void setValueAt(Object aValue, int row, int column){
        model.setValueAt(aValue, this.rows[row].index, column);
    }
    
    @Override
    public int getRowCount() { return model.getRowCount(); }
    
    @Override
    public int getColumnCount() { return model.getColumnCount();  }
    
    @Override
    public String getColumnName(int column){
        return model.getColumnName(column);
    }
    
    @Override
    public Class getColumnClass(int column){
        return model.getColumnClass(column);
    }
    
    public int getIdBookRecord(int row){
        if(model instanceof TableCopiesModel){
            return ((TableCopiesModel)model).getIdRec(this.rows[row].index);
        }
        else { return -1; }
    }
    
    public void addArrayCopies(List<CopyTable> copies) throws Exception{
        if(model instanceof TableCopiesModel){
            ((TableCopiesModel)model).addArrayCopies(copies);
        }
    }
    
    public void addRow(CopyTable copy) throws Exception{
        if(model instanceof TableCopiesModel){
            ((TableCopiesModel)model).addRow(copy);
        }
    }
    
    public void clearTable(){
        if(model instanceof TableCopiesModel){
            ((TableCopiesModel)model).clearTable();
        }
    }
    
    private class Row implements Comparable<Row>{
        public int index;
        
        @Override
        public int compareTo(Row other){
            Object a = model.getValueAt(index, sortColumn);
            Object b = model.getValueAt(other.index, sortColumn);
            if(a instanceof Comparable){
                return ((Comparable) a).compareTo(b);
            }
            else{
                return a.toString().compareToIgnoreCase(b.toString());
            }
        }
    }
}
