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
//	File:	Steps_Table.java
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

public class Steps_Line_Number {
    Steps_Line_Number (boolean visited, boolean current, boolean error, int number) {
        _visited = visited;
        _current = current;
        _error = error;
        _number = number;
    }
    
    public boolean Get_Visited() 	{ return _visited; }
    public boolean Get_Current() 	{ return _current; }
    public boolean Get_Error() 		{ return _error; }
    public int Get_Number() 		{ return _number; }
    
    public String toString() 		{ return (_number <= 9 ? "STEP0" : "STEP") + _number;	}
    
    private boolean _visited;
    private boolean _current;
    private boolean _error;
    private int _number;

}



