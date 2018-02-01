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
_WinWaitActivate("Welcome to ACAMS - Internet Explorer","Address Combo Contro")
MouseClick("left",1321,433,1)
MouseUp("left")
MouseMove(1288,497)
MouseDown("left")
MouseDown("left")
MouseMove(1287,497)
MouseUp("left")
MouseUp("left")



Sleep(5000)

_WinWaitActivate("Welcome to ACAMS - Internet Explorer","Address Combo Contro")
MouseClick("left",1047,710,1)
MouseClick("left",1100,687,1)



_WinWaitActivate("Save As","Namespace Tree Contr")
MouseClick("left",1220,691,1)
Sleep(2000)
#endregion --- Au3Recorder generated code End ---
