import {router} from '../main.js';
import {ajaxGET} from '../utils/ajax.js';

function getTemplate() {
  return `<div>
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
}


function createQuotesList($page, quotesList) {
  const $quotesList = $page.find('.quotes-list');
  $quotesList.empty();
  for (const quote of quotesList) {
    createQuotesListItem($quotesList, quote);
  }
}

function createQuotesListItem($quotesList, quote) {

  const quoteListItem = `<li class="quotes-list-item shadow border border-left-primary rounded mb-2">
  <p>Devis n°${quote.idQuote}</p>
  <p>Client: ${quote.idCustomer}</p>
  <p>Date du devis: ${quote.quoteDate}</p>
  <p>Date de début des travaux: ${quote.startDate}</p>
  <p>Durée des travaux: ${quote.workDuration}</p>
  <p>Montant: ${quote.totalAmount}€</p>
  <a class="btn btn-primary w-min" data-navigo href="devis/${quote.idQuote}">Ajouter photo</a>
</li>`;
  $quotesList.append(quoteListItem);
}

function createView() {
  
  const $page = $(getTemplate());

  ajaxGET('/api/quotes-list', null, (data) => {
    console.log(data);
    createQuotesList($page, data.quotesList);
    router.updatePageLinks();
  });

  return $page;
}

export function getQuotesPage(query) {
  return {
    getTitle: () => 'Devis',
    getView: () => createView(query)
  }
}