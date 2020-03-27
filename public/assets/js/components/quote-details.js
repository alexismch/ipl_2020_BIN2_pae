'use strict';

import {Page} from './page.js';

/**
 * @module Components
 */

/**
 * Component that hold a quote details page
 *
 * @extends module:Components.Page
 */
export class QuoteDetailsPage extends Page {

  _template = '<p>QuoteDetailsPage works</p>'

  /**
   *
   */
  constructor(quoteId) {
    super('Details du devis n°' + quoteId);

    this._$view = $(this._template);

  }

}