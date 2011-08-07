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
//	File:	Prefs_File.java
//

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

class Prefs_File
{    
    private 	Properties	_pref_properties = new Properties();
    
    Prefs_File() {
        try {
            FileInputStream in = new FileInputStream(Constants.Get_String("PrefsFile"));
            _pref_properties.load(in);
        }
        catch (IOException e) {
            
        }
    }

    public void Write() {
        try {
            FileOutputStream out = new FileOutputStream(Constants.Get_String("PrefsFile"));
            _pref_properties.store(out,"CALCView Prefs");
        }
        catch (IOException e) {

        }
    }
    
    public String Get_Property_String (String property, String dflt) {
        return _pref_properties.getProperty(property, dflt);
    }

    public boolean Get_Property_Boolean (String property, boolean dflt) {
        return Boolean.valueOf(_pref_properties.getProperty(property, new Boolean(dflt).toString())).booleanValue();
    }

    public int Get_Property_Integer (String property, int dflt) {
        return Integer.parseInt(_pref_properties.getProperty(property, new Integer(dflt).toString()));
    }
    

    public void Set_Property_String (String property, String p) {
        _pref_properties.put(property, p);
    }

    public void Set_Property_Boolean (String property, boolean p) {
        _pref_properties.put(property, new Boolean(p).toString());
    }

    public void Set_Property_Integer (String property, int p) {
        _pref_properties.put(property, new Integer(p).toString());
    }
    
}
