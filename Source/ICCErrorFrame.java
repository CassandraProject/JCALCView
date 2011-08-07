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
//	File:	ICCErrorFrame.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.util.*;

class ICCErrorFrame extends JFrame 
                    implements  ActionListener {

    // layout stuff
    private static int WIDTH = 500;
    private static int HEIGHT = 400;

    private	JButton		_ok = new JButton(Constants.Get_String("OKButton"));
    private	JTextArea	_text = new JTextArea();

    public void Add_Error(String e) {
        _text.append(e+"\n");
    }
        
    // Constructor for the frame
    public ICCErrorFrame() {                 
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Set default sizes
        setBounds((screenSize.width - WIDTH)/2, (screenSize.height - HEIGHT)/2, WIDTH, HEIGHT);
        setTitle(Constants.Get_String("ICCErrorWindow"));

        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());
        
        _text.setWrapStyleWord(true);
        _text.setLineWrap(true);
        
        JScrollPane scroll = new JScrollPane(_text);
        main_panel.add(scroll, BorderLayout.CENTER);
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(_ok);
        main_panel.add(buttons, BorderLayout.SOUTH);
        
        _ok.addActionListener(this);

        getContentPane().add(main_panel);

        show();
    }

    // Listen to events from menus and buttons
    public void actionPerformed(ActionEvent newEvent) {
        String command = newEvent.getActionCommand();
    
	if (command.equals(_ok.getActionCommand())) {
            dispose();
        }                
    }
    
    
    
}

