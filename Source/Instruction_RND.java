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
//	File:	Instruction_RND.java
//

/*
RND 
(Round)

RND reads the number in the accumulator, rounds off the value to the nearest integer, and writes the 
result into the accumulator, overwriting the original value.

If the decimal portion is less than 0.5, the value is rounded down to the integer portion of the 
number. If the decimal portion is 0.5 or greater, the value is rounded up to the next higher integer. 
In the case of negative accumulator values, if the absolute value of the decimal portion is greater 
than 0.5, the value is rounded down to the next lower integer.  
sptr(after) = sptr(before). 

CHECKED: March 28, 2003
*/


class Instruction_RND implements Instruction_Interface
{
    Instruction_RND() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();

        try {

            float acc = stack.Top(0);
                        
            stack.Replace(java.lang.Math.round(acc));
        }
        finally {
            steps.Increment_Step();
        }
    }

    public void Update_Register_Use (Mem_Step s, Registers_Table_Model reg) {
    }

    public boolean Check (Mem_Step s, Machine mem) {
        return 	s.Empty_1() && s.Empty_2();
    }

}

