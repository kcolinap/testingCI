@profile @regression
Feature: Cerrar sesion

  Contienes los TC incluidos en la seccion:

  Perfil -> ... -> Cerrar sesion

  @walletint @stage
  Scenario: Validacion de pantalla Cerrar Sesion(C803)
    Contempla el TC:
    C803 https://tunubi.atlassian.net/browse/NWA-587

    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    And Usuario presiona el link "close_session"
    Then Validar elementos de pantalla "close_session"

  @walletint @stage
  Scenario: Back desde la app en pantalla cerrar sesion(C804)
  Contempla el TC:
  C804 https://tunubi.atlassian.net/browse/NWA-587

    Given Usuario esta en pantalla "close_session"
    When Pulsar back en "app" en pantalla "close_session"
    Then Usuario esta en pantalla "profile"

  @walletint @stage
  Scenario: Background desde pantalla cerrar sesion(C806)
  Contempla el TC:
  C806 https://tunubi.atlassian.net/browse/NWA-587

    Given Usuario esta en pantalla "profile"
    And Usuario presiona el link "close_session"
    And Usuario esta en pantalla "close_session"
    And Presiona boton de aplicaciones recientes
    Then Usuario esta en pantalla "close_session"

  @walletint @stage
  Scenario: Validar cierre de sesion(C808)
  Contempla el TC:
  C808 https://tunubi.atlassian.net/browse/NWA-587

    Given Usuario esta en pantalla "close_session"
    When Usuario presiona boton "close_session"
    Then Usuario esta en pantalla "login"

  @walletint @stage
  Scenario: Matar app desde pantalla cerrar sesion (C807)
  Contempla el TC:
  C807 https://tunubi.atlassian.net/browse/NWA-587

    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    And Usuario presiona el link "close_session"
    And Usuario esta en pantalla "close_session"
    When Usuario cierra la app
    And Inicia la app
    Then Usuario esta en pantalla "login"