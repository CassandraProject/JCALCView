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
//	File:	Instruction_DEC.java
//

/*
DEC
DEC n
DEC {ROxx, IOxx, LOxx, Mxx} 
(Decrement)

DEC decrements the accumulator or the specified operand.

If blank is specified, the value in the accumulator is read, algebraically decreased by 1.0, and 
returned to the accumulator, overwriting the original contents. If an integer n is specified, the 
accumulator value is algebraically decreased by n and returned to the accumulator.

If Mxx is specified, the value in memory location xx is algebraically decreased by 1.0. 

If ROxx is specified and the block is in Auto, the indicated output is algebraically decreased by 1.0. 
If the block is in Manual, the step is skipped.

If IOxx or LOxx is specified and the block is in Auto, the indicated output is decremented by 1 as an 
integer subtraction without underflow. If the operand value before decrementation is equal to -32,768 
(-2,147,483,648 in the case of LOxx) or the block is in Manual, the step is skipped.

The stack is unaffected when the operand is other than blank or n. In all cases, sptr(after) = sptr(before).

CHECKED: March 28, 2003
*/

class Instruction_DEC implements Instruction_Interface
{
    Instruction_DEC() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();

        try {
            if (s.Empty_1()) {
                float acc = stack.Top(0);
                            
                stack.Replace(acc - 1.0F);
                
            } else if (s.Number_1()) {
                float acc = stack.Top(0);
                int c = s.Get_Value1();
                                
                stack.Replace(acc - (float) c);
    
            } else if (s.IOxx_1() && s.Empty_2()) {
                float r = reg.Index_To_Memory(s.Get_Register1()).Get_Value();
                
                if (r <= Short.MIN_VALUE)	r = Short.MIN_VALUE;
                else				r = r - 1.0F;
                
                reg.Index_To_Memory(s.Get_Register1()).Set_Value(r);
                    
            } else if (s.LOxx_1() && s.Empty_2()) {
                float r = reg.Index_To_Memory(s.Get_Register1()).Get_Value();

                if (r <= Integer.MIN_VALUE)	r = Integer.MIN_VALUE;
                else				r = r - 1.0F;

                reg.Index_To_Memory(s.Get_Register1()).Set_Value(r);
                    
            } else if (s.Register_1() && s.Empty_2()) {
                float r = reg.Index_To_Memory(s.Get_Register1()).Get_Value();
                reg.Index_To_Memory(s.Get_Register1()).Set_Value(r - 1.0F);
                    
            }
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
        return 	s.Empty_1() && s.Empty_2() ||						// DEC
                s.Number_1() && s.Empty_2() ||						// DEC c
                (s.ROxx_1() || s.IOxx_1() || s.LOxx_1() || s.Mxx_1()) && s.Empty_2(); 	// DEC {RIxx, ROxx, Mxx}
    }

}
