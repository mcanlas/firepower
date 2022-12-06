define COLOR_Black      $00
define COLOR_White      $01
define COLOR_Red        $02
define COLOR_Cyan       $03
define COLOR_Purple     $04
define COLOR_Green      $05
define COLOR_Blue       $06
define COLOR_Yellow     $07
define COLOR_Orange     $08
define COLOR_Brown      $09
define COLOR_LightRed   $0A
define COLOR_DarkGrey   $0B
define COLOR_Grey       $0C
define COLOR_LightGreen $0D
define COLOR_LightBlue  $0E
define COLOR_LightGrey  $0F


define SCREEN $200


  ; Screen(0) = White
  LDA #COLOR_White ; a = White
  STA SCREEN+0     ; Screen(0) = a

  ; Screen(1) = Green
  LDA #COLOR_Green ; a = Green
  STA SCREEN+1     ; Screen(1) = a

  ; Screen(2) = Orange
  LDA #COLOR_Orange ; a = Orange
  STA SCREEN+2      ; Screen(2) = a
