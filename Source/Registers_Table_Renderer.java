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
//	File:	Registers_Table_Renderer.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

/*class Registers_Table_Renderer
    extends DefaultTableCellRenderer
{

    public Component getTableCellRendererComponent
        (JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
        setEnabled(table == null || table.isEnabled());

        if (selected) {
            if (column == Registers_Table_Model.OUT_OF_SERVICE_COLUMN)	setBackground(Color.cyan);		
            else if (column == Registers_Table_Model.BAD_COLUMN)	setBackground(Color.red);	
            else if (column == Registers_Table_Model.ERROR_COLUMN)	setBackground(Color.yellow);
            else 							setBackground(null);
        } else
            setBackground(null);
        
        return super.getTableCellRendererComponent(table, value, selected, focused, row, column);
    }
}*/

class Registers_Table_Renderer extends JCheckBox implements TableCellRenderer {

    private javax.swing.border.Border focusBorder, noFocusBorder;
    
    {
	setContentAreaFilled(true);
	setHorizontalAlignment(CENTER);
        setBorderPainted(true);
    }

    public void updateUI() {
	super.updateUI();

	focusBorder = UIManager.getBorder("Table.focusCellHighlightBorder");
	Insets i = focusBorder.getBorderInsets(this);

	noFocusBorder = BorderFactory.createEmptyBorder(i.top, i.left, i.bottom, i.right);
    }

    // Could override additional methods to do nothing.
    // See DefaultTableCellRenderer
    public void revalidate() {
    
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean hasFocus, 
                                                    int row, int column) 
    {
        setEnabled(table.isEnabled());
        setFont(table.getFont());
        
	if (selected)	{
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
	} else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
	}
        
        // Set background
        if (Boolean.TRUE.equals(value)) {
            if (column == Registers_Table_Model.OUT_OF_SERVICE_COLUMN)	setBackground(Color.cyan);		
            else if (column == Registers_Table_Model.BAD_COLUMN)	setBackground(Color.red);	
            else if (column == Registers_Table_Model.ERROR_COLUMN)	setBackground(Color.yellow);
        } 

        // Set focus
        if (hasFocus)	setBorder(focusBorder);
        else		setBorder(noFocusBorder);
        
        setSelected(Boolean.TRUE.equals(value));
        
        return this;
    }
}


