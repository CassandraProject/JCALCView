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
//	File:	Steps_Table_Model.java
//

import java.awt.*;
import java.lang.Runtime;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.util.Observer;
import java.util.Observable;

/**
    A <code>Steps_Table_Model</code> holds the steps of the program as well as the program counter
    and initialization flag.
*/

class Steps_Table_Model extends AbstractTableModel implements Observer
{
    private 		int 			_current_step;
    private 		boolean 		_initializing;
    
    private		Registers_Table_Model	_registers;
    private		Machine			_machine;
    
    static public final short 	BREAK_COLUMN = 0;
    static public final short 	NUMBER_COLUMN = 1;
    static public final short 	INSTRUCTION_COLUMN = 2;

/**
    Constructor for the class.
    @param reg	The registers table list so we can update it when the steps change.
*/

    Steps_Table_Model (Machine machine, Registers_Table_Model reg) 
    {            
        // Remember the registers table so we can update it
        _registers = reg;    
        _machine = machine;
        
        _current_step = 1;
                
        _initializing = false;
        
        _machine.addObserver(this);
    }
    
/**
    Initializing flag. If initializing, a different code path may be taken.
    @param initializing	Set or clear the initializing flag.
*/    

    public void Set_Initializing (boolean initializing) 
    {
        _initializing = initializing;
    }

/**
    Returns the Initializing flag.
*/    

    public boolean Get_Initializing () 
    {
        return _initializing;
    }
  
      
/**
    Go to the next program step.
*/    

    public void Increment_Step () 
    {
        ++_current_step;
    }

/**
    Jump to the program step.
    @param step		Mem_Step 1 thru 50.
*/    

    public void Jump_Step (int step) 
    {        
        if (step <= _current_step)
            throw new Exception_Illegal_Branch();
    
        _current_step = step;
    }

/**
    Jump to the program step without throwing an exception.
    @param step		Mem_Step 1 thru 50.
*/    

    public void Jump_Step_Backward (int step) 
    {
        _current_step = step;
    }

/**
    Jump to the end step.
*/    

    public void Jump_End () 
    {
        _current_step = _machine.Get_Num_Steps() + 1;
    }
    
/**
    Returns a step.
    @param step		Mem_Step 1 thru 50.
*/    

    public Mem_Step Get_Step (int step) 
    {
        return (Mem_Step) _machine.Index_To_Memory(Constants.STEP01 + step - 1);
    }

/**
    What step are we presently on.
*/    

    public Mem_Step Get_Current_Step () 
    {
        return Get_Step(_current_step);
    }

/**
    Are we done executing the steps.
*/    

    public boolean Is_Done () 
    {
        return _current_step > _machine.Get_Num_Steps();
    }

/**
    Takes all of the necessary steps to start executing the block. Timers are also initialized.
    @param init		Block is initializing.
    @param timini	The value of the timini register since timers are also initialized.
*/    

    public void Begin_Running_Steps (boolean timini) 
    {
        Stop_Running_Steps();

        for (short s = 1; s <= _machine.Get_Num_Steps(); ++s) {
            Mem_Step step = Get_Step (s);
                        
            if (_initializing) {
                Init_Timer (s, timini);   
                _machine.Initialize();	// error code
            }
        }
    }

/**
    Takes all of the necessary steps to stop the block.
*/    

    public void Stop_Running_Steps () 
    {
        for (short s = 1; s <= _machine.Get_Num_Steps(); ++s) {
            Mem_Step step = Get_Step (s);
            step.Set_Visited(false);
        }

        // Start on step 1
        Jump_Step_Backward(1);
    }

/**
    Compiles all of the steps.
*/    

    public void Compile_Steps () 
    {
        for (short s = 1; s <= _machine.Get_Num_Steps(); ++s) {
            Mem_Step step = Get_Step (s);
            step.Compile(_machine);      
        }
    }
    
/**
    Compiles all of the steps.
*/    

    public boolean Has_Error () 
    {
        for (short s = 1; s <= _machine.Get_Num_Steps(); ++s) {
            Mem_Step step = Get_Step (s);
            if (step.Get_Error())	
                return true;     
        }
        
        return false;
    }

/**
    Clears the timer of all steps.
*/    

    public void Clear_Timers () 
    {
        for (short step = 1; step <= _machine.Get_Num_Steps(); ++step)
            Clear_Timer (step);        
    }

/**
    Clears the timer of a certain step.
    @param s		The step that has to be cleared.
*/    

    public void Clear_Timer (int s) {
        Mem_Step step = Get_Step (s);
        step.Set_Timer(0.0F);
    }

/**
    Clears the timer of a certain step.
    @param step		The step that has to be cleared.
    @param timini	The value of the timini register.
*/    

    public void Init_Timer (int s, boolean timini) 
    {
        Mem_Step step = Get_Step (s);
        
        if (timini) {
            switch (step.Get_Opcode()) {
                case Constants.OSP:		step.Set_Timer((float) step.Get_Value1() + 1.0F);	break;
                case Constants.DON:		step.Set_Timer((float) step.Get_Value1() + 1.0F);	break;
                case Constants.DOFF:		step.Set_Timer((float) step.Get_Value1() + 1.0F);	break;	
                default:		        step.Set_Timer((float) step.Get_Value1() + 1.0F);	break;        
            }
        } else {
            switch (step.Get_Opcode()) {
                case Constants.OSP:		step.Set_Timer(0.0F);	break;
                case Constants.DON:		step.Set_Timer(0.0F);	break;
                case Constants.DOFF:		step.Set_Timer(0.0F);	break;	
                default:		        step.Set_Timer(0.0F);	break;        
            }
        }
    }
    
/**
    Observer message
*/
    
    public void update (Observable o, Object arg) {
        // Called when the memory changed
        Compile_Steps ();
        fireTableDataChanged();
    }
    
/**
    Returns the number of rows in the table.
*/    
     
    public int getRowCount() {
        return _machine.Get_Num_Steps();
    }
    
/**
    Returns the number of columns in the table.
*/    

    public int getColumnCount() {
        return 3;
    }
        
/**
    Returns the name of the column.
    @param column	Column in the table.
*/    

    public String getColumnName(int column) {
       switch (column) {
            case BREAK_COLUMN:		return Constants.Get_String("StepTableBP");
            case NUMBER_COLUMN:		return Constants.Get_String("StepTableStep");			
            case INSTRUCTION_COLUMN:	return Constants.Get_String("StepTableStatement");	
            default:			return null;
        }
    }
    
/**
    Returns the class that represents the column type.
    @param column	Column in the table.
*/    

    public Class getColumnClass(int column) {
       switch (column) {
            case BREAK_COLUMN:		return Boolean.class;
            case NUMBER_COLUMN:		return Steps_Line_Number.class;			
            case INSTRUCTION_COLUMN:	return String.class;	
            default:			return null;
        }
    }

/**
    Is this cell in the table editable.
    @param row		Row in the table.
    @param column	Column in the table.
*/    

    public boolean isCellEditable(int row, int column) {
       switch (column) {
            case BREAK_COLUMN:		return true;
            case NUMBER_COLUMN:		return false;			
            case INSTRUCTION_COLUMN:	return true;	
            default:			return false;
        }
    }
    
/**
    Sets the value of the cell.
    @param row		Row in the table.
    @param column	Column in the table.
*/    

    public void setValueAt(Object value, int row, int column) {
        Mem_Step step = Get_Step (row+1);
        
        switch (column) {
            case BREAK_COLUMN:		if (value instanceof Boolean) {
                                            step.Set_Break(((Boolean) value).booleanValue());
                                            _machine.FireChanged ();
                                        }
                                        break;
            case INSTRUCTION_COLUMN:	if (value instanceof String) {
                                            step.Set_Configuration((String) value);
                                            step.Compile(_machine);
                                            _machine.FireChanged ();
                                        }
                                        break;	
        }
    }
    
/**
    Returns the value of the cell.
    @param row		Row in the table.
    @param column	Column in the table.
*/    

    public Object getValueAt(int row, int column) {    
        Mem_Step step = Get_Step (row+1);

        switch (column) {
            case BREAK_COLUMN:		return new Boolean(step.Get_Break());				
            case NUMBER_COLUMN:		return new Steps_Line_Number(step.Get_Visited(), ((_current_step-1) == row), step.Get_Error(), row+1);			
            case INSTRUCTION_COLUMN:	return step.Get_Configuration();	
            default:			return null;
        }
    }

    
}


