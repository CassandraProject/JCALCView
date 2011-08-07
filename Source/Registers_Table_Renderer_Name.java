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
//	File:	Registers_Table_Renderer_Name.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

/**
    A <code>Registers_Table_Renderer_Name</code> is the renderer for the line numbers that shows
    visited status and error states.
*/

class Registers_Table_Renderer_Name extends DefaultTableCellRenderer
{
    protected void setValue(Object value) {
        
        if (value instanceof Registers_Name) {
            
            if (value != null) {
                Registers_Name name = (Registers_Name) value;
                
                setText(name.Get_Name());
                
                if (name.Get_Changed())		setIcon(_visited_icon);
                else				setIcon(null);
                
                setHorizontalTextPosition(SwingConstants.RIGHT);
                setVerticalTextPosition(SwingConstants.CENTER);
                setHorizontalAlignment(SwingConstants.LEFT);
                setVerticalAlignment(SwingConstants.CENTER);
            } else {
                setText("");
                setIcon(null);
            }
        
        } else {
            super.setValue(value);
        }
        
    }
    
    static private Icon_Visited_Step 	_visited_icon = new Icon_Visited_Step();
}


