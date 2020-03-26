import {router} from '../main.js';
import {onSubmitWithNavigation} from '../utils/forms.js';
import {ajaxGET} from '../utils/ajax.js';


function getTemplate() {
  return `<div>
    <form action="quotes-list" class="form-inline p-1 elevation-1 bg-light" method="get">
      <input class="form-control form-control-sm" name="name" placeholder="Ville du devis" type="text">
      <input class="form-control form-control-sm ml-1" name="postalCode" placeholder="Code postal" type="number">
      <input class="form-control form-control-sm ml-1" name="city" placeholder="nom du client" type="text">
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

/*function createQuotesList($page, quotesList) {
  for (const quote of quotesList) {
    const quoteListItem = `<li class="quotes-list-item border rounded mb-2">
        <p>${quote.idQuote}</p>
        <p>${quote.idCustomer}</p>
        <p>${quote.workDuration}</p>
     
        <p>${quote.startDate}</p>
        <p>${quote.idQuote}</p>
        <a class="btn btn-primary w-min" href="test">Ajouter photo</a>
        </li>`;
    $page.find('.quotes-list').append(quoteListItem);
  }
} */

function createQuotesListItem($quotesList, quote) {

  const quoteListItem = `<li class="list-item border rounded mb-2">
    <p>${quote.idQuote}</p>
        <p>${quote.idCustomer}</p>
        <p>${quote.quoteDate}</p>
        <p>�${quote.workDuration}</p>
        <p>${quote.totalAmount}€</p>
        <a class="btn btn-primary w-min" href="test">Ajouter photo</a>
      </li>`;
  $quotesList.append(quoteListItem);
}

function createView() {
  
  const $page = $(getTemplate());

  ajaxGET('/api/quotes-list', null, (data) => {
    createQuotesList($page, data.quotesList);
  });

  return $page;
}

export function getQuotesPage(query) {
  return {
    getTitle: () => 'Devis',
    getView: () => createView(query)
  }
}