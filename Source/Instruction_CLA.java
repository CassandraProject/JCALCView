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
//	File:	Instruction_CLA.java
//

/*
CLA 
(Clear All Machine Registers)

CLA clears all 24 memory registers. sptr(after) = sptr(before).

CHECKED: March 14, 2003
*/

class Instruction_CLA implements Instruction_Interface
{
    Instruction_CLA() {
        
    }

    public void Run (Steps_Table_Model steps, Stack_Table_Model stack, Machine reg) {
        Mem_Step s = steps.Get_Current_Step ();
        
        reg.Index_To_Memory(Constants.M01).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M02).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M03).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M04).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M05).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M06).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M07).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M08).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M09).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M10).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M11).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M12).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M13).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M14).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M15).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M16).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M17).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M18).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M19).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M20).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M21).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M22).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M23).Set_Value(0.0F);
        reg.Index_To_Memory(Constants.M24).Set_Value(0.0F);
         
        steps.Increment_Step();
    }

    public void Update_Register_Use (Mem_Step s, Registers_Table_Model reg) {

    }

    public boolean Check (Mem_Step s, Machine mem) {
        return 	s.Empty_1() && s.Empty_2();
    }

}
