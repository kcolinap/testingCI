@regression @onboarding
Feature: Onboarding

  Como usuario quiero poder probar como se comporta el onboarding
  para usuarios que inician por primera vez la aplicacion

  @walletint
  Scenario: Validar que se muestre el onboarding - C24377
    Given Resetear app "si"
    Then Usuario esta en pantalla "onboarding"

  @walletint
  Scenario Outline: <description> - C24388
    Given Resetear app "si"
    And Usuario esta en pantalla "onboarding"
    When Swipe hasta pantalla <nroPage>
    Then Validar elementos de pantalla <nroPage>
    Examples:
      | description                                      | nroPage | testid         |
      | Validar elementos de 1er pantalla del onboarding | 1       | C24379         |
      | Validar elementos de 2da pantalla del onboarding | 2       | C24380, C24381 |
      | Validar elementos de 3ra pantalla del onboarding | 3       | C24382, C24383 |
      | Validar elementos de 4ta pantalla del onboarding | 4       | C24384, C24385 |


  @walletint
  Scenario Outline: <Description> - C24386, C24388
    Given Resetear app "<resetApp>"
    When Usuario esta en pantalla "onboarding"
    When Presionar boton "<typeButton>"
    Then Pulsar back en "<typeBack>" en pantalla "<screen>"
    Then Usuario esta en pantalla "login"
    Examples:
      | Description                                                     | resetApp | typeButton | typeBack    | screen             |
      | Validar boton REGISTRARSE y Back desde el boton de la app       |          | REGISTER   | app         | REGISTRATION_EMAIL |
      | Validar boton REGISTRARSE y Back desde el boton del dispositivo | SI       | REGISTER   | dispositivo | REGISTRATION_EMAIL |


  @walletint
  Scenario: Validar boton INICIAR SESION - C24387
    Given Resetear app "SI"
    When Usuario esta en pantalla "onboarding"
    When Presionar boton "LOGIN"
    Then Usuario esta en pantalla "login"