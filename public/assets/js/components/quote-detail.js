'use strict';

import { router } from '../main.js';
import { ajaxGET } from '../utils/ajax.js';
import { Page } from './page.js';
import { isOuvrier } from '../utils/userUtils.js'

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
      this._createQuoteDetailQuote(data.quote);
      this._createQuoteDetailClient(data.quote.customer);
      this._createQuoteDetailDevelopmentTypeList(data.quote.developmentTypes);
      this._createQuoteDetailPhotoBefore(data.quote.listPhotoBefore);
      this._createQuoteDetailPhotoAfter(data.quote.listPhotoAfter);
      if (isOuvrier() && data.quote.state.id === "QUOTE_ENTERED") {
        this._$view.find('.button').append(`<button type="button" id="confirmDate" class="btn btn-warning">Confirmer que la commande est passée.</button>`);
        this.onClickDate(this._$view.find('.button'));
      }
      router.updatePageLinks();
      this.isLoading = false;
    }, () => {
      this.isLoading = false;
    });
  }

  onClickDate($button) {
  $("#confirmDate").click(function (e) {
    $button.empty();
    const _templateToAdd = `<div class="form-group">
      <label for="page-add-devis-datetimepicker-input">Date<span class="text-danger">*</span></label>
      <div class="input-group date" id="page-add-devis-datetimepicker" data-target-input="nearest">
        <input type="text" class="form-control" id="page-add-devis-datetimepicker-input" autocomplete="off"
        data-target="#page-add-devis-datetimepicker" data-toggle="datetimepicker" name="date" required/>
        <div class="input-group-append" data-target="#page-add-devis-datetimepicker" data-toggle="datetimepicker">
          <div class="input-group-text"><i class="fa fa-calendar-alt"></i></div>
        </div>
      </div>
      <small class="input-error form-text text-danger">Une date est requise.</small>
      <div class="form-group mt-2 d-flex justify-content-end">
      <button class="btn btn-primary">Ajouter la date du début de devis.</button>
    </div>
    </div>`;

    $button.append(_templateToAdd);

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
  });
}



_createQuoteDetailQuote(quote) {
  const $quoteDetail = this._$view.find('.detail-quote');
  $quoteDetail.empty();

  const detail = `<h2>Devis: ${quote.idQuote}</h2>
                    <div>Date du devis= ${quote.quoteDate}</div>
                    <div>Montant: ${quote.totalAmount}</div>
                    <div>Date de début de devis: ${quote.startDate == null ? "Pas encore de date" : quote.startDate}</div>
                    <div>Durée des travaux: ${quote.workDuration}</div>`;
  $quoteDetail.append(detail);
}

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

_createQuoteDetailDevelopmentTypeList(typeList) {
  const $quoteDetailType = this._$view.find('.detail-quote-development-types');
  this._$view.find('types').append(`<h4>Types d'aménagements</h4>`);
  $quoteDetailType.empty();

  typeList.forEach(type => {
    this._createTypesListItem($quoteDetailType, type);
  });
}

_createTypesListItem($quoteDetailType, type){
  const detail = `<li>${type.title}</li>`;
  $quoteDetailType.append(detail);
}

_createQuoteDetailPhotoBefore(photoList){
  const $quoteDetailPhoto = this._$view.find('.detail-quote-photo-before');
  this._$view.find('.before').append(`<h4 class="before">Photo avant aménagements</h4>`);

  $quoteDetailPhoto.empty();
  if (photoList.length == 0) {
    $quoteDetailPhoto.append("<div>Il n'y a pas de photo avant aménagement!</div>");
  } else {
    photoList.forEach(photo => {
      this._createPhotoListItem($quoteDetailPhoto, photo);
    });
  }
}

_createPhotoListItem($quoteDetailPhoto, photo){
  const detail = `<img src="${photo.base64}" alt="${photo.title}">`;
  $quoteDetailPhoto.append(detail);
}

_createQuoteDetailPhotoAfter(photoList){
  const $quoteDetailPhoto = this._$view.find('.detail-quote-photo-after');
  this._$view.find('.after').append(`<h4 class="before">Photo avant aménagements</h4>`);
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