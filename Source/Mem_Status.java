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
//	File:	Mem_Status.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

class Mem_Status
{
    // Other Status
    public static final int	ONLINE_GET = 1 << 0;
    public static final int	ONLINE_PUT = 1 << 1;
    public static final int	MODIFIED = 1 << 2;

    // Status
    public static final int	BAD = 1 << 8;
    public static final int	SECURE = 1 << 9;
    public static final int	ACK = 1 << 10;
    public static final int	OOS = 1 << 11;
    public static final int	SHADOW = 1 << 12;
    public static final int	LIM_HI = 1 << 13;
    public static final int	LIM_LO = 1 << 14;
    public static final int	ERROR = 1 << 15;

    // Flags
    public static final int	CAN_CONFIG_VALUE = 1 << 5;
    public static final int	CAN_CONFIG_CONNECT = 1 << 6;

    public static final int	CAN_ONLINE = 1 << 7;
    public static final int	CAN_CHANGE_STATUS = 1 << 8;

    private int			_other_status = 0;
    private int			_status = 0;
    
    private int			_flags = 0;
        
        
    Mem_Status (int flags) {
        _flags = flags;
    }
    
    
    
    public boolean Can_Config()
    {
        return Get_Flag(CAN_CONFIG_VALUE) || Get_Flag(CAN_CONFIG_CONNECT);
    }

    public boolean Can_Change_Status ()
    {
        return Get_Flag(CAN_CHANGE_STATUS);
    }

    public boolean Can_Online ()
    {
        return Get_Flag(CAN_ONLINE);
    }



    public void Set_Other_Status (int bit, boolean s)
    {
        if (s)		_other_status = _other_status | bit;
        else		_other_status = _other_status & ~bit;
    }

    public void Set_Other_Status (int status)
    {
        _other_status = status;
    }
   
    public boolean Get_Other_Status (int bit)
    {
        return (_other_status & bit) != 0;
    }


    
    public void Set_Status (int bit, boolean s)
    {
        if (s)		_status = _status | bit;
        else		_status = _status & ~bit;
    }
   
    public void Set_Status(int status) {
        _status = status;
    }

    public boolean Get_Status (int bit)
    {
        return (_status & bit) != 0;
    }
       
    public int Get_Status() {
        return _status;
    }

    
    
    public boolean	Get_Flag (int bit) {
        return (_flags & bit) != 0;
    }

    public void Set_Flag (int bit, boolean value)
    {
        if (value)	_status = _status |  bit;
        else		_status = _status & ~bit;
    }

    public void Compile (Machine machine) {
    
    }
}


