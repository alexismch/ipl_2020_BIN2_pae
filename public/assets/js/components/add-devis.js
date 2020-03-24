'use strict';

import {router} from '../main.js';
import {ajaxGET} from '../utils/ajax.js';
import {onSubmitWithAjax} from '../utils/forms.js';

function getTemplate() {
  return `<div class="container">
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
        <p> </p>
       <p class="text-danger">Client inexistant ?      
        <input  class="btn btn-danger" type=button onclick=window.location.href="creerClient"; value="Creer un nouveau client" /></p> 
        
        <small class="input-error form-text text-danger">Un client doit être selectionné.</small>
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
      <button class="btn btn-primary">Ajouter le devis</button>
    </form>
  </div>`;
}

function createView() {
  const $page = $(getTemplate());

  const $selectClient = $page.find('#page-add-devis-customer');

  $selectClient.chosen({
    width: '100%',
    no_results_text: 'Ce client n\'existe pas !',
    allow_single_deselect: true
  });

  $selectClient.append($('<option value="1">TEST</option>'));
  $selectClient.trigger('chosen:updated');

  $selectClient.data('validator', () => {
    const errorElement = $selectClient.next().next('.input-error');
    if ($selectClient.val() === '') {
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

  const $selectTypes = $page.find('#page-add-devis-types');

  $selectTypes.chosen({
    width: '100%',
    no_results_text: 'Cet aménagment n\'existe pas !',
    allow_single_deselect: true
  });

  ajaxGET('/api/developmentType-list', null, (data) => {
    for (const developementType of data.developementTypesList) {
      console.log(developementType);
      $(`<option value="${developementType.idType}">${developementType.title}</option>`).appendTo($selectTypes);
    }
    $selectTypes.trigger('chosen:updated');
  });

  $selectTypes.data('validator', () => {
    const errorElement = $selectTypes.next().next('.input-error');
    if ($selectTypes.val()[0] === undefined) {
      errorElement.show(100);
      return false;
    } else {
      errorElement.hide(100);
      return true;
    }
  });

  onSubmitWithAjax($page.find('form'), () => {
    router.navigate('devis');
    createAlert('success', 'Le devis à bien été ajouté');
  }, (error) => {
    clearAlerts();
    createAlert('danger', error.responseJSON.error);
  });

  return $page;
}

export function getAddDevisPage() {
  return {
    getTitle: () => 'Ajouter un devis',
    getView: createView
  }
}