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
//	File:	Registers_Table.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import javax.swing.border.Border;

class Registers_Table extends JTable_Clip
{
    private static int CHECK_WIDTH = 40;

    Registers_Table () {        

        setGridColor(Color.gray);
        //setCellSelectionEnabled(false);

        setDefaultRenderer(Boolean.class, new Registers_Table_Renderer());
        setDefaultRenderer(Registers_Name.class, new Registers_Table_Renderer_Name());
        
        ((DefaultCellEditor) getDefaultEditor(String.class)).setClickCountToStart(2);
        //setRowSelectionAllowed(true);
        selection_range(Registers_Table_Model.NUMBER_COLUMN, Registers_Table_Model.CONFIG_COLUMN);
        
        //setColumnSelectionAllowed(false);
        //setCellSelectionEnabled(true);
        //setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }
    
    public void SetColumnWidths() {
        TableColumnModel steps_column_model = getColumnModel();
        
        setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        
        steps_column_model.getColumn(Registers_Table_Model.CONFIG_COLUMN).setPreferredWidth(200);
        steps_column_model.getColumn(Registers_Table_Model.NUMBER_COLUMN).setPreferredWidth(70);
        steps_column_model.getColumn(Registers_Table_Model.REGISTER_COLUMN).setPreferredWidth(70);
        
        steps_column_model.getColumn(Registers_Table_Model.SECURE_COLUMN).setPreferredWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.ERROR_COLUMN).setPreferredWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.BAD_COLUMN).setPreferredWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.OUT_OF_SERVICE_COLUMN).setPreferredWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.GET_COLUMN).setPreferredWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.PUT_COLUMN).setPreferredWidth(CHECK_WIDTH);

        steps_column_model.getColumn(Registers_Table_Model.SECURE_COLUMN).setMinWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.ERROR_COLUMN).setMinWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.BAD_COLUMN).setMinWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.OUT_OF_SERVICE_COLUMN).setMinWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.GET_COLUMN).setMinWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.PUT_COLUMN).setMinWidth(CHECK_WIDTH);

        steps_column_model.getColumn(Registers_Table_Model.SECURE_COLUMN).setMaxWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.ERROR_COLUMN).setMaxWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.BAD_COLUMN).setMaxWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.OUT_OF_SERVICE_COLUMN).setMaxWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.GET_COLUMN).setMaxWidth(CHECK_WIDTH);
        steps_column_model.getColumn(Registers_Table_Model.PUT_COLUMN).setMaxWidth(CHECK_WIDTH);

    }
    
}


