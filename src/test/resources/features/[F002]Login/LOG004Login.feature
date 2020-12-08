@ignore @login @regression
Feature: Login

  Flujos happy path de:
  -Login
  -Bloqueo de Login
  -Recupero de contrasenia

  Background:
    Given Resetear app "si"


  @ignore @walletint @stage @smoke
  Scenario: Loguearse en la App
    When Usuario "any" inicia sesion
    Then Usuario esta en pantalla "dashboard"

  @walletint @stage @smoke
  Scenario: Loguearse en la App
    Given Find user type "any" and set preReq ""
    When Iniciar sesion
    Then Usuario esta en pantalla "dashboard"

  @walletint @stage @smoke
  Scenario: Bloqueo de login
    When Usuario intenta loguearse 5 veces
    Then Validar que el intento de login esta "BLOCKED"
    And Validar redirecionamiento del mensaje de bloqueo de login


  @walletint @stage @smoke
  Scenario Outline: <descripcion> - <testID>

    Given Usuario pulsa olvide contrasenia
    And Ingresa email "<email>"
    And Tap recuperar contrasenia
    And Ir al link de recuperacion de clave
    And Ingresa clave "<clave>"
    And Validar clave "<tipoclave>"
    And Click boton circulo siguiente en pantalla "<pantalla>"
    Then Validar cambio de contrasenia
    Examples:
      | descripcion          | testID         | email    | clave     | pantalla   | tipoclave |
      | Olvido de Contraseña | C585/C563/C580 | correcto | Test-0000 | cambiopass | correcta  |
