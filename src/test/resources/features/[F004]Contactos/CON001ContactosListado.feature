@contacts @regression
Feature: Contactos -- > Listado

  Contempla todos los Tcs que se encuentran en la seccion de test rail

  Mobile ... Contactos->Listado
  https://nubiwallet.testrail.io/index.php?/suites/view/2&group_by=cases:section_id&group_order=asc&group_id=248

  @walletint
  Scenario: Permisos para acceder a los contactos por primera vez (C967)
    Given Usuario esta en pantalla "login"
    And Permiso para ver contactos "si"
    And Usuario "CON_CONTACTO_NUBI" inicia sesion
    And Borrar contactos
    And Agregar contacto "NUBI"
    And Usuario esta en pantalla "dashboard"
    When Usuario presiona boton de contactos frecuentes
    And Usuario esta en pantalla "con_contactos_y_permiso"
    Then Se visualizan los contactos "NUBI"
#    And Se visualizan los contactos "otros_contactos"

  Scenario: Resetear app
    Then Resetear app "si"

#  Este escenario va en ENVIOS/SOLICITUD.
#  Scenario: Select a contact to send or request money
#    Given Usuario esta en pantalla "login"
#    Given User set its credential to acces nubi wallet
#    Given Usuario esta en pantalla "dashboard"
#    When User tap action button to change to "<action>"
#    Then Resetear app "si"

