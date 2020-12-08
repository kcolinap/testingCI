@contacts @regression
Feature: Agregar Contactos feature

  @walletint @stage
  Scenario: Validar Agregado de contacto - C961, C951, C952, C959, C1019
    Given Resetear app "si"
    And Usuario "any" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permiso para ver contactos "si"
    And Usuario esta en pantalla "con_contactos_y_permiso"
    When Usuario presiona boton "agregar_contacto"
    And Usuario esta en pantalla "agregar_contacto"
    And Boton siguiente en pantalla "contactos" esta "deshabilitado"
    And El usuario ingresa el alias o cbu "2990000000011335410019"
    And Boton siguiente en pantalla "agregar_contacto" esta "habilitado"
    And Click siguiente en pantalla "agregar_contacto" si estado "exito"
# NO FUNCIONA PORQUE NO HAY CBU MOCKEADOS
#    Then Validar estado "exito" y validar "none", "2990000000011335410019", "FLORES LUIS MIGUEL" y el mensaje es "none"
#    And Click boton back en "app" en pantalla "agregar_contacto" si estado "exito"
#    And Ingresar el nombre del contacto con "Flores Luis Test" si estado "exito"
#    And Guardar el contacto si estado "exito"
#    And Usuario esta en pantalla "contactos_con_permiso"
#    And Validar que el contacto ingresado "Flores Luis Test" existe si estado "exito"


  @walletint @stage
  Scenario Outline: Validar Agregado de contacto - "<test_rail>"
    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    Given Usuario esta en pantalla "dashboard"
    When Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permiso para ver contactos "si"
    And Usuario esta en pantalla "contactos_desde_menu"
    And Usuario presiona boton "agregar_contacto"
    And Usuario esta en pantalla "agregar_contacto"
    And Boton siguiente en pantalla "contactos" esta "deshabilitado"
    And El usuario ingresa el alias o cbu "<alias>"
    And Boton siguiente en pantalla "agregar_contacto" esta "<btn_siguiente>"
    And Click siguiente en pantalla "agregar_contacto" si estado "<expected>"
    Then Validar estado "<expected>" y validar "<alias>", "<cbu>", "<contact_name>" y el mensaje es "<message>"
    And Click boton back en "app" en pantalla "agregar_contacto" si estado "<expected>"
    And Ingresar el nombre del contacto con "<contact_input>" si estado "<expected>"
    And Guardar el contacto si estado "<expected>"
    And Validar que el contacto ingresado "<contact_input>" existe si estado "<expected>"
    Examples:
      | alias                  | cbu                    | btn_siguiente | expected  | contact_name    | contact_input   | first_time | message                                                                                                                                                        | test_rail                     |
# Fallan en Staging porque NO existen
#      | jose.perez             | 0070024530004016651051 | habilitado    | exito     | Jose Perez   | Jose Perez Test | si         | none                                                                                                                                                           | C961, C951, C952, C959, C1019 |
#      | aliasdecvu             | none                   | habilitado    | alias_cvu | none         | none            | no         | Este alias está asociado a una cuenta\ncon CVU, todavía no podemos agendar\nel CVU, ya que por el momento no\nhacemos transferencias entre cuentas\nvirtuales. | C958                          |
      | maria.                 | none                   | deshabilitado | none      | none         | none            | no         | No es un alias o CBU válido                                                                                                                                    | C949                          |
      | 9999999999999999999999 | none                   | deshabilitado | close     | none         | none            | no         | No es un CBU válido                                                                                                                                            | C950                          |
      | 0004444444444444444444 | none                   | deshabilitado | close     | none         | none            | no         | No es un CVU válido                                                                                                                                            | C956                          |
      | 0004567292610597730948 | 0004567292610597730948 | deshabilitado | close     | Jose Perez   | Jose Perez test | si         | none                                                                                                                                                           | C956                          |

  @walletint @stage
  Scenario Outline: Validar Agregado de contacto - "<test_rail>"
    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    Given Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permiso para ver contactos "si"
    And Usuario esta en pantalla "contactos"
    When Usuario presiona boton "agregar_contacto"
    And Usuario esta en pantalla "agregar_contacto"
    And Boton siguiente en pantalla "contactos" esta "deshabilitado"
    And El usuario ingresa el alias o cbu "<alias>"
    Then Boton siguiente en pantalla "agregar_contacto" esta "<habilitado>"
    Examples:
      | alias                   | test_rail |  habilitado   |
      | 00700245300040166510    | C960      | deshabilitado |
      | 00700245300040166510888 | C954      | deshabilitado |
      | 0000204402733885658528  | C954      |   habilitado  |
      | 2992568725549885555470  | C954      |   habilitado  |

  @walletint @stage
  Scenario: Validar retorno de la pantalla de agregar contacto - "C962, C964"
    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    Given Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permiso para ver contactos "si"
    And Usuario esta en pantalla "contactos"
    And Usuario presiona boton "agregar_contacto"
    And Usuario esta en pantalla "agregar_contacto"
    When Pulsar back en "app" en pantalla "agregar_contacto"
    Then Usuario esta en pantalla "contactos"
    Given Usuario esta en pantalla "contactos"
    And Usuario presiona boton "agregar_contacto"
    And Usuario esta en pantalla "agregar_contacto"
    When Pulsar back en "dispositivo" en pantalla "agregar_contacto"
    Then Usuario esta en pantalla "contactos"

  @walletint @stage
  Scenario: Validar background en agregado de contacto - "C1021"
    Given Resetear app "si"
    Given Usuario "any" inicia sesion
    Given Usuario esta en pantalla "dashboard"
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "enviar" en el menu
    And Permiso para ver contactos "si"
    And Usuario esta en pantalla "contactos"
    And Usuario presiona boton "agregar_contacto"
    And Usuario esta en pantalla "agregar_contacto"
    And El usuario ingresa el alias o cbu "joseperez"
    And Click siguiente en pantalla "agregar_contacto"
    When Resetear app "si"
    Then Usuario esta en pantalla "login"

# SOLO walletint porque crea un contacto usando comandos adb
  @walletint
  Scenario: Agregar cuenta por CBU
    And Permiso para ver contactos "si"
    And Usuario "CON_CONTACTO_NUBI" inicia sesion
    And Borrar contactos
    And Agregar contacto "NUBI"
    And Usuario esta en pantalla "dashboard"
    And Usuario presiona boton de contactos frecuentes
    And Permiso para ver contactos "si"
    Given Usuario esta en pantalla "con_contactos_y_permiso"
    And Validar que hay contactos "NUBI"
    When Selecciona contacto "NUBI"
    Then Usuario esta en pantalla "detalle_contacto"
    And Validar detalle contacto "NUBI"
    Then Presiona sobre el boton en la pantalla detalle contacto "Agregar otra cuenta"
    Then Se validan los elementos de la pantalla agregar contacto con CBU o Alias