/*
* CALCView is a Java based Calc Block Editor
* Copyright (C) 2003
*
* Created by Tod Baudais
*
* This file is part of CALCView.
*
* CALCView is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation, either version 3 of
* the License, or (at your option) any later version.
*
* CALCView is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with CALCView.  If not, see <http://www.gnu.org/licenses/>.
*/
//
//	File:	JTable_Clip.java
//

import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.util.StringTokenizer;

public class JTable_Clip extends JTable implements Clipboard_Interface, ActionListener
{
    private static  Clipboard _clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private int 	_first_col = 0;
    private int 	_last_col = 0;

    public JTable_Clip() {
        registerKeyboardAction(this,"Cut",KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),JComponent.WHEN_FOCUSED);
        registerKeyboardAction(this,"Copy",KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),JComponent.WHEN_FOCUSED);
        registerKeyboardAction(this,"Paste",KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),JComponent.WHEN_FOCUSED);
        registerKeyboardAction(this,"Clear",KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),JComponent.WHEN_FOCUSED);
        registerKeyboardAction(this,"SelectAll",KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),JComponent.WHEN_FOCUSED);
    }
    
    public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("Cut"))
            cut();
        else if (e.getActionCommand().equals("Copy"))
            copy();
        else if (e.getActionCommand().equals("Paste"))
            paste();
        else if (e.getActionCommand().equals("Clear"))
            clear();
        else if (e.getActionCommand().equals("SelectAll"))
            selectAll();
    }
    
    
    public void selection_range(int first, int last) {
        _first_col = first;
        _last_col = last;
    }

    public void cut() {
        copy();
        clear();
    }

    public void copy() {
    
        try
        {
            if (getSelectedRowCount() > 0) {
    
                StringBuffer    sb = new StringBuffer();
        
                int    	numRows      	= getSelectedRowCount();
                int[] 	rowIndices  	= getSelectedRows();
            
                removeEditor();
        
                // Verify we have a contiguous block of cells selected.
                if ((numRows - 1 == (rowIndices[rowIndices.length - 1] - rowIndices[0])) && (numRows == rowIndices.length)) {
                    for (int rowCtr = 0; rowCtr < numRows; ++ rowCtr) {
                        for (int colCtr = _first_col; colCtr <= _last_col; ++ colCtr) {
                            sb.append(getValueAt(rowIndices[rowCtr], colCtr));
                            if (colCtr < _last_col)
                                sb.append('\t');
                        }
                            
                        sb.append('\n');
                    }
        
                    StringSelection stringSelection = new StringSelection(sb.toString());
                    _clipboard.setContents(stringSelection, stringSelection);
                }
                
            }
            
        }
        catch (Exception ex) { 
            ex.printStackTrace(); 
        }
        
    }
    

    public void paste() {        
        
        try
        {

            if (getSelectedRowCount() > 0) {
    
                int    startRow = (getSelectedRows())[0];
                int    startCol = _first_col;
        
                removeEditor();
    
                String		transferString = (String) (_clipboard.getContents(this).getTransferData(DataFlavor.stringFlavor));
                StringTokenizer	rowTokenizer = new StringTokenizer(transferString, "\n");

                for (int rowCtr = 0; rowTokenizer.hasMoreTokens(); ++ rowCtr)
                {
                    String		rowString = rowTokenizer.nextToken();
                    StringTokenizer	colTokenizer = new StringTokenizer(rowString, "\t");

                    for (int colCtr = 0; colTokenizer.hasMoreTokens(); ++ colCtr) {
                        String	value = (String) colTokenizer.nextToken();

                        if ((startRow + rowCtr < getRowCount()) && (startCol + colCtr < getColumnCount()))
                            setValueAt(value, startRow + rowCtr, startCol + colCtr);
                    }
                }
                
                repaint();
            }
        
        }
        catch (Exception ex) { 
            ex.printStackTrace(); 
        }
    }
    
    public void clear() {
        int    	numRows     = getSelectedRowCount();
        int[] 	rowIndices  = getSelectedRows();

        // Verify we have a contiguous block of cells selected.
        for (int rowCtr = 0; rowCtr < numRows; ++ rowCtr) 
            for (int colCtr = _first_col; colCtr <= _last_col; ++ colCtr) 
                setValueAt("",rowIndices[rowCtr], colCtr);
        
        repaint();
    }
        
    public void unfocus() {
        clearSelection();
    }

    public void focus() {

    }

}
