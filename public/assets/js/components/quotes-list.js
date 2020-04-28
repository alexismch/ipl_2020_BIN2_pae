import {router} from '../main.js';
import {onSubmitWithNavigation} from '../utils/forms.js';
import {ajaxGET} from '../utils/ajax.js';
import {Page} from './page.js';
import {DateInputComponent} from './inputs/datepicker-input.js';
import {MutltipleDevelopmentTypeInputComponent} from './inputs/multiple-developmentType-input.js';
import {isCustomer} from '../utils/userUtils.js';

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

_developmentTypeList = [];

  /**
   *
   */
  constructor(query, idCustomer) {
    super(isCustomer() ? 'Mes devis' : idCustomer ? 'Devis du client n°' + idCustomer : 'Devis');

    this._$view = $(this._template);

    const $selectDate = this._$view.find('.select-date');
    const datepicker = new DateInputComponent('quoteDate', false, 'Date du devis', false, false);
    const datepickerView = datepicker.getView();
    datepickerView.find('.input-group').addClass('input-group-sm');
    $selectDate.append(datepickerView);

    const $selectMultipleDevelopemntType = this._$view.find('.select-multiple-developmentType');
    const mutltipleDevelopmentTypeInputComponent = new MutltipleDevelopmentTypeInputComponent('types', (developmentTypeList) => {
      this._developmentTypeList = developmentTypeList;
    }, false, 'Type d\'aménagement(s)', false, false, 'Type d\'aménagement(s)', false);
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

    ajaxGET('/api/quotes-list', query + (idCustomer ? 'idCustomer=' + idCustomer : ''), (data) => {
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
              case 'quoteDate':
                researchMsg += 'Date du devis: ';
                const formattedDate = moment(decodeURI(entry[1])).format('L');
                if (query.match(/quoteDate=[^&]*&/)) {
                  query = query.replace(/quoteDate=[^&]*&/, 'quoteDate=' + formattedDate + '&');
                } else {
                  query = query.replace(/quoteDate=[^&]*/, 'quoteDate=' + formattedDate);
                }
                researchMsg += formattedDate + '</span>';
                shouldHide = false;
                continue;
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
            researchMsg += (entry[0] === 'types' ? '' : decodeURI(entry[1])) + '</span>';
            shouldHide = false;
          }
        }
        if (!shouldHide) {
          this._$view.find('.quotes-list-search-msg').html(researchMsg).removeClass('d-none');
          this._$view.find('form').deserialize(query);
          mutltipleDevelopmentTypeInputComponent.update();
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

    const quoteListItem = `<li class="quotes-list-item shadow border border-left-primary rounded mb-2">
  ${quote.photo ? '<img src="' + quote.photo.base64 + '" alt="' + quote.photo.title + '" />'
        : '<img src="/assets/img/img-placeholder.jpg" alt="pas de photo" />'}
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
    $quotesList.append(quoteListItem);
  }

}
