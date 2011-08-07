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
//	File:	OmDriver.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.io.*;

class OmDriver
{

/**
    Use the ICC driver task to get the block data.
    @param comp_blk	Total compound block path.
*/        
    
    void Get_Registers (String compound, String block, Machine machine) {
        String param;            
                
        // Easiest just to go through all the parameters and see if they exist
        for (short mem_loc = Constants.TYPE; mem_loc < Constants.NUM_REGISTERS; ++mem_loc)
        {	
            Mem_Interface mem_loc_obj;
            
            try {
                param = machine.Index_To_Name_Memory (mem_loc);
                mem_loc_obj = machine.Index_To_Memory (mem_loc);
            }
            catch (Exception_Illegal_Symbol e) {
                // Expect this for parameters the block doesn't support
                continue;
            }
                
            if (mem_loc_obj.Can_Online() && mem_loc_obj.Get_Other_Status(Mem_Status.ONLINE_GET)) {
                // Start Omget
                try {
                    Process child = Runtime.getRuntime().exec(Constants.Get_Prefs().Get_Omget_Path() + 
                                        " " + compound + ":" + block + "." + param);
               
                    // Read the response
                    BufferedReader in = new BufferedReader(new InputStreamReader(child.getInputStream()));
                    String 	line = in.readLine();
                    String	value = line.substring(line.lastIndexOf(" ") + 1).trim();
                    
                    // Parse the response
                    if (line.indexOf("(b)") != -1)		mem_loc_obj.Set_Value(value.equals("TRUE") ? 1.0F : 0.0F);	
                    else if (line.indexOf("(f)") != -1)	mem_loc_obj.Set_Value(Float.parseFloat(value));	
                    else if (line.indexOf("(i)") != -1)	mem_loc_obj.Set_Value((float) Integer.parseInt(value));	
                    
                    // Ignore errors
                    BufferedReader inerr = new BufferedReader(new InputStreamReader(child.getErrorStream()));            
                    while ((line = inerr.readLine()) != null) {
                        // Ignore errors
                    }
                    inerr.close();
                }
                catch (Exception e) {
                    throw new Exception_OmError();
                }
            }
                

        } // for
            

    }
    
/**
    Use the ICC driver task to get the block data.
    @param comp_blk	Total compound block path.
*/        
    
    void Put_Registers (String compound, String block, Machine machine) {
        String param;
                
        // Easiest just to go through all the parameters and see if they exist
        for (short mem_loc = Constants.TYPE; mem_loc < Constants.NUM_REGISTERS; ++mem_loc)
        {	
            Mem_Interface mem_loc_obj;
            
            try {
                param = machine.Index_To_Name_Memory (mem_loc);
                mem_loc_obj = machine.Index_To_Memory (mem_loc);
            }
            catch (Exception_Illegal_Symbol e) {
                // Expect this for parameters the block doesn't support
                continue;
            }

                
            if (mem_loc_obj.Can_Online() && mem_loc_obj.Get_Other_Status(Mem_Status.ONLINE_PUT)) {
            
                try {
                    String cmd = "";
                
                    if (mem_loc_obj instanceof Mem_Boolean)
                        cmd = " -b " + (mem_loc_obj.Get_Value() != 0.0 ? "T" : "F") + " ";
                    else if (mem_loc_obj instanceof Mem_Float)
                        cmd = " -f " + mem_loc_obj.Get_Value() + " ";
                    else if (mem_loc_obj instanceof Mem_Int)
                        cmd = " -i " + (int) mem_loc_obj.Get_Value() + " ";
                    else 
                        System.out.println("Omset: Unknown parameter type");

                    //System.out.println(Constants.Get_Prefs().Get_Omset_Path() + cmd + compound + ":" + block + "." + param);
                    
                    // Start Omset
                    Process child = Runtime.getRuntime().exec(Constants.Get_Prefs().Get_Omset_Path() + cmd + compound + ":" + block + "." + param);
                    
                    String line;            
            
                    BufferedReader in = new BufferedReader(new InputStreamReader(child.getInputStream()));            
                    while ((line = in.readLine()) != null) {
                        // Ignore output
                    }
                    in.close();

                    BufferedReader inerr = new BufferedReader(new InputStreamReader(child.getErrorStream()));            
                    while ((line = inerr.readLine()) != null) {
                        // Ignore errors
                    }
                    inerr.close();
                    
                }
                catch (Exception e) {
                    throw new Exception_OmError();
                }

            }
                
                    
        } // for

    }

}





