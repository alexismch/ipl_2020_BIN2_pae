'use strict';

import {Page} from './page.js';

/**
 * @module Components
 */

/**
 * Component that hold a user details page
 *
 * @extends module:Components.Page
 */
export class UserDetailsPage extends Page {

  _template = '<p>UserDetailsPage works</p>'

  /**
   *
   */
  constructor(userId) {
    super('Details de l\'utilisateur n°' + userId);

    this._$view = $(this._template);

  }

}