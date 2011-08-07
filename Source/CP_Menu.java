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
import java.io.*;

/**
    A <code>Steps_Table</code> the table that shows the steps.  Sets up the default table.
*/

class CP_Menu extends JComboBox_Clip
{
    CP_Menu () {
        Refresh();    
        setEditable(true);   
    }
    
    public void Refresh () {
    
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(Constants.Get_Prefs().Get_Cplns_Path())));
            
            String line;
            while ((line = in.readLine()) != null)
                addItem(line);

        }
        catch (IOException e) {

        }
    }
    
}


