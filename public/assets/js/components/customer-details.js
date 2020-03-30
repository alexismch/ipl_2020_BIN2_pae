'use strict';

import {Page} from './page.js';
import {ajaxGET} from '../utils/ajax.js';
import {router} from '../main.js';


/**
 * @module Components
 */

/**
 * Component that hold a customer details page
 *
 * @extends module:Components.Page
 */
export class CustomerDetailsPage extends Page {

  _template = `<div>
  <p>CustomerDetailsPage works</p>
  <p class="customer-details-search-msg d-none m-0 p-2 alert alert-primary"></p>
  <ul class="customer-details m-2 p-0"></ul>
  </div>`;

  /**
   *
   */
  constructor(customerId) {
    super('Details du client n°' + customerId);

    this._$view = $(this._template);

    ajaxGET('/api/customer-details', `idCustomer=${customerId}`, (data) => {

      this._createCustomerDetails(data.customerDetails);
      router.updatePageLinks();
    });
  }

  _createCustomerDetails(customerDetails){
    const $customerDetails = this._$view.find('.customer-details');
    $customerDetails.empty();
    for (const quote of customerDetails) {
      this._createCustomerDetailsItem($customerDetails, quote);
    }
  }

  _createCustomerDetailsItem($customerDetails, quote) {

    const customerDetailsItem = `<li class="customers-details-item shadow border border-left-primary rounded mb-2">
        <p>${quote.quoteDate}</p>
        <p>${quote.totalAmount}</p>
        <p>${quote.workDuration}</p>
        <p>${quote.state.title}</p>
        <p>${quote.startDate}</p>
        <a class="btn btn-primary w-min" data-navigo href="quotes/${quote.idQuote}">Détails</a>
      </li>`;
    $customerDetails.append(customerDetailsItem);
  }

}