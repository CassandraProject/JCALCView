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
//	File:	Mem_Type.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

class Mem_Type extends Mem_Status
                    implements Mem_Interface
{
    private String 	_value;
    
    Mem_Type (int flags) {
        super(flags);
        _value = "";
    }
    

    public void Set_Configuration_Value (String configuration)
    {
        Set_Configuration(configuration);
    }

    public void Set_Configuration (String configuration)
    {
        String value = configuration.toUpperCase();
        
        if (value.equals("CALCA"))		_value = value;
        else if (value.equals("CALC"))		_value = value;
        else if (value.equals("MATH"))		_value = value;
        else if (value.equals("LOGIC"))		_value = value;
    }
    
    public String Get_Configuration ()
    {
        return _value;
    }
    

    public void Set_Value_String(String r) {
        throw new Exception_Illegal_Memory_Use();
    }
    
    public void Set_Value(float r) {
        throw new Exception_Illegal_Memory_Use();
    }
    
    public String Get_Value_String() {
        throw new Exception_Illegal_Memory_Use();
    }

    public float Get_Value() {
        throw new Exception_Illegal_Memory_Use();
    }
    
    public void Compile (Machine machine) {
        if (_value.equals("CALCA"))		machine.Set_Up_Memory_CALCA();
        else if (_value.equals("CALC"))		machine.Set_Up_Memory_CALC();
        else if (_value.equals("MATH"))		machine.Set_Up_Memory_MATH();
        else if (_value.equals("LOGIC"))	machine.Set_Up_Memory_LOGIC();
    }

}


