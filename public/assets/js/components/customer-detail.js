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
export class CustomerDetailPage extends Page {

  _template = `<div>
  <p>CustomerDetailPage works</p>
  <ul class="quotes-list m-2 p-0"></ul>
  </div>`;

  /**
   *
   */
  constructor(customerId) {
    super('Details du client n°' + customerId);

    this._$view = $(this._template);

    ajaxGET('/api/customer-details', `idCustomer=${customerId}`, (data) => {

      this._createCustomerDetail(data.customerDetails);
      router.updatePageLinks();
    });
  }

  _createCustomerDetail(customerDetails) {
    const $customerDetails = this._$view.find('.quotes-list');
    $customerDetails.empty();
    for (const quote of customerDetails) {
      this._createCustomerDetailsItem($customerDetails, quote);
    }
  }

  _createCustomerDetailsItem($customerDetails, quote) {

    const customerDetailsItem = `<li class="quotes-list-item shadow border border-left-primary rounded mb-2">
  <p class="quote-first-col">Devis n°${quote.idQuote}</p>
  <p class="quote-date">Date du devis: ${quote.quoteDate}</p>
  <p class="quote-first-col">Date de début des travaux: ${quote.startDate == null ? 'Non determinée' : quote.startDate}</p>
  <p class="quote-first-col">Durée des travaux: ${quote.workDuration}</p>
  <p class="quote-amount">Montant: ${quote.totalAmount}€</p>
  <p class="quote-state"><span class="badge badge-info font-size-100">${quote.state.title}</span></p>
  <a class="quote-details-btn btn btn-primary w-min" data-navigo href="devis/${quote.idQuote}">Détails</a>
</li>`;

    $customerDetails.append(customerDetailsItem);
  }

}