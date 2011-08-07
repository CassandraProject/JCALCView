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
//	File:	Icon_Error_Step.java
//

import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
//import javax.swing.table.*;
//import javax.swing.text.*;

public class Icon_Error_Step implements Icon, SwingConstants
{

    public int getIconWidth()
    {
        return SIZE;
    }
    
    
    public int getIconHeight()
    {
        return SIZE;
    }
    
    
    public void paintIcon(Component c, Graphics g, int x, int y)
    {    
        g.setColor(Color.red);
            
        // temp variables
        int w = SIZE - 1;
        int h = SIZE - 1;
        
        g.drawOval(x, y, w, h);
        g.fillOval(x, y, w, h);
    }
    
    static final short SIZE = 8;
}
