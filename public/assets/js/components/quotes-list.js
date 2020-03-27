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
      console.log(data);
      this._createQuotesList(data.quotesList);
      router.updatePageLinks();
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

    const quoteListItem = `<li class="quotes-list-item shadow border border-left-primary rounded mb-2">
  <p>Devis n°${quote.idQuote}</p>
  <p>Client: ${quote.idCustomer}</p>
  <p>Date du devis: ${quote.quoteDate}</p>
  <p>Date de début des travaux: ${quote.startDate}</p>
  <p>Durée des travaux: ${quote.workDuration}</p>
  <p>Montant: ${quote.totalAmount}€</p>
  <a class="btn btn-primary w-min" data-navigo href="devis/${quote.idQuote}">Détails</a>
</li>`;
    $quotesList.append(quoteListItem);
  }

}
