'use strict';

import {router} from '../main.js';
import {ajaxGET} from '../utils/ajax.js';
import {Page} from './page.js';

/**
 * @module Components
 */

/**
 * Component that hold the list of development type page
 *
 * @extends module:Components.Page
 */
export class DevelopmentTypePage extends Page {

  _template = `<ul class="amenagements-list m-2 p-0"></ul>`;
  _developmentTypesList;

  /**
   *
   */
  constructor() {
    super('Aménagments');

    this._$view = $(this._template);

    ajaxGET('/api/developmentType-list', null, (data) => {
      this._developmentTypesList = data.developmentTypesList;
      this._updateDevelopmentTypesList();
    });

  }

  _updateDevelopmentTypesList() {

    this._$view.empty();
    for (const developmentType of this._developmentTypesList) {
      const developmentListItem = `<li>
  <a class="card scale shadow border-left-primary" data-navigo href="amenagements/${developmentType.idType}">
    <span class="card-body">${developmentType.title}</span>
  </a>
</li>`;
      this._$view.append(developmentListItem);
    }

    router.updatePageLinks();
  }

}
