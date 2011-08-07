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
//	File:	HTMLFrame.java
//

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.net.*;

class HTMLFrame extends JFrame 
{
    // layout stuff
    private static int SIZE_X = 700;
    private static int SIZE_Y = 800;

    private JEditorPane _html_pane;
    
    class LinkListener implements HyperlinkListener {
        
        // Necessary to implement HyperlinkListener
        public void hyperlinkUpdate(HyperlinkEvent ev){
            try{
                if (ev.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
                    URL u = ev.getURL();
                    _html_pane.setPage(u);
                }
            }
            catch (Exception e) {
            
            }
        }
    
    }

    
    HTMLFrame (URL starting_page) {
        setTitle(Constants.Get_String("InstructionsTitle"));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Set default sizes
        setBounds((screenSize.width/2)-(SIZE_X/2), (screenSize.height/2)-(SIZE_Y/2), SIZE_X, SIZE_Y);

        
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        
        _html_pane = new JEditorPane();
        _html_pane.setEditable(false);
    
        try {
            _html_pane.setPage(starting_page);
        }
        catch (IOException e) {
        
        }
        
        mainpanel.add(new JScrollPane(_html_pane), BorderLayout.CENTER);
        
        getContentPane().add(mainpanel);
        
        _html_pane.addHyperlinkListener(new LinkListener());
        
        show();
    }
    
    
}
