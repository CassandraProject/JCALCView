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
//	File:	JComboBox_Clip.java
//

import java.lang.ref.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.datatransfer.*;
     
public class JComboBox_Clip extends JComboBox implements Clipboard_Interface, ActionListener
{
    public JComboBox_Clip() {
        registerKeyboardAction(this,"Cut",KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),JComponent.WHEN_FOCUSED);
        registerKeyboardAction(this,"Copy",KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),JComponent.WHEN_FOCUSED);
        registerKeyboardAction(this,"Paste",KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),JComponent.WHEN_FOCUSED);
        registerKeyboardAction(this,"Clear",KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),JComponent.WHEN_FOCUSED);
        registerKeyboardAction(this,"SelectAll",KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),JComponent.WHEN_FOCUSED);
    }
    
    public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("Cut"))
            cut();
        else if (e.getActionCommand().equals("Copy"))
            copy();
        else if (e.getActionCommand().equals("Paste"))
            paste();
        else if (e.getActionCommand().equals("Clear"))
            clear();
        else if (e.getActionCommand().equals("SelectAll"))
            selectAll();
    }

    public void cut()			{	}
    public void copy()			{	}
    public void paste()			{	}
    public void clear()			{	}
    public void selectAll()		{	}

    public void unfocus() {
        //getEditor().getEditorComponent().clearSelection();
    }

    public void focus() {
        //getEditor().clearSelection();
    }

}
