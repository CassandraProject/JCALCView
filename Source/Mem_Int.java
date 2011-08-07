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
//	File:	Mem_Int.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

class Mem_Int extends Mem_Status
                    implements Mem_Interface
{
    private int 	_register;
    private String 	_configuration;
    
    Mem_Int (int flags) {
        super(flags);
        _register = 0;
        _configuration = "0";
    }
    

    public void Set_Configuration_Value (String configuration)
    {
        Set_Configuration(configuration);
        Set_Value_String(configuration);
    }

    public void Set_Configuration (String configuration)
    {
        _configuration = configuration;
    }
    
    public String Get_Configuration ()
    {
        return _configuration;
    }
    

    public void Set_Value_String(String r) {
        try {
            _register = (int) Float.parseFloat(r);
            
            Set_Other_Status (MODIFIED, true);
        }
        catch (Exception e) {

        }
    }
    
    public void Set_Value(float r) {
        _register = (int) r;
        
        Set_Other_Status (MODIFIED, true);
    }
    
    public String Get_Value_String() {
        return ""+((int) _register);
    }   

    public float Get_Value() {
        return (int) _register;
    }   
    
    public void Compile (Machine machine) {
    
    }
}


