'use strict';

import {router} from '../main.js';
import {createAlert} from '../utils/alerts.js';
import {onSubmitWithAjax} from '../utils/forms.js';
import {Page} from './page.js';
import {AddPictureComponent} from './inputs/picture-add.js';
import {CustomerInputComponent} from './inputs/customer-input.js';
import {DateInputComponent} from './inputs/datepicker-input.js';
import {MutltipleDevelopmentTypeInputComponent} from './inputs/multiple-developmentType-input.js';

/**
 * @module Components
 */

/**
 * Component that hold a form to add a quote
 *
 * @extends module:Components.Page
 */
export class QuoteFormPage extends Page {

  _template = `<div class="container">
  <h2>Nouveau devis</h2>
  <form action="/api/quote" class="w-100 mb-3" method="post" novalidate>
    <div class="form-group">
      <label for="page-add-devis-id">ID du devis<span class="text-danger">*</span></label>
      <input class="form-control" id="page-add-devis-id" name="quoteId" required type="text"/>
      <small class="input-error form-text text-danger">Un ID est requis.</small>
    </div>
    <div class="form-group select-customer"></div>
    <div class="form-group select-date"></div>
    <div class="form-group">
      <label for="page-add-devis-amount">Montant total (en €)<span class="text-danger">*</span></label>
      <input class="form-control" id="page-add-devis-amount" name="amount" required type="number" min="0" step=".01"/>
      <small class="input-error form-text text-danger">Un montant correct est requis.</small>
    </div>
    <div class="form-group">
      <label for="page-add-devis-duration">Durée estimée des travaux (en jours)<span class="text-danger">*</span></label>
      <input class="form-control" id="page-add-devis-duration" name="duration" required type="number" min="0"/>
      <small class="input-error form-text text-danger">Une durée correcte est requise.</small>
    </div>
    <div class="form-group select-multiple-developmentType"></div>
    <h4>Photos d'avant aménagement</h4>
    <div id="page-add-devis-photos">
    </div>
    <button id="page-add-devis-btn-add-picture" class="btn btn-secondary" type="button">Ajouter une photo supplémentaire</button>
    <div class="form-group mt-2 d-flex justify-content-end">
      <button class="btn btn-primary">Ajouter le devis</button>
    </div>
  </form>
</div>`;

  _developmentTypeList = [];
  _addPictureComponents = [];
  _$photos;

  /**
   *
   */
  constructor() {
    super('Ajouter un devis');

    this._$view = $(this._template);

    const $selectClient = this._$view.find('.select-customer');
    const customerInputComponent = new CustomerInputComponent('customerId');
    $selectClient.append(customerInputComponent.getView());

    const $selectDate = this._$view.find('.select-date');
    const datepicker = new DateInputComponent('date');
    $selectDate.append(datepicker.getView());

    const $selectMultipleDevelopemntType = this._$view.find('.select-multiple-developmentType');
    const mutltipleDevelopmentTypeInputComponent = new MutltipleDevelopmentTypeInputComponent('types', (developmentTypeList) => {
      this._developmentTypeList = developmentTypeList;
      this._addPictureComponents.forEach(addPictureComponent => addPictureComponent.setDevelopmentTypesList(this._developmentTypeList));
    });
    $selectMultipleDevelopemntType.append(mutltipleDevelopmentTypeInputComponent.getView());

    onSubmitWithAjax(this._$view.find('form'), () => {
      router.navigate('devis');
      createAlert('success', 'Le devis a bien été ajouté');
    });

    this._$photos = this._$view.find("#page-add-devis-photos");

    this._$view.find('#page-add-devis-btn-add-picture').on('click', () => {
      this._addAnAddPictureComponent();
    });

    this.isLoading = false;
  }

  _addAnAddPictureComponent() {
    const addPictureComponent = new AddPictureComponent();
    const addPictureComponentView = addPictureComponent.getView();
    addPictureComponent.setDevelopmentTypesList(this._developmentTypeList);
    const button = $(`<div class="floating-button-container-right">
        <button class="btn btn-danger floating-button-right" type="button"><i class="fas fa-times"></i></button>
      </div>`);

    button.on('click', () => {
      this._addPictureComponents = this._addPictureComponents.filter(aPicCp => aPicCp != addPictureComponent);
      button.remove();
      addPictureComponentView.remove();
    });

    this._$photos.append(button, addPictureComponentView);
    this._addPictureComponents[this._addPictureComponents.length] = addPictureComponent;
  }

}