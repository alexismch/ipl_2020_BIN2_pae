'use strict';

import { router } from '../main.js';
import { ajaxGET, ajaxPUT } from '../utils/ajax.js';
import { Page } from './page.js';
import { isOuvrier } from '../utils/userUtils.js';
import { onSubmitWithAjax } from '../utils/forms.js';
import { createAlert } from '../utils/alerts.js'

/**
 * @module Components
 */

/**
 * Component that hold a quote details page
 *
 * @extends module:Components.Page
 */
export class QuoteDetailPage extends Page {

  _template = `<div>

<app-loadbar class="position-relative h-0"></app-loadbar>    
<div class="container">
      <div class="button"></div>
      <p class="detail-quote"></p>
      <div class="client"></div>
      <p class="detail-quote-client"></p>
      <div class="types"></div>
      <ul class="detail-quote-development-types"></ul>
      <div class="before"></div>
      <div class="detail-quote-photo-before"></div>
      <div class="after"></div>
      <div class="detail-quote-photo-after"></div>
    </div>
  </div>`;


  /**
   *
   */
  constructor(quoteId) {
    super('Details du devis ' + quoteId);

    this._$view = $(this._template);

    ajaxGET(`/api/quote`, `quoteId=${quoteId}`, (data) => {
      this._$view.find('app-loadbar').remove();
      this._changeView(data);

      if (isOuvrier() && data.quote.state.id === "QUOTE_ENTERED") {
        this._$view.find('.button').append(
          `<button type="button" id="confirmDate" class="btn btn-warning">Confirmer que la commande est passée.</button>`);
        this.onClickConfirm(this._$view.find('.button'), quoteId, data.quote.state.id);
      }

      if (isOuvrier() && data.quote.state.id === "PLACED_ORDERED") {
        this.onClickDate(this._$view.find('.button'), quoteId, data.quote.state.id);
      }
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
  _changeView(data) {
    this._createQuoteDetailQuote(data.quote);
    this._createQuoteDetailClient(data.quote.customer);
    this._createQuoteDetailDevelopmentTypeList(data.quote.developmentTypes);
    this._createQuoteDetailPhotoBefore(data.quote.listPhotoBefore);
    this._createQuoteDetailPhotoAfter(data.quote.listPhotoAfter);
  }

  /**
   * set the startdate in the db and change the state of the quote to CONFIRMED_DATE
   * @param {*} $button 
   * @param {*} quoteId 
   * @param {*} stateId 
   */
  onClickDate($button, quoteId, stateId) {

    const $datePicker = $button.find('#page-add-devis-datetimepicker');
    $datePicker.datetimepicker({
      format: 'L',
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


    onSubmitWithAjax($button.find('form'), (data) => {
      this._changeView(data);
      $button.empty();
      const $startDate = this._$view.find(".startDate");
      $startDate.empty();
      $startDate.append(`<div>Date de début de devis: ${data.quote.startDate}</div>`);
      createAlert('success', 'La date a bien été introduite!');
    }, () => {
      createAlert('error', `La date n'a pas été introduite!`);
    });
  };


  /**
   * change the state of the quote to PLACED_ORDERED
   * @param {*} $button 
   * @param {*} quoteId 
   * @param {*} stateId 
   */
  onClickConfirm($button, quoteId, stateId) {
    $("#confirmDate").on('click', () => {
      $("#confirmDate").off();
      ajaxPUT(`/api/quote`, `quoteId=${quoteId}&stateId=${stateId}`, (data) => {
        this._changeView(data);
        $button.empty();
        const _templateToAdd = `<form action="/api/quote" class="w-100 mb-3" method="put" novalidate>
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
      <input type="hidden" name="quoteId" value="${quoteId}"/>
      <input type="hidden" name="stateId" value="${stateId}"/>
      <div class="form-group mt-2 d-flex justify-content-end">
        <button class="btn btn-primary">Ajouter la date du début de devis.</button>
      </div>
    </div>
    </form>`;

        $button.append(_templateToAdd);
      });
    })
  }



  /**
   * set the detail of the quote in the current page
   * @param {*} quote 
   */
  _createQuoteDetailQuote(quote) {
    const $quoteDetail = this._$view.find('.detail-quote');
    $quoteDetail.empty();


    const detail = `<h2>Devis: ${quote.idQuote}</h2>
                    <div>Date du devis= ${quote.quoteDate}</div>
                    <div>Montant: ${quote.totalAmount}</div>
                    <div class="startDate">
                      <div>Date de début de devis: ${quote.startDate == null ? "Pas encore de date" : quote.startDate}</div>
                    </div>
                    <div>Durée des travaux: ${quote.workDuration}</div>`;
    $quoteDetail.append(detail);
  }

  /**
   * set the detail of the customer in the current page
   * @param {*} customer 
   */
  _createQuoteDetailClient(customer) {
    const $quoteDetailClient = this._$view.find('.detail-quote-client');

    this._$view.find('.client').append('<h4>Client</h4>');

    $quoteDetailClient.empty();

    const detail = `<div class="ml-3">Nom: ${customer.lastName}</div>
                    <div class="ml-3">Prenom: ${customer.firstName}</div>
                    <div class="ml-3">Adresse: ${customer.address}</div>
                    <div class="ml-3">Code postal: ${customer.postalCode}</div>
                    <div class="ml-3">Ville: ${customer.city}</div>
                    <div class="ml-3">Email: ${customer.email}</div>
                    <div class="ml-3">Numéro de téléphone: ${customer.phoneNumber}</div>`;

    $quoteDetailClient.append(detail);
  }

  /**
   * give all the development type in the current page
   * @param {*} typeList 
   */
  _createQuoteDetailDevelopmentTypeList(typeList) {
    const $quoteDetailType = this._$view.find('.detail-quote-development-types');
    this._$view.find('.types').append(`<h4>Types d'aménagements</h4>`);
    $quoteDetailType.empty();

    typeList.forEach(type => {
      this._createTypesListItem($quoteDetailType, type);
    });
  }

  _createTypesListItem($quoteDetailType, type) {

    const detail = `<li>${type.title}</li>`;
    $quoteDetailType.append(detail);
  }

  /**
   * give all the photos of the quote before development
   * @param {*} typeList 
   */
  _createQuoteDetailPhotoBefore(photoList) {
    const $quoteDetailPhoto = this._$view.find('.detail-quote-photo-before');
    this._$view.find('.before').append(`<h4 class="before">Photos avant aménagements</h4>`);

    $quoteDetailPhoto.empty();
    if (photoList.length == 0) {
      $quoteDetailPhoto.append("<div>Il n'y a pas de photo avant aménagement!</div>");
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

  /**
   * give all the photos of the quote after development
   * @param {*} typeList 
   */
  _createQuoteDetailPhotoAfter(photoList) {
    const $quoteDetailPhoto = this._$view.find('.detail-quote-photo-after');
    this._$view.find('.after').append(`<h4 class="before">Photos après aménagements</h4>`);
    $quoteDetailPhoto.empty();

    if (photoList.length == 0) {
      $quoteDetailPhoto.append("<div>Il n'y a pas de photo après aménagement!</div>");
    } else {
      photoList.forEach(photo => {
        this._createPhotoListItem($quoteDetailPhoto, photo);
      });
    }
  }
}