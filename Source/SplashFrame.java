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
//  SplashFrame.java
//  CALCView
//
//  Created by Tod Baudais on Wed Feb 26 2003.
//  Copyright (c) 2003 __MyCompanyName__. All rights reserved.
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.io.*;
import java.util.*;
import javax.swing.text.*;
import java.text.*;

public class SplashFrame extends javax.swing.JDialog {

    // layout stuff
    private static int WIDTH = 600;
    private static int HEIGHT = 400;

    private static int LOGO_TAB = 0;
    private static int LICENSE_TAB = 1;
    private static int README_TAB = 2;
    private static int ACTIVATION_TAB = 3;
    
    private	JButton		_quit, _ok;
    private	JCheckBox	_accept_license;
    private	JTextField	_name;
    private	JTextField	_activation;
    private	JLabel		_message;
    
    public void Load_File (String filename, JTextArea text) {
    
        try {
            InputStream in_stream = SplashFrame.class.getResourceAsStream(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(in_stream));
            
            String line;
            while ((line = in.readLine()) != null)
                text.append(line+"\n");

        }
        catch (IOException e) {
            System.exit(0);
        }

    }
    
    public void GetPrefs() {
        _name.setText(Constants.Get_Prefs_File().Get_Property_String("UserName", ""));
        _activation.setText(Constants.Get_Prefs_File().Get_Property_String("ActivationCode", ""));
        _accept_license.setSelected(Constants.Get_Prefs_File().Get_Property_Boolean("AcceptLicense", false));
    }

    public void PutPrefs() {
        Constants.Get_Prefs_File().Set_Property_String("UserName", _name.getText());
        Constants.Get_Prefs_File().Set_Property_String("ActivationCode", _activation.getText());
        Constants.Get_Prefs_File().Set_Property_Boolean("AcceptLicense", _accept_license.isSelected());

        Constants.Get_Prefs_File().Write();
    }
    
    public void Show_Splash () {
        setVisible(true);
    }

    public void Show_Splash_Not_Valid () {
        if (!_ok.isEnabled())
            setVisible(true);
    }


    public void VerifyCode() {
        try {
            ActivationCode ac = new ActivationCode();

            Date expiry_date = ac.Check_Code (_name.getText(), _activation.getText());
            if (expiry_date.after(new Date())) {
                // Unlock
                if (_accept_license.isSelected()) {
                    _ok.setEnabled(true);
                    
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(expiry_date);
                    
                    if (gc.get(Calendar.YEAR) < 2020)
                        _message.setText(Constants.Get_String("ActivationCodeExpiresOn") + " " + expiry_date.toString());
                    else
                        _message.setText(Constants.Get_String("ActivationCodeNoExpire"));
                } else {
                    _ok.setEnabled(false);
                    _message.setText(Constants.Get_String("ActivationAcceptLicense"));
                }
            } else {
                // Lock
                _ok.setEnabled(false);
                _message.setText(Constants.Get_String("ActivationCodeExpired"));
            }
            
        }
        catch (Exception_Invalid_Code e) {
            _ok.setEnabled(false);
            _message.setText(Constants.Get_String("ActivationCodeError"));
        }
    }
    
    // Constructor for the frame
    public SplashFrame() {                                   
        super((JFrame) null, Constants.Get_String("SplashWindow"), true);

        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Set default sizes
        JTabbedPane tabbed_pane = new JTabbedPane();

        // Main Graphic        
        ImageIcon graphic = new ImageIcon(SplashFrame.class.getResource("Resources/Logo.jpg"));
        tabbed_pane.addTab(Constants.Get_String("SplashGraphicLabel"), new JLabel(graphic));
        
        // License
        JPanel license_panel = new JPanel();
        license_panel.setLayout(new BorderLayout());

        JTextArea license_text = new JTextArea();
        license_text.setLineWrap(true);
        license_text.setWrapStyleWord(true);
        Load_File (Constants.Get_String("SplashLicense"), license_text);
                
        license_panel.add(new JScrollPane(license_text), BorderLayout.CENTER);
        tabbed_pane.addTab(Constants.Get_String("SplashLicenseLabel"), license_panel);
       
        // Read Me
        JPanel read_me_panel = new JPanel();
        read_me_panel.setLayout(new BorderLayout());

        JTextArea read_me_text = new JTextArea();
        read_me_text.setLineWrap(true);
        read_me_text.setWrapStyleWord(true);
        Load_File (Constants.Get_String("SplashReadme"), read_me_text);
        
        read_me_panel.add(new JScrollPane(read_me_text), BorderLayout.CENTER);

        tabbed_pane.addTab(Constants.Get_String("SplashReadmeLabel"), read_me_panel);
        

        // Activation
        JPanel activation_panel = new JPanel();
        activation_panel.setLayout(new GridLayout(6,1));
        
        // Check Box
        JPanel accept_panel = new JPanel();
        accept_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        _accept_license = new JCheckBox(Constants.Get_String("AcceptButton"));
        _accept_license.setMaximumSize(_accept_license.getPreferredSize());
        accept_panel.add(_accept_license);
        activation_panel.add(accept_panel);
       
        // Name Entry
        JPanel name_panel = new JPanel();
        name_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        _name = new JTextField(20);
        _name.setMaximumSize(_name.getPreferredSize());
        name_panel.add(new JLabel(Constants.Get_String("SplashNameLabel")));
        name_panel.add(_name);
        activation_panel.add(name_panel);
               
        // Activation Code
        JPanel code_panel = new JPanel();
        code_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        _activation = new JTextField(20);
        _activation.setMaximumSize(_activation.getPreferredSize());
        code_panel.add(new JLabel(Constants.Get_String("SplashCodeLabel")));
        code_panel.add(_activation);
        activation_panel.add(code_panel);

        // Unlock button
        JPanel unlock_panel = new JPanel();
        unlock_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton unlock_button = new JButton(Constants.Get_String("UnlockButton"));
        unlock_button.addActionListener( new
            ActionListener()
            {
                public void actionPerformed(ActionEvent evt)
                {
                    VerifyCode();
                }
            }
        );
        unlock_panel.add(unlock_button);
        activation_panel.add(unlock_panel);
        
        // Message
        JPanel message_panel = new JPanel();
        message_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        _message = new JLabel();
        message_panel.add(_message);
        activation_panel.add(message_panel);
        
        tabbed_pane.addTab(Constants.Get_String("SplashActivationLabel"), activation_panel);
        
        
        // Add tabbed pane and buttons
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        
    	_quit = new JButton(Constants.Get_String("QuitButton"));
        _quit.addActionListener( new
            ActionListener()
            {
                public void actionPerformed(ActionEvent evt)
                {
                    System.exit(0);
                }
            }
        );
        
        _ok = new JButton(Constants.Get_String("OKButton"));
        _ok.addActionListener( new
            ActionListener()
            {
                public void actionPerformed(ActionEvent evt)
                {                    
                    PutPrefs();
                    dispose();
                }
            }
        );
        
        _ok.setEnabled(false);

        buttons.add(_quit);
        buttons.add(_ok);
        
        license_panel.add(buttons, BorderLayout.SOUTH);

        // Build the main panel
        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());
        
        main_panel.add(tabbed_pane, BorderLayout.CENTER);
        main_panel.add(buttons, BorderLayout.SOUTH);
        
        getContentPane().add(main_panel);
       
        GetPrefs();
        VerifyCode();
        
        setResizable(false);
        setBounds((screenSize.width - WIDTH)/2, (screenSize.height - HEIGHT)/2, WIDTH, HEIGHT);
    }
 
}


