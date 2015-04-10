Const ForReading = 1, ForWriting = 2, ForAppending = 8
Const TristateUseDefault = -2, TristateTrue = -1, TristateFalse = 0

function create_empty_file(sLocation)
  set objFSO = Createobject("Scripting.FileSystemObject")
  'If objFSO.Fileexists(sLocation) Then objFSO.DeleteFile sLocation
	Set locFile = objFSO.CreateTextFile(sLocation, True)
	locFile.Close
  Set objFSO = Nothing
end function

function download(sFileURL, sLocation, async)
  set objXMLHTTP = CreateObject("MSXML2.XMLHTTP")
  objXMLHTTP.open "GET", sFileURL, async
  on error resume next
	dim cnt : cnt = 1
  objXMLHTTP.send()
  if err.number = 0 then
    do until ((objXMLHTTP.Status = 200) or (cnt < 2))
      wscript.echo objXMLHTTP.Status
      wcript.sleep(200)
			cnt = cnt+1
    loop
    if objXMLHTTP.Status = 200 Then
      set objADOStream = CreateObject("ADODB.Stream")
      'objADOStream.Charset = "utf-8"
      objADOStream.Charset = "Windows-1252"
      objADOStream.Open
      objADOStream.Type = 1
      objADOStream.Write objXMLHTTP.ResponseBody
      objADOStream.Position = 0    
      set objFSO = Createobject("Scripting.FileSystemObject")
      If objFSO.Fileexists(sLocation) Then objFSO.DeleteFile sLocation
      Set objFSO = Nothing
      objADOStream.SaveToFile sLocation
      objADOStream.Close
      set objADOStream = Nothing
      download = true
    end if
  else
    download = false
  end if
  set objXMLHTTP = Nothing
end function

function search_and_save_res(sInFile, sOutFile)
  Set fso = CreateObject("Scripting.FileSystemObject")
	Set inFile = fso.OpenTextFile(sInFile, ForReading)
	'Set outFile = fso.CreateTextFile(sOutFile, true)
	Dim div_founded : div_founded = 0
  Set re = new regexp
	re.Pattern = "<a\s+href=""(http://.*?)""[^>]+>(\s*\n|.+?\s*)</a>"
	Do While inFile.AtEndOfStream <> True
    TextLine = inFile.ReadLine
		pos = InStr(TextLine,"street_nomera cf")
		if (pos > 0) then
			div_founded = 1
    end if
		if (div_founded) then
			pos = InStr(TextLine,"</div>")
			if (pos > 0) then
				exit Do
			else
				set matches = re.Execute(TextLine) 
				for c = 0 to matches.Count-1 
					Dim outFile
					Set outFile = fso.OpenTextFile(sOutFile, ForAppending, True)
					Dim streetName
					streetName = Mid(matches(c).SubMatches(0), 38)
					if (Mid( streetName, 1, 1) = "/") then
						streetName = Mid(streetName, 2)
					end if
					pos = InStr(streetName, "/")
					if pos > 1 then
						streetName = Mid(streetName, 1, pos-1)
					end if
					outFile.WriteLine(matches(c).SubMatches(1) +"|"+ streetName)
				  outFile.Close	
				next
			end if
		end if
  Loop
  inFile.Close	
end function



dim strFile : strFile = "map.by.grodno.streets.html"
dim locFile : locFile = "read_street_points.html"
dim outFile : outFile = "imp_point_street.csv"
dim cnt : cnt = 1

Set fso = CreateObject("Scripting.FileSystemObject")
Set streetsFile = fso.OpenTextFile(strFile, ForReading, False, -2)
Do While streetsFile.AtEndOfStream <> True
  urlLine = streetsFile.ReadLine
  'urlLine = "http://map.by/streets/Гродно/1/ул.%201%20Мая"
	'wscript.echo urlLine
	if (Len(urlLine) > 10) then
    if download(urlLine, locFile, false) then
      'wscript.echo "download ok"
    	search_and_save_res locFile, outFile
    else
			wscript.echo urlLine
      wscript.echo "download nok"
    end if
  end if
	cnt = cnt + 1
	if cnt > 900 then
		exit Do
	end if
Loop
streetsFile.Close	
wscript.echo "DONE!"
