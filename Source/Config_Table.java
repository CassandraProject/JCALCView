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
//	File:	Config_Table.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

class Config_Table extends JTable_Clip
{

    Config_Table () {        
        
        ((DefaultCellEditor) getDefaultEditor(String.class)).setClickCountToStart(2);
                
        setGridColor(Color.gray);
        //setRowSelectionAllowed(true);
        selection_range(Config_Table_Model.CONFIG_COLUMN, Config_Table_Model.CONFIG_COLUMN);
        
        //setColumnSelectionAllowed(false);
        //setCellSelectionEnabled(true);
        //setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }
    
    public void SetColumnWidths() {
        TableColumnModel steps_column_model = getColumnModel();
        
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        steps_column_model.getColumn(Config_Table_Model.PARAM_COLUMN).setPreferredWidth(75);
        steps_column_model.getColumn(Config_Table_Model.CONFIG_COLUMN).setPreferredWidth(200);
    }
    
}


