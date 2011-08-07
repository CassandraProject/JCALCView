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
//	File:	Config_Table_Model.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.Observer;
import java.util.Observable;


class Config_Table_Model extends AbstractTableModel implements Observer
{
    private	ArrayList	_registers;
    private	Machine		_machine;
    
    static public final int PARAM_COLUMN = 0;
    static public final int CONFIG_COLUMN = 1;
    
    
    class	Param_Item {
        public String 		_param;
        public Mem_Interface 	_mem;
    
        Param_Item (String param, Mem_Interface mem) {
            _param = param;
            _mem = mem;
        }
    }
    
    

    Config_Table_Model (Machine machine) {
        _machine = machine;

        _machine.addObserver(this);
              
        _registers = new ArrayList();

        Refresh_Config ();
    }

    /**
        Observer message
    */

    void Refresh_Config () {
        _registers.clear();
    
        // Easiest just to go through all the parameters and see if they exist
        for (short mem_loc = Constants.NAME; mem_loc < Constants.NUM_REGISTERS; ++mem_loc)
        {
            try {
                String param = _machine.Index_To_Name_Memory (mem_loc);
                Mem_Interface mem = _machine.Index_To_Memory(mem_loc);
                                
                if (mem.Can_Config()) 
                    _registers.add(new Param_Item(param, mem));
                
            }
            catch (Exception_Illegal_Symbol e) {
                // Expect this for parameters the block doesn't support
            }
            
        }
    }
    
    /**
        Observer message
    */
    
    public void update (Observable o, Object arg) {
        Refresh_Config ();
        fireTableDataChanged();
    }

                
    /*----Table Model------------------------------------------------------------------*/

    public int getRowCount() {
        return _registers.size();
    }
    
    public int getColumnCount() {
        return 2;
    }
        
    public Object getValueAt(int row, int column) {        
        switch (column) {
            case PARAM_COLUMN:		return ((Param_Item) _registers.get(row))._param;				
            case CONFIG_COLUMN:		return ((Param_Item) _registers.get(row))._mem.Get_Configuration();				
            default:			return null;
        }
    }
    
    public void setValueAt(Object value, int row, int column) {
        switch (column) {
            case PARAM_COLUMN:		break;	
            case CONFIG_COLUMN:		if (value instanceof String) {
                                            ((Param_Item) _registers.get(row))._mem.Set_Configuration((String) value);	
                                            ((Param_Item) _registers.get(row))._mem.Compile(_machine);
                                        }
                                        break;
        }
    }

    public String getColumnName(int column) {
        switch (column) {
            case PARAM_COLUMN:		return Constants.Get_String("ConfigTableParam");			
            case CONFIG_COLUMN:		return Constants.Get_String("ConfigTableConfig");
            default:			return null;
        }
    }
    
    public Class getColumnClass(int column) {
        switch (column) {
            case PARAM_COLUMN:		return String.class;
            case CONFIG_COLUMN:		return String.class;
            default:			return null;
        }
    }

    public boolean isCellEditable(int row, int column) {
        switch (column) {
            case PARAM_COLUMN:		return false;
            case CONFIG_COLUMN:		return true;
            default:			return false;
        }
    }
}

