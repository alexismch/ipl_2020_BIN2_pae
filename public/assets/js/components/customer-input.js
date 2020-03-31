'use strict';

import {Component} from './component.js';
import {ajaxGET} from '../utils/ajax.js';

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

    this._retrieveCustomerList();
  }

  get _template() {
    return `<div>
      <label for="customer-input-${this.getUniqueId()}">Client<span class="text-danger">*</span></label>
      <select id="customer-input-${this.getUniqueId()}" name="${this.name}" class="form-control" data-placeholder="Choisissez un client">
        <option value=""></option>
      </select>
      <small class="input-error form-text text-danger">Un client doit être sélectionné.</small>
      <p class="d-flex align-items-center mx-3 mt-1">
        Client inexistant ?
        <a class="btn btn-sm btn-secondary ml-3" data-navigo href="clients/ajouter">Créer un nouveau client</a>
      </p>
    </div>`;
  }

  _retrieveCustomerList() {
    ajaxGET('/api/customers-list', this.onlyNotLinked ? 'onlyNotLinked=true' : '', (data) => {
      for (const customer of data.customers) {
        $(`<option value="${customer.idCustomer}">${customer.lastName} ${customer.firstName}</option>`).appendTo(this._$selectClient);
      }
      this._$selectClient.trigger('chosen:updated');
    });
  }

}