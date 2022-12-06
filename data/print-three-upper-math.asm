; define COLOR_Black 0
; define COLOR_White 1
; define COLOR_Red 2
; define COLOR_Cyan 3
; define COLOR_Purple 4
; define COLOR_Green 5
; define COLOR_Blue 6
; define COLOR_Yellow 7
; define COLOR_Orange 8
; define COLOR_Brown 9
; define COLOR_LightRed 10
; define COLOR_DarkGrey 11
; define COLOR_Grey 12
; define COLOR_LightGreen 13
; define COLOR_LightBlue 14
; define COLOR_LightGrey 15


; define SCREEN TODO


  ; Screen(0) = White
  LDA #COLOR_White
  STA $200

  ; Screen(1) = Green
  LDA #COLOR_Green
  STA $201

  ; Screen(2) = Orange
  LDA #COLOR_Orange
  STA $202
