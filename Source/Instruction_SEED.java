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
//	File:	Instruction_SEED.java
//

/*
SEED 
(Seed Random Number Generator)

SEED stores the contents of the accumulator into the Seed value used by the RAND and RANG 
instructions. The initial Seed value is set, when the block initializes, at 100,001 and is 
changed by each iteration of the RAND or RANG instruction. If the accumulator value is less 
than zero or greater than 100,001 when the SEED instruction is executed, the instruction is 
skipped. sptr(after) = sptr(before).

CHECKED: March 28, 2003
*/

class Instruction_SEED implements Instruction_Interface
{
    Instruction_SEED() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();
        
        try {
            float acc = stack.Top(0);
            stack.Pop(1);
            reg.Index_To_Memory(Constants.RNDSEED).Set_Value(acc);
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
        reg.Use_Register (Constants.RNDSEED);
    }

    public boolean Check (Mem_Step s, Machine mem) {
        return 	s.Empty_1() && s.Empty_2();
    }

}
