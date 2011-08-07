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
//	File:	Instruction_SWP.java
//

/*
SWP
SWP {ROxx, BOxx, IOxx, Mxx} 
(Swap)

SWP with no argument causes the contents of the last two positions on the stack to be interchanged. 
The stack pointer is not moved.

SWP ROxx swaps the contents of the last position on the stack with that of output ROxx. If the block 
is not in Auto, the value of ROxx is copied to the stack, but the stack value is not copied to ROxx.

SWP BOxx swaps the contents of the last position on the stack with that of output BOxx. A True value 
in BOxx is converted to 1.0 on the stack, and a False value is converted to 0.0 on the stack. Any 
non-zero value on the stack is converted to True in BOxx, and a 0.0 value is converted to False in 
BOxx. If the block is not in Auto, the swap is treated as in the ROxx case.

SWP IOxx swaps the contents of the last position on the stack with that of output IOxx. The accumulator 
value is clamped between 32,767.0 and -32,768.0 before the swap. If the block is not in Auto, the swap 
is treated as in the ROxx case.

SWP Mxx swaps the contents of the last position on the stack with that of memory location Mxx. 

In all cases, sptr(after) = sptr(before).
*/

class Instruction_SWP implements Instruction_Interface
{
    Instruction_SWP() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();
 
        try {
            if (s.Empty_1()) {
                float acc1 = stack.Top(0);
                float acc2 = stack.Top(1);
                
                if (stack.Depth() >= 2) {
                    stack.Pop(2);
                
                    stack.Push(acc1);
                    stack.Push(acc2);
                }
            } else if (s.IOxx_1() && s.Empty_2()) {
                float acc = stack.Top(0);
                float r = reg.Index_To_Memory(s.Get_Register1()).Get_Value();
                
                if (r > Short.MAX_VALUE) r = (float) Short.MAX_VALUE;
                if (r < Short.MIN_VALUE) r = (float) Short.MIN_VALUE;
                            
                stack.Replace(r);
                reg.Index_To_Memory(s.Get_Register1()).Set_Value(acc);
            } else if (s.Register_1() && s.Empty_2()) {
                float acc = stack.Top(0);
                float r = reg.Index_To_Memory(s.Get_Register1()).Get_Value();
                            
                stack.Replace(r);
                reg.Index_To_Memory(s.Get_Register1()).Set_Value(acc);
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
        return 	s.Empty_1() && s.Empty_2() ||
                (s.Mxx_1() || s.ROxx_1() || s.BOxx_1() || s.IOxx_1() || s.LOxx_1()) && s.Empty_2();
    }

}
