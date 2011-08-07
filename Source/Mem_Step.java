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
//	File:	Mem_Step.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.StringTokenizer;

/**
    A <code>Mem_Step</code> is the actual step.
*/
 
class Mem_Step implements Mem_Interface
{
    private String		_instruction;

    private int 		_opcode;
    private int 		_register1,_register2;
    private int 		_value1, _value2;
    private boolean 		_invert1, _invert2;

    private float 		_timer;

    private boolean		_break_point;
    private boolean		_error;
    private boolean		_visited;    
    
    
    Mem_Step () {
        _instruction = new String("");
        _timer = 0.0F;
        
        _break_point = false;
        _visited = false;

        Reset();
    }
    
    private void Reset () {
        // Clear this instruction
        _opcode = Constants.NOP;
        _register1 = Constants.PARAM_EMPTY;
        _register2 = Constants.PARAM_EMPTY;
        _value1 = 0;
        _value2 = 0;
        _invert1 = false;
        _invert2 = false;
        
        Set_Error(false);
    }
        
    public void Set_Break (boolean break_point) {
        _break_point = break_point;
    }
    
    public boolean Get_Break () {
        return _break_point;
    }

    public void Set_Error (boolean error) {
        _error = error;
    }
    
    public boolean Get_Error () {
        return _error;
    }

    public void Set_Visited (boolean visited) {
        _visited = visited;
    }
    
    public boolean Get_Visited () {
        return _visited;
    }   
    
    
    
    public boolean Can_Config ()
    {
        return true;
    }
    
    public boolean Can_Change_Status ()
    {
        return false;
    }
    
    public boolean Can_Online ()
    {
        return false;
    }

    
    
    public void Set_Configuration_Value (String configuration)
    {
        Set_Configuration(configuration);
    }

    public void Set_Configuration (String instruction) {
        _instruction = instruction.toUpperCase();
    }

    public String Get_Configuration () {
        return _instruction;
    }



    public void Set_Value_String(String r) {
        throw new Exception_Illegal_Memory_Use();
    }
    
    public void Set_Value(float r) {
        throw new Exception_Illegal_Memory_Use();
    }
    
    public String Get_Value_String() {
        throw new Exception_Illegal_Memory_Use();
    }

    public float Get_Value() {
        throw new Exception_Illegal_Memory_Use();
    }


    public void Set_Status (int bit, boolean bad)
    {
        throw new Exception_Illegal_Memory_Use();
    }
    
    public void Set_Status(int status)
    {
        throw new Exception_Illegal_Memory_Use();
    }

    public boolean Get_Status (int bit)
    {
        throw new Exception_Illegal_Memory_Use();
    }
    
    public int Get_Status()
    {
        throw new Exception_Illegal_Memory_Use();
    }


    public void Set_Other_Status (int bit, boolean bad)
    {
        throw new Exception_Illegal_Memory_Use();
    }

    public void Set_Other_Status (int status)
    {
        throw new Exception_Illegal_Memory_Use();
    }

    public boolean Get_Other_Status (int bit)
    {
        throw new Exception_Illegal_Memory_Use();
    }


    
    public float Get_Timer () {
        return _timer;
    }

    public void Set_Timer (float timer) {
	_timer = timer;
    }
    
    public int Get_Opcode () {
        return _opcode;
    }

    public int Get_Register1 () {
        return _register1;
    }

    public int Get_Register2 () {
        return _register2;
    }

    public int Get_Value1 () {
        return _value1;
    }

    public int Get_Value2 () {
        return _value2;
    }


    
    private String Trim_Comment(String s, int type) {
        if (type == Machine.CALCA || type == Machine.MATH || type == Machine.LOGIC) {
            String sixteen_chars = s.substring (0,java.lang.Math.min(16, s.length()));
            int comment_start = sixteen_chars.indexOf(";");
            
            if (comment_start == -1)
                return sixteen_chars;
            else
                return sixteen_chars.substring (0,comment_start);
        } else if (type == Machine.CALC) {
            String ten_chars = s.substring (0,java.lang.Math.min(10, s.length()));
            int comment_start = ten_chars.indexOf(";");
            
            if (comment_start == -1)
                return ten_chars;
            else
                return ten_chars.substring (0,comment_start);
        }
        
        else return s;
    }

    private boolean Is_Inverted(String s) {
        int tilde = s.indexOf("~");
        
        if (tilde != 0)
            return false;
        
        return true;
    }

    private String Trim_Inverted(String s) {
        return s.substring(1);
    }
    
    public void Compile (Machine machine) { 
        Reset();

        // Tokenize the step
        String instruction_no_comment = Trim_Comment(_instruction, machine.Get_Type());
        StringTokenizer tokens = new StringTokenizer(instruction_no_comment, " ");
        
        // Check first token
        if (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            
            // Get the Opcode
            try {
                _opcode = machine.Name_To_Index_Instruction(token);
            }
            catch(Exception_Illegal_Symbol e) {
                _error = true;
                return;
            }
            
        } 


        // Check second token
        if (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            boolean isNumber = false;
            boolean isRegister = true;
                    
            // Try to read a number
            try {
                _value1 = Integer.parseInt(token);
                _register1 = Constants.PARAM_NUMBER;
                isNumber = true;
            }
            catch (NumberFormatException e) {
            }
            
            // Try to read a number as hex
            try {
                if (token.indexOf("H") == 0) {
                    _value1 = Integer.parseInt(token.substring(1),16);
                    _register1 = Constants.PARAM_NUMBER;
                    isNumber = true;
                }
            }
            catch (NumberFormatException e) {
            }
            
            // Try to read a register
            try {
                _invert1 = Is_Inverted(token);
                if (_invert1)	token = Trim_Inverted(token);
           	_register1 = machine.Name_To_Index_Memory(token);
            }
            catch(Exception_Illegal_Symbol e) {
                isRegister = false;
            }
            
            // Have we found a value
            if (!isNumber && !isRegister) {
                _error = true;
                return;
            }
            
        } 


        // Check third token
        if (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            boolean isNumber = false;
            boolean isRegister = true;
            
            // Try to read a number
            try {
                _value2 = Integer.parseInt(token);
                _register2 = Constants.PARAM_NUMBER;
                isNumber = true;
            }
            catch (NumberFormatException e) {
            }
            
            // Try to read a number as hex
            try {
                if (token.indexOf("H") == 0) {
                    _value2 = Integer.parseInt(token.substring(1),16);
                    _register2 = Constants.PARAM_NUMBER;
                    isNumber = true;
                }
            }
            catch (NumberFormatException e) {
            }  
                      
            // Try to read a register
            try {
                _invert2 = Is_Inverted(token);
                if (_invert2)	token = Trim_Inverted(token);
                _register2 = machine.Name_To_Index_Memory(token);
            }
            catch(Exception_Illegal_Symbol e) {
                isRegister = false;
            }
            
            // Have we found a value
            if (!isNumber && !isRegister) {
                _error = true;
                return;
            }
            
        }
        
        // Syntax Check
        Instruction_Interface inst_impl = machine.Index_To_Instruction(_opcode);
        if (inst_impl == null || !inst_impl.Check(this, machine)) {
            _error = true;
            return;
        }
                
    } // Compile
    
    
    public boolean	Empty_1()	{	return 	_register1 == Constants.PARAM_EMPTY;				}
    public boolean	Empty_2()	{	return 	_register2 == Constants.PARAM_EMPTY;				}

    public boolean	Number_1()	{	return 	_register1 == Constants.PARAM_NUMBER;				}
    public boolean	Number_2()	{	return 	_register2 == Constants.PARAM_NUMBER;				}

    public boolean	Invert_1()	{	return 	_invert1;							}
    public boolean	Invert_2()	{	return 	_invert2;							}

    public boolean	Register_1()	{	return 	!Empty_1() && !Number_1();					}
    public boolean	Register_2()	{	return 	!Empty_2() && !Number_2();					}
    
    public boolean	Mxx_1()		{	return Constants.Is_Memory(_register1) && !_invert1;			}
    public boolean	Mxx_2()		{	return Constants.Is_Memory(_register2) && !_invert2;			}
    public boolean	nMxx_1()	{	return Constants.Is_Memory(_register1) && _invert1;			}
    public boolean	nMxx_2()	{	return Constants.Is_Memory(_register2) && _invert2;			}
    
    public boolean	RIxx_1()	{	return Constants.Is_Real_Input(_register1) && !_invert1;		}
    public boolean	RIxx_2()	{	return Constants.Is_Real_Input(_register2) && !_invert2;		}
    public boolean	nRIxx_1()	{	return Constants.Is_Real_Input(_register1) && _invert1;			}
    public boolean	nRIxx_2()	{	return Constants.Is_Real_Input(_register2) && _invert2;			}

    public boolean	ROxx_1()	{	return Constants.Is_Real_Output(_register1) && !_invert1;		}
    public boolean	ROxx_2()	{	return Constants.Is_Real_Output(_register2) && !_invert2;		}
    public boolean	nROxx_1()	{	return Constants.Is_Real_Output(_register1) && _invert1;		}
    public boolean	nROxx_2()	{	return Constants.Is_Real_Output(_register2) && _invert2;		}

    public boolean	LIxx_1()	{	return Constants.Is_Long_Integer_Input(_register1) && !_invert1;	}
    public boolean	LIxx_2()	{	return Constants.Is_Long_Integer_Input(_register2) && !_invert2;	}
    public boolean	nLIxx_1()	{	return Constants.Is_Long_Integer_Input(_register1) && _invert1;		}
    public boolean	nLIxx_2()	{	return Constants.Is_Long_Integer_Input(_register2) && _invert2;		}

    public boolean	LOxx_1()	{	return Constants.Is_Long_Integer_Output(_register1) && !_invert1;	}
    public boolean	LOxx_2()	{	return Constants.Is_Long_Integer_Output(_register2) && !_invert2;	}
    public boolean	nLOxx_1()	{	return Constants.Is_Long_Integer_Output(_register1) && _invert1;	}
    public boolean	nLOxx_2()	{	return Constants.Is_Long_Integer_Output(_register2) && _invert2;	}
    
    public boolean	BIxx_1()	{	return Constants.Is_Boolean_Input(_register1) && !_invert1;		}
    public boolean	BIxx_2()	{	return Constants.Is_Boolean_Input(_register2) && !_invert2;		}
    public boolean	nBIxx_1()	{	return Constants.Is_Boolean_Input(_register1) && _invert1;		}
    public boolean	nBIxx_2()	{	return Constants.Is_Boolean_Input(_register2) && _invert2;		}

    public boolean	BOxx_1()	{	return Constants.Is_Boolean_Output(_register1) && !_invert1;		}
    public boolean	BOxx_2()	{	return Constants.Is_Boolean_Output(_register2) && !_invert2;		}
    public boolean	nBOxx_1()	{	return Constants.Is_Boolean_Output(_register1) && _invert1;		}
    public boolean	nBOxx_2()	{	return Constants.Is_Boolean_Output(_register2) && _invert2;		}
    
    public boolean	IIxx_1()	{	return Constants.Is_Integer_Input(_register1) && !_invert1;		}
    public boolean	IIxx_2()	{	return Constants.Is_Integer_Input(_register2) && !_invert2;		}
    public boolean	nIIxx_1()	{	return Constants.Is_Integer_Input(_register1) && _invert1;		}
    public boolean	nIIxx_2()	{	return Constants.Is_Integer_Input(_register2) && _invert2;		}

    public boolean	IOxx_1()	{	return Constants.Is_Integer_Output(_register1) && !_invert1;		}
    public boolean	IOxx_2()	{	return Constants.Is_Integer_Output(_register2) && !_invert2;		}
    public boolean	nIOxx_1()	{	return Constants.Is_Integer_Output(_register1) && _invert1;		}
    public boolean	nIOxx_2()	{	return Constants.Is_Integer_Output(_register2) && _invert2;		}

    public boolean	Ixx_1()		{	return Constants.Is_Pseudo_Input(_register1) && !_invert1;		}
    public boolean	Ixx_2()		{	return Constants.Is_Pseudo_Input(_register2) && !_invert2;		}
    public boolean	nIxx_1()	{	return Constants.Is_Pseudo_Input(_register1) && _invert1;		}
    public boolean	nIxx_2()	{	return Constants.Is_Pseudo_Input(_register2) && _invert2;		}

    public boolean	Oxx_1()		{	return Constants.Is_Pseudo_Output(_register1) && !_invert1;		}
    public boolean	Oxx_2()		{	return Constants.Is_Pseudo_Output(_register2) && !_invert2;		}
    public boolean	nOxx_1()	{	return Constants.Is_Pseudo_Output(_register1) && _invert1;		}
    public boolean	nOxx_2()	{	return Constants.Is_Pseudo_Output(_register2) && _invert2;		}
    
    public boolean	Is_Branch()	{	return 	_opcode == Constants.BIF ||
                                                        _opcode == Constants.BII ||
                                                        _opcode == Constants.BIN ||
                                                        _opcode == Constants.BIP ||
                                                        _opcode == Constants.BIT ||
                                                        _opcode == Constants.BIZ ||
                                                        _opcode == Constants.CHN ||
                                                        _opcode == Constants.GTO;					}
    
    public void		Blank_Jump (int step) {
        // If it's a branch, update the line numbers
        if (Is_Branch() && !Get_Error()) {
            int src_step = Get_Value1();
            
            if (src_step == step) {

                // Extract and insert new line number
                String src_line_string = ""+step;
                int index = _instruction.indexOf(src_line_string);
                                        
                String new_instruction = _instruction.substring(0,index) + "??" +
                                        _instruction.substring(index + src_line_string.length());
                                        
                Set_Configuration(new_instruction);
            }
        }
    
    }
    
    public void		Offset_Range (int first_step, int delta, int max) {
        // If it's a branch, update the line numbers
        if (Is_Branch() && !Get_Error()) {
            int src_step = Get_Value1();
            
            if (src_step >= first_step) {

                // Extract and insert new line number
                String src_line_string = ""+src_step;
                int index = _instruction.indexOf(src_line_string);
                
                int dst_step = src_step + delta;
                String dst_line_string = ""+dst_step;
                        
                String new_instruction = _instruction.substring(0,index) + dst_line_string +
                                        _instruction.substring(index + src_line_string.length());
                                        
                Set_Configuration(new_instruction);
            }
        }
    
    }
    
    
}


