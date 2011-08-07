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
//	File:	Instruction_SQRT.java
//

/*
SQRT 
(Square Root)

SQRT reads the value (which must be  0) in the accumulator, computes the square root of the value, 
and writes the positive root to the accumulator, writing over the original contents. 
sptr(after) = sptr(before).

An attempt to SQRT a value < 0 causes the instruction to be skipped and writes a "1" (SQRT run-time error) 
to the PERROR parameter.

CHECKED: March 28, 2003
*/

class Instruction_SQRT implements Instruction_Interface
{
    Instruction_SQRT() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();

        try {
            float acc = stack.Top(0);
            
            if (acc < 0.0F) {
                throw new Exception_SQRT_Error();	// error code
            } else {
                acc = (float) java.lang.Math.sqrt(acc);
                stack.Replace(acc);
            }
        }
        catch (Exception_SQRT_Error e) {
            if (reg.Index_To_Memory(Constants.PERROR).Get_Value() == 0.0F)
                reg.Index_To_Memory(Constants.PERROR).Set_Value(1);	// error code
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
