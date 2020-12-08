@profile @regression
Feature: Datos personales

  Contienes los TC incluidos en la seccion:

  Perfil -> ... -> Datos personales

    ############################################################

  @walletint @stage
  Scenario: Consultar los ¨Datos Personales¨  (C615)
  Contempla el tc:
    	C615 https://tunubi.atlassian.net/browse/NWA-576

    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    When Usuario presiona el link "personal_data"
    Then Validar elementos de pantalla "personal_data"

  ###############################################

  @walletint @stage
  Scenario Outline: <descripcion> en pantalla datos personales - (<testID>)
  Contempla los tc:
  C616 https://tunubi.atlassian.net/browse/NWA-576
  C617 https://tunubi.atlassian.net/browse/NWA-576

    Given Ir al dashboard
    And Pulsa el boton "perfil" en el menu
    And Usuario esta en pantalla "PROFILE"
    When Usuario presiona el link "personal_data"
    And Pulsar back en "<tipoBoton>" en pantalla "personal_data"
    Then Usuario esta en pantalla "PROFILE"
    Examples:
    | tipoBoton |           descripcion             | testID |
    |  app      |  Back desde el boton de la app    |  C616  |
    |dispositivo|Back desde el boton del dispositivo| C617   |
#
#

  @walletint @stage
  Scenario: Matar la app desde la pantalla ¨Datos personales¨ (C618)
  Contempla el tc:
  C618 https://tunubi.atlassian.net/browse/NWA-576

    Given Ir al dashboard
    And Pulsa el boton "perfil" en el menu
    And Usuario esta en pantalla "PROFILE"
    When Usuario presiona el link "personal_data"
    And Usuario esta en pantalla "personal_data"
    And Usuario cierra la app
    And Inicia la app
    Then Usuario esta en pantalla "login"