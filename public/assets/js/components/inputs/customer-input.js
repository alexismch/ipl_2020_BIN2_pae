'use strict';

import {Component} from '../component.js';
import {ajaxGET} from '../../utils/ajax.js';

/**
 * @module Components
 */

/**
 * Component that hold input client
 *
 * @extends module:Components.Component
 */
export class CustomerInputComponent extends Component {

  name;
  onlyNotLinked;
  _$selectClient;

  /**
   *
   */
  constructor(name = '', onlyNotLinked = false) {
    super();
    this.name = name;
    this.onlyNotLinked = onlyNotLinked;

    this._$view = $(this._template);

    this._$selectClient = this._$view.find('#customer-input-' + this.getUniqueId());

    this._$selectClient.chosen({
      width: '100%',
      no_results_text: 'Ce client n\'existe pas !',
      allow_single_deselect: true
    });

    this._$selectClient.data('validator', () => {
      const errorElement = this._$selectClient.next().next('.input-error');
      if (this._$selectClient.val() === '') {
        errorElement.show(100);
        return false;
      } else {
        errorElement.hide(100);
        return true;
      }
    });

    this._$selectClient.on('chosen:showing_dropdown', () => {
      this._retrieveCustomerList();
    });
  }

  get _template() {
    return `<div>
      <label for="customer-input-${this.getUniqueId()}">Client<span class="text-danger">*</span></label>
      <select id="customer-input-${this.getUniqueId()}" name="${this.name}" class="form-control" data-placeholder="Choisissez un client" required>
        <option value=""></option>
      </select>
      <small class="input-error form-text text-danger">Un client doit être sélectionné.</small>
      <p class="mx-3 mt-1">
        Client inexistant ? 
        <a href="/clients/ajouter" target="_blank">Créer un nouveau client</a>
      </p>
    </div>`;
  }

  _retrieveCustomerList() {
    ajaxGET('/api/customers-list', this.onlyNotLinked ? 'onlyNotLinked=true' : '', (data) => {
      for (const customer of data.customers) {
        if (this._$selectClient.find('option[value="' + customer.idCustomer + '"]').length == 0) {
          $(`<option value="${customer.idCustomer}">${customer.lastName} ${customer.firstName}</option>`).appendTo(this._$selectClient);
        }
      }
      this._$selectClient.trigger('chosen:updated');
    });
  }

}