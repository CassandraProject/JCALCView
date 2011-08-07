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
//  CALCViewActivation.java
//  CALCViewActivation
//
//  Created by Tod Baudais on Sat Apr 05 2003.
//  Copyright (c) 2003 __MyCompanyName__. All rights reserved.
//
//	For information on setting Java configuration information, including 
//	setting Java properties, refer to the documentation at
//		http://developer.apple.com/techpubs/java/java.html
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.*;
//import javax.swing.text.*;
//import java.text.*;


public class CALCViewActivation extends JFrame {
        JTextField _code = new JTextField("Code",20);
        JTextField _check = new JTextField("Check",20);
        JTextField _year_check = new JTextField("Year",20);
        JTextField _name = new JTextField("Name",20);
        
        JTextField _dd = new JTextField("1",3);
        JTextField _mm = new JTextField("1",3);
        JTextField _yy = new JTextField("2020",5);

        JButton	_generate = new JButton("Generate");
        JButton	_close = new JButton("Close");
	
	public CALCViewActivation() {
        
		JPanel mainpanel = new JPanel();
                mainpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                mainpanel.add(_name);

                mainpanel.add(new JLabel("DD/MM/YY :"));

                mainpanel.add(_dd);
                mainpanel.add(new JLabel("/"));
                mainpanel.add(_mm);
                mainpanel.add(new JLabel("/"));
                mainpanel.add(_yy);
                
                mainpanel.add(_generate);
                _generate.addActionListener( new
                    ActionListener()
                    {
                        public void actionPerformed(ActionEvent evt)
                        {
                            try {
                                Date today = new Date();
                                
                                today.setDate(Integer.parseInt(_dd.getText()));
                                today.setMonth(Integer.parseInt(_mm.getText()) - 1);
                                today.setYear(Integer.parseInt(_yy.getText()));
                                
                                ActivationCode ac = new ActivationCode();
                                _code.setText(ac.Generate_Code (_name.getText(), today));
                                
                                Date expires = ac.Check_Code(_name.getText(), _code.getText());
                                _check.setText(expires.toString());
                                
                                
                                GregorianCalendar gc = new GregorianCalendar();
                                gc.setTime(expires);
                    
                                _year_check.setText(new Integer(gc.get(Calendar.YEAR)).toString());

                            }
                            catch (Exception e) {
                            
                            }
                        }
                    }
                );


                mainpanel.add(_close);
                _close.addActionListener( new
                    ActionListener()
                    {
                        public void actionPerformed(ActionEvent evt)
                        {
                            System.exit(0);
                        }
                    }
                );
                
                mainpanel.add(_code);
                mainpanel.add(_check);
                mainpanel.add(_year_check);

		getContentPane().add(mainpanel);
                
		setSize(275, 300);
		setVisible(true);
	}


	
        public static void main(String args[]) {
            new CALCViewActivation();
        }

}
