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
//	File:	Instruction_NORX.java
//

/*
NORX
NORX c 
(Packed Logical Not Or)

NORX reads all the values or a specified number (c) of values from the stack, performs a 16-bit bitwise 
logical "nor" function on them, and stores the result into the new accumulator location (the new top 
of stack). This overwrites the first operand and isolates the other operands from any further access. 
Logical Not Or is defined as producing a False output if any input is True and a True output if all 
of the inputs are False.

For NORX, sptr(after) = stackbottom. For NORX c, sptr(after) = sptr(before) - c + 1.

If c exceeds the current number of stack operands, a "6" (stack underflow run-time error) is written 
to the PERROR parameter.

Each operand is truncated from real to 16-bit integer type before it is used in the calculation. The 
final result is stored on the stack as a real value.

CHECKED: March 28, 2003
*/

class Instruction_NORX implements Instruction_Interface
{
    Instruction_NORX() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();

        try {
            if (s.Empty_1() || s.Get_Value1() == 0) {
                short result = 0x00000000;
                int depth = stack.Depth();
                
                for (; depth > 0; --depth) {
                    result = (short) (result | (short) stack.Top(0));
                    if (depth > 1) stack.Pop(1); // Don't pop last element
                }
                
                stack.Replace((float) ~result);
                
            } else if (s.Number_1()) {
                short result = 0x00000000;
                int c = s.Get_Value1();
                
                for (; c > 0; --c) {
                    result = (short) (result | (short) stack.Top(0));
                    stack.Pop(1);
                }
                
                stack.Push((float) ~result);
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
        return 	s.Empty_1() && s.Empty_2() ||	// ANDX
                s.Number_1() && s.Empty_2() && s.Get_Value1() >= 0 && s.Get_Value1() <= mem.Get_Num_Memory();								// ADD c
    }

}
