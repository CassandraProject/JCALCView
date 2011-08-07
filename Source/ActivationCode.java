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
//	File:	ActivationCode.java
//

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.util.*;
import javax.swing.text.*;
import java.text.*;

class ActivationCode
{    
    final private String _cypher1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    final private String _cypher2 = "JSW9KLXDEFGATUV012Z834HIYMQR67BCNOP5";
    final private String _date_format = "MMddyyHHmmss";
    
    private String DateToString (Date date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(_date_format);
            return dateFormat.format(date);
        }
        catch (Exception e) {
            throw new Exception_Invalid_Code();
        }

    }

    private Date StringToDate (String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(_date_format);
            return dateFormat.parse(date);
        }
        catch (ParseException e) {
            throw new Exception_Invalid_Code();
        }
        
    }
    
    private String Permute (String str, String src_key, String dest_key) {
        try {
            String result = new String();
            
            for (int i = 0; i < str.length(); ++i) {
                int index = src_key.indexOf(str.charAt(i));
                result = result + dest_key.charAt(index);
            }
            
            return result;
        }
        catch (Exception e) {
            throw new Exception_Invalid_Code();
        }
    }
    
    private String AddStrings (String a, String b) {
        try {
            String result = new String();
            
            for (int i = 0; i < a.length(); ++i) {
                int an = (int) _cypher1.indexOf(a.charAt(i));
                int bn = (int) _cypher1.indexOf(b.charAt(i));
                
                result = result + _cypher1.charAt((an + bn) % _cypher1.length());
            }
            
            return result;
        }
        catch (Exception e) {
            throw new Exception_Invalid_Code();
        }
    }

    private String SubStrings (String a, String b) {
        try {
            String result = new String();
            
            for (int i = 0; i < a.length(); ++i) {
                int an = (int) _cypher1.indexOf(a.charAt(i));
                int bn = (int) _cypher1.indexOf(b.charAt(i));
                
                int index = (an - bn) % _cypher1.length();
                if (index < 0) index = _cypher1.length() + index;
                
                result = result + _cypher1.charAt(index);
            }
            
            return result;
        }
        catch (Exception e) {
            throw new Exception_Invalid_Code();
        }
    }

    private String BuildName (String name) {
        try {
            String name_upper = name.toUpperCase();
            String working = new String();
        
            for (int i = 0; i < name_upper.length(); ++i) 
                if (_cypher1.indexOf(name_upper.charAt(i)) >= 0)
                    working = working + name_upper.charAt(i);
            
            if (working.length() <= 0)
                throw new Exception_Invalid_Code();
    
            while (working.length() < 12) 
                working = working + working;
                
            return working.substring(0,12);
        }
        catch (Exception e) {
            throw new Exception_Invalid_Code();
        }
    }
    
    private int Generate_Checksum (String s) {
        try {
            int checksum = 0;
        
            for (int i = 0; i < s.length() - 1; ++i) {
                int c1 = ((int) s.charAt(i)) & 0x000000FF;
                int c2 = ((int) s.charAt(i+1)) & 0x000000FF;
                
                checksum = checksum ^ (c1 | (c2 << 8));
            }
            
            return checksum;
        }
        catch (Exception e) {
            throw new Exception_Invalid_Code();
        }
    }
    
    public String Generate_Code (String name, Date expires) {
        try {
            String namestring = BuildName(name);
            if (namestring == null)
                throw new Exception_Invalid_Code();
    
            String datestring = DateToString (expires);
            
            String code_string = Permute (AddStrings (datestring, namestring), _cypher2, _cypher1);
    
            // Assemble checksums onto string
            int checksum = Generate_Checksum (code_string);
            char c1 = _cypher1.charAt((0x000000FF & checksum) % _cypher1.length());
            char c2 = _cypher1.charAt((0x000000FF & (checksum >> 8)) % _cypher1.length());
            
            code_string = c1 + code_string + c2;
            
            return code_string;
        }
        catch (Exception e) {
            throw new Exception_Invalid_Code();
        }
    }
    
    public Date Check_Code (String name, String code) {   
        try {
            String code_string = code.substring(1,code.length()-1);
            
            // Check Checksums
            char c1test = code.charAt(0);
            char c2test = code.charAt(code.length()-1);
        
            // Assemble checksums
            int checksum = Generate_Checksum (code_string);
            char c1 = _cypher1.charAt((0x000000FF & checksum) % _cypher1.length());
            char c2 = _cypher1.charAt((0x000000FF & (checksum >> 8)) % _cypher1.length());
    
            // See if checksums match
            if (c1 != c1test || c2 != c2test)
                throw new Exception_Invalid_Code();
            
            String namestring = BuildName(name);
            if (namestring == null)
                return new Date();
            
            code_string = Permute (code_string, _cypher1, _cypher2);
            
            String datestring = SubStrings (code_string, namestring);
                    
            return StringToDate (datestring);
        }
        catch (Exception e) {
            throw new Exception_Invalid_Code();
        }
    }
    
}
