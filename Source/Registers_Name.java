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
//	File:	Registers_Name.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

/**
    A <code>Steps_Line_Number</code> hold the inforamtion required to properly render
    the line numbers.
*/

public class Registers_Name {
    Registers_Name (boolean changed, String name) {
        _changed = changed;
        _name = name;
    }
    
    public boolean Get_Changed() 	{ 	return _changed; 	}
    public String Get_Name() 		{ 	return _name; 	}

    public String toString() 	 	{ 	return _name;		}
    
    private boolean _changed;
    private String _name;
}
