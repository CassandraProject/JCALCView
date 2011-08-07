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
//	File:	Preferences.java
//

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

class Preferences extends JDialog
                    implements  ActionListener 
{
    // layout stuff
    private static int SIZE_X = 400;
    private static int SIZE_Y = 350;

    private	JButton 	_button_ok;
    private	JButton 	_button_cancel;
    private	JCheckBox	_display_errors_box;
    private	JCheckBox	_all_icc_box;
    private	JComboBox	_period_combo_box;

    private	JTextField	_omget_path_field;
    private	JTextField	_omset_path_field;
    private	JTextField	_icc_path_field;
    private	JTextField	_cplns_path_field;
        
    private	String		_omget_path;
    private	String		_omset_path;
    private	String		_icc_path;
    private	String		_cplns_path;

    private	boolean		_display_errors = true;
    private	boolean		_all_icc = false;

    private	int		_period = 1;
    
    Preferences() {                                    
        setTitle(Constants.Get_String("Preferences"));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Set default sizes
        GetPrefs();
        addPanes();
 
        setBounds((screenSize.width/2)-(SIZE_X/2), (screenSize.height/2)-(SIZE_Y/2), SIZE_X, SIZE_Y);
    }
    
    public void GetPrefs() {
        _omget_path = Constants.Get_Prefs_File().Get_Property_String("OmgetPath", Constants.Get_String("SolarisOmgetPath"));
        _omset_path = Constants.Get_Prefs_File().Get_Property_String("OmsetPath", Constants.Get_String("SolarisOmsetPath"));
        _icc_path = Constants.Get_Prefs_File().Get_Property_String("ICCPath", Constants.Get_String("SolarisICCPath"));
        _cplns_path = Constants.Get_Prefs_File().Get_Property_String("CplnsPath", Constants.Get_String("SolarisCplnsPath"));

        _display_errors = Constants.Get_Prefs_File().Get_Property_Boolean("DisplayErrors", true);
        _all_icc = Constants.Get_Prefs_File().Get_Property_Boolean("AllICC", false);
        _period = Constants.Get_Prefs_File().Get_Property_Integer("BlockPeriod", 1);
    }

    public void PutPrefs() {
        Constants.Get_Prefs_File().Set_Property_String("OmgetPath", _omget_path);
        Constants.Get_Prefs_File().Set_Property_String("OmsetPath", _omset_path);
        Constants.Get_Prefs_File().Set_Property_String("ICCPath", _icc_path);
        Constants.Get_Prefs_File().Set_Property_String("CplnsPath", _cplns_path);
        
        Constants.Get_Prefs_File().Set_Property_Boolean("DisplayErrors", _display_errors);
        Constants.Get_Prefs_File().Set_Property_Boolean("AllICC", _all_icc);
        Constants.Get_Prefs_File().Set_Property_Integer("BlockPeriod", _period);
        
        Constants.Get_Prefs_File().Write();
    }
    
    public void DefaultSolarisPrefs() {
        _omget_path_field.setText(Constants.Get_String("SolarisOmgetPath"));
        _omset_path_field.setText(Constants.Get_String("SolarisOmsetPath"));
        _icc_path_field.setText(Constants.Get_String("SolarisICCPath"));
        _cplns_path_field.setText(Constants.Get_String("SolarisCplnsPath"));
    }

    public void DefaultWindowsPrefs() {
        _omget_path_field.setText(Constants.Get_String("WindowsOmgetPath"));
        _omset_path_field.setText(Constants.Get_String("WindowsOmsetPath"));
        _icc_path_field.setText(Constants.Get_String("WindowsICCPath"));
        _cplns_path_field.setText(Constants.Get_String("WindowsCplnsPath"));
    }

    public void Show_Prefs () {
        // Copy the old values
        _omget_path_field.setText(_omget_path);
        _omset_path_field.setText(_omset_path);
        _icc_path_field.setText(_icc_path);
        _cplns_path_field.setText(_cplns_path);
        _display_errors_box.setSelected(_display_errors);
        _all_icc_box.setSelected(_all_icc);
        _period_combo_box.setSelectedIndex(_period - 1);
    
        setVisible(true);
    }
    
    public String Get_Omget_Path() {
        return _omget_path;
    }

    public String Get_Omset_Path() {
        return _omset_path;
    }

    public String Get_ICC_Path() {
        return _icc_path;
    }

    public String Get_Cplns_Path() {
        return _cplns_path;
    }

    public boolean Display_Errors() {
        return _display_errors;
    }

    public boolean All_ICC() {
        return _all_icc;
    }

    public int Get_Delay() {
        return _period_combo_box.getSelectedIndex() + 1;
    }
    
   
    // Builds the contents for the window
    public void addPanes() {
              
        // Build the edit panel
        JPanel edit_panel = new JPanel();
        edit_panel.setLayout(new GridLayout(8,1));

        // Defaults path box
        JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.CENTER));
        edit_panel.add(panel5);
                
        JButton solaris_defaults = new JButton(Constants.Get_String("SolarisDefaultButton"));
        solaris_defaults.addActionListener( new
            ActionListener()
            {
                public void actionPerformed(ActionEvent evt)
                {
                    DefaultSolarisPrefs();
                }
            }
        );
        
        panel5.add(solaris_defaults);

        JButton windows_defaults = new JButton(Constants.Get_String("WindowsDefaultButton"));
        windows_defaults.addActionListener( new
            ActionListener()
            {
                public void actionPerformed(ActionEvent evt)
                {
                    DefaultWindowsPrefs();
                }
            }
        );
        
        panel5.add(windows_defaults);


        // Omget path box
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        edit_panel.add(panel1);
        
        panel1.add(new JLabel(Constants.Get_String("OmgetPathLabel")));
        
        _omget_path_field = new JTextField(_omget_path, 25);
        _omget_path_field.setToolTipText(Constants.Get_String("OmgetPathTip"));
        panel1.add(_omget_path_field);


        // Omset path box
        JPanel panel8 = new JPanel();
        panel8.setLayout(new FlowLayout(FlowLayout.LEFT));
        edit_panel.add(panel8);
        
        panel8.add(new JLabel(Constants.Get_String("OmsetPathLabel")));
        
        _omset_path_field = new JTextField(_omset_path, 25);
        _omset_path_field.setToolTipText(Constants.Get_String("OmsetPathTip"));
        panel8.add(_omset_path_field);


        // ICC path box
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        edit_panel.add(panel2);

        panel2.add(new JLabel(Constants.Get_String("ICCPathLabel")));
  
        _icc_path_field = new JTextField(_icc_path, 25);
        _icc_path_field.setToolTipText(Constants.Get_String("ICCPathTip"));
        panel2.add(_icc_path_field);

        // cplns path box
        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
        edit_panel.add(panel4);

        panel4.add(new JLabel(Constants.Get_String("CplnsLabel")));
  
        _cplns_path_field = new JTextField(_cplns_path, 25);
        _cplns_path_field.setToolTipText(Constants.Get_String("CplnsPathTip"));
        panel4.add(_cplns_path_field);
                
                
        // Show Errors
        JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout(FlowLayout.CENTER));
        edit_panel.add(panel6);

        _display_errors_box = new JCheckBox(Constants.Get_String("ShowErrors"), true);
        _display_errors_box.setToolTipText(Constants.Get_String("ShowErrorsTip"));
        panel6.add(_display_errors_box);

        // All ICC
        JPanel panel7 = new JPanel();
        panel7.setLayout(new FlowLayout(FlowLayout.CENTER));
        edit_panel.add(panel7);

        _all_icc_box = new JCheckBox(Constants.Get_String("AllICC"), true);
        _all_icc_box.setToolTipText(Constants.Get_String("AllICCTip"));
        panel7.add(_all_icc_box);
        
        
        // Looping period
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        edit_panel.add(panel3);
        
        panel3.add(new JLabel(Constants.Get_String("BlockPeriodLabel")));
        
        _period_combo_box = new JComboBox();
        _period_combo_box.addItem(1 + " " + Constants.Get_String("BlockPeriodUnitsFirst"));
        for (int time = 2; time <= 10; ++time)
            _period_combo_box.addItem(time + " " + Constants.Get_String("BlockPeriodUnits"));

        panel3.add(_period_combo_box);
        

        // Build the buttons panel
        JPanel buttons_panel = new JPanel();
        buttons_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        _button_ok = new JButton(Constants.Get_String("OKButton"));
        _button_ok.addActionListener(this);
        buttons_panel.add(_button_ok);

        _button_cancel = new JButton(Constants.Get_String("CancelButton"));
        _button_cancel.addActionListener(this);
        buttons_panel.add(_button_cancel);
        

        // Build the main panel
        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());

        main_panel.add(edit_panel, BorderLayout.CENTER);
        main_panel.add(buttons_panel, BorderLayout.SOUTH);
                
        getContentPane().add(main_panel);
        
    }
   
    
    public void actionPerformed(ActionEvent newEvent) {
        String command = newEvent.getActionCommand();

        if (command.equals(_button_ok.getActionCommand())) {
            // Copy the new values
            _omget_path = _omget_path_field.getText();
            _omset_path = _omset_path_field.getText();
            _icc_path = _icc_path_field.getText();
            _cplns_path = _cplns_path_field.getText();
            _display_errors = _display_errors_box.isSelected();
            _all_icc = _all_icc_box.isSelected();
            _period = _period_combo_box.getSelectedIndex() + 1;
            
            PutPrefs();
        
            setVisible(false);
            
        } else if (command.equals(_button_cancel.getActionCommand())) {
            setVisible(false);
        }
                
    }

}
