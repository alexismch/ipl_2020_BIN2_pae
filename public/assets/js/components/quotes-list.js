import {router} from '../main.js';
import {onSubmitWithNavigation} from '../utils/forms.js';
import {ajaxGET} from '../utils/ajax.js';
import {Page} from './page.js';
import {MutltipleDevelopmentTypeInputComponent} from './inputs/multiple-developmentType-input.js';

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
  <form action="devis" class="form-inline p-1 elevation-1 bg-light" method="get">
  <input class="form-control form-control-sm mx-1 mt-1" name="name" placeholder="Nom du client" type="text">
  <input class="form-control form-control-sm mx-1 mt-1" name="dateDevis" placeholder="Date du devis" type="date">
  <input class="form-control form-control-sm mx-1 mt-1" name="montantMin" placeholder="Montant minimum" type="number">
  <input class="form-control form-control-sm mx-1 mt-1" name="montantMax" placeholder="Montant Maximum" type="number">

  <div class="form-group select-multiple-developmentType"></div>

    <div class="input-group input-group-sm m-1">
      <button class="btn btn-primary btn-sm w-100">Rechercher</button>
    </div>
  </form>
  <p class="quotes-list-search-msg d-none m-0 p-2 alert alert-primary"></p>
  <ul class="quotes-list m-2 p-0"></ul>
</div>`;

_developmentTypeList = [];
  /**
   *
   */
  constructor(query) {
    super('Devis');

    this._$view = $(this._template);
    const $selectMultipleDevelopemntType = this._$view.find('.select-multiple-developmentType');
    const mutltipleDevelopmentTypeInputComponent = new MutltipleDevelopmentTypeInputComponent('types', (developmentTypeList) => {
      this._developmentTypeList = developmentTypeList;
      this._addPictureComponents.forEach(addPictureComponent => addPictureComponent.setDevelopmentTypesList(this._developmentTypeList));
    });
    $selectMultipleDevelopemntType.append(mutltipleDevelopmentTypeInputComponent.getView());
    onSubmitWithNavigation(this._$view.find('form'), (url, data) => {
      if (data !== query) {
        router.navigate(url + '?' + data);
      }
    });

    ajaxGET('/api/quotes-list', query, (data) => {
      if (query !== undefined && query !== null && query !== '') {
        let shouldHide = true;
        let researchMsg = 'Résultats de la recherche: ';
        const fields = query.split('&');
        for (const field of fields) {
          const entry = field.split('=');
          if (entry[1] !== '' && entry[1] !== undefined) {
            researchMsg += '<span class="badge badge-primary mr-1 font-size-100">';
            switch (entry[0]) {
              case 'name':
                researchMsg += 'Nom: ';
                break;
              case'dateDevis':
                researchMsg += 'Date du devis: '
                break;
              case 'montantMin':
                researchMsg += 'Montant minimum: ';
                break;
              case 'montantMax':
                researchMsg += 'Montant Maximum: ';
                break;
              case 'amenagement':
                researchMsg += 'Amenagement: '
                break;
            }
            researchMsg += decodeURI(entry[1]) + '</span>';
            shouldHide = false;
          }
        }
        if (!shouldHide) {
          this._$view.find('.quotes-list-search-msg').html(researchMsg).removeClass('d-none');
          this._$view.find('form').deserialize(query);
        } else {
          this._$view.find('.quotes-list-search-msg').addClass('d-none');
        }
      } else {
        this._$view.find('.quotes-list-search-msg').addClass('d-none');
      }
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
