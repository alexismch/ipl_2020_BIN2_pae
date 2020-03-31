'use strict';

import {router} from '../main.js';
import {ajaxGET} from '../utils/ajax.js';
import {createAlert} from '../utils/alerts.js';
import {onSubmitWithAjax} from '../utils/forms.js';
import {Page} from './page.js';
import {AddPictureComponent} from './picture-add.js';
import {CustomerInputComponent} from './inputs/customer-input.js';
import {DateInputComponent} from './inputs/datepicker-input.js';

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
      <label for="page-add-devis-duration">Durée estimé des travaux (en jours)<span class="text-danger">*</span></label>
      <input class="form-control" id="page-add-devis-duration" name="duration" required type="number" min="0"/>
      <small class="input-error form-text text-danger">Une durée correcte est requise.</small>
    </div>
    <div class="form-group">
      <label for="page-add-devis-types">Type d'aménagement(s)<span class="text-danger">*</span></label>
      <select id="page-add-devis-types" name="types" class="form-control" data-placeholder="Choisissez au moins un type d'aménagement" multiple>
        <option value=""></option>
      </select>
      <small class="input-error form-text text-danger">Au moins un type d'aménagement dois être selectionné.</small>
    </div>
    <h4>Photos d'avant aménagement</h4>
    <div id="page-add-devis-photos">
    </div>
    <button id="page-add-devis-btn-add-picture" class="btn btn-secondary" type="button">Ajouter une photo suplémentaire</button>
    <div class="form-group mt-2 d-flex justify-content-end">
      <button class="btn btn-primary">Ajouter le devis</button>
    </div>
  </form>
</div>`;

  _developmentTypeList = [];
  _$selectTypes;
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

    this._$selectTypes = this._$view.find('#page-add-devis-types');

    this._$selectTypes.chosen({
      width: '100%',
      no_results_text: 'Cet aménagement n\'existe pas !',
      allow_single_deselect: true
    });

    this._$selectTypes.data('validator', () => {
      const developmentTypeSelected = this._getSeletedDevelopmentType();
      this._addPictureComponents.forEach(addPictureComponent => addPictureComponent.setDevelopmentTypesList(developmentTypeSelected));
      const errorElement = this._$selectTypes.next().next('.input-error');
      if (this._$selectTypes.val()[0] === undefined) {
        errorElement.show(100);
        return false;
      } else {
        errorElement.hide(100);
        return true;
      }
    });

    onSubmitWithAjax(this._$view.find('form'), () => {
      router.navigate('devis');
      createAlert('success', 'Le devis a bien été ajouté');
    });

    this._$photos = this._$view.find("#page-add-devis-photos");

    this._$view.find('#page-add-devis-btn-add-picture').on('click', () => {
      this._addAnAddPictureComponent();
    });

    this._retriveDevelopmentTypeList();

    this.isLoading = false;
  }

  _retriveDevelopmentTypeList() {
    ajaxGET('/api/developmentType-list', null, (data) => {
      this._developmentTypeList = data.developmentTypesList;
      for (const developmentType of this._developmentTypeList) {
        $(`<option value="${developmentType.idType}">${developmentType.title}</option>`).appendTo(this._$selectTypes);
      }
      this._$selectTypes.trigger('chosen:updated');
    });
  }

  _addAnAddPictureComponent() {
    const addPictureComponent = new AddPictureComponent();
    const addPictureComponentView = addPictureComponent.getView();
    addPictureComponent.setDevelopmentTypesList(this._getSeletedDevelopmentType());
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

  _getSeletedDevelopmentType() {
    return this._developmentTypeList.filter(
        developmentType => this._$selectTypes.val().find(selected => selected == developmentType.idType));
  }

}