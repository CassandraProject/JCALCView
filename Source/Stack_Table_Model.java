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
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

class Stack_Table_Model extends AbstractTableModel
{
    static public final short VALUE_COLUMN = 0;

    private float _stack[];
    private int _stack_pointer;
    
 
    Stack_Table_Model() {
        _stack = new float[Constants.NUM_STACK_SIZE];
        Clear();
    }
    
    
    public void Clear() {
        _stack_pointer = -1;
        //Push(0.0F);
    }

    public void Push(float v) throws Exception_Stack_Overflow {
        if (_stack_pointer >= Constants.NUM_STACK_SIZE - 1)
            throw new Exception_Stack_Overflow();

        ++_stack_pointer;
        
        _stack[_stack_pointer] = v;
    }
    
    public void Pop(int n) throws Exception_Stack_Underflow {
       if (_stack_pointer - n < -1) 
            throw new Exception_Stack_Underflow();
            
        _stack_pointer -= n;
    }
   
    public float Top (int n) {
        int sp = _stack_pointer - n;
    
        if (sp < 0) 
            return 0.0F;

        return _stack[sp];
    }
    
    public int Depth () {
         return _stack_pointer+1;
    }
    
    public void Replace (float v) {
        if (_stack_pointer >= 0 && _stack_pointer < Constants.NUM_STACK_SIZE)
            _stack[_stack_pointer] = v;
        else if (_stack_pointer < 0) 
            Push(v);
    }
    
    /*----Table Model------------------------------------------------------------------*/
        
    public int getRowCount() {
        return Constants.NUM_STACK_SIZE;
    }
    
    public int getColumnCount() {
        return 1;
    }
    
    public Object getValueAt(int row, int column) {    
        if (row > _stack_pointer)
            return Constants.Get_String("StackTableContentsEmpty");
        else
            return new String(""+Top(row));
    }
    
    public void setValueAt(Object value, int row, int column) {
      
    }

    public String getColumnName(int column) {
    	return Constants.Get_String("StackTableContents");
    }
    
    public Class getColumnClass(int column) {
    	return String.class;
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
}


