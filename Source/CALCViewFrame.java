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
//  CALCView.java
//  CALCView
//
//  Created by Tod Baudais on Wed Feb 26 2003.
//  Copyright (c) 2003 SmellyToad Software. All rights reserved.
//
//	For information on setting Java configuration information, including 
//	setting Java properties, refer to the documentation at
//		http://developer.apple.com/techpubs/java/java.html
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.io.*;
import java.util.Observer;
import java.util.Observable;


public class CALCViewFrame extends JFrame implements Observer {
    // layout stuff
    private static int BORDER = 30;
    
    private static int EXECUTION_TAB = 0;
    private static int CONFIG_TAB = 1;
    
    // Icons
    private	Icon_Loop			_loop_icon = new Icon_Loop();
                                                  
    // Declarations for menus
    protected 	JMenuBar 			_mainMenuBar = new JMenuBar();
	
    protected 	JMenu 				_fileMenu; 
    protected 	JMenuItem 			_miNew, _miOpen, _miClose, _miSave, _miSaveAs, _miQuit;
	
    protected 	JMenu 				_editMenu;
    protected 	JMenuItem 			_miUndo, _miCut, _miCopy, _miPaste, _miClear, _miSelectAll, _miInsert, _miDelete;

    protected 	JMenu 				_controlsMenu;
    protected 	JMenuItem 			_miPlay, _miPlayContinuous, _miStep, _miStop;
    protected 	JRadioButtonMenuItem 		_miInit, _miCALCA, _miCALC, _miMATH, _miLOGIC;
    
    protected 	JMenu 				_onlineMenu;
    protected 	JRadioButtonMenuItem 		_miOnline;
    protected 	JMenuItem 			_miUpload, _miDownload, _miSettings;

    protected 	JMenu 				_helpMenu;
    protected 	JMenuItem 			_miHelp, _miAbout;
    
    // Popup Menus
    protected	JPopupMenu			_popupMenu;    
    protected 	JMenuItem 			_miUndoPop, _miCutPop, _miCopyPop, _miPastePop, _miClearPop, _miSelectAllPop, _miInsertPop, _miDeletePop;

    // Menu Actions
    protected 	NewMenuListener			_new_listener = new NewMenuListener();
    protected 	OpenMenuListener		_open_listener = new OpenMenuListener();
    protected 	CloseMenuListener		_close_listener = new CloseMenuListener();
    protected 	SaveMenuListener		_save_listener = new SaveMenuListener();
    protected 	SaveAsMenuListener		_save_as_listener = new SaveAsMenuListener();
    protected 	QuitMenuListener		_quit_listener = new QuitMenuListener();

    protected 	UndoMenuListener		_undo_listener = new UndoMenuListener();
    protected 	CutMenuListener			_cut_listener = new CutMenuListener();
    protected 	CopyMenuListener		_copy_listener = new CopyMenuListener();
    protected 	PasteMenuListener		_paste_listener = new PasteMenuListener();
    protected 	ClearMenuListener		_clear_listener = new ClearMenuListener();
    protected 	SelectAllMenuListener		_select_all_listener = new SelectAllMenuListener();
    protected 	InsertListener			_insert_listener = new InsertListener();
    protected 	DeleteListener			_delete_listener = new DeleteListener();

    protected 	PlayMenuListener		_play_listener = new PlayMenuListener();
    protected 	PlayContinuousMenuListener	_play_continuous_listener = new PlayContinuousMenuListener();
    protected 	StepMenuListener		_step_listener = new StepMenuListener();
    protected 	StopMenuListener		_stop_listener = new StopMenuListener();
    protected	InitCheckboxListener		_init_listener = new InitCheckboxListener();
    protected 	CALCATypeMenuListener		_CALCA_type_listener = new CALCATypeMenuListener();
    protected 	CALCTypeMenuListener		_CALC_type_listener = new CALCTypeMenuListener();
    protected 	MATHTypeMenuListener		_MATH_type_listener = new MATHTypeMenuListener();
    protected 	LOGICTypeMenuListener		_LOGIC_type_listener = new LOGICTypeMenuListener();
    
    protected	OnlineCheckboxListener		_online_listener = new OnlineCheckboxListener();
    protected 	UploadMenuListener		_upload_listener = new UploadMenuListener();
    protected 	DownloadMenuListener		_download_listener = new DownloadMenuListener();
    protected 	SettingsMenuListener		_settings_listener = new SettingsMenuListener();

    protected 	HelpMenuListener		_help_listener = new HelpMenuListener();
    protected 	AboutMenuListener		_about_listener = new AboutMenuListener();
    
    
    // Mouse Listener
    protected	MouseListener			_mouse_listener = new MouseListener();
    
    // User Interface Elements    
    private	JButton				_play, _play_continuous, _stop, _step, _upload, _download;
    private	JCheckBox			_initialize, _online;
    private	JTextField_Clip			_block;
    private	CP_Menu				_cp;
    
    private	JTabbedPane 			_tabbed_pane;
    
    // File Open/Save dialog box
    private	JFileChooser			_file_chooser = new JFileChooser();
    private	String				_file_name = null;

    // Current Focus for Copy and Paste
    private	FocusTask			_focus_listener = new FocusTask();
    private	Clipboard_Interface		_current_clipboard_focus = null;
    
    // CALC Block Virtual Machine Stuff   
    private	Machine				_machine = new Machine();
    
    private	Registers_Table_Model		_registers = new Registers_Table_Model(_machine);
    private	Registers_Table			_registers_table = new Registers_Table();
    
    private	Steps_Table_Model		_steps = new Steps_Table_Model(_machine, _registers);
    private	Steps_Table			_steps_table = new Steps_Table();
    
    private	Stack_Table_Model		_stack = new Stack_Table_Model();
    private	Stack_Table			_stack_table = new Stack_Table();

    private	Config_Table_Model		_config = new Config_Table_Model(_machine);
    private	Config_Table			_config_table = new Config_Table();
    
    private	JSplitPane 			_split_panel_horz;
    private	JSplitPane 			_split_panel_vert;
    
    private	Emulator			_emulator = new Emulator();
    
    // IO Drivers
    private	ICCDriver			_icc_driver = new ICCDriver();
    private	OmDriver 			_omget_driver = new OmDriver();

    private	Timer				_play_continuous_timer;
    
    // Frame checkboxes
    private	boolean				_online_selected = false;
    private	boolean				_init_selected = false;




    // Constructor for the frame
    public CALCViewFrame() {       
        _machine.addObserver(this);
                        
        setupWindow();
    }
    
    /**
        User Interface
    */
    
    // Builds the file menu for the window
    public void addFileMenuItems() {
    	_fileMenu = new JMenu(Constants.Get_String("FileMenu")); 

    	_miNew = new JMenuItem (Constants.Get_String("NewItem"));
        _miNew.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _fileMenu.add(_miNew).setEnabled(false);
        _miNew.addActionListener(_new_listener);
        
    	_miOpen = new JMenuItem (Constants.Get_String("OpenItem"));
        _miOpen.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _fileMenu.add(_miOpen).setEnabled(true);
        _miOpen.addActionListener(_open_listener);
        
    	_miClose = new JMenuItem (Constants.Get_String("CloseItem"));
        _miClose.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _fileMenu.add(_miClose).setEnabled(false);
        _miClose.addActionListener(_close_listener);
 
        _fileMenu.addSeparator();
        
     	_miSave = new JMenuItem (Constants.Get_String("SaveItem"));
        _miSave.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _fileMenu.add(_miSave).setEnabled(true);
        _miSave.addActionListener(_save_listener);
        
    	_miSaveAs = new JMenuItem (Constants.Get_String("SaveasItem"));
        _fileMenu.add(_miSaveAs).setEnabled(true);
        _miSaveAs.addActionListener(_save_as_listener);
       
        _fileMenu.addSeparator(); 
        
    	_miQuit = new JMenuItem (Constants.Get_String("QuitItem"));
        _miQuit.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _fileMenu.add(_miQuit).setEnabled(true);
        _miQuit.addActionListener(_quit_listener);
		
        _mainMenuBar.add(_fileMenu);
    }
	
    // Builds the edit menu for the window
    public void addEditMenuItems() {
        _editMenu = new JMenu(Constants.Get_String("EditMenu"));
        
   	_miUndo = new JMenuItem(Constants.Get_String("UndoItem"));
        _miUndo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _editMenu.add(_miUndo).setEnabled(true);
        _miUndo.addActionListener(_undo_listener);
        _editMenu.addSeparator();
        
    	_miCut = new JMenuItem(Constants.Get_String("CutItem"));
        _miCut.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _editMenu.add(_miCut).setEnabled(true);
        _miCut.addActionListener(_cut_listener);
        
    	_miCopy = new JMenuItem(Constants.Get_String("CopyItem"));
        _miCopy.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _editMenu.add(_miCopy).setEnabled(true);
        _miCopy.addActionListener(_copy_listener);
        
    	_miPaste = new JMenuItem(Constants.Get_String("PasteItem"));
        _miPaste.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _editMenu.add(_miPaste).setEnabled(true);
        _miPaste.addActionListener(_paste_listener);
        
    	_miClear = new JMenuItem(Constants.Get_String("ClearItem"));
        _editMenu.add(_miClear).setEnabled(true);
        _miClear.addActionListener(_clear_listener);
        _editMenu.addSeparator();
        
    	_miSelectAll = new JMenuItem(Constants.Get_String("SelectAllItem"));
        _miSelectAll.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _editMenu.add(_miSelectAll).setEnabled(true);
        _miSelectAll.addActionListener(_select_all_listener);

        _editMenu.addSeparator();

    	_miInsert = new JMenuItem(Constants.Get_String("InsertItem"));
        //_miInsert.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _editMenu.add(_miInsert).setEnabled(true);
        _miInsert.addActionListener(_insert_listener);

    	_miDelete = new JMenuItem(Constants.Get_String("DeleteItem"));
        //_miDelete.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _editMenu.add(_miDelete).setEnabled(true);
        _miDelete.addActionListener(_delete_listener);

        _mainMenuBar.add(_editMenu);
    }
    
    // Builds the edit menu for the window
    public void addControlsMenuItems() {
	_controlsMenu = new JMenu(Constants.Get_String("ControlsMenu"));
        
        // Apple bug workaround: http://developer.apple.com/techpubs/macosx/ReleaseNotes/java10.2.html
        Object radioIcon = UIManager.get("RadioButtonMenuItem.checkIcon");
        UIManager.put("RadioButtonMenuItem.checkIcon", UIManager.get("CheckBoxMenuItem.checkIcon"));
        _miInit = new JRadioButtonMenuItem(Constants.Get_String("InitItem"));
        UIManager.put("RadioButtonMenuItem.checkIcon", radioIcon);

	_miCALCA = new JRadioButtonMenuItem(Constants.Get_String("CALCAItem"));
	_miCALC = new JRadioButtonMenuItem(Constants.Get_String("CALCItem"));
	_miMATH = new JRadioButtonMenuItem(Constants.Get_String("MATHItem"));
        _miLOGIC = new JRadioButtonMenuItem(Constants.Get_String("LOGICItem"));


	_miPlay = new JMenuItem(Constants.Get_String("PlayItem"));
        _miPlay.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _controlsMenu.add(_miPlay).setEnabled(true);
        _miPlay.addActionListener(_play_listener);

	_miPlayContinuous = new JMenuItem(Constants.Get_String("PlayContinuousItem"));
        //_miPlayContinuous.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _controlsMenu.add(_miPlayContinuous).setEnabled(true);
        _miPlayContinuous.addActionListener(_play_continuous_listener);

	_miStep = new JMenuItem(Constants.Get_String("StepItem"));
        _miStep.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _controlsMenu.add(_miStep).setEnabled(true);
        _miStep.addActionListener(_step_listener);

	_miStop = new JMenuItem(Constants.Get_String("StopItem"));
        _miStop.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _controlsMenu.add(_miStop).setEnabled(true);
        _miStop.addActionListener(_stop_listener);
       
        _controlsMenu.addSeparator();

 
        _miInit.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _controlsMenu.add(_miInit).setEnabled(true);
        _miInit.addActionListener(_init_listener);

        _controlsMenu.addSeparator();

        //_miCALCA.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _controlsMenu.add(_miCALCA).setEnabled(true);
        _miCALCA.addActionListener(_CALCA_type_listener);
        _miCALCA.setSelected(true);
        
        //_miCALC.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _controlsMenu.add(_miCALC).setEnabled(true);
        _miCALC.addActionListener(_CALC_type_listener);

        //_miMATH.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _controlsMenu.add(_miMATH).setEnabled(true);
        _miMATH.addActionListener(_MATH_type_listener);

        //_miLOGIC.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _controlsMenu.add(_miLOGIC).setEnabled(true);
        _miLOGIC.addActionListener(_LOGIC_type_listener);

        _mainMenuBar.add(_controlsMenu);
    }

    // Builds the edit menu for the window
    public void addOnlineMenuItems() {
    	_onlineMenu = new JMenu(Constants.Get_String("OnlineMenu"));
        
        // Apple bug workaround: http://developer.apple.com/techpubs/macosx/ReleaseNotes/java10.2.html
        Object radioIcon = UIManager.get("RadioButtonMenuItem.checkIcon");
        UIManager.put("RadioButtonMenuItem.checkIcon", UIManager.get("CheckBoxMenuItem.checkIcon"));
        _miOnline = new JRadioButtonMenuItem(Constants.Get_String("OnlineItem"));
        UIManager.put("RadioButtonMenuItem.checkIcon", radioIcon);
	
        _miUpload = new JMenuItem(Constants.Get_String("UploadItem"));
	_miDownload = new JMenuItem(Constants.Get_String("DownloadItem"));
	_miSettings = new JMenuItem(Constants.Get_String("SettingsItem"));

    
        _miOnline.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _onlineMenu.add(_miOnline).setEnabled(true);
        _miOnline.addActionListener(_online_listener);

        _onlineMenu.addSeparator();

        //_miUpload.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _onlineMenu.add(_miUpload).setEnabled(true);
        _miUpload.addActionListener(_upload_listener);

        //_miDownload.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _onlineMenu.add(_miDownload).setEnabled(true);
        _miDownload.addActionListener(_download_listener);

        _onlineMenu.addSeparator();

        //_miSettings.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _onlineMenu.add(_miSettings).setEnabled(true);
        _miSettings.addActionListener(_settings_listener);

        _mainMenuBar.add(_onlineMenu);
    }

    // Builds the edit menu for the window
    public void addHelpMenuItems() {
    	_helpMenu = new JMenu(Constants.Get_String("HelpMenu"));

        _miHelp = new JMenuItem(Constants.Get_String("HelpItem"));
        _miAbout = new JMenuItem(Constants.Get_String("AboutItem"));
        
        //_miHelp.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _helpMenu.add(_miHelp).setEnabled(true);
        _miHelp.addActionListener(_help_listener);

        _helpMenu.addSeparator();

        //_miHelp.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        _helpMenu.add(_miAbout).setEnabled(true);
        _miAbout.addActionListener(_about_listener);

        _mainMenuBar.add(_helpMenu);
    }
    
    // Builds the popup menu for the window
    public void addPopupMenuItems() {
        _popupMenu = new JPopupMenu();
        
        _miUndoPop = new JMenuItem(Constants.Get_String("UndoItem"));
        _popupMenu.add(_miUndoPop).setEnabled(true);
        _miUndoPop.addActionListener(_undo_listener);
        _popupMenu.addSeparator();
        
    	_miCutPop = new JMenuItem(Constants.Get_String("CutItem"));
        _popupMenu.add(_miCutPop).setEnabled(true);
        _miCutPop.addActionListener(_cut_listener);
        
    	_miCopyPop = new JMenuItem(Constants.Get_String("CopyItem"));
        _popupMenu.add(_miCopyPop).setEnabled(true);
        _miCopyPop.addActionListener(_copy_listener);
        
    	_miPastePop = new JMenuItem(Constants.Get_String("PasteItem"));
        _popupMenu.add(_miPastePop).setEnabled(true);
        _miPastePop.addActionListener(_paste_listener);
        
    	_miClearPop = new JMenuItem(Constants.Get_String("ClearItem"));
        _popupMenu.add(_miClearPop).setEnabled(true);
        _miClearPop.addActionListener(_clear_listener);
        _popupMenu.addSeparator();
        
    	_miSelectAllPop = new JMenuItem(Constants.Get_String("SelectAllItem"));
        _popupMenu.add(_miSelectAllPop).setEnabled(true);
        _miSelectAllPop.addActionListener(_select_all_listener);

        _popupMenu.addSeparator();

    	_miInsertPop = new JMenuItem(Constants.Get_String("InsertItem"));
        _popupMenu.add(_miInsertPop).setEnabled(true);
        _miInsertPop.addActionListener(_insert_listener);

    	_miDeletePop = new JMenuItem(Constants.Get_String("DeleteItem"));
        _popupMenu.add(_miDeletePop).setEnabled(true);
        _miDeletePop.addActionListener(_delete_listener);
    }

	
    // Assembles the menu bar for the window
    public void addMenus() {
        addFileMenuItems();
        addEditMenuItems();
        addControlsMenuItems();
        addOnlineMenuItems();
        addHelpMenuItems();
        setJMenuBar (_mainMenuBar);
        
        addPopupMenuItems();
    }
 
    // Builds the execution tab
    public void addPanes(JTabbedPane tab) {
        // Registers table        
        _registers_table.setModel(_registers);
        _registers_table.SetColumnWidths();
        _registers_table.addFocusListener(_focus_listener);
        _registers_table.addMouseListener(_mouse_listener);
        JScrollPane registers_panel_scroller = new JScrollPane(_registers_table);

        // Statements table. Note that the registers are passed to the constructor because
        // the registers table is updated when the statements change.
        _steps_table.setModel(_steps);
        _steps_table.SetColumnWidths();
        _steps_table.addFocusListener(_focus_listener);
        _steps_table.addMouseListener(_mouse_listener);
        JScrollPane steps_panel_scroller = new JScrollPane(_steps_table);

        // Stack
        _stack_table.setModel(_stack);
        _stack_table.addFocusListener(_focus_listener);
        _stack_table.addMouseListener(_mouse_listener);
        JScrollPane stack_panel_scroller = new JScrollPane(_stack_table);

        // Build the Split Vertical Pane
        _split_panel_vert = new JSplitPane(JSplitPane.VERTICAL_SPLIT, registers_panel_scroller, stack_panel_scroller);
        _split_panel_vert.setOneTouchExpandable(true);

        // Build the Split Horizontal Pane
        _split_panel_horz = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, steps_panel_scroller, _split_panel_vert);
        _split_panel_horz.setOneTouchExpandable(true);

        tab.addTab(Constants.Get_String("ExecutionTab"), _split_panel_horz);
        
        _config_table.setModel(_config);
        _config_table.addFocusListener(_focus_listener);
        _config_table.SetColumnWidths();
        _config_table.addMouseListener(_mouse_listener);
        JScrollPane config_panel_scroller = new JScrollPane(_config_table);
        
        tab.addTab(Constants.Get_String("ConfigTab"), config_panel_scroller);
        
        
        // Set default editor for tables
        JTextField_Clip text_field = new JTextField_Clip();
        text_field.addFocusListener(_focus_listener);
        
        DefaultCellEditor editor = new DefaultCellEditor(text_field);
        
        _steps_table.setDefaultEditor(String.class, editor);
        _registers_table.setDefaultEditor(String.class, editor);
        _stack_table.setDefaultEditor(String.class, editor);
        _config_table.setDefaultEditor(String.class, editor);
    }

    // Builds the execution toolbar
    public void addExecutionToolbarPane(JToolBar toolbar) {
    
    	_play = new JButton(Constants.Get_String("PlayButton"), new Icon_Play());
        _play.addActionListener(_play_listener);
        _play.setToolTipText(Constants.Get_String("PlayButtonTip"));
        toolbar.add(_play);

        toolbar.addSeparator();

    	_play_continuous = new JButton(Constants.Get_String("PlayContinuousButton"), _loop_icon);
        _play_continuous.addActionListener(_play_continuous_listener);
        _play_continuous.setToolTipText(Constants.Get_String("PlayContinuousButtonTip"));
        toolbar.add(_play_continuous);

        toolbar.addSeparator();

    	_stop = new JButton(Constants.Get_String("StopButton"), new Icon_Stop());
        _stop.addActionListener(_stop_listener);
        _stop.setToolTipText(Constants.Get_String("StopButtonTip"));
        toolbar.add(_stop);

        toolbar.addSeparator();

    	_step = new JButton(Constants.Get_String("StepButton"), new Icon_Step());
        _step.addActionListener(_step_listener);
        _step.setToolTipText(Constants.Get_String("StepButtonTip"));
        toolbar.add(_step);

        toolbar.addSeparator();

    	_initialize = new JCheckBox(Constants.Get_String("InitializeCheckbox"));
        _initialize.addActionListener(_init_listener);
        _initialize.setToolTipText(Constants.Get_String("InitializeCheckboxTip"));
        toolbar.add(_initialize);
    }

    // Builds the connection toolbar
    public void addConnectionToolbarPane(JToolBar toolbar) {
    	_online = new JCheckBox(Constants.Get_String("OnlineCheckbox"));
        _online.addActionListener(_online_listener);
        _online.setToolTipText(Constants.Get_String("OnlineCheckboxTip"));
        toolbar.add(_online);

        toolbar.addSeparator();
        
        _cp = new CP_Menu();
        //_cp.setMaximumSize(_cp.getPreferredSize());
        toolbar.add(_cp);

        toolbar.addSeparator();

    	_block = new JTextField_Clip(Constants.Get_String("CompoundBlock"));
        _block.setToolTipText(Constants.Get_String("BlockTip"));
        _block.addFocusListener(_focus_listener);
        //_block.setMaximumSize(_block.getPreferredSize());
        _block.addMouseListener(_mouse_listener);
        toolbar.add(_block);
        
        // Sync Fonts
        _cp.setFont(_block.getFont());
        _cp.addMouseListener(_mouse_listener);


        toolbar.addSeparator();

    	_download = new JButton(Constants.Get_String("DownloadButton"), new Icon_Download());
        _download.addActionListener(_download_listener);
        _download.setToolTipText(Constants.Get_String("DownloadButtonTip"));
        toolbar.add(_download);
    
        toolbar.addSeparator();

    	_upload = new JButton(Constants.Get_String("UploadButton"), new Icon_Upload());
        _upload.addActionListener(_upload_listener);
        _upload.setToolTipText(Constants.Get_String("UploadButtonTip"));
        toolbar.add(_upload);
    }
 
    // Builds the contents for the window
    public void addPanes() {
        // Execution Toolbar
        JToolBar execution_panel = new JToolBar();
        addExecutionToolbarPane(execution_panel);
  
        // Connection Toolbar
        JToolBar connection_panel = new JToolBar();
        addConnectionToolbarPane(connection_panel); 
        
        // Assemble the total toolbar
        Box toolbar_panel = Box.createHorizontalBox();
        toolbar_panel.add(execution_panel);
        toolbar_panel.add(connection_panel);
        
        // Build the main panel
        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());

        main_panel.add(toolbar_panel, BorderLayout.NORTH);
        
        // Build the tabbed pane area
        _tabbed_pane = new JTabbedPane();
        main_panel.add(_tabbed_pane, BorderLayout.CENTER);
        
   	addPanes(_tabbed_pane);
        
        // Add all the panes
        getContentPane().add(main_panel);
    }

    // Assembles the menu bar for the window
    public void setupWindow() {
        setTitle(Constants.Get_String("MainWindow"));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Set default sizes
        setBounds(BORDER, BORDER, java.lang.Math.min(screenSize.width - 2*BORDER, 1000), screenSize.height - 2*BORDER);

        addMenus();
        addPanes();
        
        setVisible(true);

        _split_panel_horz.setDividerLocation(0.5F);
	_split_panel_vert.setDividerLocation(0.5F);

        // Repaint the window
        repaint();
        
        
        // Build non graphical elements
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
                
        // Looping timer
        _play_continuous_timer = new Timer (1000, new TimerTask());  // 1000 gets changed later
        _play_continuous_timer.setRepeats(true);
        _play_continuous_timer.setCoalesce(true);
        
        // File chooser
        _file_chooser.setCurrentDirectory(new File("."));
        
        // Action Listeners
	_online_listener.Sync_Checkboxes();
	_init_listener.Sync_Checkboxes();
    }
    
    /**
        Error Dialog
    */

    void Show_Error(String title, String message) {
        if (Constants.Get_Prefs().Display_Errors()) {
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
            Stop_Looping();
        }
    }
    
    
    /**
        Try to extract a compound name.
        @param comp_blk	Total compound block path.
    */    

    String Parse_Compound () {
        int separator = _block.getText().indexOf(":");
        return _block.getText().substring(0,separator);
    }

    /**
        Try to extract a block name.
        @param comp_blk	Total compound block path.
    */    

    String Parse_Block () {
        int separator = _block.getText().indexOf(":");
        return _block.getText().substring(separator+1);
    }
    
    

    /**
        Focus Listener
    */
    
    class FocusTask extends FocusAdapter {
        public void focusGained(FocusEvent newEvent) {   
        
            if (newEvent.getComponent() instanceof Clipboard_Interface &&
                _current_clipboard_focus != (Clipboard_Interface) newEvent.getComponent()) {
                
                try {  
                    if (_current_clipboard_focus != null)
                        _current_clipboard_focus.unfocus();
            
                    _current_clipboard_focus = (Clipboard_Interface) newEvent.getComponent();
                    _current_clipboard_focus.focus();
                }
                catch (Exception e) {
                
                }
            }
        }
    }

    /**
        Mouse Listener
    */
    
    class MouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent newEvent) {            
            if (newEvent.isPopupTrigger())
                _popupMenu.show(newEvent.getComponent(), newEvent.getX(), newEvent.getY());
        }
        
    }
    
    /**
        Observer message
    */
    
    public void update (Observable o, Object arg) {
        // Sync menu to type
        int type = _machine.Get_Type();
        
        _miCALCA.setSelected(false);
        _miCALC.setSelected(false);
        _miMATH.setSelected(false);
        _miLOGIC.setSelected(false);
        
        if (type == Machine.CALCA)	_miCALCA.setSelected(true);
        else if (type == Machine.CALC)	_miCALC.setSelected(true);
        else if (type == Machine.MATH)	_miMATH.setSelected(true);
        else if (type == Machine.LOGIC)	_miLOGIC.setSelected(true);
    }

    /**
        Timer Actions
    */
    
    void Start_Looping () {
        _play_continuous_timer.setDelay(Constants.Get_Prefs().Get_Delay() * 1000);
        _play_continuous_timer.start();
        
        _loop_icon.Set_Color(new Color(0.0F,0.7F,0.0F));
        _play_continuous.repaint();
    }

    void Stop_Looping () {
        _play_continuous_timer.stop();

        _loop_icon.Set_Color(Color.red);
        _play_continuous.repaint();
    }
    
    class TimerTask implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            _play_listener.actionPerformed(newEvent);
        }
    }

    /**
        Button and menu Actions
    */
    class NewMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
        }
    }

    class OpenMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            _file_chooser.rescanCurrentDirectory();
            int result = _file_chooser.showOpenDialog(CALCViewFrame.this);
    
            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = _file_chooser.getSelectedFile().getPath();
                                
                try {
                    InputStreamReader in = new InputStreamReader(new FileInputStream(filename));
                    
                    _icc_driver.Input_ICC_Stream(in, _machine);
                    _file_name = filename;                    
                    
                    _registers.Clear_All_Status();
                }
                catch (IOException e) {
                
                }
                
            }
        }
    }

    class CloseMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {

        }
    }

    class SaveMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            if (_file_name != null) 
                SaveFile (_file_name);
            else 
                _save_as_listener.actionPerformed(null);
        }
    }

    class SaveAsMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            _file_chooser.rescanCurrentDirectory();
            int result = _file_chooser.showSaveDialog(CALCViewFrame.this);
    
            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = _file_chooser.getSelectedFile().getPath();
                SaveFile (filename);
            }
        }
    }

    class QuitMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            
            int choice = JOptionPane.showConfirmDialog((Component) null,
                Constants.Get_String("QuitSaveText"),
                Constants.Get_String("QuitSaveWindowText"),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
                
            if (choice == JOptionPane.YES_OPTION) {
                _save_listener.actionPerformed(null);
            	System.exit(0);
            } else if (choice == JOptionPane.NO_OPTION)	
            	System.exit(0);
        }
    }


    class UndoMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {

        }
    }

    class CutMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            if (_current_clipboard_focus != null)	
                _current_clipboard_focus.cut();
        }
    }

    class CopyMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            if (_current_clipboard_focus != null)	
                _current_clipboard_focus.copy();
        }
    }

    class PasteMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            if (_current_clipboard_focus != null)	
                _current_clipboard_focus.paste();
        }
    }

    class ClearMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            if (_current_clipboard_focus != null)	
                _current_clipboard_focus.clear();
        }
    }

    class SelectAllMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            if (_current_clipboard_focus != null)	
                _current_clipboard_focus.selectAll();
        }
    }

    class InsertListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            if (_steps_table.getSelectedRowCount() > 0) {
                
                int    	count      	= _steps_table.getSelectedRowCount();
                int[] 	rowIndices  	= _steps_table.getSelectedRows(); 

                java.util.Arrays.sort(rowIndices);
                
                for (int row_index = 0; row_index < count; ++row_index) {
                    int row = rowIndices[row_index];
                    Mem_Step dst_step;
    
                    // Blank jumps going to step 50 because it'll disappear
                    for (int r = 0; r < _machine.Get_Num_Steps(); ++r) {
                        dst_step = _steps.Get_Step(r+1);
                        dst_step.Blank_Jump(_machine.Get_Num_Steps());
                    }
                    
                    _steps.Compile_Steps();
        
                    // Copy last rows down
                    for (int r = _machine.Get_Num_Steps() - 1; r > row; --r) {
                        dst_step = _steps.Get_Step(r+1);
                        Mem_Step src_step = _steps.Get_Step(r+1-1);
                        dst_step.Set_Configuration(src_step.Get_Configuration());
                    }
            
                    // Clear Rows
                    dst_step = _steps.Get_Step(row+1);
                    dst_step.Set_Configuration("");  
                    
                    _steps.Compile_Steps();
            
                    // Offset indices
                    for (int r = 0; r < _machine.Get_Num_Steps(); ++r) {
                        dst_step = _steps.Get_Step(r+1);
                        dst_step.Offset_Range(row+1, 1, _machine.Get_Num_Steps());
                    }

                    _steps.Compile_Steps();                
                }
                
                _machine.FireChanged ();
            }
        }
    }

    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            if (_steps_table.getSelectedRowCount() > 0) {
                
                int    	count      	= _steps_table.getSelectedRowCount();
                int[] 	rowIndices  	= _steps_table.getSelectedRows(); 
                
                java.util.Arrays.sort(rowIndices);
                
                for (int row_index = count - 1; row_index >= 0; --row_index) {
                    int row = rowIndices[row_index];
                    Mem_Step dst_step;

                    // Blank jumps going to row
                    for (int r = 0; r < _machine.Get_Num_Steps(); ++r) {
                        dst_step = _steps.Get_Step(r+1);
                        dst_step.Blank_Jump(row+1);
                    }
    
                    _steps.Compile_Steps();
    
                    // Copy last rows up
                    for (int r = row; r < _machine.Get_Num_Steps() - 1; ++r) {
                        dst_step = _steps.Get_Step(r+1);
                        Mem_Step src_step = _steps.Get_Step(r+1+1);
                        dst_step.Set_Configuration(src_step.Get_Configuration());
                    }
            
                    // Clear Rows
                    dst_step = _steps.Get_Step(_machine.Get_Num_Steps());
                    dst_step.Set_Configuration("");  
                                        
                    _steps.Compile_Steps();
            
                    // Offset indices
                    for (int r = 0; r < _machine.Get_Num_Steps(); ++r) {
                        dst_step = _steps.Get_Step(r+1);
                        dst_step.Offset_Range(row+1,-1, _machine.Get_Num_Steps());
                    }
                    
                    _steps.Compile_Steps();
                }
                
                _machine.FireChanged ();
            }
        }
    }

    class PlayMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {        
            _emulator.Set_Play();
            _emulator.run();
            
            repaint();
        }
    }

    class PlayContinuousMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {            
            Start_Looping ();
        }
    }

    class StepMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            Stop_Looping ();
        
            _emulator.Set_Step();
            _emulator.run();

            repaint();
        }
    }

    class StopMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            // Clear the visited flags
            Stop_Looping ();
            
            _steps.Stop_Running_Steps ();
            _emulator.Set_Stop();

            repaint();
        }
    }
    
    class InitCheckboxListener implements ActionListener {
        void Sync_Checkboxes () {
            _miInit.setSelected(_init_selected);
            _initialize.setSelected(_init_selected);
        }

        public void actionPerformed(ActionEvent newEvent) {
            _init_selected = !_init_selected;
            
            _miInit.setSelected(_init_selected);
            _initialize.setSelected(_init_selected);
        }
    }

    class CALCATypeMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            _machine.Set_Up_Memory_CALCA();
        }
    }

    class CALCTypeMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            _machine.Set_Up_Memory_CALC();
        }
    }

    class MATHTypeMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            _machine.Set_Up_Memory_MATH();
        }
    }

    class LOGICTypeMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            _machine.Set_Up_Memory_LOGIC();
        }
    }


    class OnlineCheckboxListener implements ActionListener {    
        void Sync_Checkboxes () {
            _miOnline.setSelected(_online_selected);
            _online.setSelected(_online_selected);
        }
    
        public void actionPerformed(ActionEvent newEvent) {
            _online_selected = !_online_selected;
            
            _miOnline.setSelected(_online_selected);
            _online.setSelected(_online_selected);
        }
    }

    class UploadMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
        
            // Check for error in any step
            if (_steps.Has_Error ()) {
                int choice = JOptionPane.showConfirmDialog((Component) null,
                    Constants.Get_String("ErrorContinueText"),
                    Constants.Get_String("ErrorContinueWindowText"),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                    
                if (choice == JOptionPane.CANCEL_OPTION)	
                    return;	// Don't continue
            }
        
            //Prompt for Add or Modify
            Object[] options = {
                                    Constants.Get_String("AddButton"),
                                    Constants.Get_String("ModifyButton"),
                                    Constants.Get_String("CancelButton")
                                };
                                
            int choice = JOptionPane.showOptionDialog((Component) null,
                    Constants.Get_String("AddModifyText"),
                    Constants.Get_String("AddModifyWindowText"),
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]);
                
            try {

                // Check result if we have to do anything            
                switch (choice) {
                    case 1:	_icc_driver.Put_ICC_Dump((String) _cp.getSelectedItem(), Parse_Compound(), Parse_Block(), _machine, ICCDriver.MODIFY);
                                break;
                                
                    case 0:	_icc_driver.Put_ICC_Dump((String) _cp.getSelectedItem(), Parse_Compound(), Parse_Block(), _machine, ICCDriver.ADD);
                                break;
                                
                    default:	break;
                }
                
            }
            catch (Exception_ICCError e) {
                Show_Error("Exception", Constants.Get_String("ExceptionICCMsg"));
            }
            catch (Exception_CMPDBLK e) {
                Show_Error("Exception", Constants.Get_String("ExceptionCMPDBLK"));
            }
            
        }
    }

    class DownloadMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {            
            try {
                _icc_driver.Get_ICC_Dump((String) _cp.getSelectedItem(), Parse_Compound(), Parse_Block(), _machine);
                
                _registers.Clear_All_Status();
            }
            catch (Exception_ICCError e) {
                Show_Error("Exception", Constants.Get_String("ExceptionICCMsg"));
            }
            catch (Exception_CMPDBLK e) {
           	Show_Error("Exception", Constants.Get_String("ExceptionCMPDBLK"));
            }

        }
    }
    
    class SettingsMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            Constants.Get_Prefs().Show_Prefs ();
        }
    }

    class HelpMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            new HTMLFrame(HTMLFrame.class.getResource(Constants.Get_String("InstructionsPath")));
        }
    }

    class AboutMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent newEvent) {
            Constants.Get_Splash().Show_Splash ();
        }
    }


 
 
        
    public void SaveFile (String filename) {
        _file_name = filename;
                
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename));
            _icc_driver.Output_ICC_Stream(Parse_Compound(), Parse_Block(), _machine, out, ICCDriver.FILE);
            out.close();
        }
        catch (IOException e) {
        
        }
        catch (Exception_CMPDBLK e) {
            Show_Error("Exception", Constants.Get_String("ExceptionCMPDBLK"));
        }
    }
        
        
    /**
        The emulator execution class
    */        
    
    class Emulator
    {
        public static final int	PLAY = 1;
        public static final int	STOP = 2;
        public static final int	STEP = 3;
        public static final int	ERROR = 4;
        
        private	int			_state = STOP;
        private	boolean			_begin = true;
        
        void	Set_Play () {	_state = PLAY;			}
        void	Set_Stop () {	_state = STOP;	_begin = true;	}
        void	Set_Step () {	_state = STEP;			}
                
        public void run () {        
            try {
            
                if (_begin) {
                    // Set initialize flags
                    boolean initializing = _init_selected;
                    _steps.Set_Initializing(initializing);

                    // Get Timini if there is one
                    boolean timini = false;
                    if (_machine.Has_Memory (Constants.TIMINI))
                        timini = _machine.Index_To_Memory(Constants.TIMINI).Get_Value() != 0.0F;
                                      
                    _stack.Clear ();
                    _steps.Begin_Running_Steps (timini);
                    
                    // If we are online, load values from CP
                    if (_online_selected) {
                        try {
                            _omget_driver.Get_Registers (Parse_Compound(), Parse_Block(), _machine);
                        }
                        catch (Exception_OmError e) {
                            Show_Error("Exception", Constants.Get_String("ExceptionOmMsg"));
                        }
                        catch (Exception_CMPDBLK e) {
                            Show_Error("Exception", Constants.Get_String("ExceptionCMPDBLK"));
                        }
                    }

                    // Clear errors
                    _machine.Index_To_Memory(Constants.PERROR).Set_Value(0.0F);

                    // Done beginning
                    _begin = false;
                }

                // Clear all modified flags
                _registers.Clear_Modified ();
            
                while (_state == PLAY || _state == STEP) {
                    Mem_Step step = _steps.Get_Current_Step();
                    
                    // First check for error
                    if (step.Get_Error()) {
                        _state = ERROR;
                        Stop_Looping ();
                        break;
                    }
                    
                    // Check for break point
                    if (_state == PLAY && step.Get_Break() && !step.Get_Visited()) {
                        _state = STOP;
                        step.Set_Visited(true);
                        Stop_Looping ();
                        break;                    
                    }
                    
                    step.Set_Visited(true);
                    
                    Instruction_Interface step_impl = _machine.Index_To_Instruction(step.Get_Opcode());
                
                    step_impl.Run(_steps, _stack, _machine);
                     
                    // If we are done
                    if (_steps.Is_Done()) {
                        _begin = true;
                        _steps.Jump_Step_Backward(1);
                        _state = STOP;
                        
                        // Write the results if necessary
                        if (_online_selected) {
                            try {
                                _omget_driver.Put_Registers (Parse_Compound(), Parse_Block(), _machine);
                            }
                            catch (Exception_OmError e) {
                                Show_Error("Exception", Constants.Get_String("ExceptionOmMsg"));
                            }
                            catch (Exception_CMPDBLK e) {
                                Show_Error("Exception", Constants.Get_String("ExceptionCMPDBLK"));
                            }
                        }                   
                    }
                    
                    // If we were just stepping
                    if(_state == STEP)
                        break;
                    
                }
                
            }

            catch (Exception_Index_Error e) {
                Show_Error("Exception", Constants.Get_String("ExceptionIndexMsg"));
            }
            catch (Exception_LN_Error e) {
                Show_Error("Exception", Constants.Get_String("ExceptionLNMsg"));
            }
            catch (Exception_Stack_Overflow e) {
                Show_Error("Exception", Constants.Get_String("ExceptionStackOverflowMsg"));
            }
            catch (Exception_LOG_Error e) {
                Show_Error("Exception", Constants.Get_String("ExceptionLOGMsg"));
            }
            catch (Exception_Bit_Error e) {
                Show_Error("Exception", Constants.Get_String("ExceptionBitMsg"));
            }
            catch (Exception_SQRT_Error e) {
                Show_Error("Exception", Constants.Get_String("ExceptionSQRTMsg"));
            }
            catch (Exception_EXP_Error e) {
                Show_Error("Exception", Constants.Get_String("ExceptionEXPMsg"));
            }
            catch (Exception_ACOS_Error e) {
                Show_Error("Exception", Constants.Get_String("ExceptionACOSMsg"));
            }
            catch (Exception_ASIN_Error e) {
                Show_Error("Exception", Constants.Get_String("ExceptionASINMsg"));
            }
            catch (Exception_Syntax_Error e) {
                Show_Error("Exception", Constants.Get_String("ExceptionSyntaxErrMsg"));
            }
            catch (Exception_Stack_Underflow e) {
                Show_Error("Exception", Constants.Get_String("ExceptionStackUnderflowMsg"));
            }
            catch (Exception_Illegal_Symbol e) {
                Show_Error("Exception", Constants.Get_String("ExceptionIllegalSymbolMsg"));
            }
            catch (Exception_Illegal_Branch e) {
                Show_Error("Exception", Constants.Get_String("ExceptionIllegalBranchMsg"));
            }
            catch (Exception_DIV_Error e) {
                Show_Error("Exception", Constants.Get_String("ExceptionDIVMsg"));
            }
            finally {
                _state = STOP;
            }
        }
    }



}


