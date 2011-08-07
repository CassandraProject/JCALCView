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
//	File:	Icon_Current_Step.java
//

import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
//import javax.swing.table.*;
//import javax.swing.text.*;

public class Icon_Current_Step implements Icon, SwingConstants
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
        g.setColor(new Color(0.0F,0.7F,0.0F));
    
        
        // allocate an array for building a polygon
        int[] px = new int[3];
        int[] py = new int[3];
        
        // temp variables
        int w = SIZE - 1;
        int h = SIZE - 1;
        
        px[0] = x;	py[0] = y;
        px[1] = x+w;	py[1] = y + h/2;
        px[2] = x;	py[2] = y + h;
        
        Polygon p = new Polygon(px, py, 3);
        g.drawPolygon(p);
        g.fillPolygon(p);
    }
    
    static final short SIZE = 8;
}
