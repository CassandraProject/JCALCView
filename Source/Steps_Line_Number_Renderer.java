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
//	File:	Steps_Line_Number_Renderer.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

/**
    A <code>Steps_Line_Number_Renderer</code> is the renderer for the line numbers that shows
    visited status and error states.
*/

class Steps_Line_Number_Renderer extends DefaultTableCellRenderer
{
    protected void setValue(Object value) {
        
        if (value instanceof Steps_Line_Number) {
            
            if (value != null) {
                Steps_Line_Number line = (Steps_Line_Number) value;
                
                setText(""+line.Get_Number());
                
                if (line.Get_Error())		setIcon(_error_icon);
                else if (line.Get_Current())	setIcon(_current_icon);
                else if (line.Get_Visited())	setIcon(_visited_icon);
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
    static private Icon_Current_Step 	_current_icon = new Icon_Current_Step();
    static private Icon_Error_Step 	_error_icon = new Icon_Error_Step();
}


