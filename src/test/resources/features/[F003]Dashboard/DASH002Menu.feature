@dashboard @regression
Feature: Menu

  Comtempla los siguientes TCS:

  C485 https://tunubi.atlassian.net/browse/NWA-595
  C486 https://tunubi.atlassian.net/browse/NWA-595
  C487
  C488


  @walletint @stage
  Scenario Outline: Validacion de botones en menu (C485-C486-C487-C488)

    Given Resetear app "<resetarApp>"

    And Usuario "<usuario>" inicia sesion
    Given Usuario esta en pantalla "dashboard"
    Given Pulsa el boton "principal" en el menu
    When Pulsa el boton "<funcion>" en el menu
   # And Permitir el acceso a los contactos "<condicion>" por primera vez "<condicion>"
    And Aceptar permisos de "CONTACTOS"
    Then Usuario esta en pantalla "<pantalla>"
    Then Ir al dashboard
    Examples:
      | funcion         |              pantalla           | condicion | resetarApp | usuario |
      | enviar          |             contactos           | si        |     si     |   any   |
      | solicitar       |             contactos           |           |            |         |
      | extraer_dinero  |           extraer_saldo         |           |            |         |
      | recarga_sube    |            registro_sube        |           |            |         |
      | recarga_celular | select_compania_recarga_celular |           |            |         |
      | pagar_factura   |         pago_de_servicios       |           |            |         |
      | solicitudes     |             solicitudes         |           |            |         |
      | contactos       |             contactos           |           |            |         |
      | movimientos     |        ultimos_movimientos      |           |            |         |

  @walletint @stage @ignore
  Scenario: Validar acceso a modulo tarjeta prepaga (C497)

    Given Ir al dashboard
    Given Usuario esta en pantalla "dashboard"
    Given Pulsa el boton "tarjeta_prepaga" en el menu
    Then Usuario esta en pantalla "tarjeta_prepaga"
