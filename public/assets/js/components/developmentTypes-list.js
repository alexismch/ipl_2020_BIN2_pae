'use strict';

import {router} from '../main.js';
import {ajaxGET} from '../utils/ajax.js';
import {Page} from './page.js';
import {isWorker} from '../utils/userUtils.js';

/**
 * @module Components
 */

/**
 * Component that hold the list of development type page
 *
 * @extends module:Components.Page
 */
export class DevelopmentTypesListPage extends Page {

  _template = `<div>
  <ul class="amenagements-list m-2 p-0"></ul>
</div>`;
  _developmentTypesList;

  /**
   *
   */
  constructor() {
    super('Aménagments');

    this._$view = $(this._template);

    if (isWorker()) {
      const addDevelopmentTypeButton = `<div class="d-flex justify-content-end p-1 elevation-1 bg-light">
  <a class="btn btn-sm btn-primary" data-navigo href="amenagements/ajouter">Ajouter un aménagement</a>
</div>`;
      this._$view.prepend($(addDevelopmentTypeButton));
    }

    ajaxGET('/api/developmentType-list', null, (data) => {
      this._developmentTypesList = data.developmentTypesList;
      this._updateDevelopmentTypesList();
      router.updatePageLinks();
      this.isLoading = false;
    }, () => {
      this.isLoading = false;
    });

  }

  _updateDevelopmentTypesList() {

    for (const developmentType of this._developmentTypesList) {
      const developmentListItem = `<li>
  <a class="card scale shadow border-left-primary" data-navigo href="amenagements/${developmentType.idType}">
    <span class="card-body">${developmentType.title}</span>
  </a>
</li>`;
      this._$view.find('.amenagements-list').append(developmentListItem);
    }

  }

}
