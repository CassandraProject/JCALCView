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
//	File:	Instruction_SUB.java
//

/*
SUB
SUB {RIxx, ROxx, Mxx} 
SUB {RIxx, ROxx, Mxx} {n, RIxx, ROxx, Mxx} 
(Subtract)

SUB reads the values from the top two stack locations, subtracts the second operand (subtrahend) 
from the first operand (minuend), decrements the stack pointer, and writes the difference into the 
new accumulator location (the new top of stack location). This overwrites the first operand and 
isolates the second operand from any further access. (The later stack value is subtracted from the 
earlier one.) sptr(after) = sptr(before) - 1.

SUB RIxx reads the value stored in RIxx (the CALCA's Real Input parameter xx), subtracts it from the 
value that it pops from the stack, then pushes the result back onto the stack. SUB ROxx and SUB Mxx 
do the same for the values stored at ROxx and memory location xx, respectively. 
sptr(after) = sptr(before).

SUB RIxx Mxx reads the values stored in RIxx and Mxx, subtracts the Mxx value from the RIxx value, 
and stores the result on the stack. Whenever both operands are specified, the second operand is 
subtracted from the first operand. sptr(after) = sptr(before) + 1.

SUB RIxx n reads the value stored in RIxx and a specified integer constant (n), substracts n from 
the RIxx value, and stores the result on the stack. If the first operand is ROxx or Mxx, the operation 
is similar. sptr(after) = sptr(before) + 1.
*/

class Instruction_SUB implements Instruction_Interface
{
    Instruction_SUB() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();

        try {
            if (s.Empty_1()) {
                float acc1 = stack.Top(0);
                float acc2 = stack.Top(1);
                
                stack.Pop(1);
                
                stack.Replace(acc2-acc1);
                
            } else if (s.Register_1() && s.Empty_2()) {
                float acc = stack.Top(0);
                float r = reg.Index_To_Memory(s.Get_Register1()).Get_Value();
                            
                stack.Replace(acc-r);
                    
            } else if (s.Register_1() && s.Number_2()) {
                float r = reg.Index_To_Memory(s.Get_Register1()).Get_Value();
                float n = s.Get_Value2();
                        
                stack.Push(r-n);
        
            } else if (s.Register_1() && s.Register_2()) {
                float reg1 = reg.Index_To_Memory(s.Get_Register1()).Get_Value();
                float reg2 = reg.Index_To_Memory(s.Get_Register2()).Get_Value();
                
                stack.Push(reg1-reg2);
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
        return 	s.Empty_1() && s.Empty_2() ||									// SUB
                (s.RIxx_1() || s.ROxx_1() || s.Mxx_1()) && s.Empty_2() || 					// SUB {RIxx, ROxx, Mxx}
                (s.RIxx_1() || s.ROxx_1() || s.Mxx_1()) && (s.Number_2() || s.RIxx_2() || s.ROxx_2() || s.Mxx_2()); // SUB {RIxx, ROxx, Mxx} {n,RIxx, ROxx, Mxx}
    }

}
