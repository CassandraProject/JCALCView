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
//	File:	ICCDriver.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.io.*;

class ICCDriver
{
    public static int NONE = -1;
    public static int FILE = 0;
    public static int ADD = 1;
    public static int MODIFY = 2;

/**
    Try to extract a compound name.
    @param comp_blk	Total compound block path.
*/    

    String Parse_Compound (String comp_blk) {
        int separator = comp_blk.indexOf(":");
                
        return comp_blk.substring(0,separator);
    }

/**
    Try to extract a block name.
    @param comp_blk	Total compound block path.
*/    

    String Parse_Block (String comp_blk) {
        int separator = comp_blk.indexOf(":");
        
        return comp_blk.substring(separator+1);
    }
    
/**
    Use the ICC driver task to get the block data.
    @param comp_blk	Total compound block path.
*/        
    
    void Get_ICC_Dump (String cp, String compound, String block, Machine machine) {
        
        // Build the Query String
        String query = 	"OPEN " + cp + " READ calcview\n" +
                        "GET " + compound + ":" + block + " ALL\n" +
                        "CLOSE\n" +
                        "EXIT\n\n";
            
        try {        
            Process child = Runtime.getRuntime().exec(Constants.Get_Prefs().Get_ICC_Path());
        
            // Write the query
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(child.getOutputStream()));
            out.write(query);
            out.close();
            
            // Read the input
            Input_ICC_Stream (new InputStreamReader(child.getInputStream()), machine);

            // Read the errors
            Display_Errors (new InputStreamReader(child.getErrorStream()));
    
        }
        catch(Exception e) {
            throw new Exception_ICCError();
        }
        
    }

/**
    Use the ICC driver task to set the block data.
    @param comp_blk	Total compound block path.
*/        

    void Put_ICC_Dump (String cp, String compound, String block, Machine machine, int mode) {            
        // Fill out the Compound and block before uploading
        machine.Index_To_Memory(Constants.NAME).Set_Configuration(block);

        // Build the Query String
        String query_begin = 	"OPEN " + cp + " ALL calcview\n";

        String query_end = 	"CLOSE\n" +
                                "EXIT\n\n";

        try {        
            Process child = Runtime.getRuntime().exec(Constants.Get_Prefs().Get_ICC_Path());
        
            // Write the query
            OutputStreamWriter out = new OutputStreamWriter(child.getOutputStream());
            out.write(query_begin);

            // Read the input
            Output_ICC_Stream (compound, block, machine, out, mode);
    
            // Finish up the write
            out.write(query_end);
            
            out.close();

            // Read the errors
            Display_Errors (new InputStreamReader(child.getErrorStream()));
        }
        catch(Exception e) {
            throw new Exception_ICCError();
        }

    }
    
    
    void Display_Errors (InputStreamReader file) {        
        
        try {
            // ICC Reader
            BufferedReader in = new BufferedReader(file);
            String line;            
            
            ICCErrorFrame error_box = null;
            
            while ((line = in.readLine()) != null)
                if (Constants.Get_Prefs().All_ICC() || line.indexOf("FAIL") != -1 || line.indexOf("WARN") != -1) {
                    if (error_box == null)	
                        error_box = new ICCErrorFrame();
                    
                    error_box.Add_Error(line);
                }

            file.close();
        }
        catch (IOException e) {

        }
        

    }
   
/**
    Parse the ICC dump to fill out the steps.
    @param steps	Steps to set.
    @param registers	Registers to set.
    @param icc		The dump.
*/        
    
    void Input_ICC_Stream (InputStreamReader file, Machine machine) {
            
        try {
            // ICC Reader
            BufferedReader in = new BufferedReader(file);
            
            String line, param, config;
            int separator;
            
            
            while ((line = in.readLine()) != null)
            {
                // Windows Hack - so we don't get hung up.
                if (line.trim().equals("END"))
                    break;
            
                try {
                    separator = line.indexOf("=");
                    param = line.substring(0,separator).trim();
                    config = line.substring(separator+1).trim();
                        
                    int mem_loc = machine.Name_To_Index_Memory (param);
                    Mem_Interface mem = machine.Index_To_Memory(mem_loc);
                    
                    mem.Set_Configuration_Value(config);
                    mem.Compile(machine);
                    
                }
                catch (Exception e) {
        
                }
            }
            
            file.close();
        }
        catch (IOException e) {

        }
                
        machine.FireChanged();
    }

/**
    Parse the ICC dump to fill out the steps.
    @param steps	Steps to get.
    @param registers	Registers to get.
*/        

    void Output_ICC_Stream (String compound, String block, Machine machine, OutputStreamWriter out, int mode) 
    {
        Output_ICC_Stream_Range (compound, block, machine, out, mode, Constants.TYPE, Constants.NUM_REGISTERS-1);
    }

/**
    Parse the ICC dump to fill out the steps.
    @param steps	Steps to get.
    @param registers	Registers to get.
*/        

    void Output_ICC_Stream_Range (String compound, String block, Machine machine, OutputStreamWriter out, int mode, int firstreg, int lastreg) 
    {
        try {
        
            // ICC Writer
            String param, config;
            
            // Do NAME first
            param = machine.Index_To_Name_Memory (Constants.NAME);
            config = machine.Index_To_Memory(Constants.NAME).Get_Configuration();
            
            machine.Index_To_Memory(Constants.NAME).Set_Configuration(block);
            
            if (mode == FILE)
                out.write(param + " = " + compound + ":" + block + "\n");
            else if (mode == ADD) 
                out.write("ADD " + compound + ":" + block + "\n");
            else if (mode == MODIFY) 
                out.write("MODIFY " + compound + ":" + block + "\n");
            
                
            // Easiest just to go through all the parameters and see if they exist
            for (int mem_loc = firstreg; mem_loc <= lastreg; ++mem_loc)
            {
		// Ignore TYPE parameter on MODIFY
		if (mode == MODIFY && mem_loc == Constants.TYPE)
                    continue;
	
                try {
                    param = machine.Index_To_Name_Memory (mem_loc);
                    config = machine.Index_To_Memory(mem_loc).Get_Configuration();
                    
                    Mem_Interface mem_loc_obj = machine.Index_To_Memory (mem_loc);
                    
                    if (mem_loc_obj.Can_Config()) {
                        // Padd Param for attractive output
                        while (param.length() < 8)
                            param = param + " ";
                        
                        out.write("  " + param + "= " + config + "\n");
                    }
                    
                }
                catch (Exception_Illegal_Symbol e) {
                    // Expect this for parameters the block doesn't support
                }
                
            }
            
            // have to terminate with END
            out.write("END\n");
        }
        catch (IOException e) {
    
        }
        
    }
}

