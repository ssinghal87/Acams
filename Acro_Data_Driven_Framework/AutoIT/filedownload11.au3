#region --- Au3Recorder generated code Start (v3.3.9.5 KeyboardLayout=00000409)  ---

#region --- Internal functions Au3Recorder Start ---
Func _Au3RecordSetup()
Opt('WinWaitDelay',100)
Opt('WinDetectHiddenText',1)
Opt('MouseCoordMode',0)
Local $aResult = DllCall('User32.dll', 'int', 'GetKeyboardLayoutNameW', 'wstr', '')
If $aResult[1] <> '00000409' Then
  MsgBox(64, 'Warning', 'Recording has been done under a different Keyboard layout' & @CRLF & '(00000409->' & $aResult[1] & ')')
EndIf

EndFunc

Func _WinWaitActivate($title,$text,$timeout=0)
	WinWait($title,$text,$timeout)
	If Not WinActive($title,$text) Then WinActivate($title,$text)
	WinWaitActive($title,$text,$timeout)
EndFunc

_AU3RecordSetup()
#endregion --- Internal functions Au3Recorder End ---

_WinWaitActivate("CMS Card - Internet Explorer","Address Combo Contro")
MouseClick("left",1315,490,1)

_WinWaitActivate("CMS Card - Internet Explorer","Address Combo Contro")
MouseClick("left",1266,559,1)

Sleep(5000)

_WinWaitActivate("CMS Card - Internet Explorer","Address Combo Contro")
MouseClick("left",1052,713,1)
MouseClick("left",1080,695,1)

_WinWaitActivate("Save As","Namespace Tree Contr")
MouseClick("left",528,455,1)
#endregion --- Au3Recorder generated code End ---
