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
//	File:	Constants.java
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.Locale;
import java.util.ResourceBundle;

class Constants
{
    // Resource Bundle
    private static ResourceBundle _resbundle = ResourceBundle.getBundle("strings", Locale.getDefault());
    
    public static String	Get_String 		(String r) {	return _resbundle.getString(r);	}

    // preferences File
    static private Prefs_File	_prefs_file = new Prefs_File();
    
    static public Prefs_File Get_Prefs_File () {
        return _prefs_file;
    }

    // splash Pane
    static private SplashFrame	_splash = new SplashFrame();
    
    static public SplashFrame Get_Splash () {
        return _splash;
    }

    // preferences Pane
    static private Preferences	_preferences = new Preferences();
    
    static public Preferences Get_Prefs () {
        return _preferences;
    }


    // Block limits
    public static final int	NUM_STACK_SIZE = 25;
    public static final int	NUM_INSTRUCTIONS = 122;
    public static final int	NUM_REGISTERS = 242;

    public static final int	PARAM_EMPTY = -1;
    public static final int	PARAM_NUMBER = -2;
    public static final int	PARAM_ERROR = -3;


    // Other registers
    public static final int	NAME = 0;
    public static final int	TYPE = 1;
    public static final int	DESCRP = 2;
    public static final int	PERIOD = 3;
    public static final int	PHASE = 4;
    public static final int	LOOPID = 5;

    // I/O Parameters
    public static final int	RI01 = 6;
    public static final int	RI02 = 7;
    public static final int	RI03 = 8;
    public static final int	RI04 = 9;
    public static final int	RI05 = 10;
    public static final int	RI06 = 11;
    public static final int	RI07 = 12;
    public static final int	RI08 = 13;

    public static final int	HSCI1 = 14;
    public static final int	HSCI2 = 15;
    public static final int	HSCI3 = 16;
    public static final int	HSCI4 = 17;
    public static final int	HSCI5 = 18;
    public static final int	HSCI6 = 19;
    public static final int	HSCI7 = 20;
    public static final int	HSCI8 = 21;

    public static final int	LSCI1 = 22;
    public static final int	LSCI2 = 23;
    public static final int	LSCI3 = 24;
    public static final int	LSCI4 = 25;
    public static final int	LSCI5 = 26;
    public static final int	LSCI6 = 27;
    public static final int	LSCI7 = 28;
    public static final int	LSCI8 = 29;

    public static final int	DELTI1 = 30;
    public static final int	DELTI2 = 31;
    public static final int	DELTI3 = 32;
    public static final int	DELTI4 = 33;
    public static final int	DELTI5 = 34;
    public static final int	DELTI6 = 35;
    public static final int	DELTI7 = 36;
    public static final int	DELTI8 = 37;

    public static final int	EI1 = 38;
    public static final int	EI2 = 39;
    public static final int	EI3 = 40;
    public static final int	EI4 = 41;
    public static final int	EI5 = 42;
    public static final int	EI6 = 43;
    public static final int	EI7 = 44;
    public static final int	EI8 = 45;

    public static final int	BI01 = 46;
    public static final int	BI02 = 47;
    public static final int	BI03 = 48;
    public static final int	BI04 = 49;
    public static final int	BI05 = 50;
    public static final int	BI06 = 51;
    public static final int	BI07 = 52;
    public static final int	BI08 = 53;
    public static final int	BI09 = 54;
    public static final int	BI10 = 55;
    public static final int	BI11 = 56;
    public static final int	BI12 = 57;
    public static final int	BI13 = 58;
    public static final int	BI14 = 59;
    public static final int	BI15 = 60;
    public static final int	BI16 = 61;

    public static final int	II01 = 62;
    public static final int	II02 = 63;

    public static final int	LI01 = 64;
    public static final int	LI02 = 65;

    public static final int	BO01 = 66;
    public static final int	BO02 = 67;
    public static final int	BO03 = 68;
    public static final int	BO04 = 69;
    public static final int	BO05 = 70;
    public static final int	BO06 = 71;
    public static final int	BO07 = 72;
    public static final int	BO08 = 73;

    public static final int	RO01 = 74;
    public static final int	RO02 = 75;
    public static final int	RO03 = 76;
    public static final int	RO04 = 77;
    
    public static final int	HSCO1 = 78;
    public static final int	HSCO2 = 79;
    public static final int	HSCO3 = 80;
    public static final int	HSCO4 = 81;

    public static final int	LSCO1 = 82;
    public static final int	LSCO2 = 83;
    public static final int	LSCO3 = 84;
    public static final int	LSCO4 = 85;

    public static final int	EO1 = 86;
    public static final int	EO2 = 87;
    public static final int	EO3 = 88;
    public static final int	EO4 = 89;

    public static final int	IO01 = 90;
    public static final int	IO02 = 91;
    public static final int	IO03 = 92;
    public static final int	IO04 = 93;
    public static final int	IO05 = 94;
    public static final int	IO06 = 95;

    public static final int	LO01 = 96;
    public static final int	LO02 = 97;
    
    public static final int	MA = 98;
    public static final int	INITMA = 99;
    public static final int	TIMINI = 100;

    public static final int	M01 = 101;
    public static final int	M02 = 102;
    public static final int	M03 = 103;
    public static final int	M04 = 104;
    public static final int	M05 = 105;
    public static final int	M06 = 106;
    public static final int	M07 = 107;
    public static final int	M08 = 108;
    public static final int	M09 = 109;
    public static final int	M10 = 110;
    public static final int	M11 = 111;
    public static final int	M12 = 112;
    public static final int	M13 = 113;
    public static final int	M14 = 114;
    public static final int	M15 = 115;
    public static final int	M16 = 116;
    public static final int	M17 = 117;
    public static final int	M18 = 118;
    public static final int	M19 = 119;
    public static final int	M20 = 120;
    public static final int	M21 = 121;
    public static final int	M22 = 122;
    public static final int	M23 = 123;
    public static final int	M24 = 124;

    public static final int	STEP01 = 125;
    public static final int	STEP02 = 126;
    public static final int	STEP03 = 127;
    public static final int	STEP04 = 128;
    public static final int	STEP05 = 129;
    public static final int	STEP06 = 130;
    public static final int	STEP07 = 131;
    public static final int	STEP08 = 132;
    public static final int	STEP09 = 133;
    public static final int	STEP10 = 134;
    public static final int	STEP11 = 135;
    public static final int	STEP12 = 136;
    public static final int	STEP13 = 137;
    public static final int	STEP14 = 138;
    public static final int	STEP15 = 139;
    public static final int	STEP16 = 140;
    public static final int	STEP17 = 141;
    public static final int	STEP18 = 142;
    public static final int	STEP19 = 143;
    public static final int	STEP20 = 144;
    public static final int	STEP21 = 145;
    public static final int	STEP22 = 146;
    public static final int	STEP23 = 147;
    public static final int	STEP24 = 148;
    public static final int	STEP25 = 149;
    public static final int	STEP26 = 150;
    public static final int	STEP27 = 151;
    public static final int	STEP28 = 152;
    public static final int	STEP29 = 153;
    public static final int	STEP30 = 154;
    public static final int	STEP31 = 155;
    public static final int	STEP32 = 156;
    public static final int	STEP33 = 157;
    public static final int	STEP34 = 158;
    public static final int	STEP35 = 159;
    public static final int	STEP36 = 160;
    public static final int	STEP37 = 161;
    public static final int	STEP38 = 162;
    public static final int	STEP39 = 163;
    public static final int	STEP40 = 164;
    public static final int	STEP41 = 165;
    public static final int	STEP42 = 166;
    public static final int	STEP43 = 167;
    public static final int	STEP44 = 168;
    public static final int	STEP45 = 169;
    public static final int	STEP46 = 170;
    public static final int	STEP47 = 171;
    public static final int	STEP48 = 172;
    public static final int	STEP49 = 173;
    public static final int	STEP50 = 174;
    
    // Pseudoparameters
    public static final int	I01 = 175;
    public static final int	I02 = 176;
    public static final int	I03 = 177;
    public static final int	I04 = 178;
    public static final int	I05 = 179;
    public static final int	I06 = 180;
    public static final int	I07 = 181;
    public static final int	I08 = 182;
    public static final int	I09 = 183;
    public static final int	I10 = 184;
    public static final int	I11 = 185;
    public static final int	I12 = 186;
    public static final int	I13 = 187;
    public static final int	I14 = 188;
    public static final int	I15 = 189;
    public static final int	I16 = 190;
    public static final int	I17 = 191;
    public static final int	I18 = 192;
    public static final int	I19 = 193;
    public static final int	I20 = 194;
    public static final int	I21 = 195;
    public static final int	I22 = 196;
    public static final int	I23 = 197;
    public static final int	I24 = 198;
    public static final int	I25 = 199;
    public static final int	I26 = 200;
    public static final int	I27 = 201;
    public static final int	I28 = 202;
    public static final int	I29 = 203;
    public static final int	I30 = 204;
    public static final int	I31 = 205;
    public static final int	I32 = 206;

    public static final int	O01 = 207;
    public static final int	O02 = 208;
    public static final int	O03 = 209;
    public static final int	O04 = 210;
    public static final int	O05 = 211;
    public static final int	O06 = 212;
    public static final int	O07 = 213;
    public static final int	O08 = 214;
    public static final int	O09 = 215;
    public static final int	O10 = 216;
    public static final int	O11 = 217;
    public static final int	O12 = 218;
    public static final int	O13 = 219;
    public static final int	O14 = 220;
    public static final int	O15 = 221;
    public static final int	O16 = 222;
    public static final int	O17 = 223;
    public static final int	O18 = 224;
    public static final int	O19 = 225;
    public static final int	O20 = 226;
    public static final int	O21 = 227;
    public static final int	O22 = 228;
    public static final int	O23 = 229;
    public static final int	O24 = 230;
    public static final int	O25 = 231;
    public static final int	O26 = 232;
    public static final int	O27 = 233;
    public static final int	O28 = 234;
    public static final int	O29 = 235;
    public static final int	O30 = 236;
    public static final int	O31 = 237;
    public static final int	O32 = 238;

    // Other registers not exposed
    public static final int	RNDSEED = 239;
    public static final int	TIME = 240;
    public static final int	PERROR = 241;
    
    
    
    public static boolean Is_Boolean_Input 		(int r)	{	return (r >= BI01) && (r <= BI16);	}
    public static boolean Is_Pseudo_Input 		(int r)	{	return (r >= I01) && (r <= I32);	}
    public static boolean Is_Real_Input 		(int r)	{	return (r >= RI01) && (r <= RI08);	}
    public static boolean Is_Integer_Input 		(int r)	{	return (r >= II01) && (r <= II02);	}
    public static boolean Is_Long_Integer_Input 	(int r)	{	return (r >= LI01) && (r <= LI02);	}
 
    public static boolean Is_Boolean_Output 		(int r)	{	return (r >= BO01) && (r <= BO08);	}
    public static boolean Is_Pseudo_Output 		(int r)	{	return (r >= O01) && (r <= O32);	}
    public static boolean Is_Real_Output 		(int r)	{	return (r >= RO01) && (r <= RO04);	}
    public static boolean Is_Integer_Output 		(int r)	{	return (r >= IO01) && (r <= IO06);	}
    public static boolean Is_Long_Integer_Output 	(int r)	{	return (r >= LO01) && (r <= LO02);	}

    public static boolean Is_Memory 			(int r)	{	return (r >= M01) && (r <= M24);	}

    
    
    
    // Instruction Opcodes - No Op
    public static final int	NOP = 0;

    // Instruction Opcodes - Arithmetic
    public static final int	ABS = 1;
    public static final int	ACOS = 2;
    public static final int	ADD = 3;
    public static final int	ALN = 4;
    public static final int	ALOG = 5;
    public static final int	ASIN = 6;
    public static final int	ATAN = 7;
    public static final int	AVE = 8;
    public static final int	CHS = 9;
    public static final int	COS = 10;
    public static final int	DEC = 11;
    public static final int	DIV = 12;
    public static final int	EXP = 13;
    public static final int	IDIV = 14;
    public static final int	IMOD = 15;
    public static final int	INC = 16;
    public static final int	LN = 17;
    public static final int	LOG = 18;
    public static final int	MAX = 19;
    public static final int	MAXO = 20;
    public static final int	MIN = 21;
    public static final int	MEDN = 22;
    public static final int	MUL = 23;
    public static final int	RAND = 24;
    public static final int	RANG = 25;
    public static final int	RND = 26;
    public static final int	SEED = 27;
    public static final int	SIN = 28;
    public static final int	SQR = 29;
    public static final int	SQRT = 30;
    public static final int	SUB = 31;
    public static final int	TAN = 32;
    public static final int	TRC = 33;
    
    // Instruction Opcodes - Boolean
    public static final int	AND = 34;
    public static final int	ANDX = 35;
    public static final int	NAN = 36;
    public static final int	NANX = 121;
    public static final int	NAND = 37;
    public static final int	NOR = 38;
    public static final int	NORX = 39;
    public static final int	NOT = 40;
    public static final int	NOTX = 41;
    public static final int	NXO = 42;
    public static final int	NXOR = 43;
    public static final int	NXOX = 44;
    public static final int	OR = 45;
    public static final int	ORX = 46;
    public static final int	XOR = 47;
    public static final int	XORX = 48;
    
    // Instruction Opcodes - I/O
    public static final int	CBD = 49;
    public static final int	CE = 50;
    public static final int	COO = 51;
    public static final int	IN = 52;
    public static final int	INB = 53;
    public static final int	INH = 54;
    public static final int	INL = 55;
    public static final int	INR = 56;
    public static final int	INS = 57;
    public static final int	OUT = 58;
    public static final int	RBD = 59;
    public static final int	RCL = 60;
    public static final int	RCN = 61;
    public static final int	RE = 62;
    public static final int	REL = 63;
    public static final int	RON = 64;
    public static final int	ROO = 65;
    public static final int	RQE = 66;
    public static final int	RQL = 67;
    public static final int	SAC = 68;
    public static final int	SBD = 69;
    public static final int	SE = 70;
    public static final int	SEC = 71;
    public static final int	SOO = 72;
    public static final int	STH = 73;
    public static final int	STL = 74;
    public static final int	SWP = 75;
    
    // Instruction Opcodes - Cascade Propagation
    public static final int	PRI = 76;
    public static final int	PRO = 77;
    public static final int	PRP = 78;
    
    // Instruction Opcodes - Machine and Stack
    public static final int	CLA = 79;
    public static final int	CLM = 80;
    public static final int	CST = 81;
    public static final int	DUP = 82;
    public static final int	LAC = 83;
    public static final int	LACI = 84;
    public static final int	POP = 85;
    public static final int	STM = 86;
    public static final int	STMI = 87;
    public static final int	TSTB = 88;
    
    // Instruction Opcodes - Flow control
    public static final int	BIF = 89;
    public static final int	BII = 90;
    public static final int	BIN = 91;
    public static final int	BIP = 92;
    public static final int	BIT = 93;
    public static final int	BIZ = 94;
    public static final int	END = 95;
    public static final int	EXIT = 96;
    public static final int	GTI = 97;
    public static final int	GTO = 98;
    
    // Instruction Opcodes - Clear/Set
    public static final int	CLL = 99;
    public static final int	CLR = 100;
    public static final int	CLRB = 101;
    public static final int	SET = 102;
    public static final int	SETB = 103;
    public static final int	SSF = 104;
    public static final int	SSI = 105;
    public static final int	SSN = 106;
    public static final int	SSP = 107;
    public static final int	SST = 108;
    public static final int	SSZ = 109;
    
    // Instruction Opcodes - Timing
    public static final int	CHI = 110;
    public static final int	CHN = 111;
    public static final int	DOFF = 112;
    public static final int	DON = 113;
    public static final int	OSP = 114;
    public static final int	TIM = 115;
    
    // Instruction Opcodes - Logic
    public static final int	FF = 116;
    public static final int	MRS = 117;
    
    // Instruction Opcodes - Error Control
    public static final int	CLE = 118;
    public static final int	RER = 119;
    public static final int	SIEC = 120;
    
    
}
