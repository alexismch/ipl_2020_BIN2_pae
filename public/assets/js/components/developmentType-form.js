'use strict';

import {router} from '../main.js';
import {Page} from './page.js';
import {onSubmitWithAjax} from '../utils/forms.js';
import {createAlert} from '../utils/alerts.js';

/**
 * @module Components
 */

/**
 * Component that hold a form to add a developnentType
 *
 * @extends module:Components.Page
 */
export class DevelopmentTypeFormPage extends Page {

  _template = `<div class="container">
  <h2>Ajouter un aménagement</h2>
  <form action="/api/developmentType" method="post" novalidate>
    <div class="form-group">
      <label for="page-development-type-form-nom-${this.getUniqueId()}">Nom<span class="text-danger">*</span></label>
      <input class="form-control" id="page-development-type-form-nom-${this.getUniqueId()}" name="typeTitle" required type="text"/>
      <small class="input-error form-text text-danger">Le nom de l'aménagement est requis.</small>
    </div>
    <button class="btn btn-primary">Ajouter l'aménagement</button>
  </form>
</div>`;

  /**
   *
   */
  constructor() {
    super('Ajouter un aménagement');

    this._$view = $(this._template);

    onSubmitWithAjax(this._$view.find('form'), () => {
      router.navigate('amenagements');
      createAlert('success', 'L\'aménagment a bien été ajouté');
    });

    this.isLoading = false;
  }

}