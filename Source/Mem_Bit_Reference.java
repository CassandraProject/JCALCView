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
//	File:	Mem_Bit_Reference.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

class Mem_Bit_Reference extends Mem_Status
                    implements Mem_Interface
{
    private Mem_Interface 	_register;
    private int 		_bit_number;
    
    Mem_Bit_Reference (int flags, Mem_Interface register, int bit_number) {
        super(flags);
        _register = register;
        _bit_number = bit_number;
    }
    

    public void Set_Configuration_Value (String configuration)
    {
        Set_Configuration(configuration);
        Set_Value_String(configuration);
    }

    public void Set_Configuration (String configuration)
    {
        
    }
    
    public String Get_Configuration ()
    {
        return "";
    }
    

    public void Set_Value_String(String r) {
        try {
            boolean bit = (int) Float.parseFloat(r) != 0;
                            
            if (bit == true)	_register.Set_Value((int) _register.Get_Value() | (1 << _bit_number));
            else			_register.Set_Value((int) _register.Get_Value() & ~(1 << _bit_number));

            Set_Other_Status (MODIFIED, true);
        }
        catch (Exception e) {

        }
    }
    
    public void Set_Value(float r) {
        boolean bit = (r != 0.0F) ? true : false;
        
        if (bit == true)		_register.Set_Value((int) _register.Get_Value() | (1 << _bit_number));
        else			_register.Set_Value((int) _register.Get_Value() & ~(1 << _bit_number));
        
        Set_Other_Status (MODIFIED, true);
    }

    public String Get_Value_String() {
        return ((int) _register.Get_Value() & (1 << _bit_number)) != 0 ? "1" : "0";
    }
    
    public float Get_Value() {
        return ((int) _register.Get_Value() & (1 << _bit_number)) != 0  ? 1.0F : 0.0F;
    }

    public void Compile (Machine machine) {
    
    }

}


