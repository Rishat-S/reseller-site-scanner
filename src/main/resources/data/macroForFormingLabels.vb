Sub FormatTheBordersSizeOfLabel(destinationSheet)
  MsgBox "Start format borders of label: " & destinationSheet
  
  For i = 1 To 6
  If i Mod 2 = 0 Then
      Sheets(destinationSheet).Columns(i).ColumnWidth = 29.29
    Else
      Sheets(destinationSheet).Columns(i).ColumnWidth = 0.25
    End If
  Next i
 
  Dim isFirst As Integer
  Dim numberOfFilledRows As Integer
  isFirst = 0
  numberOfFilledRows = Sheets(destinationSheet).Cells(Sheets(destinationSheet).Rows.Count, 2).End(xlUp).Row
  For i = 1 To numberOfFilledRows
    isFirst = isFirst + 1
    If isFirst = 1 Then
      Sheets(destinationSheet).Rows(i).RowHeight = 45
    Else
      Sheets(destinationSheet).Rows(i).RowHeight = 30
    End If
    
    If isFirst = 3 Then
    isFirst = 0
    End If
  Next i
End Sub

Sub Main()
  MsgBox "Start label creator"
  
  Dim redemption As String
  Dim organizer As String
  Dim sourceSheet As String
  Dim destinationSheet As String
  Dim numberOfColumn As Integer
  Dim firstRowOfLabel As Integer
  Dim numberOfFirstFilledRow As Integer
  Dim numberOfFilledRows As Integer
  Dim resellerName As String
'  Dim rangeForFormat As String
  
  sourceSheet = ActiveSheet.Name
  resellerName = Sheets(sourceSheet).Cells(1, 2)
  
  If resellerName = "Вадим Аминов" Then
    destinationSheet = "Бир_В"
    organizer = " руб. орг. Аминов"
  ElseIf resellerName = "Татьяна Дедюхина" Then
    destinationSheet = "Бирки_Т"
    organizer = "/ орг. Дедюхина"
  ElseIf resellerName = "Лариса Машина" Then
    destinationSheet = "Бирки"
    organizer = "/ орг. Дедюхина"
  Else
    MsgBox "Reseller is not match. Exit."
    Exit Sub
  End If

  redemption = Sheets(sourceSheet).Cells(1, 3)
  numberOfFilledRows = ActiveSheet.Cells(ActiveSheet.Rows.Count, 1).End(xlUp).Row - 1
  numberOfFirstFilledRow = 6
  numberOfColumn = 0
  firstRowOfLabel = 1

  MsgBox "Закупка № " & redemption & " " & resellerName & " " & numberOfFilledRows - 3 & " Labels"

  Sheets(destinationSheet).Cells.Clear

  For i = numberOfFirstFilledRow To numberOfFilledRows

    numberOfColumn = numberOfColumn + 2

    With Sheets(destinationSheet).Cells(firstRowOfLabel, numberOfColumn)
      .Value2 = Sheets(sourceSheet).Cells(i, 1)
      .WrapText = True
      .Borders(xlEdgeTop).Weight = xlMedium
      .Borders(xlEdgeLeft).Weight = xlMedium
      .Borders(xlEdgeRight).Weight = xlMedium
      .Borders(xlEdgeBottom).Weight = xlHairline
    End With

    If resellerName = "Вадим Аминов" Then
        With Sheets(destinationSheet).Cells(firstRowOfLabel + 1, numberOfColumn)
            .Value2 = Sheets(sourceSheet).Cells(i, 3) & " = " & Sheets(sourceSheet).Cells(i, 4) & organizer
            .WrapText = True
            .Borders(xlEdgeLeft).Weight = xlMedium
            .Borders(xlEdgeRight).Weight = xlMedium
            .Borders(xlEdgeBottom).Weight = xlHairline
        End With
    ElseIf resellerName = "Татьяна Дедюхина" Then
        With Sheets(destinationSheet).Cells(firstRowOfLabel + 1, numberOfColumn)
            .Value2 = Sheets(sourceSheet).Cells(i, 3) & " " & organizer
            .WrapText = True
            .Borders(xlEdgeLeft).Weight = xlMedium
            .Borders(xlEdgeRight).Weight = xlMedium
        .Borders(xlEdgeBottom).Weight = xlHairline
        End With
    ElseIf resellerName = "Лариса Машина" Then
        With Sheets(destinationSheet).Cells(firstRowOfLabel + 1, numberOfColumn)
            .Value2 = Sheets(sourceSheet).Cells(i, 3) & " " & organizer
            .WrapText = True
            .Borders(xlEdgeLeft).Weight = xlMedium
            .Borders(xlEdgeRight).Weight = xlMedium
            .Borders(xlEdgeBottom).Weight = xlHairline
        End With
    Else
        MsgBox "Reseller is not match. Exit."
        Exit Sub
    End If

    With Sheets(destinationSheet).Cells(firstRowOfLabel + 2, numberOfColumn)
      .Value2 = redemption & " Офис: " & Sheets(sourceSheet).Cells(i, 2)
      .Cells(firstRowOfLabel + 2, numberOfColumn).WrapText = True
      .Borders(xlEdgeLeft).Weight = xlMedium
      .Borders(xlEdgeRight).Weight = xlMedium
      .Borders(xlEdgeBottom).Weight = xlMedium
    End With

    If numberOfColumn = 6 Then
      numberOfColumn = 0
      firstRowOfLabel = firstRowOfLabel + 3
    End If

  Next i

  FormatTheBordersSizeOfLabel destinationSheet

End Sub
