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
//	File:	Instruction_RANG.java
//

/*
RANG 
(Generate Random Number, Gaussian)

RANG generates a random number with Gaussian distribution and places it in the accumulator. 
Two consecutive implicit RAND executions are used, after which the following formula determines 
the value to be set into the accumulator:
x = result of the first of two RAND iterations
y = result of the second of two RAND iterations
Accumulator = square root ((-2.0 * ln (x)) * cos(2.0*PI*y))

In this expression, "ln" represents the natural logarithm function. sptr(after) = sptr(before) + 1.

CHECKED: March 28, 2003
*/

class Instruction_RANG implements Instruction_Interface
{
    Instruction_RANG() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();
        
        try {
            int randseed = (int) reg.Index_To_Memory(Constants.RNDSEED).Get_Value();
            
            randseed = (randseed * 125) % 2796203;
            float x = ((float) randseed / 2796203.0F);

            randseed = (randseed * 125) % 2796203;
            float y = ((float) randseed / 2796203.0F);            
            
            float acc = (float) java.lang.Math.sqrt((-2.0F * java.lang.Math.log(x)) * 
                                java.lang.Math.abs(java.lang.Math.cos(2.0F * (float) java.lang.Math.PI * y)));
            
            reg.Index_To_Memory(Constants.RNDSEED).Set_Value((float) randseed);
            stack.Push(acc);
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
        reg.Use_Register (Constants.RNDSEED);
    }

    public boolean Check (Mem_Step s, Machine mem) {
        return 	s.Empty_1() && s.Empty_2();
    }

}
