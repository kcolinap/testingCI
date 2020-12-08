@login @regression
Feature: Login

  Contempla los TCs que se encuentran en la seccion Login --> Email/ usuario y Contraseña

  @walletint @stage
  Scenario Outline: Login - casos negativos
  Given Resetear app "<resetearApp>"
  When Usuario ingresa "<user>" y "<password>"
  Then Validar que boton iniciar sesion esta "<condition>"
  Then Mensaje de error en input de pantalla "login" se encuentra "<legend>"
  Examples:
  |             user      |       password    |       condition       |   legend  | resetearApp |
  |                       |     111Cc-c11     |     deshabilitado     |     f     |     si      |
  |           userdt31    |                   |     deshabilitado     |    f      |             |
  |           userdt31    |      Eder-876     |       habilitado      |     t     |             |
  |           userdt30    |      111Cc-11     |       habilitado      |    t      |             |
  |demotest31@yopmail.com |                   |      deshabilitado    |    f      |             |
  |demotest31@yopmail.com |     122Cc-c11     |       habilitado      |    t      |             |
