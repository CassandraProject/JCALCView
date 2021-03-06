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
//	File:	Instruction_TSTB.java
//

/*
TSTB
TSTB b 
(Test Packed Boolean)

TSTB tests a specified bit in the accumulator. If blank is specified the bit number is expected to be 
at the top of the stack and this bit number is removed from the stack before the instruction is 
executed. sptr(after) = sptr(before) - 1.

If the bit number is less than 1 or greater than 16 an "11" (bit error run-time error) is written to 
the PERROR parameter.

If the argument b is specified it indicates a bit number between 1 and 16 in the accumulator. The 
stack is not popped in this case. sptr(after) = sptr(before).

Bit 1 is the most significant bit of the accumulator, and bit 16 the least significant.

If the tested bit is 0, the value False (0.0) replaces the accumulator; if it is 1, the value True 
(1.0) replaces the accumulator.
*/

class Instruction_TSTB implements Instruction_Interface
{
    Instruction_TSTB() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();
        
        try {
            if (s.Empty_1()) {
                int bit = (short) stack.Top(0);
                
                if (bit < 1 || bit > 16) {
                    throw new Exception_Bit_Error();
                } else {
                    int acc = (int) stack.Top(1);
                    stack.Pop(1);
                    acc = acc & (1 << (16-bit));
                    stack.Replace((acc != 0) ? 1.0F : 0.0F);
                }
                
            } else if (s.Number_1()) {
                int bit = (short) s.Get_Value1();
                
                if (bit < 1 || bit > 16) {
                    throw new Exception_Bit_Error();
                } else {
                    int acc = (int) stack.Top(0);
                    acc = acc & (1 << (16-bit));
                    stack.Replace((acc != 0) ? 1.0F : 0.0F);
                }
    
            }
        }
        catch (Exception_Stack_Overflow e) {
            if (reg.Index_To_Memory(Constants.PERROR).Get_Value() == 0.0F)
                reg.Index_To_Memory(Constants.PERROR).Set_Value(5);	// error code
            throw e;
        }
        catch (Exception_Stack_Underflow e) {
            if (reg.Index_To_Memory(Constants.PERROR).Get_Value() == 0.0F)
                reg.Index_To_Memory(Constants.PERROR).Set_Value(6);	// error code
            throw e;
        }
        catch (Exception_Bit_Error e) {
            if (reg.Index_To_Memory(Constants.PERROR).Get_Value() == 0.0F)
                reg.Index_To_Memory(Constants.PERROR).Set_Value(11);	// error code
            throw e;
        }
        finally {
            steps.Increment_Step();
        }
    }

    public void Update_Register_Use (Mem_Step s, Registers_Table_Model reg) {
        reg.Use_Register (Constants.PERROR);
        if (s.Register_1())	reg.Use_Register (s.Get_Register1());      
        if (s.Register_2())	reg.Use_Register (s.Get_Register2());      
    }

    public boolean Check (Mem_Step s, Machine mem) {
        return 	s.Empty_1() && s.Empty_2() ||
                s.Number_1() && s.Empty_2() && s.Get_Value1() >= 1 && s.Get_Value1() <= 16;									// ADD c
    }

}
