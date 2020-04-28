'use strict';

import {Page} from './page.js';
import {ajaxGET} from '../utils/ajax.js';
import {router} from '../main.js';

/**
 * @module Components
 */

/**
 * Component that hold a customer details page
 *
 * @extends module:Components.Page
 */
export class CustomerDetailPage extends Page {

  _template = `<div>
  <form action="devis" class="form-inline p-1 elevation-1 bg-light quotes-list-search" method="get">
  <input class="form-control form-control-sm mx-1 mt-1" name="name" placeholder="Nom du client" type="text">
  <div class="form-group select-date mx-1 mt-1"></div>
  <input class="form-control form-control-sm mx-1 mt-1" name="montantMin" placeholder="Montant minimum" type="number">
  <input class="form-control form-control-sm mx-1 mt-1" name="montantMax" placeholder="Montant maximum" type="number">

  <div class="form-group select-multiple-developmentType mx-1 mt-1"></div>

    <div class="input-group input-group-sm mx-1 mt-1">
      <button class="btn btn-primary btn-sm w-100">Rechercher</button>
    </div>
  </form>
  <p class="quotes-list-search-msg d-none m-0 p-2 alert alert-primary rounded-0"></p>
  <ul class="quotes-list m-2 p-0"></ul>
</div>`;

  /**
   *
   */
  constructor(customerId) {
    super('Details du client');

    this._$view = $(this._template);

    const $selectDate = this._$view.find('.select-date');
    const datepicker = new DateInputComponent('quoteDate', false, 'Date du devis', false, false);
    const datepickerView = datepicker.getView();
    datepickerView.find('.input-group').addClass('input-group-sm');
    $selectDate.append(datepickerView);

    const $selectMultipleDevelopemntType = this._$view.find('.select-multiple-developmentType');
    const mutltipleDevelopmentTypeInputComponent = new MutltipleDevelopmentTypeInputComponent('types', (developmentTypeList) => {
      this._developmentTypeList = developmentTypeList;
    }, false, 'Type d\'aménagement(s)', false, false, 'Type d\'aménagement(s)');
    const mutltipleDevelopmentTypeInputComponentView = mutltipleDevelopmentTypeInputComponent.getView();
    mutltipleDevelopmentTypeInputComponentView.find(
        '#multiple_developmentType_input_' + mutltipleDevelopmentTypeInputComponent.getUniqueId() + '_chosen .chosen-choices').css({
      padding: 0
    });
    $selectMultipleDevelopemntType.append(mutltipleDevelopmentTypeInputComponentView);

    onSubmitWithNavigation(this._$view.find('form'), (url, data) => {
      if (data !== query) {
        router.navigate(url + '?' + data);
      }
    });

    ajaxGET('/api/customer-details', `idCustomer=${customerId}`, (data) => {
      if (jQuery.isEmptyObject(data.customerDetails)) {
        this._noDetail();
      } else {
        this._createCustomerDetail(data.customerDetails);
      }

      router.updatePageLinks();
      this.isLoading = false;
    }, () => {
      this.isLoading = false;
    });
  }

  _noDetail(){
    const detailsItem = `<p>Il n'ya pas de devis!</p>`;
    const $customerDetails = this._$view.find('.quotes-list');
    $customerDetails.append(detailsItem);
  }

  _createCustomerDetail(customerDetails) {
    const $customerDetails = this._$view.find('.quotes-list');
    $customerDetails.empty();
    for (const quote of customerDetails) {
      this._createCustomerDetailsItem($customerDetails, quote);
    }
  }

  _createCustomerDetailsItem($customerDetails, quote) {

    const customerDetailsItem = `<li class="quotes-list-item shadow border border-left-primary rounded mb-2">
    <img src="/assets/img/img-placeholder.jpg" alt="devis n°${quote.idQuote}" />
    <p class="quote-first-col">Devis n°${quote.idQuote} introduit le ${moment(quote.quoteDate).format('L')}</p>
    <p class="quote-first-col">Client: ${quote.customer.lastName} ${quote.customer.firstName}</p>
    <p class="quote-first-col">Date de début des travaux: ${quote.startDate === null ? 'Non determinée' : moment(quote.startDate).format('L')}</p>
    <p class="quote-first-col">Durée des travaux: ${quote.workDuration}</p>
    <p class="quote-first-col">Montant: ${quote.totalAmount}€</p>
    <ul class="quote-development-types">
      <li><span class="badge badge-primary font-size-100">A1 blabla</span></li>
      <li><span class="badge badge-primary font-size-100">A2 blablablabla</span></li>
      <li><span class="badge badge-primary font-size-100">A1 bla</span></li>
      <li><span class="badge badge-primary font-size-100">A1 blablablablablablablabla</span></li>
      <li><span class="badge badge-primary font-size-100">A1 blablablablablabla</span></li>
    </ul>
    <p class="quote-state"><span class="badge badge-info font-size-100">${quote.state.title}</span></p>
    <a class="quote-details-btn btn btn-primary w-min" data-navigo href="devis/${quote.idQuote}">Détails</a>
  </li>`;
    $customerDetails.append(customerDetailsItem);
  }

}