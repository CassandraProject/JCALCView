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
//	File:	Instruction_SIEC.java
//

/*
SIEC 
(Skip if Error Clear)

SIEC skips the next program step if the internal error flag (the PERROR parameter) is zero. PERROR 
retains the first run-time error encountered during each block execution, or the first error 
encountered after a CLE instruction sptr(after) = sptr(before).

CHECKED: March 28, 2003
*/

class Instruction_SIEC implements Instruction_Interface
{
    Instruction_SIEC() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();
        
        if (reg.Index_To_Memory(Constants.PERROR).Get_Value() == 0.0F)
            steps.Increment_Step(); // Increment twice
        
        steps.Increment_Step();
    }

    public void Update_Register_Use (Mem_Step s, Registers_Table_Model reg) {
        reg.Use_Register (Constants.PERROR);
    }

    public boolean Check (Mem_Step s, Machine mem) {
        return 	s.Empty_1() && s.Empty_2();
    }

}
