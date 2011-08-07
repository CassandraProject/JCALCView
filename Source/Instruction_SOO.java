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
//	File:	Instruction_SOO.java
//

/*
SOO {ROxx, BOxx, IOxx, LOxx} 
(Set Out-of-Service Status)

SOO sets the Out-of-Service status bit of the specified output parameter to True. Its effect is 
identical in Auto or Manual mode. sptr(after) = sptr(before).

CHECKED: March 14, 2003
*/

class Instruction_SOO implements Instruction_Interface
{
    Instruction_SOO() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();

        reg.Index_To_Memory(s.Get_Register1()).Set_Status(Mem_Status.OOS,true);
        
        steps.Increment_Step();
    }

    public void Update_Register_Use (Mem_Step s, Registers_Table_Model reg) {

    }

    public boolean Check (Mem_Step s, Machine mem) {
        return 	(s.ROxx_1() || s.IOxx_1() || s.LOxx_1() || s.BOxx_1()) && s.Empty_2();
    }

}
