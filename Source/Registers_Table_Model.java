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
//	File:	Registers_Table_Model.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.util.*;
import java.lang.*;
import java.lang.Runtime;
import java.io.*;
import java.util.Observer;
import java.util.Observable;


class Registers_Table_Model extends AbstractTableModel implements Observer
{
    private	ArrayList	_register_use;
    private	Machine		_machine;
    
    static public final int GET_COLUMN = 0;
    static public final int PUT_COLUMN = 1;
    static public final int OUT_OF_SERVICE_COLUMN = 2;
    static public final int BAD_COLUMN = 3;
    static public final int ERROR_COLUMN = 4;
    static public final int SECURE_COLUMN = 5;
    static public final int REGISTER_COLUMN = 6;
    static public final int NUMBER_COLUMN = 7;
    static public final int CONFIG_COLUMN = 8;

    Registers_Table_Model (Machine machine) {        
        _machine = machine;
        _register_use = new ArrayList();

        _machine.addObserver(this);
    }
        
    public void Use_Register (int r) {
        if (!_register_use.contains(new Integer(r)))
            _register_use.add(new Integer(r));
    }
    
    public void Refresh_Registers() {
        _register_use.clear();
        
        for (short s = 1; s <= _machine.Get_Num_Steps(); ++s) {
            Mem_Step step = (Mem_Step) _machine.Index_To_Memory(Constants.STEP01 + s - 1);
                    
            // Mem_Float Update
            Instruction_Interface inst_impl = _machine.Index_To_Instruction(step.Get_Opcode());
            inst_impl.Update_Register_Use (step, this);
        }

        Collections.sort(_register_use);
        
        Clear_Modified ();
    }

    public void Clear_Modified () {
        for (short r = 0; r < _register_use.size(); ++r) {
            int index = ((Integer) _register_use.get(r)).intValue();
            _machine.Index_To_Memory(index).Set_Other_Status(Mem_Status.MODIFIED, false);     
        }
    }

    public void Clear_All_Status () {
        for (short r = 0; r < _register_use.size(); ++r) {
            int index = ((Integer) _register_use.get(r)).intValue();
            _machine.Index_To_Memory(index).Set_Other_Status(0);     
            _machine.Index_To_Memory(index).Set_Status(0);     
        }        
    }
    
    /**
        Observer message
    */
    
    public void update (Observable o, Object arg) {
        // Called when the memory changed
        
        Refresh_Registers();
        fireTableDataChanged();
    }
    
    /*----Table Model------------------------------------------------------------------*/

    public int getRowCount() {
        return _register_use.size();
    }
    
    public int getColumnCount() {
        return 9;
    }
        
    public Object getValueAt(int row, int column) {    
        int index = ((Integer) _register_use.get(row)).intValue();
    
        switch (column) {
            case GET_COLUMN:		return new Boolean (_machine.Index_To_Memory(index).Get_Other_Status(Mem_Status.ONLINE_GET));				
            case PUT_COLUMN:		return new Boolean (_machine.Index_To_Memory(index).Get_Other_Status(Mem_Status.ONLINE_PUT));				
            case OUT_OF_SERVICE_COLUMN:	return new Boolean (_machine.Index_To_Memory(index).Get_Status(Mem_Status.OOS));				
            case BAD_COLUMN:		return new Boolean (_machine.Index_To_Memory(index).Get_Status(Mem_Status.BAD));				
            case ERROR_COLUMN:		return new Boolean (_machine.Index_To_Memory(index).Get_Status(Mem_Status.ERROR));				
            case SECURE_COLUMN:		return new Boolean (_machine.Index_To_Memory(index).Get_Status(Mem_Status.SECURE));				 
            case REGISTER_COLUMN:	return new Registers_Name(_machine.Index_To_Memory(index).Get_Other_Status(Mem_Status.MODIFIED), _machine.Index_To_Name_Memory(index));				
            case NUMBER_COLUMN:		return _machine.Index_To_Memory(index).Get_Value_String();			
            case CONFIG_COLUMN:		return _machine.Index_To_Memory(index).Get_Configuration();			
            default:			return null;
        }
    }
    
    public void setValueAt(Object value, int row, int column) {
        int index = ((Integer) _register_use.get(row)).intValue();

        switch (column) {
            case GET_COLUMN:		if (value instanceof Boolean) {
                                            _machine.Index_To_Memory(index).Set_Other_Status(Mem_Status.ONLINE_GET,((Boolean) value).booleanValue());
                                        }
                                        break;			
            case PUT_COLUMN:		if (value instanceof Boolean) {
                                            _machine.Index_To_Memory(index).Set_Other_Status(Mem_Status.ONLINE_PUT,((Boolean) value).booleanValue());
                                        }
                                        break;			
            case OUT_OF_SERVICE_COLUMN:	if (value instanceof Boolean) {
                                            _machine.Index_To_Memory(index).Set_Status(Mem_Status.OOS,((Boolean) value).booleanValue());		
                                        }
                                        break;			
            case BAD_COLUMN:		if (value instanceof Boolean) {
                                            _machine.Index_To_Memory(index).Set_Status(Mem_Status.BAD,((Boolean) value).booleanValue());		
                                        }
                                        break;			
            case ERROR_COLUMN:		if (value instanceof Boolean) {
                                            _machine.Index_To_Memory(index).Set_Status(Mem_Status.ERROR,((Boolean) value).booleanValue());	
                                        }
                                        break;			
            case SECURE_COLUMN:		if (value instanceof Boolean) {
                                            _machine.Index_To_Memory(index).Set_Status(Mem_Status.SECURE,((Boolean) value).booleanValue());
                                        }
                                        break;			
            case NUMBER_COLUMN:		if (value instanceof String) {
                                            _machine.Index_To_Memory(index).Set_Value_String((String) value);
                                        }
                                        break;
            case CONFIG_COLUMN:		if (value instanceof String) {
                                            _machine.Index_To_Memory(index).Set_Configuration((String) value);
                                        }
                                        break;
        }
    }

    public String getColumnName(int column) {
       switch (column) {
            case GET_COLUMN:		return Constants.Get_String("RegTableGET");			
            case PUT_COLUMN:		return Constants.Get_String("RegTablePUT");			
            case OUT_OF_SERVICE_COLUMN:	return Constants.Get_String("RegTableOOS");			
            case BAD_COLUMN:		return Constants.Get_String("RegTableBAD");			
            case ERROR_COLUMN:		return Constants.Get_String("RegTableERR");			
            case SECURE_COLUMN:		return Constants.Get_String("RegTableSEC");			
            case REGISTER_COLUMN:	return Constants.Get_String("RegTableParam");			
            case NUMBER_COLUMN:		return Constants.Get_String("RegTableValue");			
            case CONFIG_COLUMN:		return Constants.Get_String("RegTableConfig");			
            default:			return null;
        }
    }
    
    public Class getColumnClass(int column) {
       switch (column) {
            case GET_COLUMN:		return Boolean.class;
            case PUT_COLUMN:		return Boolean.class;
            case OUT_OF_SERVICE_COLUMN:	return Boolean.class;
            case BAD_COLUMN:		return Boolean.class;
            case ERROR_COLUMN:		return Boolean.class;
            case SECURE_COLUMN:		return Boolean.class;
            case REGISTER_COLUMN:	return Registers_Name.class;
            case NUMBER_COLUMN:		return String.class;			
            case CONFIG_COLUMN:		return String.class;			
            default:			return null;
        }
    }

    public boolean isCellEditable(int row, int column) {
        int index = ((Integer) _register_use.get(row)).intValue();

        switch (column) {
            case GET_COLUMN:		return _machine.Index_To_Memory(index).Can_Online();
            case PUT_COLUMN:		return _machine.Index_To_Memory(index).Can_Online();
            case OUT_OF_SERVICE_COLUMN:	return _machine.Index_To_Memory(index).Can_Change_Status();
            case BAD_COLUMN:		return _machine.Index_To_Memory(index).Can_Change_Status();
            case ERROR_COLUMN:		return _machine.Index_To_Memory(index).Can_Change_Status();
            case SECURE_COLUMN:		return _machine.Index_To_Memory(index).Can_Change_Status();
            case REGISTER_COLUMN:	return false;
            case NUMBER_COLUMN:		return true;			
            case CONFIG_COLUMN:		return true;			
            default:			return false;
        }
    }
}

