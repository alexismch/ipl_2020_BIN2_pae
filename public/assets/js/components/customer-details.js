'use strict';

import {Page} from './page.js';

/**
 * @module Components
 */

/**
 * Component that hold a customer details page
 *
 * @extends module:Components.Page
 */
export class CustomerDetailsPage extends Page {

  _template = '<p>CustomerDetailsPage works</p>'

  /**
   *
   */
  constructor(customerId) {
    super('Details du client n°' + customerId);

    this._$view = $(this._template);

    ajaxGET('/api/customers-list', customerId, (data) => {

      this._createCustomerDetail(data.customerDetail);
      router.updatePageLinks();
    });
  }

  _createCustomerDetail(customerDetail){

  }

}