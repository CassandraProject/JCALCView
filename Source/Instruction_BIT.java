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
//	File:	Instruction_BIT.java
//

/*
BIT s 
(Branch If True)

BIT s branches to the step number designated by s if the value in the accumulator (the current top of 
stack location) is non-zero. sptr(after) = sptr(before).

An attempt to branch to a step number less than or equal to the current step number or greater than 
the step number containing the END statement (50 if there is no END step), writes a "-4" (invalid 
goto syntax error) to the PERROR parameter.

CHECKED: March 14, 2003
*/

class Instruction_BIT implements Instruction_Interface
{
    Instruction_BIT() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();
        
        float acc = stack.Top(0);

        try {
            if (acc != 0.0F) {
                steps.Jump_Step(s.Get_Value1());
            } else {
                steps.Increment_Step();
            }
        }
        catch (Exception_Illegal_Branch e) {
            if (reg.Index_To_Memory(Constants.PERROR).Get_Value() == 0.0F)
                reg.Index_To_Memory(Constants.PERROR).Set_Value(-4);	// error code
            steps.Increment_Step();
            throw e;
        }

        
    }

    public void Update_Register_Use (Mem_Step s, Registers_Table_Model reg) {
        reg.Use_Register (Constants.PERROR);
    }

    public boolean Check (Mem_Step s, Machine mem) {
        return s.Number_1() && s.Empty_2() && s.Get_Value1() >= 1 && s.Get_Value1() <= mem.Get_Num_Steps();									// ADD c
    }

}
