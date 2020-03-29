'use strict';

/**
 * @module Alerts
 */

/**
 * Create an alert and append it to all .alerts elements in the dom.
 *
 * @param {String} level The bootstrap v4.4 alert level like 'primary', 'success', 'warning', 'danger', ...
 * @param {String} message The message displayed in the alert
 */
function createAlert(level, message) {

  $('.alerts').append(
      `<div class="alert alert-${level} alert-dismissible fade show m-1" role="alert">
  ${message}
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
      <span aria-hidden="true">&times;</span>
  </button>
</div>`);

}

/**
 * Clear alerts by removing all element in $('.alerts') elements
 */
function clearAlerts() {
  $('.alerts').empty();
}

export {createAlert, clearAlerts};