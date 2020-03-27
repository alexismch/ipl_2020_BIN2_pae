'use strict';

import {router} from '../main.js';
import {ajaxGET} from '../utils/ajax.js';
import {onSubmitWithAjax} from '../utils/forms.js';
import {Page} from './page.js';
import {AddPictureComponent} from './picture-add.js';

/**
 * @module Components
 */

/**
 * Component that hold a form to add a quote
 *
 * @extends module:Components.Component
 */
export class AddQuotePage extends Page {

  _template = `<div class="container">
  <h2>Nouveau devis</h2>
  <form action="/api/quote" class="w-100 mb-3" method="post" novalidate>
    <div class="form-group">
      <label for="page-add-devis-id">ID du devis<span class="text-danger">*</span></label>
      <input class="form-control" id="page-add-devis-id" name="quoteId" required type="text"/>
      <small class="input-error form-text text-danger">Un ID est requis.</small>
    </div>
    <div class="form-group">
      <label for="page-add-devis-customer">Client<span class="text-danger">*</span></label>
      <select id="page-add-devis-customer" name="customerId" class="form-control" data-placeholder="Choisissez un client">
        <option value=""></option>
      </select>
      <small class="input-error form-text text-danger">Un client doit être selectionné.</small>
      <p class="d-flex align-items-center mx-3 mt-1">
        Client inexistant ?
        <a class="btn btn-sm btn-secondary ml-3" data-navigo href="../clients/ajouter">Creer un nouveau client</a>
      </p>
    </div>
    <div class="form-group">
      <label for="page-add-devis-datetimepicker-input">Date<span class="text-danger">*</span></label>
      <div class="input-group date" id="page-add-devis-datetimepicker" data-target-input="nearest">
        <input type="text" class="form-control" id="page-add-devis-datetimepicker-input" autocomplete="off"
        data-target="#page-add-devis-datetimepicker" data-toggle="datetimepicker" name="date" required/>
        <div class="input-group-append" data-target="#page-add-devis-datetimepicker" data-toggle="datetimepicker">
          <div class="input-group-text"><i class="fa fa-calendar-alt"></i></div>
        </div>
      </div>
      <small class="input-error form-text text-danger">Une date est requise.</small>
    </div>
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

  _developmentTypeList;
  _$selectClient;
  _$selectTypes;
  _addPictureComponents = [];
  _$photos;

  /**
   *
   */
  constructor() {
    super('Ajouter un devis');
  }

  /**
   * @inheritDoc
   */
  getView() {
    const $page = $(this._template);

    this._$selectClient = $page.find('#page-add-devis-customer');

    this._$selectClient.chosen({
      width: '100%',
      no_results_text: 'Ce client n\'existe pas !',
      allow_single_deselect: true
    });

    this._$selectClient.data('validator', () => {
      const errorElement = this._$selectClient.next().next('.input-error');
      if (this._$selectClient.val() === '') {
        errorElement.show(100);
        return false;
      } else {
        errorElement.hide(100);
        return true;
      }
    });

    const $datePicker = $page.find('#page-add-devis-datetimepicker');
    $datePicker.datetimepicker({
      format: 'L',
      minDate: moment(),
      widgetPositioning: {
        horizontal: 'left',
        vertical: 'auto'
      }
    });
    const $datePickerInput = $datePicker.find('#page-add-devis-datetimepicker-input');
    $datePickerInput.data('validator', () => {
      const $errorElement = $datePicker.next('.input-error');
      if ($datePickerInput[0].checkValidity()) {
        $errorElement.hide(100);
        return true;
      } else {
        $errorElement.show(100);
        return false;
      }
    });
    $datePickerInput.on('blur', () => {
      $datePicker.datetimepicker('hide');
    });

    this._$selectTypes = $page.find('#page-add-devis-types');

    this._$selectTypes.chosen({
      width: '100%',
      no_results_text: 'Cet aménagement n\'existe pas !',
      allow_single_deselect: true
    });

    const addPictureComponent1 = new AddPictureComponent();

    this._$selectTypes.data('validator', () => {
      const errorElement = this._$selectTypes.next().next('.input-error');
      if (this._$selectTypes.val()[0] === undefined) {
        errorElement.show(100);
        this._addPictureComponents.forEach(addPictureComponent => addPictureComponent.setDevelopmentTypesList([]));
        return false;
      } else {
        const developmentTypeSelected = this._developmentTypeList.filter(
            developmentType => this._$selectTypes.val().find(selected => selected == developmentType.idType));
        this._addPictureComponents.forEach(addPictureComponent => addPictureComponent.setDevelopmentTypesList(developmentTypeSelected));
        errorElement.hide(100);
        return true;
      }
    });

    onSubmitWithAjax($page.find('form'), () => {
      router.navigate('devis');
      createAlert('success', 'Le devis a bien été ajouté');
    }, (error) => {
      clearAlerts();
      createAlert('danger', error.responseJSON.error);
    }, undefined, undefined, true);

    this._$photos = $page.find("#page-add-devis-photos");

    $page.find('#page-add-devis-btn-add-picture').on('click', () => {
      this._addAnAddPictureComponent();
    });

    this._addAnAddPictureComponent();
    this._retriveCustomerList();
    this._retriveDevelopmentTypeList();

    return $page;
  }

  _retriveCustomerList() {
    ajaxGET('/api/customers-list', null, (data) => {
      for (const customer of data.customers) {
        $(`<option value="${customer.idCustomer}">${customer.lastName} ${customer.firstName}</option>`).appendTo(this._$selectClient);
      }
      this._$selectClient.trigger('chosen:updated');
    });
  }

  _retriveDevelopmentTypeList() {
    ajaxGET('/api/developmentType-list', null, (data) => {
      this._developmentTypeList = data.developementTypesList;
      for (const developementType of this._developmentTypeList) {
        $(`<option value="${developementType.idType}">${developementType.title}</option>`).appendTo(this._$selectTypes);
      }
      this._$selectTypes.trigger('chosen:updated');
    });
  }

  _addAnAddPictureComponent() {
    const addPictureComponent = new AddPictureComponent();
    const addPictureComponentView = addPictureComponent.getView();
    const button = $(`<div class="floating-button-container-right">
        <button class="btn btn-danger floating-button-right" type="button"><i class="fas fa-times"></i></button>
      </div>`);

    button.on('click', () => {
      if (this._addPictureComponents.length <= 1) {
        return;
      }
      this._addPictureComponents = this._addPictureComponents.filter(aPicCp => aPicCp != addPictureComponent);
      button.remove();
      addPictureComponentView.remove();
    });

    this._$photos.append(button, addPictureComponentView);
    this._addPictureComponents[this._addPictureComponents.length] = addPictureComponent;
  }

}