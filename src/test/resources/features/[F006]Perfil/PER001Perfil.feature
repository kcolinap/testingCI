@profile @regression
Feature: Perfil

  Contienes los TC incluidos en la seccion:

  Perfil -> ... ->

    ##############################################################


  @walletint @stage
  Scenario: Validar landing ¨Perfil¨(C608)

    Contempla el TC:
      C608 https://tunubi.atlassian.net/browse/NWA-578

    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    Given Usuario esta en pantalla "dashboard"
    When Pulsa el boton "perfil" en el menu
    Then Validar elementos de pantalla "perfil"


  @walletint @stage
  Scenario Outline: Volver al dashboard desde pantalla perfil con back del device(C609)
  Contempla el TC:
  C608 https://tunubi.atlassian.net/browse/NWA-578

    Given Usuario esta en pantalla "<pantalla>"
    Then Pulsar back en "dispositivo" en pantalla "<pantalla>"
    And Usuario esta en pantalla "dashboard"
    Examples: 
    | boton | pantalla |
    | perfil| PROFILE  |

  @walletint @stage
  Scenario: Background desde pantalla perfil (C610)
  Contempla el TC:
  C610 https://tunubi.atlassian.net/browse/NWA-578

    Given Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    And Usuario esta en pantalla "PROFILE"
    When Presiona boton de aplicaciones recientes
    Then Usuario esta en pantalla "PROFILE"

  @walletint @stage
  Scenario: Matar la app desde la pantalla ¨Perfil¨ (C611)
  Contempla el tc:
  C611 https://tunubi.atlassian.net/browse/NWA-580

    Given Usuario esta en pantalla "PROFILE"
    When Usuario cierra la app
    And Inicia la app
    Then Usuario esta en pantalla "login"