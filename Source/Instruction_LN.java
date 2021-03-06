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
//	File:	Instruction_LN.java
//

/*
LN 
(Natural Logarithm)

LN reads the value (which must be positive) in the accumulator, computes the natural (base e) 
logarithm of the value, and writes the result to the accumulator, overwriting the original 
contents. sptr(after) = sptr(before).

An attempt to execute this instruction with a zero or negative value in the accumulator causes 
the instruction to be skipped and writes an "8" (LN run-time error) to the PERROR parameter.

CHECKED: March 28, 2003
*/

class Instruction_LN implements Instruction_Interface
{
    Instruction_LN() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();

        try {
            float acc = stack.Top(0);
            
            if (acc <= 0.0F) {
                throw new Exception_LN_Error();	// error code
            } else {
                acc = (float) java.lang.Math.log(acc);
                stack.Replace(acc);
            }
        }
        catch (Exception_LN_Error e) {
            if (reg.Index_To_Memory(Constants.PERROR).Get_Value() == 0.0F)
                reg.Index_To_Memory(Constants.PERROR).Set_Value(8);	// error code
            throw e;
        }
        finally {
            steps.Increment_Step();
        }
    }

    public void Update_Register_Use (Mem_Step s, Registers_Table_Model reg) {
        reg.Use_Register (Constants.PERROR);
    }

    public boolean Check (Mem_Step s, Machine mem) {
        return 	s.Empty_1() && s.Empty_2();
    }

}
