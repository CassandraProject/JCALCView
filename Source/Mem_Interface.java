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
//	File:	Mem_Interface.java
//

interface Mem_Interface
{
    public boolean Can_Config ();
    public boolean Can_Change_Status ();
    public boolean Can_Online ();


    public void Set_Configuration_Value (String config);
    public void Set_Configuration (String s);
    public String Get_Configuration ();
    
    public void Set_Value_String (String v);
    public void Set_Value (float v);
    
    public String Get_Value_String ();
    public float Get_Value ();
    
    public void Set_Status (int bit, boolean bad);
    public void Set_Status(int status);

    public boolean Get_Status (int bit);
    public int Get_Status();

    public void Set_Other_Status (int bit, boolean bad);
    public void Set_Other_Status (int status);
    public boolean Get_Other_Status (int bit);
    
    public void Compile (Machine machine);
}


