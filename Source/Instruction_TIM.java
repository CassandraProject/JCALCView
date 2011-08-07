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
//	File:	Instruction_TIM.java
//

/*
TIM 
(Time Since Midnight)

TIM gets the time, in seconds since midnight, and pushes it onto the stack sptr(after) = sptr(before) + 1.
*/

class Instruction_TIM implements Instruction_Interface
{
    Instruction_TIM() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();
    
        try {
            float r = reg.Index_To_Memory(Constants.TIME).Get_Value();
    
            stack.Push(r);
        }
        catch (Exception_Stack_Overflow e) {
            if (reg.Index_To_Memory(Constants.PERROR).Get_Value() == 0.0F)
                reg.Index_To_Memory(Constants.PERROR).Set_Value(5);	// error code
            throw e;
        }
        finally {
            steps.Increment_Step();
        }
    }

    public void Update_Register_Use (Mem_Step s, Registers_Table_Model reg) {
        reg.Use_Register (Constants.PERROR);
        reg.Use_Register (Constants.TIME);
    }

    public boolean Check (Mem_Step s, Machine mem) {
        return 	s.Empty_1() && s.Empty_2();
    }

}