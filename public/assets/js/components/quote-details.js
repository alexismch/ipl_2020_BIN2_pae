'use strict';

import {router} from '../main.js';
import {ajaxGET} from '../utils/ajax.js';
import {Page} from './page.js';

/**
 * @module Components
 */

/**
 * Component that hold a quote details page
 *
 * @extends module:Components.Page
 */
export class QuoteDetailsPage extends Page {

  _template = `<div>
  <app-loadbar class="position-relative h-0"></app-loadbar>
  <div class="container">
    <p class="detail-quote"></p>
    <h4>Client</h4>
    <p class="detail-quote-client"></p>
    <h4>Types d'aménagements</h4>
    <ul class="detail-quote-development-types"></ul>
    <h4>Photo avant aménagements</h4>
    <div class="detail-quote-photo-before"></div>
    <h4>Photo après aménagements</h4>
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
      this._createQuoteDetailsQuote(data.quote);
      this._createQuoteDetailsClient(data.quote.customer);
      this._createQuoteDetailsDevelopmentTypeList(data.quote.developmentTypesSet);
      this._createQuoteDetailPhotoBefore(data.quote.listPhotoBefore);
      this._createQuoteDetailPhotoAfter(data.quote.listPhotoAfter);
      router.updatePageLinks();
    });

  }


  _createQuoteDetailsQuote(quote) {
    const $quoteDetail = this._$view.find('.detail-quote');
    $quoteDetail.empty();

    const detail = `<h2>Devis: ${quote.idQuote}</h2>
                    <div>Date du devis= ${quote.quoteDate}</div>
                    <div>Montant: ${quote.totalAmount}</div>
                    <div>Date de début de devis: ${quote.startDate == null ? "Pas encore de date" : quote.startDate}</div>
                    <div>Durée des travaux: ${quote.workDuration}</div>`;
    $quoteDetail.append(detail);
  }

  _createQuoteDetailsClient(customer){
    const $quoteDetailClient = this._$view.find('.detail-quote-client');
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

  _createQuoteDetailsDevelopmentTypeList(typeList){
    const $quoteDetailType = this._$view.find('.detail-quote-development-types');
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
    $quoteDetailPhoto.empty();
    if(photoList.length == 0){
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
    $quoteDetailPhoto.empty();

    if(photoList.length == 0){
      $quoteDetailPhoto.append("<div>Il n'y a pas de photo après aménagement!</div>");
    } else {
      photoList.forEach(photo => {
        this._createPhotoListItem($quoteDetailPhoto, photo);
      });
    }
  }
}