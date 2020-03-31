import {router} from '../main.js';
import {ajaxGET} from '../utils/ajax.js';
import {Page} from './page.js';

/**
 * @module Components
 */

/**
 * Component that hold the quotes list page
 *
 * @extends module:Components.Page
 */
export class QuotesListPage extends Page {

  _template = `<div>
  <form action="quotes-list" class="form-inline p-1 elevation-1 bg-light" method="get">
    <!-- TODO ajouter filter devis -->
    <p>Filtre à faire</p>
    <div class="input-group input-group-sm m-1">
      <button class="btn btn-primary btn-sm w-100">Rechercher</button>
    </div>
  </form>
  <p class="quotes-list-search-msg d-none m-0 p-2 alert alert-primary"></p>
  <ul class="quotes-list m-2 p-0"></ul>
</div>`;

  /**
   *
   */
  constructor() {
    super('Devis');

    this._$view = $(this._template);

    ajaxGET('/api/quotes-list', null, (data) => {
      this._createQuotesList(data.quotesList);
      router.updatePageLinks();
      this.isLoading = false;
    }, () => {
      this.isLoading = false;
    });

  }

  _createQuotesList(quotesList) {
    const $quotesList = this._$view.find('.quotes-list');
    $quotesList.empty();
    for (const quote of quotesList) {
      this._createQuotesListItem($quotesList, quote);
    }
  }

  _createQuotesListItem($quotesList, quote) {

    console.log(quote);

    const quoteListItem = `<li class="quotes-list-item shadow border border-left-primary rounded mb-2">
  <p class="quote-first-col">Devis n°${quote.idQuote}</p>
  <p class="quote-first-col">Client: ${quote.customer.lastName} ${quote.customer.firstName}</p>
  <p class="quote-date">Date du devis: ${quote.quoteDate}</p>
  <p class="quote-first-col">Date de début des travaux: ${quote.startDate == null ? 'Non determinée' : quote.startDate}</p>
  <p class="quote-first-col">Durée des travaux: ${quote.workDuration}</p>
  <p class="quote-amount">Montant: ${quote.totalAmount}€</p>
  <p class="quote-state"><span class="badge badge-info font-size-100">${quote.state.title}</span></p>
  <a class="quote-details-btn btn btn-primary w-min" data-navigo href="devis/${quote.idQuote}">Détails</a>
</li>`;
    $quotesList.append(quoteListItem);
  }

}
