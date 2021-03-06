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
//	File:	Instruction_SSF.java
//

/*
SSF {ROxx, BOxx, IOxx, LOxx, Mxx} 
(Set and Skip if False)

SSF sets the specified operand to 1.0 (if ROxx or Mxx) or 1 (if IOxx or LOxx) or True (if BOxx) and 
skips the next program step when the accumulator value is 0.0. Otherwise the instruction is ignored 
and the next instruction in sequence is executed.

If this instruction is the last or next-to-last step in the CALCA block program and the accumulator 
is zero, the operand is set and then the block program terminates.

When the block is in Manual, the outputs ROxx, BOxx, IOxx, and LOxx are unchanged, but the skip 
action still occurs based on the value of the accumulator. sptr(after) = sptr(before). SSF is 
identical to SSZ.

CHECKED: March 28, 2003
*/

class Instruction_SSF implements Instruction_Interface
{
    Instruction_SSF() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();

        float acc = stack.Top(0);

        if (acc == 0.0F) {
            reg.Index_To_Memory(s.Get_Register1()).Set_Value(1.0F);
            steps.Increment_Step();
        }

        steps.Increment_Step();
    }

    public void Update_Register_Use (Mem_Step s, Registers_Table_Model reg) {
        if (s.Register_1())	reg.Use_Register (s.Get_Register1());      
        if (s.Register_2())	reg.Use_Register (s.Get_Register2());      
        reg.Use_Register (Constants.PERROR);
    }

    public boolean Check (Mem_Step s, Machine mem) {
        return 	(s.ROxx_1() || s.BOxx_1() || s.IOxx_1() || s.LOxx_1() || s.Mxx_1()) && s.Empty_2();
    }

}
