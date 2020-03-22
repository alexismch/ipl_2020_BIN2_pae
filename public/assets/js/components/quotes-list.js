'use strict';

import {ajaxGET} from '../utils/ajax.js'

function getTemplate() {
  return `<div>
  <ul class="quotes-list m-2 p-0"></ul>
</div>`;
}

function createQuotesList($page, quotesList) {
  for (const quote of quotesList) {
    const quoteListItem = `<li class="quotes-list-item border rounded mb-2">
        <p>${quote.idQuote}</p>
        <p>${quote.idCustomer}</p>
        <p>${quote.workDuration}</p>
        <p>${quote.state}</p>
        <p>${quote.idQuote}</p>
        <p>${quote.idQuote}</p>
        <a class="btn btn-primary w-min" href="test">Ajouter photo</a>
        </li>`;
    $page.find('.quotes-list').append(quoteListItem);
  }
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