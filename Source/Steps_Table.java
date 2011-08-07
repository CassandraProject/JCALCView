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
//	File:	Steps_Table.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

/**
    A <code>Steps_Table</code> the table that shows the steps.  Sets up the default table.
*/

class Steps_Table extends JTable_Clip
{
    private static int CHECK_WIDTH = 40;
    private static int NUMBER_WIDTH = 60;

    Steps_Table () {        

        setDefaultRenderer(Steps_Line_Number.class, new Steps_Line_Number_Renderer());
        
        ((DefaultCellEditor) getDefaultEditor(String.class)).setClickCountToStart(2);
                
        setGridColor(Color.gray);
        //setRowSelectionAllowed(true);
        selection_range(Steps_Table_Model.INSTRUCTION_COLUMN, Steps_Table_Model.INSTRUCTION_COLUMN);
        //setColumnSelectionAllowed(false);
        //setCellSelectionEnabled(true);
        //setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }

    public void SetColumnWidths() {
        TableColumnModel steps_column_model = getColumnModel();
        
        //setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        steps_column_model.getColumn(Steps_Table_Model.BREAK_COLUMN).setMinWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Steps_Table_Model.BREAK_COLUMN).setMaxWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Steps_Table_Model.BREAK_COLUMN).setPreferredWidth(CHECK_WIDTH);

        steps_column_model.getColumn(Steps_Table_Model.NUMBER_COLUMN).setMinWidth(NUMBER_WIDTH);
        steps_column_model.getColumn(Steps_Table_Model.NUMBER_COLUMN).setMaxWidth(NUMBER_WIDTH);
        steps_column_model.getColumn(Steps_Table_Model.NUMBER_COLUMN).setPreferredWidth(NUMBER_WIDTH);
        
        steps_column_model.getColumn(Steps_Table_Model.INSTRUCTION_COLUMN).setPreferredWidth(200);
    }
    
}


