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
//	File:	Icon_Stop.java
//

import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
//import javax.swing.table.*;
//import javax.swing.text.*;

public class Icon_Stop implements Icon, SwingConstants
{

    public int getIconWidth()
    {
        return WIDTH;
    }
    
    
    public int getIconHeight()
    {
        return HEIGHT;
    }
    
    
    public void paintIcon(Component c, Graphics g, int x, int y)
    {    
        g.setColor(Color.red);
    
        
        // allocate an array for building a polygon
        int[] px = new int[4];
        int[] py = new int[4];
        
        // temp variables
        int w = WIDTH - 1;
        int h = HEIGHT - 1;
        
        px[0] = x + BORDER;	py[0] = y + BORDER;
        px[1] = x+w - BORDER;	py[1] = y + BORDER;
        px[2] = x+w - BORDER;	py[2] = y + h - BORDER;
        px[3] = x + BORDER;	py[3] = y + h - BORDER;

        Polygon p = new Polygon(px, py, 4);
        g.drawPolygon(p);
        g.fillPolygon(p);
    }
    
    static final short BORDER = 6;
    static final short WIDTH = 25;
    static final short HEIGHT = 25;
}
