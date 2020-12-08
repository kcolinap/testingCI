@profile @regression
Feature: Nueva clave Nubi

  Contienes los TC incluidos en la seccion:

          Perfil -> ... -> Pin Nuevo


  @walletint @stage
  Scenario:  Nueva clave Nubi - Completitud de pantalla(C783)
    Contempla el tc:
  C783 https://tunubi.atlassian.net/browse/NWA-2263

    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "perfil" en el menu
    When Usuario presiona el link "security"
    And Usuario presiona el link "change_nubipass"
    And Ingresa clave nubi "correcto"
    Then Validar elementos de pantalla "new_nubi_password"


  @walletint @stage
  Scenario: Nueva clave Nubi - Clave incompleta(C787)
  Contempla los tc:
  C787 https://tunubi.atlassian.net/browse/NWA-2263

    Given Usuario esta en pantalla "new_nubi_password"
    When Usuario ingresa una nueva clave Nubi "incompleto"
    Then Validar pantalla cuando nueva clave nubi es "incompleto"

  @ignore @walletint @stage
  Scenario: Salir desde la pantalla Nueva clave Nubi(C797)
    Contempla el tc
    C797 https://tunubi.atlassian.net/browse/NWA-2263

    Given Pulsa el boton "perfil" en el menu
    And Usuario esta en pantalla "perfil"
    And Usuario presiona el link "security"
    And Usuario presiona el link "change_nubipass"
    And Ingresa clave nubi "correcto"
    And Validar pantalla cuando el pin es "correcto"
    When Usuario pulsa boton cerrar en pantalla "cambiopin"
    Then Usuario esta en pantalla "security"
#
#
  @walletint @stage
  Scenario: Nueva clave Nubi - Back desde el device(C799)
  Contempla el tc
  C799 https://tunubi.atlassian.net/browse/NWA-2263

    Given Usuario esta en pantalla "new_nubi_password"
    When Pulsar back en "dispositivo" en pantalla "new_nubi_password"
    Then Usuario esta en pantalla "current_nubi_password"
#
#
  @walletint @stage
  Scenario: Matar la app desde pantalla Nueva clave Nubi(C798)
  Contempla el tc
  C798 https://tunubi.atlassian.net/browse/NWA-2263

    Given Usuario esta en pantalla "current_nubi_password"
    And Ingresa clave nubi "correcto"
    And Usuario ingresa una nueva clave Nubi "incompleto"
    When Usuario cierra la app
    And Inicia la app
    Then Usuario esta en pantalla "login"