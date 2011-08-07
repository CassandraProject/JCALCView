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
//	File:	Machine.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.util.*;
import java.lang.*;
import java.lang.Runtime;
import java.io.*;


class Machine extends Observable
{
    private	Map _map_string_to_index_memory = new TreeMap();
    private	Map _map_index_to_string_memory = new TreeMap();
    private	Map _map_index_to_memory = new TreeMap();
    
    public 	Map _map_string_to_index_instruction = new TreeMap();
    public 	Map _map_index_to_string_instruction = new TreeMap();
    public 	Map _map_index_to_instruction = new TreeMap();

    public 	Map _map_name_to_name = new TreeMap();

    public static int CALCA = 1;
    public static int CALC = 2;
    public static int LOGIC = 3;
    public static int MATH = 4;

    private	int	_type = 0;
    private	int	_num_steps = 0;
    private	int	_num_memory = 0;
/**
*/    
   
    Machine () {
        Set_Up_Memory_CALCA();
        Initialize();
        
        // Alternate names for registers
        _map_name_to_name.put("M1", "M01");
        _map_name_to_name.put("M2", "M02");
        _map_name_to_name.put("M3", "M03");
        _map_name_to_name.put("M4", "M04");
        _map_name_to_name.put("M5", "M05");
        _map_name_to_name.put("M6", "M06");
        _map_name_to_name.put("M7", "M07");
        _map_name_to_name.put("M8", "M08");
        _map_name_to_name.put("M9", "M09");

        _map_name_to_name.put("BI1", "BI01");
        _map_name_to_name.put("BI2", "BI02");
        _map_name_to_name.put("BI3", "BI03");
        _map_name_to_name.put("BI4", "BI04");
        _map_name_to_name.put("BI5", "BI05");
        _map_name_to_name.put("BI6", "BI06");
        _map_name_to_name.put("BI7", "BI07");
        _map_name_to_name.put("BI8", "BI08");
        _map_name_to_name.put("BI9", "BI09");

        _map_name_to_name.put("BO1", "BO01");
        _map_name_to_name.put("BO2", "BO02");
        _map_name_to_name.put("BO3", "BO03");
        _map_name_to_name.put("BO4", "BO04");
        _map_name_to_name.put("BO5", "BO05");
        _map_name_to_name.put("BO6", "BO06");
        _map_name_to_name.put("BO7", "BO07");
        _map_name_to_name.put("BO8", "BO08");

        _map_name_to_name.put("II1", "II01");
        _map_name_to_name.put("II2", "II02");

        _map_name_to_name.put("IO1", "IO01");
        _map_name_to_name.put("IO2", "IO02");
        _map_name_to_name.put("IO3", "IO03");
        _map_name_to_name.put("IO4", "IO04");
        _map_name_to_name.put("IO5", "IO05");
        _map_name_to_name.put("IO6", "IO06");

        _map_name_to_name.put("LI1", "LI01");
        _map_name_to_name.put("LI2", "LI02");

        _map_name_to_name.put("LO1", "LO01");
        _map_name_to_name.put("LO2", "LO02");

        _map_name_to_name.put("RI1", "RI01");
        _map_name_to_name.put("RI2", "RI02");
        _map_name_to_name.put("RI3", "RI03");
        _map_name_to_name.put("RI4", "RI04");
        _map_name_to_name.put("RI5", "RI05");
        _map_name_to_name.put("RI6", "RI06");
        _map_name_to_name.put("RI7", "RI07");
        _map_name_to_name.put("RI8", "RI08");

        _map_name_to_name.put("RO1", "RO01");
        _map_name_to_name.put("RO2", "RO02");
        _map_name_to_name.put("RO3", "RO03");
        _map_name_to_name.put("RO4", "RO04");

        _map_name_to_name.put("I1", "I01");
        _map_name_to_name.put("I2", "I02");
        _map_name_to_name.put("I3", "I03");
        _map_name_to_name.put("I4", "I04");
        _map_name_to_name.put("I5", "I05");
        _map_name_to_name.put("I6", "I06");
        _map_name_to_name.put("I7", "I07");
        _map_name_to_name.put("I8", "I08");
        _map_name_to_name.put("I9", "I09");

        _map_name_to_name.put("O1", "O01");
        _map_name_to_name.put("O2", "O02");
        _map_name_to_name.put("O3", "O03");
        _map_name_to_name.put("O4", "O04");
        _map_name_to_name.put("O5", "O05");
        _map_name_to_name.put("O6", "O06");
        _map_name_to_name.put("O7", "O07");
        _map_name_to_name.put("O8", "O08");
        _map_name_to_name.put("O9", "O09");

    }
    
/**
*/    
    
    public void Initialize () {
        try {
            Index_To_Memory(Constants.RNDSEED).Set_Value(100001.0F);	// error code
        }
        catch(Exception_Illegal_Symbol e) {
        
        }
    }
    
/**
*/    

    public void FireChanged () {
        setChanged();
        notifyObservers();
    }
    
/**
*/    
    
    public int Name_To_Index_Memory (String mem_name)
    {
        String real_name;
        
        // Check for aliases
        if (_map_name_to_name.containsKey(mem_name))
            real_name = (String) _map_name_to_name.get(mem_name);
        else
            real_name = mem_name;

        // Look up the register
        if (_map_string_to_index_memory.containsKey(real_name)) {
            return ((Integer) _map_string_to_index_memory.get(real_name)).intValue();
        } else {
            throw new Exception_Illegal_Symbol();
        }
    }

/**
*/    
    
    public Mem_Interface Index_To_Memory (int mem_index)
    {
        if (_map_index_to_memory.containsKey(new Integer(mem_index))) {
            return (Mem_Interface) _map_index_to_memory.get(new Integer(mem_index));
        } else {
            throw new Exception_Illegal_Symbol();
        }
    }

/**
*/    
    
    public String Index_To_Name_Memory (int mem_index)
    {
        if (_map_index_to_string_memory.containsKey(new Integer(mem_index))) {
            return (String) _map_index_to_string_memory.get(new Integer(mem_index));
        } else {
            throw new Exception_Illegal_Symbol();
        }
    }
    
/**
*/    
    
    public boolean Has_Memory (int mem_index)
    {
        return _map_index_to_string_memory.containsKey(new Integer(mem_index));
    }

    
/**
*/    
    
    public int Name_To_Index_Instruction (String mem_name)
    {
        if (_map_string_to_index_instruction.containsKey(mem_name)) {
            return ((Integer) _map_string_to_index_instruction.get(mem_name)).intValue();
        } else {
            throw new Exception_Illegal_Symbol();
        }
    }

/**
*/    
    
    public Instruction_Interface Index_To_Instruction (int mem_index)
    {
        if (_map_index_to_instruction.containsKey(new Integer(mem_index))) {
            return (Instruction_Interface) _map_index_to_instruction.get(new Integer(mem_index));
        } else {
            throw new Exception_Illegal_Symbol();
        }
    }

/**
*/    
    
    public String Index_To_Name_Instruction (int mem_index)
    {
        if (_map_index_to_string_instruction.containsKey(new Integer(mem_index))) {
            return (String) _map_index_to_string_instruction.get(new Integer(mem_index));
        } else {
            throw new Exception_Illegal_Symbol();
        }
    }
    
/**
*/    

    private void Add_Instruction (String mem_name, int mem_index, Instruction_Interface instruction)
    {
        if (!_map_string_to_index_instruction.containsKey(mem_name))
            _map_string_to_index_instruction.put(mem_name, new Integer(mem_index));
            
        if (!_map_index_to_instruction.containsKey(new Integer(mem_index)))
            _map_index_to_instruction.put(new Integer(mem_index), instruction);            

        if (!_map_index_to_string_instruction.containsKey(new Integer(mem_index)))
            _map_index_to_string_instruction.put(new Integer(mem_index), mem_name);            
    }

    private void Remove_Instruction (String mem_name, int mem_index)
    {
        if (_map_string_to_index_instruction.containsKey(mem_name))
            _map_string_to_index_instruction.remove(mem_name);
            
        if (_map_index_to_instruction.containsKey(new Integer(mem_index)))
            _map_index_to_instruction.remove(new Integer(mem_index));            

        if (_map_index_to_string_instruction.containsKey(new Integer(mem_index)))
            _map_index_to_string_instruction.remove(new Integer(mem_index));            
    }
    
/**
*/    

    private void Add_Memory_Location (String mem_name, int mem_index, Mem_Interface mem_location, String default_value)
    {
        mem_location.Set_Configuration_Value(default_value);

        if (!_map_string_to_index_memory.containsKey(mem_name))
            _map_string_to_index_memory.put(mem_name, new Integer(mem_index));
            
        if (!_map_index_to_memory.containsKey(new Integer(mem_index)))
            _map_index_to_memory.put(new Integer(mem_index), mem_location);            

        if (!_map_index_to_string_memory.containsKey(new Integer(mem_index)))
            _map_index_to_string_memory.put(new Integer(mem_index), mem_name);            
    }

/**
*/    

    private void Remove_Memory_Location (String mem_name, int mem_index)
    {
        if (_map_string_to_index_memory.containsKey(mem_name))
            _map_string_to_index_memory.remove(mem_name);
            
        if (_map_index_to_memory.containsKey(new Integer(mem_index)))
            _map_index_to_memory.remove(new Integer(mem_index));            

        if (_map_index_to_string_memory.containsKey(new Integer(mem_index)))
            _map_index_to_string_memory.remove(new Integer(mem_index));            
    }

/**
*/    

    public int Get_Num_Steps() {
        return _num_steps;
    }

/**
*/    

    public int Get_Num_Memory() {
        return _num_memory;
    }

/**
*/    

    public int Get_Type() {
        return _type;
    }

/**
*/    

    public void Set_Up_Memory_CALCA ()
    {
        _type = CALCA;
        
        Add_Instruction("NOP", Constants.NOP, new Instruction_NOP());
       
        Add_Instruction("ABS", Constants.ABS, new Instruction_ABS());
        Add_Instruction("ACOS", Constants.ACOS, new Instruction_ACOS());
        Add_Instruction("ADD", Constants.ADD, new Instruction_ADD());
        Add_Instruction("ALN", Constants.ALN, new Instruction_ALN());
        Add_Instruction("ALOG", Constants.ALOG, new Instruction_ALOG());
        Add_Instruction("ASIN", Constants.ASIN, new Instruction_ASIN());
        Add_Instruction("ATAN", Constants.ATAN, new Instruction_ATAN());
        Add_Instruction("AVE", Constants.AVE, new Instruction_AVE());
        Add_Instruction("CHS", Constants.CHS, new Instruction_CHS());
        Add_Instruction("COS", Constants.COS, new Instruction_COS());
        Add_Instruction("DEC", Constants.DEC, new Instruction_DEC());
        Add_Instruction("DIV", Constants.DIV, new Instruction_DIV());
        Add_Instruction("EXP", Constants.EXP, new Instruction_EXP());
        Add_Instruction("IDIV", Constants.IDIV, new Instruction_IDIV());
        Add_Instruction("IMOD", Constants.IMOD, new Instruction_IMOD());
        Add_Instruction("INC", Constants.INC, new Instruction_INC());
        Add_Instruction("LN", Constants.LN, new Instruction_LN());
        Add_Instruction("LOG", Constants.LOG, new Instruction_LOG());
        Add_Instruction("MAX", Constants.MAX, new Instruction_MAX());
        Add_Instruction("MAXO", Constants.MAXO, new Instruction_MAXO());
        Add_Instruction("MIN", Constants.MIN, new Instruction_MIN());
        Add_Instruction("MEDN", Constants.MEDN, new Instruction_MEDN());
        Add_Instruction("MUL", Constants.MUL, new Instruction_MUL());
        Add_Instruction("RAND", Constants.RAND, new Instruction_RAND());
        Add_Instruction("RANG", Constants.RANG, new Instruction_RANG());
        Add_Instruction("RND", Constants.RND, new Instruction_RND());
        Add_Instruction("SEED", Constants.SEED, new Instruction_SEED());
        Add_Instruction("SIN", Constants.SIN, new Instruction_SIN());
        Add_Instruction("SQR", Constants.SQR, new Instruction_SQR());
        Add_Instruction("SQRT", Constants.SQRT, new Instruction_SQRT());
        Add_Instruction("SUB", Constants.SUB, new Instruction_SUB());
        Add_Instruction("TAN", Constants.TAN, new Instruction_TAN());
        Add_Instruction("TRC", Constants.TRC, new Instruction_TRC());
    
        Add_Instruction("AND", Constants.AND, new Instruction_AND());
        Add_Instruction("ANDX", Constants.ANDX, new Instruction_ANDX());
        Add_Instruction("NAN", Constants.NAN, new Instruction_NAN());
        Add_Instruction("NANX", Constants.NANX, new Instruction_NANX());
        Add_Instruction("NAND", Constants.NAND, new Instruction_NAND());
        Add_Instruction("NOR", Constants.NOR, new Instruction_NOR());
        Add_Instruction("NORX", Constants.NORX, new Instruction_NORX());
        Add_Instruction("NOT", Constants.NOT, new Instruction_NOT());
        Add_Instruction("NOTX", Constants.NOTX, new Instruction_NOTX());
        Add_Instruction("NXO", Constants.NXO, new Instruction_NXO());
        Add_Instruction("NXOR", Constants.NXOR, new Instruction_NXOR());
        Add_Instruction("NXOX", Constants.NXOX, new Instruction_NXOX());
        Add_Instruction("OR", Constants.OR, new Instruction_OR());
        Add_Instruction("ORX", Constants.ORX, new Instruction_ORX());
        Add_Instruction("XOR", Constants.XOR, new Instruction_XOR());
        Add_Instruction("XORX", Constants.XORX, new Instruction_XORX());
    
        Add_Instruction("CBD", Constants.CBD, new Instruction_CBD());
        Add_Instruction("CE", Constants.CE, new Instruction_CE());
        Add_Instruction("COO", Constants.COO, new Instruction_COO());
        Add_Instruction("IN", Constants.IN, new Instruction_IN());
        Add_Instruction("INB", Constants.INB, new Instruction_INB());
        Add_Instruction("INH", Constants.INH, new Instruction_INH());
        Add_Instruction("INL", Constants.INL, new Instruction_INL());
        Add_Instruction("INR", Constants.INR, new Instruction_INR());
        Add_Instruction("INS", Constants.INS, new Instruction_INS());
        Add_Instruction("OUT", Constants.OUT, new Instruction_OUT());
        Add_Instruction("RBD", Constants.RBD, new Instruction_RBD());
        Add_Instruction("RCL", Constants.RCL, new Instruction_RCL());
        Add_Instruction("RCN", Constants.RCN, new Instruction_RCN());
        Add_Instruction("RE", Constants.RE, new Instruction_RE());
        Add_Instruction("REL", Constants.REL, new Instruction_REL());
        Add_Instruction("RON", Constants.RON, new Instruction_RON());
        Add_Instruction("ROO", Constants.ROO, new Instruction_ROO());
        Add_Instruction("RQE", Constants.RQE, new Instruction_RQE());
        Add_Instruction("RQL", Constants.RQL, new Instruction_RQL());
        Add_Instruction("SAC", Constants.SAC, new Instruction_SAC());
        Add_Instruction("SBD", Constants.SBD, new Instruction_SBD());
        Add_Instruction("SE", Constants.SE, new Instruction_SE());
        Add_Instruction("SEC", Constants.SEC, new Instruction_SEC());
        Add_Instruction("SOO", Constants.SOO, new Instruction_SOO());
        Add_Instruction("STH", Constants.STH, new Instruction_STH());
        Add_Instruction("STL", Constants.STL, new Instruction_STL());
        Add_Instruction("SWP", Constants.SWP, new Instruction_SWP());
    
        Add_Instruction("PRI", Constants.PRI, new Instruction_PRI());
        Add_Instruction("PRO", Constants.PRO, new Instruction_PRO());
        Add_Instruction("PRP", Constants.PRP, new Instruction_PRP());
    
        Add_Instruction("CLA", Constants.CLA, new Instruction_CLA());
        Add_Instruction("CLM", Constants.CLM, new Instruction_CLM());
        Add_Instruction("CST", Constants.CST, new Instruction_CST());
        Add_Instruction("DUP", Constants.DUP, new Instruction_DUP());
        Add_Instruction("LAC", Constants.LAC, new Instruction_LAC());
        Add_Instruction("LACI", Constants.LACI, new Instruction_LACI());
        Add_Instruction("POP", Constants.POP, new Instruction_POP());
        Add_Instruction("STM", Constants.STM, new Instruction_STM());
        Add_Instruction("STMI", Constants.STMI, new Instruction_STMI());
        Add_Instruction("TSTB", Constants.TSTB, new Instruction_TSTB());
    
        Add_Instruction("BIF", Constants.BIF, new Instruction_BIF());
        Add_Instruction("BII", Constants.BII, new Instruction_BII());
        Add_Instruction("BIN", Constants.BIN, new Instruction_BIN());
        Add_Instruction("BIP", Constants.BIP, new Instruction_BIP());
        Add_Instruction("BIT", Constants.BIT, new Instruction_BIT());
        Add_Instruction("BIZ", Constants.BIZ, new Instruction_BIZ());
        Add_Instruction("END", Constants.END, new Instruction_END());
        Add_Instruction("EXIT", Constants.EXIT, new Instruction_EXIT());
        Add_Instruction("GTI", Constants.GTI, new Instruction_GTI());
        Add_Instruction("GTO", Constants.GTO, new Instruction_GTO());
    
        Add_Instruction("CLL", Constants.CLL, new Instruction_CLL());
        Add_Instruction("CLR", Constants.CLR, new Instruction_CLR());
        Add_Instruction("CLRB", Constants.CLRB, new Instruction_CLRB());
        Add_Instruction("SET", Constants.SET, new Instruction_SET());
        Add_Instruction("SETB", Constants.SETB, new Instruction_SETB());
        Add_Instruction("SSF", Constants.SSF, new Instruction_SSF());
        Add_Instruction("SSI", Constants.SSI, new Instruction_SSI());
        Add_Instruction("SSN", Constants.SSN, new Instruction_SSN());
        Add_Instruction("SSP", Constants.SSP, new Instruction_SSP());
        Add_Instruction("SST", Constants.SST, new Instruction_SST());
        Add_Instruction("SSZ", Constants.SSZ, new Instruction_SSZ());
    
        Add_Instruction("CHI", Constants.CHI, new Instruction_CHI());
        Add_Instruction("CHN", Constants.CHN, new Instruction_CHN());
        Add_Instruction("DOFF", Constants.DOFF, new Instruction_DOFF());
        Add_Instruction("DON", Constants.DON, new Instruction_DON());
        Add_Instruction("OSP", Constants.OSP, new Instruction_OSP());
        Add_Instruction("TIM", Constants.TIM, new Instruction_TIM());
    
        Add_Instruction("FF", Constants.FF, new Instruction_FF());
        Add_Instruction("MRS", Constants.MRS, new Instruction_MRS());
    
        Add_Instruction("CLE", Constants.CLE, new Instruction_CLE());
        Add_Instruction("RER", Constants.RER, new Instruction_RER());
        Add_Instruction("SIEC", Constants.SIEC, new Instruction_SIEC()); 


        Add_Memory_Location("NAME", Constants.NAME, 	new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "");
        Add_Memory_Location("TYPE", Constants.TYPE, 	new Mem_Type(Mem_Status.CAN_CONFIG_VALUE), "");
        Add_Memory_Location("DESCRP", Constants.DESCRP, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "");
        Add_Memory_Location("PERIOD", Constants.PERIOD, new Mem_Int(Mem_Status.CAN_CONFIG_VALUE), "1");
        Add_Memory_Location("PHASE", Constants.PHASE,	new Mem_Int(Mem_Status.CAN_CONFIG_VALUE), "0");
        Add_Memory_Location("LOOPID", Constants.LOOPID, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "");

        Add_Memory_Location("BI01", Constants.BI01, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI02", Constants.BI02, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI03", Constants.BI03, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI04", Constants.BI04, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI05", Constants.BI05, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI06", Constants.BI06, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI07", Constants.BI07, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI08", Constants.BI08, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI09", Constants.BI09, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI10", Constants.BI10, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI11", Constants.BI11, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI12", Constants.BI12, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI13", Constants.BI13, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI14", Constants.BI14, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI15", Constants.BI15, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI16", Constants.BI16, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");

        Add_Memory_Location("RI01", Constants.RI01, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI02", Constants.RI02, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI03", Constants.RI03, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI04", Constants.RI04, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI05", Constants.RI05, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI06", Constants.RI06, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI07", Constants.RI07, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI08", Constants.RI08, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");

        Remove_Memory_Location("HSCI1", Constants.HSCI1);
        Remove_Memory_Location("HSCI2", Constants.HSCI2);
        Remove_Memory_Location("HSCI3", Constants.HSCI3);
        Remove_Memory_Location("HSCI4", Constants.HSCI4);
        Remove_Memory_Location("HSCI5", Constants.HSCI5);
        Remove_Memory_Location("HSCI6", Constants.HSCI6);
        Remove_Memory_Location("HSCI7", Constants.HSCI7);
        Remove_Memory_Location("HSCI8", Constants.HSCI8);

        Remove_Memory_Location("LSCI1", Constants.LSCI1);
        Remove_Memory_Location("LSCI2", Constants.LSCI2);
        Remove_Memory_Location("LSCI3", Constants.LSCI3);
        Remove_Memory_Location("LSCI4", Constants.LSCI4);
        Remove_Memory_Location("LSCI5", Constants.LSCI5);
        Remove_Memory_Location("LSCI6", Constants.LSCI6);
        Remove_Memory_Location("LSCI7", Constants.LSCI7);
        Remove_Memory_Location("LSCI8", Constants.LSCI8);

        Remove_Memory_Location("DELTI1", Constants.DELTI1);
        Remove_Memory_Location("DELTI2", Constants.DELTI2);
        Remove_Memory_Location("DELTI3", Constants.DELTI3);
        Remove_Memory_Location("DELTI4", Constants.DELTI4);
        Remove_Memory_Location("DELTI5", Constants.DELTI5);
        Remove_Memory_Location("DELTI6", Constants.DELTI6);
        Remove_Memory_Location("DELTI7", Constants.DELTI7);
        Remove_Memory_Location("DELTI8", Constants.DELTI8);

        Remove_Memory_Location("EI1", Constants.EI1);
        Remove_Memory_Location("EI2", Constants.EI2);
        Remove_Memory_Location("EI3", Constants.EI3);
        Remove_Memory_Location("EI4", Constants.EI4);
        Remove_Memory_Location("EI5", Constants.EI5);
        Remove_Memory_Location("EI6", Constants.EI6);
        Remove_Memory_Location("EI7", Constants.EI7);
        Remove_Memory_Location("EI8", Constants.EI8);


        Add_Memory_Location("II01", Constants.II01, new Mem_Int(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("II02", Constants.II02, new Mem_Int(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");

        Add_Memory_Location("LI01", Constants.LI01, new Mem_Int(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("LI02", Constants.LI02, new Mem_Int(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");

        Add_Memory_Location("BO01", Constants.BO01, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO02", Constants.BO02, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO03", Constants.BO03, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO04", Constants.BO04, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO05", Constants.BO05, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO06", Constants.BO06, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO07", Constants.BO07, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO08", Constants.BO08, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");

        Add_Memory_Location("RO01", Constants.RO01, new Mem_Float(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RO02", Constants.RO02, new Mem_Float(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RO03", Constants.RO03, new Mem_Float(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RO04", Constants.RO04, new Mem_Float(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");

        Remove_Memory_Location("HSCO1", Constants.HSCO1);
        Remove_Memory_Location("HSCO2", Constants.HSCO2);
        Remove_Memory_Location("HSCO3", Constants.HSCO3);
        Remove_Memory_Location("HSCO4", Constants.HSCO4);

        Remove_Memory_Location("LSCO1", Constants.LSCO1);
        Remove_Memory_Location("LSCO2", Constants.LSCO2);
        Remove_Memory_Location("LSCO3", Constants.LSCO3);
        Remove_Memory_Location("LSCO4", Constants.LSCO4);

        Remove_Memory_Location("EO1", Constants.EO1);
        Remove_Memory_Location("EO2", Constants.EO2);
        Remove_Memory_Location("EO3", Constants.EO3);
        Remove_Memory_Location("EO4", Constants.EO4);


        Add_Memory_Location("IO01", Constants.IO01, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("IO02", Constants.IO02, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("IO03", Constants.IO03, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("IO04", Constants.IO04, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("IO05", Constants.IO05, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("IO06", Constants.IO06, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");

        Add_Memory_Location("LO01", Constants.LO01, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("LO02", Constants.LO02, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");

        Add_Memory_Location("M01", Constants.M01, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M02", Constants.M02, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M03", Constants.M03, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M04", Constants.M04, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M05", Constants.M05, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M06", Constants.M06, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M07", Constants.M07, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M08", Constants.M08, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M09", Constants.M09, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M10", Constants.M10, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M11", Constants.M11, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M12", Constants.M12, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M13", Constants.M13, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M14", Constants.M14, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M15", Constants.M15, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M16", Constants.M16, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M17", Constants.M17, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M18", Constants.M18, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M19", Constants.M19, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M20", Constants.M20, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M21", Constants.M21, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M22", Constants.M22, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M23", Constants.M23, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M24", Constants.M24, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");    
        _num_memory = 24;
        
        Add_Memory_Location("MA", Constants.MA, new Mem_Boolean(Mem_Status.CAN_CONFIG_VALUE), "1");
        Add_Memory_Location("INITMA", Constants.INITMA, new Mem_Boolean(Mem_Status.CAN_CONFIG_VALUE), "1");
        Add_Memory_Location("TIMINI", Constants.TIMINI, new Mem_Boolean(Mem_Status.CAN_CONFIG_VALUE), "1");
    
        Add_Memory_Location("STEP01", Constants.STEP01, new Mem_Step(), "");
        Add_Memory_Location("STEP02", Constants.STEP02, new Mem_Step(), "");
        Add_Memory_Location("STEP03", Constants.STEP03, new Mem_Step(), "");
        Add_Memory_Location("STEP04", Constants.STEP04, new Mem_Step(), "");
        Add_Memory_Location("STEP05", Constants.STEP05, new Mem_Step(), "");
        Add_Memory_Location("STEP06", Constants.STEP06, new Mem_Step(), "");
        Add_Memory_Location("STEP07", Constants.STEP07, new Mem_Step(), "");
        Add_Memory_Location("STEP08", Constants.STEP08, new Mem_Step(), "");
        Add_Memory_Location("STEP09", Constants.STEP09, new Mem_Step(), "");
        Add_Memory_Location("STEP10", Constants.STEP10, new Mem_Step(), "");
        Add_Memory_Location("STEP11", Constants.STEP11, new Mem_Step(), "");
        Add_Memory_Location("STEP12", Constants.STEP12, new Mem_Step(), "");
        Add_Memory_Location("STEP13", Constants.STEP13, new Mem_Step(), "");
        Add_Memory_Location("STEP14", Constants.STEP14, new Mem_Step(), "");
        Add_Memory_Location("STEP15", Constants.STEP15, new Mem_Step(), "");
        Add_Memory_Location("STEP16", Constants.STEP16, new Mem_Step(), "");
        Add_Memory_Location("STEP17", Constants.STEP17, new Mem_Step(), "");
        Add_Memory_Location("STEP18", Constants.STEP18, new Mem_Step(), "");
        Add_Memory_Location("STEP19", Constants.STEP19, new Mem_Step(), "");
        Add_Memory_Location("STEP20", Constants.STEP20, new Mem_Step(), "");
        Add_Memory_Location("STEP21", Constants.STEP21, new Mem_Step(), "");
        Add_Memory_Location("STEP22", Constants.STEP22, new Mem_Step(), "");
        Add_Memory_Location("STEP23", Constants.STEP23, new Mem_Step(), "");
        Add_Memory_Location("STEP24", Constants.STEP24, new Mem_Step(), "");
        Add_Memory_Location("STEP25", Constants.STEP25, new Mem_Step(), "");
        Add_Memory_Location("STEP26", Constants.STEP26, new Mem_Step(), "");
        Add_Memory_Location("STEP27", Constants.STEP27, new Mem_Step(), "");
        Add_Memory_Location("STEP28", Constants.STEP28, new Mem_Step(), "");
        Add_Memory_Location("STEP29", Constants.STEP29, new Mem_Step(), "");
        Add_Memory_Location("STEP30", Constants.STEP30, new Mem_Step(), "");
        Add_Memory_Location("STEP31", Constants.STEP31, new Mem_Step(), "");
        Add_Memory_Location("STEP32", Constants.STEP32, new Mem_Step(), "");
        Add_Memory_Location("STEP33", Constants.STEP33, new Mem_Step(), "");
        Add_Memory_Location("STEP34", Constants.STEP34, new Mem_Step(), "");
        Add_Memory_Location("STEP35", Constants.STEP35, new Mem_Step(), "");
        Add_Memory_Location("STEP36", Constants.STEP36, new Mem_Step(), "");
        Add_Memory_Location("STEP37", Constants.STEP37, new Mem_Step(), "");
        Add_Memory_Location("STEP38", Constants.STEP38, new Mem_Step(), "");
        Add_Memory_Location("STEP39", Constants.STEP39, new Mem_Step(), "");
        Add_Memory_Location("STEP40", Constants.STEP40, new Mem_Step(), "");
        Add_Memory_Location("STEP41", Constants.STEP41, new Mem_Step(), "");
        Add_Memory_Location("STEP42", Constants.STEP42, new Mem_Step(), "");
        Add_Memory_Location("STEP43", Constants.STEP43, new Mem_Step(), "");
        Add_Memory_Location("STEP44", Constants.STEP44, new Mem_Step(), "");
        Add_Memory_Location("STEP45", Constants.STEP45, new Mem_Step(), "");
        Add_Memory_Location("STEP46", Constants.STEP46, new Mem_Step(), "");
        Add_Memory_Location("STEP47", Constants.STEP47, new Mem_Step(), "");
        Add_Memory_Location("STEP48", Constants.STEP48, new Mem_Step(), "");
        Add_Memory_Location("STEP49", Constants.STEP49, new Mem_Step(), "");
        Add_Memory_Location("STEP50", Constants.STEP50, new Mem_Step(), "");
        _num_steps = 50;
        
        // Pseudoparameters
        Add_Memory_Location("I01", Constants.I01, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 31) , "0"); 
        Add_Memory_Location("I02", Constants.I02, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 30) , "0"); 
        Add_Memory_Location("I03", Constants.I03, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 29) , "0"); 
        Add_Memory_Location("I04", Constants.I04, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 28) , "0"); 
        Add_Memory_Location("I05", Constants.I05, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 27) , "0"); 
        Add_Memory_Location("I06", Constants.I06, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 26) , "0"); 
        Add_Memory_Location("I07", Constants.I07, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 25) , "0"); 
        Add_Memory_Location("I08", Constants.I08, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 24) , "0"); 
        Add_Memory_Location("I09", Constants.I09, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 23) , "0"); 
        Add_Memory_Location("I10", Constants.I10, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 22) , "0"); 
        Add_Memory_Location("I11", Constants.I11, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 21) , "0"); 
        Add_Memory_Location("I12", Constants.I12, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 20) , "0"); 
        Add_Memory_Location("I13", Constants.I13, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 19) , "0"); 
        Add_Memory_Location("I14", Constants.I14, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 18) , "0"); 
        Add_Memory_Location("I15", Constants.I15, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 17) , "0"); 
        Add_Memory_Location("I16", Constants.I16, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 16) , "0"); 
        Add_Memory_Location("I17", Constants.I17, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 15) , "0"); 
        Add_Memory_Location("I18", Constants.I18, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 14) , "0"); 
        Add_Memory_Location("I19", Constants.I19, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 13) , "0"); 
        Add_Memory_Location("I20", Constants.I20, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 12) , "0"); 
        Add_Memory_Location("I21", Constants.I21, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 11) , "0"); 
        Add_Memory_Location("I22", Constants.I22, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 10) , "0"); 
        Add_Memory_Location("I23", Constants.I23, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 9) , "0"); 
        Add_Memory_Location("I24", Constants.I24, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 8) , "0"); 
        Add_Memory_Location("I25", Constants.I25, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 7) , "0"); 
        Add_Memory_Location("I26", Constants.I26, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 6) , "0"); 
        Add_Memory_Location("I27", Constants.I27, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 5) , "0"); 
        Add_Memory_Location("I28", Constants.I28, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 4) , "0"); 
        Add_Memory_Location("I29", Constants.I29, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 3) , "0"); 
        Add_Memory_Location("I30", Constants.I30, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 2) , "0"); 
        Add_Memory_Location("I31", Constants.I31, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 1) , "0"); 
        Add_Memory_Location("I32", Constants.I32, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 0) , "0"); 

        Add_Memory_Location("O01", Constants.O01, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 31) , "0"); 
        Add_Memory_Location("O02", Constants.O02, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 30) , "0"); 
        Add_Memory_Location("O03", Constants.O03, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 29) , "0"); 
        Add_Memory_Location("O04", Constants.O04, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 28) , "0"); 
        Add_Memory_Location("O05", Constants.O05, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 27) , "0"); 
        Add_Memory_Location("O06", Constants.O06, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 26) , "0"); 
        Add_Memory_Location("O07", Constants.O07, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 25) , "0"); 
        Add_Memory_Location("O08", Constants.O08, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 24) , "0"); 
        Add_Memory_Location("O09", Constants.O09, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 23) , "0"); 
        Add_Memory_Location("O10", Constants.O10, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 22) , "0"); 
        Add_Memory_Location("O11", Constants.O11, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 21) , "0"); 
        Add_Memory_Location("O12", Constants.O12, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 20) , "0"); 
        Add_Memory_Location("O13", Constants.O13, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 19) , "0"); 
        Add_Memory_Location("O14", Constants.O14, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 18) , "0"); 
        Add_Memory_Location("O15", Constants.O15, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 17) , "0"); 
        Add_Memory_Location("O16", Constants.O16, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 16) , "0"); 
        Add_Memory_Location("O17", Constants.O17, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 15) , "0"); 
        Add_Memory_Location("O18", Constants.O18, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 14) , "0"); 
        Add_Memory_Location("O19", Constants.O19, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 13) , "0"); 
        Add_Memory_Location("O20", Constants.O20, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 12) , "0"); 
        Add_Memory_Location("O21", Constants.O21, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 11) , "0"); 
        Add_Memory_Location("O22", Constants.O22, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 10) , "0"); 
        Add_Memory_Location("O23", Constants.O23, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 9) , "0"); 
        Add_Memory_Location("O24", Constants.O24, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 8) , "0"); 
        Add_Memory_Location("O25", Constants.O25, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 7) , "0"); 
        Add_Memory_Location("O26", Constants.O26, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 6) , "0"); 
        Add_Memory_Location("O27", Constants.O27, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 5) , "0"); 
        Add_Memory_Location("O28", Constants.O28, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 4) , "0"); 
        Add_Memory_Location("O29", Constants.O29, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 3) , "0"); 
        Add_Memory_Location("O30", Constants.O30, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 2) , "0"); 
        Add_Memory_Location("O31", Constants.O31, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 1) , "0"); 
        Add_Memory_Location("O32", Constants.O32, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 0) , "0"); 

        
        // Other registers not exposed
        Add_Memory_Location("RNDSEED", Constants.RNDSEED, new Mem_Int(0), "0"); 
        Add_Memory_Location("TIME", Constants.TIME, 	new Mem_Int(0), "0"); 
        Add_Memory_Location("PERROR", Constants.PERROR, new Mem_Int(0), "0"); 


        Index_To_Memory (Constants.TYPE).Set_Configuration("CALCA");
        FireChanged ();
    }

/**
*/    
    
    public void Set_Up_Memory_CALC() {
        _type = CALC;

        Add_Instruction("NOP", Constants.NOP, new Instruction_NOP());
       
        Add_Instruction("ABS", Constants.ABS, new Instruction_ABS());
        Add_Instruction("ACOS", Constants.ACOS, new Instruction_ACOS());
        Add_Instruction("ADD", Constants.ADD, new Instruction_ADD());
        Add_Instruction("ALN", Constants.ALN, new Instruction_ALN());
        Add_Instruction("ALOG", Constants.ALOG, new Instruction_ALOG());
        Add_Instruction("ASIN", Constants.ASIN, new Instruction_ASIN());
        Add_Instruction("ATAN", Constants.ATAN, new Instruction_ATAN());
        Add_Instruction("AVE", Constants.AVE, new Instruction_AVE());
        Add_Instruction("CHS", Constants.CHS, new Instruction_CHS());
        Add_Instruction("COS", Constants.COS, new Instruction_COS());
        Add_Instruction("DEC", Constants.DEC, new Instruction_DEC());
        Add_Instruction("DIV", Constants.DIV, new Instruction_DIV());
        Add_Instruction("EXP", Constants.EXP, new Instruction_EXP());
        Add_Instruction("IDIV", Constants.IDIV, new Instruction_IDIV());
        Add_Instruction("IMOD", Constants.IMOD, new Instruction_IMOD());
        Add_Instruction("INC", Constants.INC, new Instruction_INC());
        Add_Instruction("LN", Constants.LN, new Instruction_LN());
        Add_Instruction("LOG", Constants.LOG, new Instruction_LOG());
        Add_Instruction("MAX", Constants.MAX, new Instruction_MAX());
        Add_Instruction("MAXO", Constants.MAXO, new Instruction_MAXO());
        Add_Instruction("MIN", Constants.MIN, new Instruction_MIN());
        Add_Instruction("MEDN", Constants.MEDN, new Instruction_MEDN());
        Add_Instruction("MUL", Constants.MUL, new Instruction_MUL());
        Add_Instruction("RAND", Constants.RAND, new Instruction_RAND());
        Add_Instruction("RANG", Constants.RANG, new Instruction_RANG());
        Add_Instruction("RND", Constants.RND, new Instruction_RND());
        Add_Instruction("SEED", Constants.SEED, new Instruction_SEED());
        Add_Instruction("SIN", Constants.SIN, new Instruction_SIN());
        Add_Instruction("SQR", Constants.SQR, new Instruction_SQR());
        Add_Instruction("SQRT", Constants.SQRT, new Instruction_SQRT());
        Add_Instruction("SUB", Constants.SUB, new Instruction_SUB());
        Add_Instruction("TAN", Constants.TAN, new Instruction_TAN());
        Add_Instruction("TRC", Constants.TRC, new Instruction_TRC());
    
        Add_Instruction("AND", Constants.AND, new Instruction_AND());
        Add_Instruction("ANDX", Constants.ANDX, new Instruction_ANDX());
        Remove_Instruction("NAN", Constants.NAN);
        Add_Instruction("NANX", Constants.NANX, new Instruction_NANX());
        Add_Instruction("NAND", Constants.NAND, new Instruction_NAND());
        Add_Instruction("NOR", Constants.NOR, new Instruction_NOR());
        Add_Instruction("NORX", Constants.NORX, new Instruction_NORX());
        Add_Instruction("NOT", Constants.NOT, new Instruction_NOT());
        Add_Instruction("NOTX", Constants.NOTX, new Instruction_NOTX());
        Remove_Instruction("NXO", Constants.NXO);
        Add_Instruction("NXOR", Constants.NXOR, new Instruction_NXOR());
        Add_Instruction("NXOX", Constants.NXOX, new Instruction_NXOX());
        Add_Instruction("OR", Constants.OR, new Instruction_OR());
        Add_Instruction("ORX", Constants.ORX, new Instruction_ORX());
        Add_Instruction("XOR", Constants.XOR, new Instruction_XOR());
        Add_Instruction("XORX", Constants.XORX, new Instruction_XORX());
    
        Add_Instruction("CBD", Constants.CBD, new Instruction_CBD());
        Add_Instruction("CE", Constants.CE, new Instruction_CE());
        Add_Instruction("COO", Constants.COO, new Instruction_COO());
        Add_Instruction("IN", Constants.IN, new Instruction_IN());
        Add_Instruction("INB", Constants.INB, new Instruction_INB());
        Add_Instruction("INH", Constants.INH, new Instruction_INH());
        Add_Instruction("INL", Constants.INL, new Instruction_INL());
        Add_Instruction("INR", Constants.INR, new Instruction_INR());
        Add_Instruction("INS", Constants.INS, new Instruction_INS());
        Add_Instruction("OUT", Constants.OUT, new Instruction_OUT());
        Add_Instruction("RBD", Constants.RBD, new Instruction_RBD());
        Add_Instruction("RCL", Constants.RCL, new Instruction_RCL());
        Add_Instruction("RCN", Constants.RCN, new Instruction_RCN());
        Add_Instruction("RE", Constants.RE, new Instruction_RE());
        Add_Instruction("REL", Constants.REL, new Instruction_REL());
        Add_Instruction("RON", Constants.RON, new Instruction_RON());
        Add_Instruction("ROO", Constants.ROO, new Instruction_ROO());
        Remove_Instruction("RQE", Constants.RQE);
        Add_Instruction("RQL", Constants.RQL, new Instruction_RQL());
        Remove_Instruction("SAC", Constants.SAC);
        Add_Instruction("SBD", Constants.SBD, new Instruction_SBD());
        Add_Instruction("SE", Constants.SE, new Instruction_SE());
        Add_Instruction("SEC", Constants.SEC, new Instruction_SEC());
        Add_Instruction("SOO", Constants.SOO, new Instruction_SOO());
        Add_Instruction("STH", Constants.STH, new Instruction_STH());
        Add_Instruction("STL", Constants.STL, new Instruction_STL());
        Add_Instruction("SWP", Constants.SWP, new Instruction_SWP());
    
        Add_Instruction("PRI", Constants.PRI, new Instruction_PRI());
        Add_Instruction("PRO", Constants.PRO, new Instruction_PRO());
        Add_Instruction("PRP", Constants.PRP, new Instruction_PRP());
    
        Add_Instruction("CLA", Constants.CLA, new Instruction_CLA());
        Add_Instruction("CLM", Constants.CLM, new Instruction_CLM());
        Add_Instruction("CST", Constants.CST, new Instruction_CST());
        Add_Instruction("DUP", Constants.DUP, new Instruction_DUP());
        Add_Instruction("LAC", Constants.LAC, new Instruction_LAC());
        Add_Instruction("LACI", Constants.LACI, new Instruction_LACI());
        Add_Instruction("POP", Constants.POP, new Instruction_POP());
        Add_Instruction("STM", Constants.STM, new Instruction_STM());
        Add_Instruction("STMI", Constants.STMI, new Instruction_STMI());
        Remove_Instruction("TSTB", Constants.TSTB);
    
        Add_Instruction("BIF", Constants.BIF, new Instruction_BIF());
        Add_Instruction("BII", Constants.BII, new Instruction_BII());
        Add_Instruction("BIN", Constants.BIN, new Instruction_BIN());
        Add_Instruction("BIP", Constants.BIP, new Instruction_BIP());
        Add_Instruction("BIT", Constants.BIT, new Instruction_BIT());
        Add_Instruction("BIZ", Constants.BIZ, new Instruction_BIZ());
        Add_Instruction("END", Constants.END, new Instruction_END());
        Add_Instruction("EXIT", Constants.EXIT, new Instruction_EXIT());
        Add_Instruction("GTI", Constants.GTI, new Instruction_GTI());
        Add_Instruction("GTO", Constants.GTO, new Instruction_GTO());
    
        Remove_Instruction("CLL", Constants.CLL);
        Add_Instruction("CLR", Constants.CLR, new Instruction_CLR());
        Add_Instruction("CLRB", Constants.CLRB, new Instruction_CLRB());
        Add_Instruction("SET", Constants.SET, new Instruction_SET());
        Add_Instruction("SETB", Constants.SETB, new Instruction_SETB());
        Add_Instruction("SSF", Constants.SSF, new Instruction_SSF());
        Add_Instruction("SSI", Constants.SSI, new Instruction_SSI());
        Add_Instruction("SSN", Constants.SSN, new Instruction_SSN());
        Add_Instruction("SSP", Constants.SSP, new Instruction_SSP());
        Add_Instruction("SST", Constants.SST, new Instruction_SST());
        Add_Instruction("SSZ", Constants.SSZ, new Instruction_SSZ());
    
        Add_Instruction("CHI", Constants.CHI, new Instruction_CHI());
        Add_Instruction("CHN", Constants.CHN, new Instruction_CHN());
        Add_Instruction("DOFF", Constants.DOFF, new Instruction_DOFF());
        Add_Instruction("DON", Constants.DON, new Instruction_DON());
        Add_Instruction("OSP", Constants.OSP, new Instruction_OSP());
        Add_Instruction("TIM", Constants.TIM, new Instruction_TIM());
    
        Add_Instruction("FF", Constants.FF, new Instruction_FF());
        Add_Instruction("MRS", Constants.MRS, new Instruction_MRS());
    
        Add_Instruction("CLE", Constants.CLE, new Instruction_CLE());
        Add_Instruction("RER", Constants.RER, new Instruction_RER());
        Add_Instruction("SIEC", Constants.SIEC, new Instruction_SIEC());



        Add_Memory_Location("NAME", Constants.NAME, 	new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "");
        Add_Memory_Location("TYPE", Constants.TYPE, 	new Mem_Type(Mem_Status.CAN_CONFIG_VALUE), "");
        Add_Memory_Location("DESCRP", Constants.DESCRP, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "");
        Add_Memory_Location("PERIOD", Constants.PERIOD, new Mem_Int(Mem_Status.CAN_CONFIG_VALUE), "1");
        Add_Memory_Location("PHASE", Constants.PHASE,	new Mem_Int(Mem_Status.CAN_CONFIG_VALUE), "0");
        Add_Memory_Location("LOOPID", Constants.LOOPID, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "");

        Add_Memory_Location("BI01", Constants.BI01, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI02", Constants.BI02, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI03", Constants.BI03, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI04", Constants.BI04, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI05", Constants.BI05, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI06", Constants.BI06, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI07", Constants.BI07, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI08", Constants.BI08, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI09", Constants.BI09, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI10", Constants.BI10, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI11", Constants.BI11, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI12", Constants.BI12, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI13", Constants.BI13, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI14", Constants.BI14, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI15", Constants.BI15, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI16", Constants.BI16, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");

        Add_Memory_Location("RI01", Constants.RI01, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI02", Constants.RI02, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI03", Constants.RI03, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI04", Constants.RI04, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI05", Constants.RI05, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI06", Constants.RI06, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI07", Constants.RI07, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI08", Constants.RI08, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");

        Add_Memory_Location("HSCI1", Constants.HSCI1, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "100.0");
        Add_Memory_Location("HSCI2", Constants.HSCI2, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "100.0");
        Add_Memory_Location("HSCI3", Constants.HSCI3, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "100.0");
        Add_Memory_Location("HSCI4", Constants.HSCI4, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "100.0");
        Add_Memory_Location("HSCI5", Constants.HSCI5, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "100.0");
        Add_Memory_Location("HSCI6", Constants.HSCI6, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "100.0");
        Add_Memory_Location("HSCI7", Constants.HSCI7, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "100.0");
        Add_Memory_Location("HSCI8", Constants.HSCI8, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "100.0");

        Add_Memory_Location("LSCI1", Constants.LSCI1, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("LSCI2", Constants.LSCI2, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("LSCI3", Constants.LSCI3, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("LSCI4", Constants.LSCI4, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("LSCI5", Constants.LSCI5, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("LSCI6", Constants.LSCI6, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("LSCI7", Constants.LSCI7, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("LSCI8", Constants.LSCI8, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");

        Add_Memory_Location("DELTI1", Constants.DELTI1, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("DELTI2", Constants.DELTI2, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("DELTI3", Constants.DELTI3, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("DELTI4", Constants.DELTI4, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("DELTI5", Constants.DELTI5, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("DELTI6", Constants.DELTI6, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("DELTI7", Constants.DELTI7, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("DELTI8", Constants.DELTI8, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");

        Add_Memory_Location("EI1", Constants.EI1, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "%");
        Add_Memory_Location("EI2", Constants.EI2, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "%");
        Add_Memory_Location("EI3", Constants.EI3, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "%");
        Add_Memory_Location("EI4", Constants.EI4, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "%");
        Add_Memory_Location("EI5", Constants.EI5, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "%");
        Add_Memory_Location("EI6", Constants.EI6, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "%");
        Add_Memory_Location("EI7", Constants.EI7, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "%");
        Add_Memory_Location("EI8", Constants.EI8, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "%");


        Add_Memory_Location("II01", Constants.II01, new Mem_Int(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("II02", Constants.II02, new Mem_Int(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");

        Add_Memory_Location("LI01", Constants.LI01, new Mem_Int(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("LI02", Constants.LI02, new Mem_Int(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");

        Add_Memory_Location("BO01", Constants.BO01, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO02", Constants.BO02, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO03", Constants.BO03, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO04", Constants.BO04, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO05", Constants.BO05, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO06", Constants.BO06, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO07", Constants.BO07, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO08", Constants.BO08, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");

        Add_Memory_Location("RO01", Constants.RO01, new Mem_Float(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RO02", Constants.RO02, new Mem_Float(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RO03", Constants.RO03, new Mem_Float(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RO04", Constants.RO04, new Mem_Float(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");

        Add_Memory_Location("HSCO1", Constants.HSCO1, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "100.0");
        Add_Memory_Location("HSCO2", Constants.HSCO2, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "100.0");
        Add_Memory_Location("HSCO3", Constants.HSCO3, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "100.0");
        Add_Memory_Location("HSCO4", Constants.HSCO4, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "100.0");

        Add_Memory_Location("LSCO1", Constants.LSCO1, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("LSCO2", Constants.LSCO2, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("LSCO3", Constants.LSCO3, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");
        Add_Memory_Location("LSCO4", Constants.LSCO4, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE), "0.0");

        Add_Memory_Location("EO1", Constants.EO1, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "%");
        Add_Memory_Location("EO2", Constants.EO2, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "%");
        Add_Memory_Location("EO3", Constants.EO3, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "%");
        Add_Memory_Location("EO4", Constants.EO4, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "%");


        Add_Memory_Location("IO01", Constants.IO01, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("IO02", Constants.IO02, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("IO03", Constants.IO03, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("IO04", Constants.IO04, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("IO05", Constants.IO05, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("IO06", Constants.IO06, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");

        Add_Memory_Location("LO01", Constants.LO01, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("LO02", Constants.LO02, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");

        Add_Memory_Location("M01", Constants.M01, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M02", Constants.M02, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M03", Constants.M03, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M04", Constants.M04, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M05", Constants.M05, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M06", Constants.M06, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M07", Constants.M07, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M08", Constants.M08, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M09", Constants.M09, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M10", Constants.M10, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M11", Constants.M11, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M12", Constants.M12, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M13", Constants.M13, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M14", Constants.M14, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M15", Constants.M15, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M16", Constants.M16, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M17", Constants.M17, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M18", Constants.M18, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M19", Constants.M19, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M20", Constants.M20, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M21", Constants.M21, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M22", Constants.M22, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M23", Constants.M23, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M24", Constants.M24, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");    
        _num_memory = 24;

        Add_Memory_Location("MA", Constants.MA, new Mem_Boolean(Mem_Status.CAN_CONFIG_VALUE), "1");
        Add_Memory_Location("INITMA", Constants.INITMA, new Mem_Boolean(Mem_Status.CAN_CONFIG_VALUE), "1");
        Add_Memory_Location("TIMINI", Constants.TIMINI, new Mem_Boolean(Mem_Status.CAN_CONFIG_VALUE), "1");
    
        Add_Memory_Location("STEP01", Constants.STEP01, new Mem_Step(), "");
        Add_Memory_Location("STEP02", Constants.STEP02, new Mem_Step(), "");
        Add_Memory_Location("STEP03", Constants.STEP03, new Mem_Step(), "");
        Add_Memory_Location("STEP04", Constants.STEP04, new Mem_Step(), "");
        Add_Memory_Location("STEP05", Constants.STEP05, new Mem_Step(), "");
        Add_Memory_Location("STEP06", Constants.STEP06, new Mem_Step(), "");
        Add_Memory_Location("STEP07", Constants.STEP07, new Mem_Step(), "");
        Add_Memory_Location("STEP08", Constants.STEP08, new Mem_Step(), "");
        Add_Memory_Location("STEP09", Constants.STEP09, new Mem_Step(), "");
        Add_Memory_Location("STEP10", Constants.STEP10, new Mem_Step(), "");
        Add_Memory_Location("STEP11", Constants.STEP11, new Mem_Step(), "");
        Add_Memory_Location("STEP12", Constants.STEP12, new Mem_Step(), "");
        Add_Memory_Location("STEP13", Constants.STEP13, new Mem_Step(), "");
        Add_Memory_Location("STEP14", Constants.STEP14, new Mem_Step(), "");
        Add_Memory_Location("STEP15", Constants.STEP15, new Mem_Step(), "");
        Add_Memory_Location("STEP16", Constants.STEP16, new Mem_Step(), "");
        Add_Memory_Location("STEP17", Constants.STEP17, new Mem_Step(), "");
        Add_Memory_Location("STEP18", Constants.STEP18, new Mem_Step(), "");
        Add_Memory_Location("STEP19", Constants.STEP19, new Mem_Step(), "");
        Add_Memory_Location("STEP20", Constants.STEP20, new Mem_Step(), "");
        Add_Memory_Location("STEP21", Constants.STEP21, new Mem_Step(), "");
        Add_Memory_Location("STEP22", Constants.STEP22, new Mem_Step(), "");
        Add_Memory_Location("STEP23", Constants.STEP23, new Mem_Step(), "");
        Add_Memory_Location("STEP24", Constants.STEP24, new Mem_Step(), "");
        Add_Memory_Location("STEP25", Constants.STEP25, new Mem_Step(), "");
        Add_Memory_Location("STEP26", Constants.STEP26, new Mem_Step(), "");
        Add_Memory_Location("STEP27", Constants.STEP27, new Mem_Step(), "");
        Add_Memory_Location("STEP28", Constants.STEP28, new Mem_Step(), "");
        Add_Memory_Location("STEP29", Constants.STEP29, new Mem_Step(), "");
        Add_Memory_Location("STEP30", Constants.STEP30, new Mem_Step(), "");
        Add_Memory_Location("STEP31", Constants.STEP31, new Mem_Step(), "");
        Add_Memory_Location("STEP32", Constants.STEP32, new Mem_Step(), "");
        Add_Memory_Location("STEP33", Constants.STEP33, new Mem_Step(), "");
        Add_Memory_Location("STEP34", Constants.STEP34, new Mem_Step(), "");
        Add_Memory_Location("STEP35", Constants.STEP35, new Mem_Step(), "");
        Add_Memory_Location("STEP36", Constants.STEP36, new Mem_Step(), "");
        Add_Memory_Location("STEP37", Constants.STEP37, new Mem_Step(), "");
        Add_Memory_Location("STEP38", Constants.STEP38, new Mem_Step(), "");
        Add_Memory_Location("STEP39", Constants.STEP39, new Mem_Step(), "");
        Add_Memory_Location("STEP40", Constants.STEP40, new Mem_Step(), "");
        Add_Memory_Location("STEP41", Constants.STEP41, new Mem_Step(), "");
        Add_Memory_Location("STEP42", Constants.STEP42, new Mem_Step(), "");
        Add_Memory_Location("STEP43", Constants.STEP43, new Mem_Step(), "");
        Add_Memory_Location("STEP44", Constants.STEP44, new Mem_Step(), "");
        Add_Memory_Location("STEP45", Constants.STEP45, new Mem_Step(), "");
        Add_Memory_Location("STEP46", Constants.STEP46, new Mem_Step(), "");
        Add_Memory_Location("STEP47", Constants.STEP47, new Mem_Step(), "");
        Add_Memory_Location("STEP48", Constants.STEP48, new Mem_Step(), "");
        Add_Memory_Location("STEP49", Constants.STEP49, new Mem_Step(), "");
        Add_Memory_Location("STEP50", Constants.STEP50, new Mem_Step(), "");
        _num_steps = 50;

        // Pseudoparameters
        Add_Memory_Location("I01", Constants.I01, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 31) , "0"); 
        Add_Memory_Location("I02", Constants.I02, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 30) , "0"); 
        Add_Memory_Location("I03", Constants.I03, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 29) , "0"); 
        Add_Memory_Location("I04", Constants.I04, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 28) , "0"); 
        Add_Memory_Location("I05", Constants.I05, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 27) , "0"); 
        Add_Memory_Location("I06", Constants.I06, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 26) , "0"); 
        Add_Memory_Location("I07", Constants.I07, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 25) , "0"); 
        Add_Memory_Location("I08", Constants.I08, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 24) , "0"); 
        Add_Memory_Location("I09", Constants.I09, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 23) , "0"); 
        Add_Memory_Location("I10", Constants.I10, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 22) , "0"); 
        Add_Memory_Location("I11", Constants.I11, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 21) , "0"); 
        Add_Memory_Location("I12", Constants.I12, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 20) , "0"); 
        Add_Memory_Location("I13", Constants.I13, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 19) , "0"); 
        Add_Memory_Location("I14", Constants.I14, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 18) , "0"); 
        Add_Memory_Location("I15", Constants.I15, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 17) , "0"); 
        Add_Memory_Location("I16", Constants.I16, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 16) , "0"); 
        Add_Memory_Location("I17", Constants.I17, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 15) , "0"); 
        Add_Memory_Location("I18", Constants.I18, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 14) , "0"); 
        Add_Memory_Location("I19", Constants.I19, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 13) , "0"); 
        Add_Memory_Location("I20", Constants.I20, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 12) , "0"); 
        Add_Memory_Location("I21", Constants.I21, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 11) , "0"); 
        Add_Memory_Location("I22", Constants.I22, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 10) , "0"); 
        Add_Memory_Location("I23", Constants.I23, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 9) , "0"); 
        Add_Memory_Location("I24", Constants.I24, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 8) , "0"); 
        Add_Memory_Location("I25", Constants.I25, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 7) , "0"); 
        Add_Memory_Location("I26", Constants.I26, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 6) , "0"); 
        Add_Memory_Location("I27", Constants.I27, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 5) , "0"); 
        Add_Memory_Location("I28", Constants.I28, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 4) , "0"); 
        Add_Memory_Location("I29", Constants.I29, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 3) , "0"); 
        Add_Memory_Location("I30", Constants.I30, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 2) , "0"); 
        Add_Memory_Location("I31", Constants.I31, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 1) , "0"); 
        Add_Memory_Location("I32", Constants.I32, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 0) , "0"); 

        Add_Memory_Location("O01", Constants.O01, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 31) , "0"); 
        Add_Memory_Location("O02", Constants.O02, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 30) , "0"); 
        Add_Memory_Location("O03", Constants.O03, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 29) , "0"); 
        Add_Memory_Location("O04", Constants.O04, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 28) , "0"); 
        Add_Memory_Location("O05", Constants.O05, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 27) , "0"); 
        Add_Memory_Location("O06", Constants.O06, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 26) , "0"); 
        Add_Memory_Location("O07", Constants.O07, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 25) , "0"); 
        Add_Memory_Location("O08", Constants.O08, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 24) , "0"); 
        Add_Memory_Location("O09", Constants.O09, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 23) , "0"); 
        Add_Memory_Location("O10", Constants.O10, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 22) , "0"); 
        Add_Memory_Location("O11", Constants.O11, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 21) , "0"); 
        Add_Memory_Location("O12", Constants.O12, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 20) , "0"); 
        Add_Memory_Location("O13", Constants.O13, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 19) , "0"); 
        Add_Memory_Location("O14", Constants.O14, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 18) , "0"); 
        Add_Memory_Location("O15", Constants.O15, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 17) , "0"); 
        Add_Memory_Location("O16", Constants.O16, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 16) , "0"); 
        Add_Memory_Location("O17", Constants.O17, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 15) , "0"); 
        Add_Memory_Location("O18", Constants.O18, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 14) , "0"); 
        Add_Memory_Location("O19", Constants.O19, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 13) , "0"); 
        Add_Memory_Location("O20", Constants.O20, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 12) , "0"); 
        Add_Memory_Location("O21", Constants.O21, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 11) , "0"); 
        Add_Memory_Location("O22", Constants.O22, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 10) , "0"); 
        Add_Memory_Location("O23", Constants.O23, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 9) , "0"); 
        Add_Memory_Location("O24", Constants.O24, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 8) , "0"); 
        Add_Memory_Location("O25", Constants.O25, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 7) , "0"); 
        Add_Memory_Location("O26", Constants.O26, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 6) , "0"); 
        Add_Memory_Location("O27", Constants.O27, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 5) , "0"); 
        Add_Memory_Location("O28", Constants.O28, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 4) , "0"); 
        Add_Memory_Location("O29", Constants.O29, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 3) , "0"); 
        Add_Memory_Location("O30", Constants.O30, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 2) , "0"); 
        Add_Memory_Location("O31", Constants.O31, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 1) , "0"); 
        Add_Memory_Location("O32", Constants.O32, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 0) , "0"); 
        
        // Other registers not exposed
        Add_Memory_Location("RNDSEED", Constants.RNDSEED, new Mem_Int(0), "0"); 
        Add_Memory_Location("TIME", Constants.TIME, 	new Mem_Int(0), "0"); 
        Add_Memory_Location("PERROR", Constants.PERROR, new Mem_Int(0), "0"); 


        Index_To_Memory (Constants.TYPE).Set_Configuration("CALC");
        FireChanged ();
    }

/**
*/    
    
    public void Set_Up_Memory_MATH() {
        _type = MATH;

        Add_Instruction("NOP", Constants.NOP, new Instruction_NOP());
       
        Add_Instruction("ABS", Constants.ABS, new Instruction_ABS());
        Add_Instruction("ACOS", Constants.ACOS, new Instruction_ACOS());
        Add_Instruction("ADD", Constants.ADD, new Instruction_ADD());
        Add_Instruction("ALN", Constants.ALN, new Instruction_ALN());
        Add_Instruction("ALOG", Constants.ALOG, new Instruction_ALOG());
        Add_Instruction("ASIN", Constants.ASIN, new Instruction_ASIN());
        Add_Instruction("ATAN", Constants.ATAN, new Instruction_ATAN());
        Add_Instruction("AVE", Constants.AVE, new Instruction_AVE());
        Add_Instruction("CHS", Constants.CHS, new Instruction_CHS());
        Add_Instruction("COS", Constants.COS, new Instruction_COS());
        Add_Instruction("DEC", Constants.DEC, new Instruction_DEC());
        Add_Instruction("DIV", Constants.DIV, new Instruction_DIV());
        Add_Instruction("EXP", Constants.EXP, new Instruction_EXP());
        Remove_Instruction("IDIV", Constants.IDIV);
        Remove_Instruction("IMOD", Constants.IMOD);
        Add_Instruction("INC", Constants.INC, new Instruction_INC());
        Add_Instruction("LN", Constants.LN, new Instruction_LN());
        Add_Instruction("LOG", Constants.LOG, new Instruction_LOG());
        Add_Instruction("MAX", Constants.MAX, new Instruction_MAX());
        Remove_Instruction("MAXO", Constants.MAXO);
        Add_Instruction("MIN", Constants.MIN, new Instruction_MIN());
        Add_Instruction("MEDN", Constants.MEDN, new Instruction_MEDN());
        Add_Instruction("MUL", Constants.MUL, new Instruction_MUL());
        Remove_Instruction("RAND", Constants.RAND);
        Remove_Instruction("RANG", Constants.RANG);
        Remove_Instruction("RND", Constants.RND);
        Remove_Instruction("SEED", Constants.SEED);
        Add_Instruction("SIN", Constants.SIN, new Instruction_SIN());
        Add_Instruction("SQR", Constants.SQR, new Instruction_SQR());
        Add_Instruction("SQRT", Constants.SQRT, new Instruction_SQRT());
        Add_Instruction("SUB", Constants.SUB, new Instruction_SUB());
        Add_Instruction("TAN", Constants.TAN, new Instruction_TAN());
        Remove_Instruction("TRC", Constants.TRC);
    
        Remove_Instruction("AND", Constants.AND);
        Remove_Instruction("ANDX", Constants.ANDX);
        Remove_Instruction("NAN", Constants.NAN);
        Remove_Instruction("NANX", Constants.NANX);
        Remove_Instruction("NAND", Constants.NAND);
        Remove_Instruction("NOR", Constants.NOR);
        Remove_Instruction("NORX", Constants.NORX);
        Remove_Instruction("NOT", Constants.NOT);
        Remove_Instruction("NOTX", Constants.NOTX);
        Remove_Instruction("NXO", Constants.NXO);
        Remove_Instruction("NXOR", Constants.NXOR);
        Remove_Instruction("NXOX", Constants.NXOX);
        Remove_Instruction("OR", Constants.OR);
        Remove_Instruction("ORX", Constants.ORX);
        Remove_Instruction("XOR", Constants.XOR);
        Remove_Instruction("XORX", Constants.XORX);
    
        Add_Instruction("CBD", Constants.CBD, new Instruction_CBD());
        Remove_Instruction("CE", Constants.CE);
        Remove_Instruction("COO", Constants.COO);
        Add_Instruction("IN", Constants.IN, new Instruction_IN());
        Remove_Instruction("INB", Constants.INB);
        Remove_Instruction("INH", Constants.INH);
        Remove_Instruction("INL", Constants.INL);
        Add_Instruction("INR", Constants.INR, new Instruction_INR());
        Add_Instruction("INS", Constants.INS, new Instruction_INS());
        Add_Instruction("OUT", Constants.OUT, new Instruction_OUT());
        Add_Instruction("RBD", Constants.RBD, new Instruction_RBD());
        Add_Instruction("RCL", Constants.RCL, new Instruction_RCL());
        Remove_Instruction("RCN", Constants.RCN);
        Remove_Instruction("RE", Constants.RE);
        Remove_Instruction("REL", Constants.REL);
        Remove_Instruction("RON", Constants.RON);
        Remove_Instruction("ROO", Constants.ROO);
        Add_Instruction("RQE", Constants.RQE, new Instruction_RQE());
        Add_Instruction("RQL", Constants.RQL, new Instruction_RQL());
        Remove_Instruction("SAC", Constants.SAC);
        Add_Instruction("SBD", Constants.SBD, new Instruction_SBD());
        Remove_Instruction("SE", Constants.SE);
        Add_Instruction("SEC", Constants.SEC, new Instruction_SEC());
        Remove_Instruction("SOO", Constants.SOO);
        Remove_Instruction("STH", Constants.STH);
        Remove_Instruction("STL", Constants.STL);
        Add_Instruction("SWP", Constants.SWP, new Instruction_SWP());
    
        Remove_Instruction("PRI", Constants.PRI);
        Add_Instruction("PRO", Constants.PRO, new Instruction_PRO());
        Remove_Instruction("PRP", Constants.PRP);
    
        Add_Instruction("CLA", Constants.CLA, new Instruction_CLA());
        Add_Instruction("CLM", Constants.CLM, new Instruction_CLM());
        Add_Instruction("CST", Constants.CST, new Instruction_CST());
        Add_Instruction("DUP", Constants.DUP, new Instruction_DUP());
        Remove_Instruction("LAC", Constants.LAC);
        Add_Instruction("LACI", Constants.LACI, new Instruction_LACI());
        Add_Instruction("POP", Constants.POP, new Instruction_POP());
        Add_Instruction("STM", Constants.STM, new Instruction_STM());
        Add_Instruction("STMI", Constants.STMI, new Instruction_STMI());
        Remove_Instruction("TSTB", Constants.TSTB);
    
        Remove_Instruction("BIF", Constants.BIF);
        Add_Instruction("BII", Constants.BII, new Instruction_BII());
        Add_Instruction("BIN", Constants.BIN, new Instruction_BIN());
        Add_Instruction("BIP", Constants.BIP, new Instruction_BIP());
        Remove_Instruction("BIT", Constants.BIT);
        Add_Instruction("BIZ", Constants.BIZ, new Instruction_BIZ());
        Add_Instruction("END", Constants.END, new Instruction_END());
        Add_Instruction("EXIT", Constants.EXIT, new Instruction_EXIT());
        Add_Instruction("GTI", Constants.GTI, new Instruction_GTI());
        Add_Instruction("GTO", Constants.GTO, new Instruction_GTO());
    
        Remove_Instruction("CLL", Constants.CLL);
        Add_Instruction("CLR", Constants.CLR, new Instruction_CLR());
        Remove_Instruction("CLRB", Constants.CLRB);
        Add_Instruction("SET", Constants.SET, new Instruction_SET());
        Remove_Instruction("SETB", Constants.SETB);
        Remove_Instruction("SSF", Constants.SSF);
        Add_Instruction("SSI", Constants.SSI, new Instruction_SSI());
        Add_Instruction("SSN", Constants.SSN, new Instruction_SSN());
        Add_Instruction("SSP", Constants.SSP, new Instruction_SSP());
        Remove_Instruction("SST", Constants.SST);
        Add_Instruction("SSZ", Constants.SSZ, new Instruction_SSZ());
    
        Remove_Instruction("CHI", Constants.CHI);
        Remove_Instruction("CHN", Constants.CHN);
        Remove_Instruction("DOFF", Constants.DOFF);
        Remove_Instruction("DON", Constants.DON);
        Remove_Instruction("OSP", Constants.OSP);
        Remove_Instruction("TIM", Constants.TIM);
    
        Remove_Instruction("FF", Constants.FF);
        Remove_Instruction("MRS", Constants.MRS);
    
        Remove_Instruction("CLE", Constants.CLE);
        Remove_Instruction("RER", Constants.RER);
        Remove_Instruction("SIEC", Constants.SIEC);    



        Add_Memory_Location("NAME", Constants.NAME, 	new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "");
        Add_Memory_Location("TYPE", Constants.TYPE, 	new Mem_Type(Mem_Status.CAN_CONFIG_VALUE), "");
        Add_Memory_Location("DESCRP", Constants.DESCRP, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "");
        Add_Memory_Location("PERIOD", Constants.PERIOD, new Mem_Int(Mem_Status.CAN_CONFIG_VALUE), "1");
        Add_Memory_Location("PHASE", Constants.PHASE,	new Mem_Int(Mem_Status.CAN_CONFIG_VALUE), "0");
        Add_Memory_Location("LOOPID", Constants.LOOPID, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "");

        Remove_Memory_Location("BI01", Constants.BI01);
        Remove_Memory_Location("BI02", Constants.BI02);
        Remove_Memory_Location("BI03", Constants.BI03);
        Remove_Memory_Location("BI04", Constants.BI04);
        Remove_Memory_Location("BI05", Constants.BI05);
        Remove_Memory_Location("BI06", Constants.BI06);
        Remove_Memory_Location("BI07", Constants.BI07);
        Remove_Memory_Location("BI08", Constants.BI08);
        Remove_Memory_Location("BI09", Constants.BI09);
        Remove_Memory_Location("BI10", Constants.BI10);
        Remove_Memory_Location("BI11", Constants.BI11);
        Remove_Memory_Location("BI12", Constants.BI12);
        Remove_Memory_Location("BI13", Constants.BI13);
        Remove_Memory_Location("BI14", Constants.BI14);
        Remove_Memory_Location("BI15", Constants.BI15);
        Remove_Memory_Location("BI16", Constants.BI16);

        Add_Memory_Location("RI01", Constants.RI01, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI02", Constants.RI02, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI03", Constants.RI03, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI04", Constants.RI04, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI05", Constants.RI05, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI06", Constants.RI06, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI07", Constants.RI07, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI08", Constants.RI08, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");

        Remove_Memory_Location("HSCI1", Constants.HSCI1);
        Remove_Memory_Location("HSCI2", Constants.HSCI2);
        Remove_Memory_Location("HSCI3", Constants.HSCI3);
        Remove_Memory_Location("HSCI4", Constants.HSCI4);
        Remove_Memory_Location("HSCI5", Constants.HSCI5);
        Remove_Memory_Location("HSCI6", Constants.HSCI6);
        Remove_Memory_Location("HSCI7", Constants.HSCI7);
        Remove_Memory_Location("HSCI8", Constants.HSCI8);

        Remove_Memory_Location("LSCI1", Constants.LSCI1);
        Remove_Memory_Location("LSCI2", Constants.LSCI2);
        Remove_Memory_Location("LSCI3", Constants.LSCI3);
        Remove_Memory_Location("LSCI4", Constants.LSCI4);
        Remove_Memory_Location("LSCI5", Constants.LSCI5);
        Remove_Memory_Location("LSCI6", Constants.LSCI6);
        Remove_Memory_Location("LSCI7", Constants.LSCI7);
        Remove_Memory_Location("LSCI8", Constants.LSCI8);

        Remove_Memory_Location("DELTI1", Constants.DELTI1);
        Remove_Memory_Location("DELTI2", Constants.DELTI2);
        Remove_Memory_Location("DELTI3", Constants.DELTI3);
        Remove_Memory_Location("DELTI4", Constants.DELTI4);
        Remove_Memory_Location("DELTI5", Constants.DELTI5);
        Remove_Memory_Location("DELTI6", Constants.DELTI6);
        Remove_Memory_Location("DELTI7", Constants.DELTI7);
        Remove_Memory_Location("DELTI8", Constants.DELTI8);

        Remove_Memory_Location("EI1", Constants.EI1);
        Remove_Memory_Location("EI2", Constants.EI2);
        Remove_Memory_Location("EI3", Constants.EI3);
        Remove_Memory_Location("EI4", Constants.EI4);
        Remove_Memory_Location("EI5", Constants.EI5);
        Remove_Memory_Location("EI6", Constants.EI6);
        Remove_Memory_Location("EI7", Constants.EI7);
        Remove_Memory_Location("EI8", Constants.EI8);


        Remove_Memory_Location("II01", Constants.II01);
        Remove_Memory_Location("II02", Constants.II02);

        Remove_Memory_Location("LI01", Constants.LI01);
        Remove_Memory_Location("LI02", Constants.LI02);

        Remove_Memory_Location("BO01", Constants.BO01);
        Remove_Memory_Location("BO02", Constants.BO02);
        Remove_Memory_Location("BO03", Constants.BO03);
        Remove_Memory_Location("BO04", Constants.BO04);
        Remove_Memory_Location("BO05", Constants.BO05);
        Remove_Memory_Location("BO06", Constants.BO06);
        Remove_Memory_Location("BO07", Constants.BO07);
        Remove_Memory_Location("BO08", Constants.BO08);

        Add_Memory_Location("RO01", Constants.RO01, new Mem_Float(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RO02", Constants.RO02, new Mem_Float(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RO03", Constants.RO03, new Mem_Float(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RO04", Constants.RO04, new Mem_Float(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");

        Remove_Memory_Location("HSCO1", Constants.HSCO1);
        Remove_Memory_Location("HSCO2", Constants.HSCO2);
        Remove_Memory_Location("HSCO3", Constants.HSCO3);
        Remove_Memory_Location("HSCO4", Constants.HSCO4);

        Remove_Memory_Location("LSCO1", Constants.LSCO1);
        Remove_Memory_Location("LSCO2", Constants.LSCO2);
        Remove_Memory_Location("LSCO3", Constants.LSCO3);
        Remove_Memory_Location("LSCO4", Constants.LSCO4);

        Remove_Memory_Location("EO1", Constants.EO1);
        Remove_Memory_Location("EO2", Constants.EO2);
        Remove_Memory_Location("EO3", Constants.EO3);
        Remove_Memory_Location("EO4", Constants.EO4);


        Remove_Memory_Location("IO01", Constants.IO01);
        Remove_Memory_Location("IO02", Constants.IO02);
        Remove_Memory_Location("IO03", Constants.IO03);
        Remove_Memory_Location("IO04", Constants.IO04);
        Remove_Memory_Location("IO05", Constants.IO05);
        Remove_Memory_Location("IO06", Constants.IO06);

        Remove_Memory_Location("LO01", Constants.LO01);
        Remove_Memory_Location("LO02", Constants.LO02);

        Add_Memory_Location("M01", Constants.M01, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M02", Constants.M02, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M03", Constants.M03, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M04", Constants.M04, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M05", Constants.M05, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Remove_Memory_Location("M06", Constants.M06);
        Remove_Memory_Location("M07", Constants.M07);
        Remove_Memory_Location("M08", Constants.M08);
        Remove_Memory_Location("M09", Constants.M09);
        Remove_Memory_Location("M10", Constants.M10);
        Remove_Memory_Location("M11", Constants.M11);
        Remove_Memory_Location("M12", Constants.M12);
        Remove_Memory_Location("M13", Constants.M13);
        Remove_Memory_Location("M14", Constants.M14);
        Remove_Memory_Location("M15", Constants.M15);
        Remove_Memory_Location("M16", Constants.M16);
        Remove_Memory_Location("M17", Constants.M17);
        Remove_Memory_Location("M18", Constants.M18);
        Remove_Memory_Location("M19", Constants.M19);
        Remove_Memory_Location("M20", Constants.M20);
        Remove_Memory_Location("M21", Constants.M21);
        Remove_Memory_Location("M22", Constants.M22);
        Remove_Memory_Location("M23", Constants.M23);
        Remove_Memory_Location("M24", Constants.M24);    
        _num_memory = 5;

        Add_Memory_Location("MA", Constants.MA, new Mem_Boolean(Mem_Status.CAN_CONFIG_VALUE), "1");
        Add_Memory_Location("INITMA", Constants.INITMA, new Mem_Boolean(Mem_Status.CAN_CONFIG_VALUE), "1");
        Remove_Memory_Location("TIMINI", Constants.TIMINI);
    
        Add_Memory_Location("STEP01", Constants.STEP01, new Mem_Step(), "");
        Add_Memory_Location("STEP02", Constants.STEP02, new Mem_Step(), "");
        Add_Memory_Location("STEP03", Constants.STEP03, new Mem_Step(), "");
        Add_Memory_Location("STEP04", Constants.STEP04, new Mem_Step(), "");
        Add_Memory_Location("STEP05", Constants.STEP05, new Mem_Step(), "");
        Add_Memory_Location("STEP06", Constants.STEP06, new Mem_Step(), "");
        Add_Memory_Location("STEP07", Constants.STEP07, new Mem_Step(), "");
        Add_Memory_Location("STEP08", Constants.STEP08, new Mem_Step(), "");
        Add_Memory_Location("STEP09", Constants.STEP09, new Mem_Step(), "");
        Add_Memory_Location("STEP10", Constants.STEP10, new Mem_Step(), "");
        Add_Memory_Location("STEP11", Constants.STEP11, new Mem_Step(), "");
        Add_Memory_Location("STEP12", Constants.STEP12, new Mem_Step(), "");
        Add_Memory_Location("STEP13", Constants.STEP13, new Mem_Step(), "");
        Add_Memory_Location("STEP14", Constants.STEP14, new Mem_Step(), "");
        Add_Memory_Location("STEP15", Constants.STEP15, new Mem_Step(), "");
        Add_Memory_Location("STEP16", Constants.STEP16, new Mem_Step(), "");
        Add_Memory_Location("STEP17", Constants.STEP17, new Mem_Step(), "");
        Add_Memory_Location("STEP18", Constants.STEP18, new Mem_Step(), "");
        Add_Memory_Location("STEP19", Constants.STEP19, new Mem_Step(), "");
        Add_Memory_Location("STEP20", Constants.STEP20, new Mem_Step(), "");
        Remove_Memory_Location("STEP21", Constants.STEP21);
        Remove_Memory_Location("STEP22", Constants.STEP22);
        Remove_Memory_Location("STEP23", Constants.STEP23);
        Remove_Memory_Location("STEP24", Constants.STEP24);
        Remove_Memory_Location("STEP25", Constants.STEP25);
        Remove_Memory_Location("STEP26", Constants.STEP26);
        Remove_Memory_Location("STEP27", Constants.STEP27);
        Remove_Memory_Location("STEP28", Constants.STEP28);
        Remove_Memory_Location("STEP29", Constants.STEP29);
        Remove_Memory_Location("STEP30", Constants.STEP30);
        Remove_Memory_Location("STEP31", Constants.STEP31);
        Remove_Memory_Location("STEP32", Constants.STEP32);
        Remove_Memory_Location("STEP33", Constants.STEP33);
        Remove_Memory_Location("STEP34", Constants.STEP34);
        Remove_Memory_Location("STEP35", Constants.STEP35);
        Remove_Memory_Location("STEP36", Constants.STEP36);
        Remove_Memory_Location("STEP37", Constants.STEP37);
        Remove_Memory_Location("STEP38", Constants.STEP38);
        Remove_Memory_Location("STEP39", Constants.STEP39);
        Remove_Memory_Location("STEP40", Constants.STEP40);
        Remove_Memory_Location("STEP41", Constants.STEP41);
        Remove_Memory_Location("STEP42", Constants.STEP42);
        Remove_Memory_Location("STEP43", Constants.STEP43);
        Remove_Memory_Location("STEP44", Constants.STEP44);
        Remove_Memory_Location("STEP45", Constants.STEP45);
        Remove_Memory_Location("STEP46", Constants.STEP46);
        Remove_Memory_Location("STEP47", Constants.STEP47);
        Remove_Memory_Location("STEP48", Constants.STEP48);
        Remove_Memory_Location("STEP49", Constants.STEP49);
        Remove_Memory_Location("STEP50", Constants.STEP50);
        _num_steps = 20;
        
        // Pseudoparameters
        Remove_Memory_Location("I01", Constants.I01); 
        Remove_Memory_Location("I02", Constants.I02); 
        Remove_Memory_Location("I03", Constants.I03); 
        Remove_Memory_Location("I04", Constants.I04); 
        Remove_Memory_Location("I05", Constants.I05); 
        Remove_Memory_Location("I06", Constants.I06); 
        Remove_Memory_Location("I07", Constants.I07); 
        Remove_Memory_Location("I08", Constants.I08); 
        Remove_Memory_Location("I09", Constants.I09); 
        Remove_Memory_Location("I10", Constants.I10); 
        Remove_Memory_Location("I11", Constants.I11); 
        Remove_Memory_Location("I12", Constants.I12); 
        Remove_Memory_Location("I13", Constants.I13); 
        Remove_Memory_Location("I14", Constants.I14); 
        Remove_Memory_Location("I15", Constants.I15); 
        Remove_Memory_Location("I16", Constants.I16); 
        Remove_Memory_Location("I17", Constants.I17); 
        Remove_Memory_Location("I18", Constants.I18); 
        Remove_Memory_Location("I19", Constants.I19); 
        Remove_Memory_Location("I20", Constants.I20); 
        Remove_Memory_Location("I21", Constants.I21); 
        Remove_Memory_Location("I22", Constants.I22); 
        Remove_Memory_Location("I23", Constants.I23); 
        Remove_Memory_Location("I24", Constants.I24); 
        Remove_Memory_Location("I25", Constants.I25); 
        Remove_Memory_Location("I26", Constants.I26); 
        Remove_Memory_Location("I27", Constants.I27); 
        Remove_Memory_Location("I28", Constants.I28); 
        Remove_Memory_Location("I29", Constants.I29); 
        Remove_Memory_Location("I30", Constants.I30); 
        Remove_Memory_Location("I31", Constants.I31); 
        Remove_Memory_Location("I32", Constants.I32); 

        Remove_Memory_Location("O01", Constants.O01); 
        Remove_Memory_Location("O02", Constants.O02); 
        Remove_Memory_Location("O03", Constants.O03); 
        Remove_Memory_Location("O04", Constants.O04); 
        Remove_Memory_Location("O05", Constants.O05); 
        Remove_Memory_Location("O06", Constants.O06); 
        Remove_Memory_Location("O07", Constants.O07); 
        Remove_Memory_Location("O08", Constants.O08); 
        Remove_Memory_Location("O09", Constants.O09); 
        Remove_Memory_Location("O10", Constants.O10); 
        Remove_Memory_Location("O11", Constants.O11); 
        Remove_Memory_Location("O12", Constants.O12); 
        Remove_Memory_Location("O13", Constants.O13); 
        Remove_Memory_Location("O14", Constants.O14); 
        Remove_Memory_Location("O15", Constants.O15); 
        Remove_Memory_Location("O16", Constants.O16); 
        Remove_Memory_Location("O17", Constants.O17); 
        Remove_Memory_Location("O18", Constants.O18); 
        Remove_Memory_Location("O19", Constants.O19); 
        Remove_Memory_Location("O20", Constants.O20); 
        Remove_Memory_Location("O21", Constants.O21); 
        Remove_Memory_Location("O22", Constants.O22); 
        Remove_Memory_Location("O23", Constants.O23); 
        Remove_Memory_Location("O24", Constants.O24); 
        Remove_Memory_Location("O25", Constants.O25); 
        Remove_Memory_Location("O26", Constants.O26); 
        Remove_Memory_Location("O27", Constants.O27); 
        Remove_Memory_Location("O28", Constants.O28); 
        Remove_Memory_Location("O29", Constants.O29); 
        Remove_Memory_Location("O30", Constants.O30); 
        Remove_Memory_Location("O31", Constants.O31); 
        Remove_Memory_Location("O32", Constants.O32); 

        // Other registers not exposed
        Remove_Memory_Location("RNDSEED", Constants.RNDSEED); 
        Remove_Memory_Location("TIME", Constants.TIME); 
        Add_Memory_Location("PERROR", Constants.PERROR, new Mem_Int(0), "0"); 
    

        Index_To_Memory (Constants.TYPE).Set_Configuration("MATH");
        FireChanged ();
    }

/**
*/    
    
    public void Set_Up_Memory_LOGIC() {
        _type = LOGIC;

        Add_Instruction("NOP", Constants.NOP, new Instruction_NOP());
       
        Remove_Instruction("ABS", Constants.ABS);
        Remove_Instruction("ACOS", Constants.ACOS);
        Remove_Instruction("ADD", Constants.ADD);
        Remove_Instruction("ALN", Constants.ALN);
        Remove_Instruction("ALOG", Constants.ALOG);
        Remove_Instruction("ASIN", Constants.ASIN);
        Remove_Instruction("ATAN", Constants.ATAN);
        Remove_Instruction("AVE", Constants.AVE);
        Remove_Instruction("CHS", Constants.CHS);
        Remove_Instruction("COS", Constants.COS);
        Remove_Instruction("DEC", Constants.DEC);
        Remove_Instruction("DIV", Constants.DIV);
        Remove_Instruction("EXP", Constants.EXP);
        Remove_Instruction("IDIV", Constants.IDIV);
        Remove_Instruction("IMOD", Constants.IMOD);
        Remove_Instruction("INC", Constants.INC);
        Remove_Instruction("LN", Constants.LN);
        Remove_Instruction("LOG", Constants.LOG);
        Remove_Instruction("MAX", Constants.MAX);
        Remove_Instruction("MAXO", Constants.MAXO);
        Remove_Instruction("MIN", Constants.MIN);
        Remove_Instruction("MEDN", Constants.MEDN);
        Remove_Instruction("MUL", Constants.MUL);
        Remove_Instruction("RAND", Constants.RAND);
        Remove_Instruction("RANG", Constants.RANG);
        Remove_Instruction("RND", Constants.RND);
        Remove_Instruction("SEED", Constants.SEED);
        Remove_Instruction("SIN", Constants.SIN);
        Remove_Instruction("SQR", Constants.SQR);
        Remove_Instruction("SQRT", Constants.SQRT);
        Remove_Instruction("SUB", Constants.SUB);
        Remove_Instruction("TAN", Constants.TAN);
        Remove_Instruction("TRC", Constants.TRC);
    
        Add_Instruction("AND", Constants.AND, new Instruction_AND());
        Remove_Instruction("ANDX", Constants.ANDX);
        Remove_Instruction("NAN", Constants.NAN);
        Remove_Instruction("NANX", Constants.NANX);
        Add_Instruction("NAND", Constants.NAND, new Instruction_NAND());
        Add_Instruction("NOR", Constants.NOR, new Instruction_NOR());
        Remove_Instruction("NORX", Constants.NORX);
        Add_Instruction("NOT", Constants.NOT, new Instruction_NOT());
        Remove_Instruction("NOTX", Constants.NOTX);
        Remove_Instruction("NXO", Constants.NXO);
        Add_Instruction("NXOR", Constants.NXOR, new Instruction_NXOR());
        Remove_Instruction("NXOX", Constants.NXOX);
        Add_Instruction("OR", Constants.OR, new Instruction_OR());
        Remove_Instruction("ORX", Constants.ORX);
        Add_Instruction("XOR", Constants.XOR, new Instruction_XOR());
        Remove_Instruction("XORX", Constants.XORX);
    
        Add_Instruction("CBD", Constants.CBD, new Instruction_CBD());
        Remove_Instruction("CE", Constants.CE);
        Remove_Instruction("COO", Constants.COO);
        Add_Instruction("IN", Constants.IN, new Instruction_IN());
        Add_Instruction("INB", Constants.INB, new Instruction_INB());
        Remove_Instruction("INH", Constants.INH);
        Remove_Instruction("INL", Constants.INL);
        Remove_Instruction("INR", Constants.INR);
        Remove_Instruction("INS", Constants.INS);
        Add_Instruction("OUT", Constants.OUT, new Instruction_OUT());
        Add_Instruction("RBD", Constants.RBD, new Instruction_RBD());
        Add_Instruction("RCL", Constants.RCL, new Instruction_RCL());
        Remove_Instruction("RCN", Constants.RCN);
        Remove_Instruction("RE", Constants.RE);
        Add_Instruction("REL", Constants.REL, new Instruction_REL());
        Remove_Instruction("RON", Constants.RON);
        Remove_Instruction("ROO", Constants.ROO);
        Add_Instruction("RQE", Constants.RQE, new Instruction_RQE());
        Add_Instruction("RQL", Constants.RQL, new Instruction_RQL());
        Remove_Instruction("SAC", Constants.SAC);
        Add_Instruction("SBD", Constants.SBD, new Instruction_SBD());
        Remove_Instruction("SE", Constants.SE);
        Add_Instruction("SEC", Constants.SEC, new Instruction_SEC());
        Remove_Instruction("SOO", Constants.SOO);
        Remove_Instruction("STH", Constants.STH);
        Remove_Instruction("STL", Constants.STL);
        Add_Instruction("SWP", Constants.SWP, new Instruction_SWP());
    
        Remove_Instruction("PRI", Constants.PRI);
        Remove_Instruction("PRO", Constants.PRO);
        Remove_Instruction("PRP", Constants.PRP);
    
        Add_Instruction("CLA", Constants.CLA, new Instruction_CLA());
        Add_Instruction("CLM", Constants.CLM, new Instruction_CLM());
        Add_Instruction("CST", Constants.CST, new Instruction_CST());
        Add_Instruction("DUP", Constants.DUP, new Instruction_DUP());
        Remove_Instruction("LAC", Constants.LAC);
        Remove_Instruction("LACI", Constants.LACI);
        Add_Instruction("POP", Constants.POP, new Instruction_POP());
        Add_Instruction("STM", Constants.STM, new Instruction_STM());
        Remove_Instruction("STMI", Constants.STMI);
        Remove_Instruction("TSTB", Constants.TSTB);
    
        Add_Instruction("BIF", Constants.BIF, new Instruction_BIF());
        Add_Instruction("BII", Constants.BII, new Instruction_BII());
        Remove_Instruction("BIN", Constants.BIN);
        Remove_Instruction("BIP", Constants.BIP);
        Add_Instruction("BIT", Constants.BIT, new Instruction_BIT());
        Remove_Instruction("BIZ", Constants.BIZ);
        Add_Instruction("END", Constants.END, new Instruction_END());
        Add_Instruction("EXIT", Constants.EXIT, new Instruction_EXIT());
        Add_Instruction("GTI", Constants.GTI, new Instruction_GTI());
        Add_Instruction("GTO", Constants.GTO, new Instruction_GTO());
    
        Remove_Instruction("CLL", Constants.CLL);
        Add_Instruction("CLR", Constants.CLR, new Instruction_CLR());
        Remove_Instruction("CLRB", Constants.CLRB);
        Add_Instruction("SET", Constants.SET, new Instruction_SET());
        Remove_Instruction("SETB", Constants.SETB);
        Add_Instruction("SSF", Constants.SSF, new Instruction_SSF());
        Add_Instruction("SSI", Constants.SSI, new Instruction_SSI());
        Remove_Instruction("SSN", Constants.SSN);
        Remove_Instruction("SSP", Constants.SSP);
        Add_Instruction("SST", Constants.SST, new Instruction_SST());
        Remove_Instruction("SSZ", Constants.SSZ);
    
        Remove_Instruction("CHI", Constants.CHI);
        Remove_Instruction("CHN", Constants.CHN);
        Add_Instruction("DOFF", Constants.DOFF, new Instruction_DOFF());
        Add_Instruction("DON", Constants.DON, new Instruction_DON());
        Add_Instruction("OSP", Constants.OSP, new Instruction_OSP());
        Remove_Instruction("TIM", Constants.TIM);
    
        Add_Instruction("FF", Constants.FF, new Instruction_FF());
        Add_Instruction("MRS", Constants.MRS, new Instruction_MRS());
    
        Remove_Instruction("CLE", Constants.CLE);
        Remove_Instruction("RER", Constants.RER);
        Remove_Instruction("SIEC", Constants.SIEC); 



        Add_Memory_Location("NAME", Constants.NAME, 	new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "");
        Add_Memory_Location("TYPE", Constants.TYPE, 	new Mem_Type(Mem_Status.CAN_CONFIG_VALUE), "");
        Add_Memory_Location("DESCRP", Constants.DESCRP, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "");
        Add_Memory_Location("PERIOD", Constants.PERIOD, new Mem_Int(Mem_Status.CAN_CONFIG_VALUE), "1");
        Add_Memory_Location("PHASE", Constants.PHASE,	new Mem_Int(Mem_Status.CAN_CONFIG_VALUE), "0");
        Add_Memory_Location("LOOPID", Constants.LOOPID, new Mem_String(Mem_Status.CAN_CONFIG_VALUE), "");

        Add_Memory_Location("BI01", Constants.BI01, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI02", Constants.BI02, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI03", Constants.BI03, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI04", Constants.BI04, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI05", Constants.BI05, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI06", Constants.BI06, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI07", Constants.BI07, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI08", Constants.BI08, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI09", Constants.BI09, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI10", Constants.BI10, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI11", Constants.BI11, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI12", Constants.BI12, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI13", Constants.BI13, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI14", Constants.BI14, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI15", Constants.BI15, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BI16", Constants.BI16, new Mem_Boolean(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");

        Add_Memory_Location("RI01", Constants.RI01, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Add_Memory_Location("RI02", Constants.RI02, new Mem_Float(Mem_Status.CAN_CONFIG_CONNECT | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0.0");
        Remove_Memory_Location("RI03", Constants.RI03);
        Remove_Memory_Location("RI04", Constants.RI04);
        Remove_Memory_Location("RI05", Constants.RI05);
        Remove_Memory_Location("RI06", Constants.RI06);
        Remove_Memory_Location("RI07", Constants.RI07);
        Remove_Memory_Location("RI08", Constants.RI08);

        Remove_Memory_Location("HSCI1", Constants.HSCI1);
        Remove_Memory_Location("HSCI2", Constants.HSCI2);
        Remove_Memory_Location("HSCI3", Constants.HSCI3);
        Remove_Memory_Location("HSCI4", Constants.HSCI4);
        Remove_Memory_Location("HSCI5", Constants.HSCI5);
        Remove_Memory_Location("HSCI6", Constants.HSCI6);
        Remove_Memory_Location("HSCI7", Constants.HSCI7);
        Remove_Memory_Location("HSCI8", Constants.HSCI8);

        Remove_Memory_Location("LSCI1", Constants.LSCI1);
        Remove_Memory_Location("LSCI2", Constants.LSCI2);
        Remove_Memory_Location("LSCI3", Constants.LSCI3);
        Remove_Memory_Location("LSCI4", Constants.LSCI4);
        Remove_Memory_Location("LSCI5", Constants.LSCI5);
        Remove_Memory_Location("LSCI6", Constants.LSCI6);
        Remove_Memory_Location("LSCI7", Constants.LSCI7);
        Remove_Memory_Location("LSCI8", Constants.LSCI8);

        Remove_Memory_Location("DELTI1", Constants.DELTI1);
        Remove_Memory_Location("DELTI2", Constants.DELTI2);
        Remove_Memory_Location("DELTI3", Constants.DELTI3);
        Remove_Memory_Location("DELTI4", Constants.DELTI4);
        Remove_Memory_Location("DELTI5", Constants.DELTI5);
        Remove_Memory_Location("DELTI6", Constants.DELTI6);
        Remove_Memory_Location("DELTI7", Constants.DELTI7);
        Remove_Memory_Location("DELTI8", Constants.DELTI8);

        Remove_Memory_Location("EI1", Constants.EI1);
        Remove_Memory_Location("EI2", Constants.EI2);
        Remove_Memory_Location("EI3", Constants.EI3);
        Remove_Memory_Location("EI4", Constants.EI4);
        Remove_Memory_Location("EI5", Constants.EI5);
        Remove_Memory_Location("EI6", Constants.EI6);
        Remove_Memory_Location("EI7", Constants.EI7);
        Remove_Memory_Location("EI8", Constants.EI8);


        Remove_Memory_Location("II01", Constants.II01);
        Remove_Memory_Location("II02", Constants.II02);

        Add_Memory_Location("LI01", Constants.LI01, new Mem_Int(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Remove_Memory_Location("LI02", Constants.LI02);

        Add_Memory_Location("BO01", Constants.BO01, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO02", Constants.BO02, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO03", Constants.BO03, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Add_Memory_Location("BO04", Constants.BO04, new Mem_Boolean(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Remove_Memory_Location("BO05", Constants.BO05);
        Remove_Memory_Location("BO06", Constants.BO06);
        Remove_Memory_Location("BO07", Constants.BO07);
        Remove_Memory_Location("BO08", Constants.BO08);

        Remove_Memory_Location("RO01", Constants.RO01);
        Remove_Memory_Location("RO02", Constants.RO02);
        Remove_Memory_Location("RO03", Constants.RO03);
        Remove_Memory_Location("RO04", Constants.RO04);

        Remove_Memory_Location("HSCO1", Constants.HSCO1);
        Remove_Memory_Location("HSCO2", Constants.HSCO2);
        Remove_Memory_Location("HSCO3", Constants.HSCO3);
        Remove_Memory_Location("HSCO4", Constants.HSCO4);

        Remove_Memory_Location("LSCO1", Constants.LSCO1);
        Remove_Memory_Location("LSCO2", Constants.LSCO2);
        Remove_Memory_Location("LSCO3", Constants.LSCO3);
        Remove_Memory_Location("LSCO4", Constants.LSCO4);

        Remove_Memory_Location("EO1", Constants.EO1);
        Remove_Memory_Location("EO2", Constants.EO2);
        Remove_Memory_Location("EO3", Constants.EO3);
        Remove_Memory_Location("EO4", Constants.EO4);


        Remove_Memory_Location("IO01", Constants.IO01);
        Remove_Memory_Location("IO02", Constants.IO02);
        Remove_Memory_Location("IO03", Constants.IO03);
        Remove_Memory_Location("IO04", Constants.IO04);
        Remove_Memory_Location("IO05", Constants.IO05);
        Remove_Memory_Location("IO06", Constants.IO06);

        Add_Memory_Location("LO01", Constants.LO01, new Mem_Int(Mem_Status.CAN_ONLINE | Mem_Status.CAN_CHANGE_STATUS), "0");
        Remove_Memory_Location("LO02", Constants.LO02);

        Add_Memory_Location("M01", Constants.M01, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M02", Constants.M02, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M03", Constants.M03, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M04", Constants.M04, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Add_Memory_Location("M05", Constants.M05, new Mem_Float(Mem_Status.CAN_CONFIG_VALUE | Mem_Status.CAN_ONLINE), "0.0");
        Remove_Memory_Location("M06", Constants.M06);
        Remove_Memory_Location("M07", Constants.M07);
        Remove_Memory_Location("M08", Constants.M08);
        Remove_Memory_Location("M09", Constants.M09);
        Remove_Memory_Location("M10", Constants.M10);
        Remove_Memory_Location("M11", Constants.M11);
        Remove_Memory_Location("M12", Constants.M12);
        Remove_Memory_Location("M13", Constants.M13);
        Remove_Memory_Location("M14", Constants.M14);
        Remove_Memory_Location("M15", Constants.M15);
        Remove_Memory_Location("M16", Constants.M16);
        Remove_Memory_Location("M17", Constants.M17);
        Remove_Memory_Location("M18", Constants.M18);
        Remove_Memory_Location("M19", Constants.M19);
        Remove_Memory_Location("M20", Constants.M20);
        Remove_Memory_Location("M21", Constants.M21);
        Remove_Memory_Location("M22", Constants.M22);
        Remove_Memory_Location("M23", Constants.M23);
        Remove_Memory_Location("M24", Constants.M24);    
        _num_memory = 5;

        Add_Memory_Location("MA", Constants.MA, new Mem_Boolean(Mem_Status.CAN_CONFIG_VALUE), "1");
        Add_Memory_Location("INITMA", Constants.INITMA, new Mem_Boolean(Mem_Status.CAN_CONFIG_VALUE), "1");
        Add_Memory_Location("TIMINI", Constants.TIMINI, new Mem_Boolean(Mem_Status.CAN_CONFIG_VALUE), "1");
    
        Add_Memory_Location("STEP01", Constants.STEP01, new Mem_Step(), "");
        Add_Memory_Location("STEP02", Constants.STEP02, new Mem_Step(), "");
        Add_Memory_Location("STEP03", Constants.STEP03, new Mem_Step(), "");
        Add_Memory_Location("STEP04", Constants.STEP04, new Mem_Step(), "");
        Add_Memory_Location("STEP05", Constants.STEP05, new Mem_Step(), "");
        Add_Memory_Location("STEP06", Constants.STEP06, new Mem_Step(), "");
        Add_Memory_Location("STEP07", Constants.STEP07, new Mem_Step(), "");
        Add_Memory_Location("STEP08", Constants.STEP08, new Mem_Step(), "");
        Add_Memory_Location("STEP09", Constants.STEP09, new Mem_Step(), "");
        Add_Memory_Location("STEP10", Constants.STEP10, new Mem_Step(), "");
        Add_Memory_Location("STEP11", Constants.STEP11, new Mem_Step(), "");
        Add_Memory_Location("STEP12", Constants.STEP12, new Mem_Step(), "");
        Add_Memory_Location("STEP13", Constants.STEP13, new Mem_Step(), "");
        Add_Memory_Location("STEP14", Constants.STEP14, new Mem_Step(), "");
        Add_Memory_Location("STEP15", Constants.STEP15, new Mem_Step(), "");
        Remove_Memory_Location("STEP16", Constants.STEP16);
        Remove_Memory_Location("STEP17", Constants.STEP17);
        Remove_Memory_Location("STEP18", Constants.STEP18);
        Remove_Memory_Location("STEP19", Constants.STEP19);
        Remove_Memory_Location("STEP20", Constants.STEP20);
        Remove_Memory_Location("STEP21", Constants.STEP21);
        Remove_Memory_Location("STEP22", Constants.STEP22);
        Remove_Memory_Location("STEP23", Constants.STEP23);
        Remove_Memory_Location("STEP24", Constants.STEP24);
        Remove_Memory_Location("STEP25", Constants.STEP25);
        Remove_Memory_Location("STEP26", Constants.STEP26);
        Remove_Memory_Location("STEP27", Constants.STEP27);
        Remove_Memory_Location("STEP28", Constants.STEP28);
        Remove_Memory_Location("STEP29", Constants.STEP29);
        Remove_Memory_Location("STEP30", Constants.STEP30);
        Remove_Memory_Location("STEP31", Constants.STEP31);
        Remove_Memory_Location("STEP32", Constants.STEP32);
        Remove_Memory_Location("STEP33", Constants.STEP33);
        Remove_Memory_Location("STEP34", Constants.STEP34);
        Remove_Memory_Location("STEP35", Constants.STEP35);
        Remove_Memory_Location("STEP36", Constants.STEP36);
        Remove_Memory_Location("STEP37", Constants.STEP37);
        Remove_Memory_Location("STEP38", Constants.STEP38);
        Remove_Memory_Location("STEP39", Constants.STEP39);
        Remove_Memory_Location("STEP40", Constants.STEP40);
        Remove_Memory_Location("STEP41", Constants.STEP41);
        Remove_Memory_Location("STEP42", Constants.STEP42);
        Remove_Memory_Location("STEP43", Constants.STEP43);
        Remove_Memory_Location("STEP44", Constants.STEP44);
        Remove_Memory_Location("STEP45", Constants.STEP45);
        Remove_Memory_Location("STEP46", Constants.STEP46);
        Remove_Memory_Location("STEP47", Constants.STEP47);
        Remove_Memory_Location("STEP48", Constants.STEP48);
        Remove_Memory_Location("STEP49", Constants.STEP49);
        Remove_Memory_Location("STEP50", Constants.STEP50);
        _num_steps = 15;

        // Pseudoparameters
        Add_Memory_Location("I01", Constants.I01, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 31) , "0"); 
        Add_Memory_Location("I02", Constants.I02, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 30) , "0"); 
        Add_Memory_Location("I03", Constants.I03, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 29) , "0"); 
        Add_Memory_Location("I04", Constants.I04, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 28) , "0"); 
        Add_Memory_Location("I05", Constants.I05, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 27) , "0"); 
        Add_Memory_Location("I06", Constants.I06, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 26) , "0"); 
        Add_Memory_Location("I07", Constants.I07, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 25) , "0"); 
        Add_Memory_Location("I08", Constants.I08, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 24) , "0"); 
        Add_Memory_Location("I09", Constants.I09, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 23) , "0"); 
        Add_Memory_Location("I10", Constants.I10, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 22) , "0"); 
        Add_Memory_Location("I11", Constants.I11, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 21) , "0"); 
        Add_Memory_Location("I12", Constants.I12, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 20) , "0"); 
        Add_Memory_Location("I13", Constants.I13, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 19) , "0"); 
        Add_Memory_Location("I14", Constants.I14, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 18) , "0"); 
        Add_Memory_Location("I15", Constants.I15, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 17) , "0"); 
        Add_Memory_Location("I16", Constants.I16, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 16) , "0"); 
        Add_Memory_Location("I17", Constants.I17, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 15) , "0"); 
        Add_Memory_Location("I18", Constants.I18, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 14) , "0"); 
        Add_Memory_Location("I19", Constants.I19, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 13) , "0"); 
        Add_Memory_Location("I20", Constants.I20, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 12) , "0"); 
        Add_Memory_Location("I21", Constants.I21, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 11) , "0"); 
        Add_Memory_Location("I22", Constants.I22, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 10) , "0"); 
        Add_Memory_Location("I23", Constants.I23, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 9) , "0"); 
        Add_Memory_Location("I24", Constants.I24, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 8) , "0"); 
        Add_Memory_Location("I25", Constants.I25, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 7) , "0"); 
        Add_Memory_Location("I26", Constants.I26, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 6) , "0"); 
        Add_Memory_Location("I27", Constants.I27, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 5) , "0"); 
        Add_Memory_Location("I28", Constants.I28, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 4) , "0"); 
        Add_Memory_Location("I29", Constants.I29, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 3) , "0"); 
        Add_Memory_Location("I30", Constants.I30, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 2) , "0"); 
        Add_Memory_Location("I31", Constants.I31, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 1) , "0"); 
        Add_Memory_Location("I32", Constants.I32, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LI01), 0) , "0"); 

        Add_Memory_Location("O01", Constants.O01, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 31) , "0"); 
        Add_Memory_Location("O02", Constants.O02, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 30) , "0"); 
        Add_Memory_Location("O03", Constants.O03, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 29) , "0"); 
        Add_Memory_Location("O04", Constants.O04, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 28) , "0"); 
        Add_Memory_Location("O05", Constants.O05, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 27) , "0"); 
        Add_Memory_Location("O06", Constants.O06, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 26) , "0"); 
        Add_Memory_Location("O07", Constants.O07, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 25) , "0"); 
        Add_Memory_Location("O08", Constants.O08, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 24) , "0"); 
        Add_Memory_Location("O09", Constants.O09, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 23) , "0"); 
        Add_Memory_Location("O10", Constants.O10, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 22) , "0"); 
        Add_Memory_Location("O11", Constants.O11, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 21) , "0"); 
        Add_Memory_Location("O12", Constants.O12, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 20) , "0"); 
        Add_Memory_Location("O13", Constants.O13, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 19) , "0"); 
        Add_Memory_Location("O14", Constants.O14, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 18) , "0"); 
        Add_Memory_Location("O15", Constants.O15, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 17) , "0"); 
        Add_Memory_Location("O16", Constants.O16, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 16) , "0"); 
        Add_Memory_Location("O17", Constants.O17, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 15) , "0"); 
        Add_Memory_Location("O18", Constants.O18, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 14) , "0"); 
        Add_Memory_Location("O19", Constants.O19, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 13) , "0"); 
        Add_Memory_Location("O20", Constants.O20, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 12) , "0"); 
        Add_Memory_Location("O21", Constants.O21, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 11) , "0"); 
        Add_Memory_Location("O22", Constants.O22, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 10) , "0"); 
        Add_Memory_Location("O23", Constants.O23, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 9) , "0"); 
        Add_Memory_Location("O24", Constants.O24, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 8) , "0"); 
        Add_Memory_Location("O25", Constants.O25, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 7) , "0"); 
        Add_Memory_Location("O26", Constants.O26, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 6) , "0"); 
        Add_Memory_Location("O27", Constants.O27, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 5) , "0"); 
        Add_Memory_Location("O28", Constants.O28, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 4) , "0"); 
        Add_Memory_Location("O29", Constants.O29, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 3) , "0"); 
        Add_Memory_Location("O30", Constants.O30, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 2) , "0"); 
        Add_Memory_Location("O31", Constants.O31, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 1) , "0"); 
        Add_Memory_Location("O32", Constants.O32, new Mem_Bit_Reference(0, Index_To_Memory(Constants.LO01), 0) , "0"); 
        
        // Other registers not exposed
        Add_Memory_Location("RNDSEED", Constants.RNDSEED, new Mem_Int(0), "0"); 
        Add_Memory_Location("TIME", Constants.TIME, 	new Mem_Int(0), "0"); 
        Add_Memory_Location("PERROR", Constants.PERROR, new Mem_Int(0), "0"); 
    

        Index_To_Memory (Constants.TYPE).Set_Configuration("LOGIC");
        FireChanged ();
    }
    
    
}

