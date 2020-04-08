'use strict';

import {router} from '../main.js';
import {ajaxGET, ajaxPUT, ajaxDELETE} from '../utils/ajax.js';
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
  <div class="formContainer"></div>
  <div class="cancelContainer"></div>
  <p class="detail-quote"></p>
  <p class="detail-quote-client"></p>
  <div class="types"></div>
  <ul class="detail-quote-development-types"></ul>
  <div class="before"></div>
  <div class="detail-quote-photo-before"></div>
  <div class="after"></div>
  <div class="detail-quote-photo-after"></div>
</div>`;

  /**
   *
   */
  constructor(quoteId) {
    super('Details du devis ' + quoteId);

    this._$view = $(this._template);

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
    const $cancelButton = this. _cancelQuote(quoteId).addClass("ml-1");
    const $changeStartDateButton = this. _changeStartDate(quoteId, stateId).addClass("ml-1");
    const $deleteStartDateButton = this. _deleteStartDate(quoteId,stateId).addClass("ml-1");

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
    const $quoteDetail = this._$view.find('.detail-quote');

    const detail = `<h2>Devis: ${quote.idQuote}</h2>
                    <p><span class="badge badge-info font-size-100">${quote.state.title}</span></p>
                    <p class="my-0">Date du devis: ${moment(quote.quoteDate).format('L')}</p>
                    <p class="my-0">Montant: ${quote.totalAmount}</p>
                    <div class="startDate d-flex">
                      <p class="my-0">Date de début des traveaux: ${quote.startDate == null ? "Pas encore de date" : moment(quote.startDate).format('L')}</p>
                      <div class="buttonToAdd"></div>
                    </div>
                    <p class="my-0">Durée des travaux: ${quote.workDuration}</p>`;

    $quoteDetail.empty().append(detail);
  }

  /**
   * set the detail of the customer in the current page
   * @param {*} customer
   */
  _createQuoteDetailClient(customer) {
    const $quoteDetailClient = this._$view.find('.detail-quote-client');

    const detail = `<h4>Client</h4>
                    <p class="ml-3 my-0">Nom: ${customer.lastName}</p>
                    <p class="ml-3 my-0">Prenom: ${customer.firstName}</p>
                    <p class="ml-3 my-0">Adresse: ${customer.address}</p>
                    <p class="ml-3 my-0">Code postal: ${customer.postalCode}</p>
                    <p class="ml-3 my-0">Ville: ${customer.city}</p>
                    <p class="ml-3 my-0">Email: ${customer.email}</p>
                    <p class="ml-3 my-0">Numéro de téléphone: ${customer.phoneNumber}</p>`;

    $quoteDetailClient.empty().append(detail);
  }

  /**
   * give all the development type in the current page
   * @param {*} typeList
   */
  _createQuoteDetailDevelopmentTypeList(typeList) {
    this._$view.find('.types').empty().append(`<h4>Types d'aménagements</h4>`);

    const $quoteDetailType = this._$view.find('.detail-quote-development-types');
    $quoteDetailType.empty();

    typeList.forEach(type => {
      $quoteDetailType.append($(`<li>${type.title}</li>`));
    });
  }

  /**
   * give all the photos of the quote before development
   * @param {*} typeList
   */
  _createQuoteDetailPhotoBefore(photoList) {
    const $quoteDetailPhoto = this._$view.find('.detail-quote-photo-before');
    this._$view.find('.before').empty().append('<h4>Photos avant aménagements</h4>');
    $quoteDetailPhoto.empty();

    if (photoList.length == 0) {
      $quoteDetailPhoto.append("<p>Il n'y a pas de photo avant aménagement!</p>");
    } else {
      photoList.forEach(photo => {
        this._createPhotoListItem($quoteDetailPhoto, photo);
      });
    }
  }

  /**
   * give all the photos of the quote after development
   * @param {*} typeList
   */
  _createQuoteDetailPhotoAfter(photoList) {
    const $quoteDetailPhoto = this._$view.find('.detail-quote-photo-after');
    this._$view.find('.after').empty().append('<h4>Photos après aménagements</h4>');
    $quoteDetailPhoto.empty();

    if (photoList.length == 0) {
      $quoteDetailPhoto.append("<p>Il n'y a pas de photo après aménagement!</p>");
    } else {
      photoList.forEach(photo => {
        this._createPhotoListItem($quoteDetailPhoto, photo);
      });
    }
  }

  _createPhotoListItem($quoteDetailPhoto, photo) {
    const detail = `<img src="${photo.base64}" alt="${photo.title}">`;
    $quoteDetailPhoto.append(detail);
  }

}