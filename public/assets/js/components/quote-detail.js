'use strict';

import {router} from '../main.js';
import {ajaxDELETE, ajaxGET, ajaxPUT} from '../utils/ajax.js';
import {Page} from './page.js';
import {isWorker} from '../utils/userUtils.js';
import {onSubmitWithAjax} from '../utils/forms.js';
import {createAlert} from '../utils/alerts.js';
import {DateInputComponent} from './inputs/datepicker-input.js';

/**
 * @module Components
 */

/**
 * Component that hold a quote details page
 *
 * @extends module:Components.Page
 */
export class QuoteDetailPage extends Page {

  _template = `<div class="container">
  <h2 class="quote-title mb-3"></h2>
  <div class="worker-panel card shadow mb-3">
    <div class="card-header">
      <h4 class="m-0 text-primary">Ouvrier/Patron</h4>
    </div>
    <div class="card-body">
      <div class="formContainer"></div>
      <div class="cancelContainer"></div>
    </div>
  </div>
  <div class="row">
    <div class="col-lg-6">
      <div class="detail-quote card shadow mb-3"></div>
    </div>
    <div class="col-lg-6">
      <div class="detail-quote-client card shadow mb-3"></div>
    </div>
    <div class="col-lg-12">
      <div class="detail-quote-development-types card shadow mb-3"></div>
    </div>
  </div>
  <div class="detail-quote-photos-before card shadow mb-3"></div>
  <div class="detail-quote-photos-after card shadow mb-3"></div>
</div>`;

  /**
   *
   */
  constructor(quoteId) {
    super('Details du devis ' + quoteId);

    this._$view = $(this._template);

    if (isWorker()) {
      this._$view.find('.worker-panel').show();
    } else {
      this._$view.find('.worker-panel').hide();
    }

    ajaxGET(`/api/quote`, `quoteId=${quoteId}`, (data) => {

      this._changeView(data.quote);

      router.updatePageLinks();
      this.isLoading = false;
    }, () => {
      this.isLoading = false;
    });

  }

  /**
   * Change the viewu of the current page
   * @param {*} data
   */
  _changeView(quote) {
    this._createQuoteDetailQuote(quote);
    this._createQuoteDetailClient(quote.customer);
    this._createQuoteDetailDevelopmentTypeList(quote.developmentTypes);
    this._createQuoteDetailPhotoBefore(quote.listPhotoBefore);
    this._createQuoteDetailPhotoAfter(quote.listPhotoAfter);
    this._createWorkerButton(quote);
  }

  _createWorkerButton(quote) {

    if (!isWorker()) {
      return;
    }

    const $formContainer = this._$view.find('.formContainer');
    const $cancelCointainer = this._$view.find('.cancelContainer');
    $formContainer.empty();
    $cancelCointainer.empty();

    switch (quote.state.id) {

      case 'QUOTE_ENTERED':
        this._createPlaceOrderForm($formContainer, quote.idQuote, quote.state.id);
        break;
      case 'PLACED_ORDERED':
        this._createConfirmDateForm($formContainer, quote.idQuote, quote.state.id, quote.startDate);
        break;
      case 'CONFIRMED_DATE':
        this._createPartialInvoiceForm($formContainer, quote.idQuote, quote.state.id);
        break;

    }
  }

  /**
   * change the state of the quote to PLACED_ORDERED
   * @param {*} $button
   * @param {*} quoteId
   * @param {*} stateId
   */
  _createPlaceOrderForm($formContainer, quoteId, stateId) {

    const form = `<form action="/api/quote" class="w-100 mb-3" method="put" novalidate>
  <div class="form-group date-container"></div>
  <input type="hidden" name="quoteId" value="${quoteId}"/>
  <input type="hidden" name="stateId" value="${stateId}"/>
  <div class="form-group mt-2 d-flex justify-content-end">
    <button class="btn btn-primary">Confirmer la commande</button>
  </div>
</form>`;

    const $form = $(form);

    const $selectDate = $form.find('.date-container');
    const datepicker = new DateInputComponent('date');
    $selectDate.append(datepicker.getView());

    const $cancelButton = this._cancelQuote(quoteId).addClass("float-right");

    onSubmitWithAjax($form, (data) => {
      this._changeView(data.quote);
      createAlert('success', 'La commande a bien été confirmée !');
    }, () => {
      createAlert('error', `La commande n'a pas été confirmée !`);
    });

    $formContainer.append($form).append($cancelButton);
    
  }

  /**
   * create the button cancel and the listener
   * @param {*} quoteId 
   */
  _cancelQuote(quoteId){
    const $cancelButton = $(`<button class="btn btn-danger" type="button">Annuler l'aménagement</button>`);
    $cancelButton.on('click', () => {

      this.isLoading = true;

      ajaxPUT(`/api/quote`, `quoteId=${quoteId}&stateId=CANCELLED`, (data) => {
        this._changeView(data.quote);
        this.isLoading = false;
      }, () => {
        this.isLoading = false;
      });
    });
    return $cancelButton;
  }


  /**
   * Hide all the buttons and add the datepicker, when you've chosen the date, the function will dodo an ajaxPut 
   * @param {*} quoteId 
   * @param {*} stateId 
   */
  _changeStartDate(quoteId, stateId){
    const $changeStartDateButton = $(`<button class="btn btn-warning btn-sm" type="button">Modifier date des débuts de travaux</button>`);

    $changeStartDateButton.on("click", () => {
      const $formContainer = this._$view.find('.formContainer');
      this._$view.find('.formContainer,.buttonToAdd').empty();

      const form = `<form action="/api/quote" class="w-100 mb-3" method="put" novalidate>
                      <div class="form-group date-container"></div>
                      <input type="hidden" name="quoteId" value="${quoteId}"/>
                      <input type="hidden" name="stateId" value="${stateId}"/>
                      <div class="form-group mt-2 d-flex justify-content-end">
                        <button class="btn btn-primary">Changer la date</button>
                      </div>
                    </form>`;

    const $form = $(form);

    const $selectDate = $form.find('.date-container');
    const datepicker = new DateInputComponent('date');
    $selectDate.append(datepicker.getView());

    onSubmitWithAjax($form, (data) => {
      this._changeView(data.quote);
      createAlert('success', 'La date a bien été modifié!');
    }, () => {
      createAlert('error', `La date n'a pas été modifié!`);
    });

    $formContainer.append($form);

    });

    return $changeStartDateButton;
  }
  

  /**
   * Delete the start date and hide the btn delete and the btn that allows to go to the CONFIRMED_DATE state
   * @param {*} quoteId 
   */
  _deleteStartDate(quoteId){
    const $deleteStartDateButton = $(`<button class="btn btn-danger btn-sm deleteStartDate" type="button">Supprimer date des débuts de travaux</button>`);

    $deleteStartDateButton.on("click", () => {

      this.isLoading = true;
      
      ajaxDELETE(`/api/quote?quoteId=${quoteId}`, null, (data) => {
        this._changeView(data.quote);
        this._$view.find('.deleteStartDate').hide();
        this.isLoading = false;
      }, () => {
        this.isLoading = false;
      });
    });


    return $deleteStartDateButton;
  }

  /**
   * set the startdate in the db and change the state of the quote to CONFIRMED_DATE
   * @param {*} $button
   * @param {*} quoteId
   * @param {*} stateId
   */
  _createConfirmDateForm($formContainer, quoteId, stateId, startDate) {

    const $divButtons = $(` <div class="form-group mt-2 d-flex">
                    </div>`);
    const $confirmDateButton = $('<button class="btn btn-primary deleteStartDate" type="button">Confirmer la date de début des travaux</button>');
    const $cancelButton = this._cancelQuote(quoteId).addClass("ml-1");
    const $changeStartDateButton = this._changeStartDate(quoteId, stateId).addClass("ml-1");
    const $deleteStartDateButton = this._deleteStartDate(quoteId, stateId).addClass("ml-1 mt-2");

    this._$view.find('.buttonToAdd').append($changeStartDateButton).append($deleteStartDateButton);

    $divButtons.append($confirmDateButton);
    this._$view.find('.cancelContainer').append($cancelButton);

    $confirmDateButton.on('click', () => {

      this.isLoading = true;

      ajaxPUT(`/api/quote`, `quoteId=${quoteId}&stateId=${stateId}`, (data) => {
        this._changeView(data.quote);
        this.isLoading = false;
      }, () => {
        this.isLoading = false;
      });
    });

    $formContainer.append($divButtons);


    if(startDate == null){
      this._$view.find('.deleteStartDate').hide();
    }
  };

  //TODO
  _createPartialInvoiceForm($formContainer, quoteId, stateId){
    const $cancelButton = this._cancelQuote(quoteId).addClass("ml-1");

    this._$view.find('.cancelContainer').append($cancelButton);
  }

  /**
   * set the detail of the quote in the current page
   * @param {*} quote
   */
  _createQuoteDetailQuote(quote) {
    this._$view.find('.quote-title').append(`Devis: ${quote.idQuote}`);

    const $quoteDetail = this._$view.find('.detail-quote');

    const detail = `<div class="card-header py-3">
  <h4 class="m-0 text-primary">Infos</h4>
</div>
<div class="card-body">
  <p><span class="badge badge-info font-size-100">${quote.state.title}</span></p>
  <p class="my-0">Date du devis: ${moment(quote.quoteDate).format('L')}</p>
  <p class="my-0">Montant: ${quote.totalAmount}</p>
  <div class="startDate d-flex">
    <p class="my-0">Date de début des traveaux: ${quote.startDate == null ? "Pas encore de date" : moment(quote.startDate).format('L')}</p>
    <div class="buttonToAdd"></div>
  </div>
  <p class="my-0">Durée des travaux: ${quote.workDuration}</p>
</div>`;

    $quoteDetail.empty().append(detail);
  }

  /**
   * set the detail of the customer in the current page
   * @param {*} customer
   */
  _createQuoteDetailClient(customer) {
    const $quoteDetailClient = this._$view.find('.detail-quote-client');

    const detail = `<div class="card-header py-3">
  <h4 class="m-0 text-primary">Client</h4>
</div>
<div class="card-body">
  <p class="m-0">Nom: ${customer.lastName}</p>
  <p class="m-0">Prenom: ${customer.firstName}</p>
  <p class="m-0">Adresse: ${customer.address}</p>
  <p class="m-0">Code postal: ${customer.postalCode}</p>
  <p class="m-0">Ville: ${customer.city}</p>
  <p class="m-0">Email: ${customer.email}</p>
  <p class="m-0">Numéro de téléphone: ${customer.phoneNumber}</p>
</div>`;

    $quoteDetailClient.empty().append(detail);
  }

  /**
   * give all the development type in the current page
   * @param {*} typeList
   */
  _createQuoteDetailDevelopmentTypeList(typeList) {
    const $quoteDetailTypes = this._$view.find('.detail-quote-development-types');
    $quoteDetailTypes.empty();
    $quoteDetailTypes.append(`<div class="card-header py-3"><h4 class="m-0 text-primary">Types d'aménagements</h4></div>`);

    const $quoteDetailTypesList = $('<ul>', {class: 'list m-1'});

    typeList.forEach(type => {
      $quoteDetailTypesList.append($(`<li>${type.title}</li>`));
    });

    $quoteDetailTypes.append($('<div class="card-body"></div>').append($quoteDetailTypesList));
  }

  /**
   * give all the photos of the quote before development
   * @param {*} typeList
   */
  _createQuoteDetailPhotoBefore(photoList) {
    const $quoteDetailPhoto = this._$view.find('.detail-quote-photos-before');
    $quoteDetailPhoto.empty().append('<div class="card-header py-3"><h4 class="m-0 text-primary">Photos avant aménagements</h4></div>');
    this._createPhotoList($quoteDetailPhoto, photoList);
  }

  /**
   * give all the photos of the quote after development
   * @param {*} typeList
   */
  _createQuoteDetailPhotoAfter(photoList) {
    const $quoteDetailPhoto = this._$view.find('.detail-quote-photos-after');
    $quoteDetailPhoto.empty().append('<div class="card-header py-3"><h4 class="m-0 text-primary">Photos après aménagements</h4></div>');
    this._createPhotoList($quoteDetailPhoto, photoList);
  }

  _createPhotoList($container, photoList) {
    const $cardBody = $('<div class="card-body"></div>');
    if (photoList.length == 0) {
      $cardBody.append("<p class='empty'>Il n'y a pas de photo après aménagement!</p>");
    } else {
      const $list = $('<div>', {class: 'list'});
      photoList.forEach(photo => {
        this._createPhotoListItem($list, photo);
      });
      $cardBody.append($list);
    }
    $container.append($cardBody);
  }

  _createPhotoListItem($quoteDetailPhoto, photo) {
    const detail = `<img src="${photo.base64}" alt="${photo.title}">`;
    $quoteDetailPhoto.append(detail);
  }

}