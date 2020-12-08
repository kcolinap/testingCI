@movements @regression
Feature: Contacto - CVU no habilitado
  Contempla los tcs incluidos en :
  https://nubiwallet.testrail.io/index.php?/suites/view/2&group_by=cases:section_id&group_order=asc&group_id=278


  Background:
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    Given Permiso para ver contactos "si"

  @walletint
  Scenario Outline: <descripcion> - (<id>)

    Validar que se solicite la validacion de identidad al generar cvu
    desde el flujo enviar
    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    Given Usuario "<usuario>" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Usuario es tipo 1
    And Pulsa el boton "principal" en el menu
    And Pulsa el boton "<tipoOperacion>" en el menu
    And Usuario esta en pantalla "contactos_desde_menu"
    And Validar que hay contactos "S"
    When Seleccionar contacto "<tipoContacto>"
    And Usuario esta en pantalla "detalle_contacto"
    And Validar que el contacto seleccionado posee cuenta "<tipoCuenta>"
    And Seleccionar la cuenta "<tipoCuenta>"
    Then Usuario esta en pantalla "validar_identidad_cvu"
    Then Resetear app "si"
    Examples:
    |     usuario      | tipoContacto | tipoCuenta | tipoOperacion |                          descripcion                         |  id   |
   |  nocvu_bancario  |   bancario   |  bancaria  |      enviar   | Solicitud validacion de identidad al generar CVU desde enviar| C1230 |
    |nocvu_nubibancario|     nubi     |  bancaria  |      enviar   | Solicitud validacion de identidad al generar CVU desde enviar| C1230 |
#
##
#  Scenario Outline: <descripcion> - (<id>)
#
#  Validar pantalla de generacion de CVU
#    al validar identidad
#
#    Given Usuario <usuario> inicia sesion
#    And Usuario esta en pantalla "dashboard"
#    And Usuario es tipo 2
#    And Pulsa el boton "principal" en el menu
#    And Pulsa el boton "<tipoOperacion>" en el menu
#    And Usuario esta en pantalla "contactos_desde_menu"
#    And Validar que hay contactos <tipoContacto>
#    When Seleccionar contacto <tipoContacto>
#    And Usuario esta en pantalla "detalle_contacto"
#    And Validar que el contacto seleccionado posee cuenta <tipoCuenta>
#    And Seleccionar la cuenta <tipoCuenta>
#    Then Usuario esta en pantalla "generar_cvu"
#    Then Resetear app "si"
#    Examples:
#      |    usuario      | tipoContacto | tipoCuenta | tipoOperacion |                    descripcion                          |  id   |
#      |usuario_sin_cvu_3|   bancario   |  bancaria  |      enviar   | Validar pantalla generacion de cvu al validar identidad desde menu enviar | C1225 |
##
##
#  Scenario Outline: <descripcion> - (<id>)
#
#  Validar pantalla "éxito" al generar un CVU desde flujo "Enviar"
#
#    Given Usuario <usuario> inicia sesion
#    And Usuario esta en pantalla "dashboard"
#    And Usuario es tipo 2
#    And Pulsa el boton "principal" en el menu
#    And Pulsa el boton "<tipoOperacion>" en el menu
#    And Usuario esta en pantalla "contactos_desde_menu"
#    And Validar que hay contactos <tipoContacto>
#    When Seleccionar contacto <tipoContacto>
#    And Usuario esta en pantalla "detalle_contacto"
#    And Validar que el contacto seleccionado posee cuenta <tipoCuenta>
#    And Seleccionar la cuenta <tipoCuenta>
#    And Usuario esta en pantalla "generar_cvu"
#    And Pulsa en el boton generar para generar CVU
#    Then Usuario esta en pantalla "cvu_generado"
#    Then Resetear app "si"
#    Examples:
#      |    usuario      | tipoContacto | tipoCuenta | tipoOperacion |                     descripcion                    |  id   |
#      |usuario_sin_cvu_3|   bancario   |  bancaria  |      enviar   | Validar generacion de cvu exitosa desde menu enviar | C1226 |
##
##
#  Scenario Outline: <descripcion> - (<id>)
#
#  Validar inicio de tranferencia hacia cuenta bancaria
#    o contacto nubi bancario cuando el usuario tiene el CVU
#    habilitado
#
#    Given Usuario <usuario> inicia sesion
#    And Usuario esta en pantalla "dashboard"
#    And Pulsa el boton "principal" en el menu
#    And Pulsa el boton "<tipoOperacion>" en el menu
#    And Usuario esta en pantalla "contactos_desde_menu"
#    And Validar que hay contactos <tipoContacto>
#    When Seleccionar contacto <tipoContacto>
#    And Usuario esta en pantalla "detalle_contacto"
#    And Validar que el contacto seleccionado posee cuenta <tipoCuenta>
#    And Seleccionar la cuenta <tipoCuenta>
#    Then Validar que modo enviar_bancaria este activo
#    Then Resetear app "si"
#    Examples:
#      |     usuario       | tipoContacto | tipoCuenta | tipoOperacion |          descripcion              |  id   |
#      | usuario_con_cvu_3 |   bancario   |  bancaria  |      enviar   | Usuario con CVU desde menu enviar | C1227 |
#  #
#  #
#    Scenario Outline: <descripcion> - (<id>)
#
#    Validar que se solicite la validacion de identidad al generar cvu
#    desde la tarjeta de contactos
#
#    Given Usuario <usuario> inicia sesion
#    And Usuario esta en pantalla "dashboard"
#    And Usuario es tipo 1
#    And Usuario presiona boton de contactos frecuentes
#    And Usuario esta en pantalla "contactos_desde_dashboard"
#    And Validar que hay contactos <tipoContacto>
#    When Seleccionar contacto <tipoContacto>
#    And Usuario esta en pantalla "detalle_contacto"
#    And Validar que el contacto seleccionado posee cuenta <tipoCuenta>
#    And Seleccionar la cuenta <tipoCuenta>
#    Then Usuario esta en pantalla "validar_identidad_cvu"
#    Then Resetear app "si"
#    Examples:
#    |    usuario      | tipoContacto | tipoCuenta | tipoOperacion |                          descripcion                         |  id   |
#    |usuario_sin_cvu_3|   bancario   |  bancaria  |      enviar   | Solicitud validacion de identidad al generar CVU desde tarjeta contactos| C1224 |
##    |usuario_sin_cvu_2|     nubi     |  bancaria  |      enviar   | Solicitud validacion de identidad al generar CVU desde enviar| C1224 |
##
##
#  Scenario Outline: <descripcion> - (<id>)
#
#  Validar pantalla "Generación de CVU"
#  al validar la identidad desde  tarjeta de contactos
#
#    Given Usuario <usuario> inicia sesion
#    And Usuario esta en pantalla "dashboard"
#    And Usuario es tipo 2
#    And Usuario presiona boton de contactos frecuentes
#    And Usuario esta en pantalla "contactos_desde_dashboard"
#    And Validar que hay contactos <tipoContacto>
#    When Seleccionar contacto <tipoContacto>
#    And Usuario esta en pantalla "detalle_contacto"
#    And Validar que el contacto seleccionado posee cuenta <tipoCuenta>
#    And Seleccionar la cuenta <tipoCuenta>
#    Then Usuario esta en pantalla "generar_cvu"
#    Then Resetear app "si"
#    Examples:
#      |    usuario      | tipoContacto | tipoCuenta | tipoOperacion |                    descripcion                          |  id   |
#      |usuario_sin_cvu_3|   bancario   |  bancaria  |      enviar   | Validar pantalla generacion de cvu al validar identidad desde tarjeta de contactos | C1231 |
##
##
  @walletint
  Scenario Outline: <descripcion> - (<id>)

  Validar pantalla "éxito" al generar un CVU desde flujo "tarjeta de Contactos"

    Given Resetear app "si"
    Given Usuario esta en pantalla "login"
    Given Permiso para ver contactos "si"
    Given Usuario "<usuario>" inicia sesion
    And Usuario esta en pantalla "dashboard"
    And Usuario es tipo 2
    And Usuario presiona boton de contactos frecuentes
    And Usuario esta en pantalla "contactos_desde_dashboard"
    And Validar que hay contactos "<tipoContacto>"
    When Seleccionar contacto "<tipoContacto>"
    And Usuario esta en pantalla "detalle_contacto"
    And Validar que el contacto seleccionado posee cuenta "<tipoCuenta>"
    And Seleccionar la cuenta "<tipoCuenta>"
    And Usuario esta en pantalla "generar_cvu"
    And Pulsa en el boton generar para generar CVU
    Then Usuario esta en pantalla "cvu_generado"
    Then Resetear app "si"
    Examples:
      |    usuario      | tipoContacto | tipoCuenta | tipoOperacion |                     descripcion                    |  id   |
      |usuario_sin_cvu_3|   bancario   |  bancaria  |      enviar   | Validar generacion de cvu exitosa desde tarjeta de contactos | C1232 |
#
#
#  Scenario Outline: <descripcion> - (<id>)
#
#  Validar inicio de tranferencia hacia cuenta bancaria
#  o contacto nubi bancario cuando el usuario tiene el CVU
#  habilitado
#
#    Given Usuario <usuario> inicia sesion
#    And Usuario esta en pantalla "dashboard"
#    And Pulsa el boton "principal" en el menu
#    And Pulsa el boton "<tipoOperacion>" en el menu
#    And Usuario esta en pantalla "contactos_desde_menu"
#    And Validar que hay contactos <tipoContacto>
#    When Seleccionar contacto <tipoContacto>
#    And Usuario esta en pantalla "detalle_contacto"
#    And Validar que el contacto seleccionado posee cuenta <tipoCuenta>
#    And Seleccionar la cuenta <tipoCuenta>
#    Then Validar que modo enviar_bancaria este activo
#    Then Resetear app "si"
#    Examples:
#      |     usuario       | tipoContacto | tipoCuenta | tipoOperacion |          descripcion              |  id   |
#      | usuario_con_cvu_3 |   bancario   |  bancaria  |      enviar   | Usuario con CVU desde menu enviar | C1227 |