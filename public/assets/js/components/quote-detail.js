'use strict';

import {router} from '../main.js';
import {ajaxDELETE, ajaxGET, ajaxPUT} from '../utils/ajax.js';
import {Page} from './page.js';
import {isWorker} from '../utils/userUtils.js';
import {onSubmitWithAjax, serializeFormToJson} from '../utils/forms.js';
import {createAlert} from '../utils/alerts.js';
import {DateInputComponent} from './inputs/datepicker-input.js';
import {AddPictureComponent} from './inputs/picture-add.js';

/**
 * @module Components
 */

/**
 * Component that hold a quote details page
 *
 * @extends module:Components.Page
 */
export class QuoteDetailPage extends Page {

  _template = `<div class="container">
  <h2 class="quote-title mb-3"></h2>
  <div class="worker-panel card mb-3">
    <div class="card-header">
      <h4>Ouvrier/Patron</h4>
    </div>
    <div class="card-body">
      <div class="formContainer"></div>
      <div class="cancelContainer d-flex justify-content-end"></div>
    </div>
  </div>
  <div class="row">
    <div class="col-lg-6">
      <div class="detail-quote card mb-3"></div>
    </div>
    <div class="col-lg-6">
      <div class="detail-quote-client card mb-3"></div>
    </div>
    <div class="col-lg-12">
      <div class="detail-quote-development-types card mb-3"></div>
    </div>
  </div>
  <div class="detail-quote-photos-before card mb-3"></div>
  <div class="detail-quote-photos-after card mb-3"></div>
</div>`;

  /**
   *
   */
  constructor(quoteId) {
    super('Details du devis ' + quoteId);

    this._$view = $(this._template);

    if (isWorker()) {
      this._$view.find('.worker-panel').show();
    } else {
      this._$view.find('.worker-panel').hide();
    }

    ajaxGET(`/api/quote`, `quoteId=${quoteId}`, (data) => {

      this._changeView(data.quote);

      router.updatePageLinks();
      this.isLoading = false;
    }, () => {
      this.isLoading = false;
    });

  }

  /**
   * Change the viewu of the current page
   * @param {*} data
   */
  _changeView(quote) {
    this._createQuoteDetailQuote(quote);
    this._createQuoteDetailClient(quote.customer);
    this._createQuoteDetailDevelopmentTypeList(quote.developmentTypes);
    this._createQuoteDetailPhotoBefore(quote.listPhotoBefore);
    this._createQuoteDetailPhotoAfter(quote.listPhotoAfter);
    this._createWorkerButton(quote);
  }

  _createWorkerButton(quote) {

    if (!isWorker()) {
      return;
    }

    const $formContainer = this._$view.find('.formContainer');
    const $cancelContainer = this._$view.find('.cancelContainer');
    $formContainer.empty();
    $cancelContainer.empty();

    switch (quote.state.id) {

      case 'QUOTE_ENTERED':
        this._createPlaceOrderForm($formContainer, quote.idQuote, quote.state.id);
        this._createCancelQuoteButton($cancelContainer, quote.idQuote);
        break;
      case 'PLACED_ORDERED':
        this._createConfirmDateForm($formContainer, quote);
        this._createCancelQuoteButton($cancelContainer, quote.idQuote);
        break;
      case 'CONFIRMED_DATE':
        if (quote.workDuration > 15) {
          this._createPartialInvoiceForm($formContainer, quote.idQuote, quote.state.id);
        } else {
          this._createTotal_InvoiceForm($formContainer, quote.idQuote, quote.state.id);
        }
        this._createCancelQuoteButton($cancelContainer, quote.idQuote);
        break;
      case 'PARTIAL_INVOICE':
        // TODO
        this._createTotal_InvoiceForm($formContainer, quote.idQuote, quote.state.id);
        this._createCancelQuoteButton($cancelContainer, quote.idQuote);
        break;
      case 'TOTAL_INVOICE':
        this._createVisibleForm($formContainer, quote.idQuote, quote.state.id);
        this._createAddPhotoButton(quote.idQuote, quote.developmentTypes);
        break;
      case 'VISIBLE':
        this._createAddPhotoButton(quote.idQuote, quote.developmentTypes);
        break;
      case 'CANCELLED':
        this._createCanceledMessage($formContainer);
        break;
    }
  }

  /**
   * change the state of the quote to PLACED_ORDERED
   * @param {*} $button
   * @param {*} quoteId
   * @param {*} stateId
   */
  _createPlaceOrderForm($formContainer, quoteId, stateId) {

    const $form = $(`<form action="/api/quote" class="w-100 mb-3" method="put" novalidate>
  <div class="form-group date-container"></div>
  <input type="hidden" name="quoteId" value="${quoteId}"/>
  <input type="hidden" name="stateId" value="${stateId}"/>
  <div class="form-group mt-2 d-flex justify-content-end">
    <button class="btn btn-primary">Confirmer la commande</button>
  </div>
</form>`);

    const $selectDate = $form.find('.date-container');
    const datepicker = new DateInputComponent('date');
    $selectDate.append(datepicker.getView());

    onSubmitWithAjax($form, (data) => {
      this._changeView(data.quote);
      createAlert('success', 'La commande a bien été confirmée !');
    }, () => {
      createAlert('danger', 'La commande n\'a pas été confirmée !');
    });

    $formContainer.append($form);

  }

  /**
   * create the button cancel and the listener
   * @param {*} quoteId
   */
  _createCancelQuoteButton($cancelContainer, quoteId) {
    const $cancelButton = $(`<button class="btn btn-danger" type="button">Annuler l'aménagement</button>`);
    $cancelButton.on('click', () => {

      this.isLoading = true;

      ajaxPUT(`/api/quote`, `quoteId=${quoteId}&stateId=CANCELLED`, (data) => {
        this._changeView(data.quote);
        this.isLoading = false;
      }, () => {
        this.isLoading = false;
      });
    });
    $cancelContainer.append($cancelButton);
  }

  /**
   *
   */
  _createCanceledMessage($container) {
    $container.append('<p class="text-danger">Ce devis a été annulé.</p>');
  }

  /**
   * Hide all the buttons and add the datepicker, when you've chosen the date, the function will dodo an ajaxPut
   * @param {*} quoteId
   * @param {*} stateId
   */
  _changeStartDate(quote) {
    const $changeStartDateButton = $(`<button class="btn btn-warning btn-sm" type="button">Modifier date des débuts de travaux</button>`);

    $changeStartDateButton.on('click', (e) => {
      const $startDateControlButtons = this._$view.find('.startDateControlButtons');
      this._$view.find('.formContainer').empty();

      const $form = $(`<form action="/api/quote" class="w-100 my-2 alert alert-primary" method="put" novalidate>
                      <div class="form-group date-container"></div>
                      <input type="hidden" name="quoteId" value="${quote.idQuote}"/>
                      <input type="hidden" name="stateId" value="${quote.state.id}"/>
                      <div class="form-group mt-1 mb-0 d-flex justify-content-end">
                        <button class="btn btn-danger btn-sm mr-1 cancel" type="button">Annuler</button>
                        <button class="btn btn-primary btn-sm">Changer la date</button>
                      </div>
                    </form>`);

      const $selectDate = $form.find('.date-container');
      const datepicker = new DateInputComponent('date', true, 'Date de début des travaux');
      const datepickerView = datepicker.getView();
      datepickerView.find('.input-group').addClass('input-group-sm');
      $selectDate.append(datepickerView);

      $form.find('.cancel').on('click', () => this._changeView(quote));

      onSubmitWithAjax($form, (data) => {
        this._changeView(data.quote);
        createAlert('success', 'La date de début des travaux a été modifiée !');
      }, () => {
        createAlert('danger', 'La date de début des travaux n\'a pas été modifiée !');
      });

      $(e.target).remove();
      $startDateControlButtons.prepend($form);
    });

    return $changeStartDateButton;
  }

  /**
   * Delete the start date and hide the btn delete and the btn that allows to go to the CONFIRMED_DATE state
   * @param {*} quoteId 
   */
  _deleteStartDate(quoteId) {
    const $deleteStartDateButton = $(`<button class="btn btn-danger btn-sm deleteStartDate" type="button">Supprimer date des débuts de travaux</button>`);

    $deleteStartDateButton.on('click', () => {

      this.isLoading = true;

      ajaxDELETE(`/api/quote?quoteId=${quoteId}`, null, (data) => {
        this._changeView(data.quote);
        this.isLoading = false;
        createAlert('success', 'La date de début des travaux a été supprimée !');
      }, () => {
        this.isLoading = false;
        createAlert('danger', 'La date de début des travaux n\'a pas pu être supprimée !');
      });
    });


    return $deleteStartDateButton;
  }

  /**
   * set the startdate in the db and change the state of the quote to CONFIRMED_DATE
   * @param {*} $button
   * @param {*} quoteId
   * @param {*} stateId
   */
  _createConfirmDateForm($formContainer, quote) {

    const $form = $(`<form action="/api/quote" class="w-100 mb-3" method="put" novalidate>
                      <input type="hidden" name="quoteId" value="${quote.idQuote}"/>
                      <input type="hidden" name="stateId" value="${quote.state.id}"/>
                      <div class="d-flex justify-content-end disable-msg">
                        <small class="text-danger">Veuillez indiquer un date de début des travaux</small>
                      </div>
                      <div class="form-group mt-2 d-flex justify-content-end">
                        <button class="btn btn-primary" disabled>Confirmer la date de début des travaux</button>
                      </div>
                    </form>`);

    onSubmitWithAjax($form, (data) => {
      this._changeView(data.quote);
      createAlert('success', 'La date a bien été confirmée !');
    }, () => {
      createAlert('danger', 'La date n\'a pas été confirmée !');
    });

    $formContainer.append($form);

    const $changeStartDateButton = this._changeStartDate(quote).addClass("mt-1 mr-1");
    const $deleteStartDateButton = this._deleteStartDate(quote.idQuote, quote.state.id).addClass("mt-1 mb-1");

    const startDateControlButtons = this._$view.find('.startDateControlButtons');
    startDateControlButtons.append($changeStartDateButton);

    if (quote.startDate !== null) {
      $form.find('.disable-msg').remove();
      $form.find('button').removeAttr('disabled');
      startDateControlButtons.append($deleteStartDateButton);
    }
  };


  
  _createPartialInvoiceForm($formContainer, quoteId, stateId) {
    const $form = $(`<form action="/api/quote" class="w-100 mb-3" method="put" novalidate>
    <div class="form-group date-container"></div>
    <input type="hidden" name="quoteId" value="${quoteId}"/>
    <input type="hidden" name="stateId" value="${stateId}"/>
    <div class="form-group mt-2 d-flex justify-content-end">
      <button class="btn btn-primary">Confirmer la facture de mileu de chantier</button>
    </div>
  </form>`);

    onSubmitWithAjax($form, (data) => {
      this._changeView(data.quote);
      createAlert('success', 'La facture a bien été envoyée !');
    }, () => {
      createAlert('danger', 'La facture n\'a pas été envoyée !');
    });

    $formContainer.append($form);
  }

  _createTotal_InvoiceForm($formContainer, quoteId, stateId) {

    const $form = $(`<form action="/api/quote" class="w-100 mb-3" method="put" novalidate>
    <div class="form-group date-container"></div>
    <input type="hidden" name="quoteId" value="${quoteId}"/>
    <input type="hidden" name="stateId" value="${stateId}"/>
    <div class="form-group mt-2 d-flex justify-content-end">
      <button class="btn btn-primary">Confirmer la facture</button>
    </div>
  </form>`);

    onSubmitWithAjax($form, (data) => {
      this._changeView(data.quote);
      createAlert('success', 'La facture a bien été envoyée !');
    }, () => {
      createAlert('danger', 'La facture n\'a pas été envoyée !');
    });

    $formContainer.append($form);
  }

  _createVisibleForm($formContainer, quoteId, stateId) {

    const $form = $(`<form action="/api/quote" class="w-100 mb-3" method="put" novalidate>
    <div class="form-group date-container"></div>
    <input type="hidden" name="quoteId" value="${quoteId}"/>
    <input type="hidden" name="stateId" value="${stateId}"/>
    <div class="form-group mt-2 d-flex justify-content-end">
      <button class="btn btn-primary">Rendre la réalisation visible</button>
    </div>
  </form>`);

    onSubmitWithAjax($form, (data) => {
      this._changeView(data.quote);
      createAlert('success', 'La réalisation a été rendue visible.');
    }, () => {
      createAlert('danger', 'La réalisation n\'a pas été rendue visible.');
    });

    $formContainer.append($form);

  }

  _createAddPhotoButton(quoteId, developmentTypeList) {
    const $btn = $('<button class="btn btn-sm btn-primary" type="button">Ajouter des photos</button>');

    let $addPictureForm = this._$view.find('.detail-quote-photos-after .card-body .photos-add');

    $btn.on('click', () => {
      if ($addPictureForm.length < 1) {
        $addPictureForm = $(`<form class="photos-add card p-4 mb-2 align-items-end" method="post" action="/api/photo" novalidate>
  <input type="hidden" name="quoteId" value="${quoteId}" />
  <button class="btn btn-sm btn-primary" type="submit">Enregister les nouvelles photos</button>
</form>`);

        onSubmitWithAjax($addPictureForm, () => {
          const data = serializeFormToJson($addPictureForm);
          const $list = this._$view.find('.detail-quote-photos-after .card-body .list');
          if (Array.isArray(data['pictureData'])) {
            for (let i = 0; i < data['pictureData'].length; i++) {
              this._createPhotoItem($list, {
                base64: data['pictureData'][i],
                title: data['pictureTitle'][i]
              });
            }
          } else {
            this._createPhotoItem($list, {
              base64: data['pictureData'],
              title: data['pictureTitle']
            });
          }

          $addPictureForm.remove();
          $addPictureForm = this._$view.find('.detail-quote-photos-after .card-body .photos-add');

          createAlert('success', 'Les photos ont été ajoutées.');
        }, () => {
          createAlert('danger', 'Les photos n\'ont pas été ajoutées.');
        });

        this._$view.find('.detail-quote-photos-after .card-body').prepend($addPictureForm);
      }

      const addPictureComponent = new AddPictureComponent(false);
      addPictureComponent.setDevelopmentTypesList(developmentTypeList);
      const addPictureComponentView = addPictureComponent.getView();
      addPictureComponentView.addClass('w-100'
          + ' no-shadow');
      $addPictureForm.children('.empty').remove();

      const $removeBtn = $(`<div class="floating-button-container-right">
        <button class="btn btn-danger floating-button-right" type="button"><i class="fas fa-times"></i></button>
      </div>`);

      $removeBtn.on('click', () => {
        $removeBtn.remove();
        addPictureComponentView.remove();
        if ($addPictureForm.children(':not(button,input[name="quoteId"])').length < 1) {
          $addPictureForm.remove();
          $addPictureForm = this._$view.find('.detail-quote-photos-after .card-body .photos-add');
        }
      });

      $addPictureForm.prepend($removeBtn, addPictureComponentView);
    });

    this._$view.find('.detail-quote-photos-after .card-header').append($btn);
  }

  /**
   * set the detail of the quote in the current page
   * @param {*} quote
   */
  _createQuoteDetailQuote(quote) {
    this._$view.find('.quote-title').empty().append(`Devis: ${quote.idQuote}`);

    const $quoteDetail = this._$view.find('.detail-quote');

    const detail = `<div class="card-header">
  <h4>Infos</h4>
</div>
<div class="card-body">
  <p><span class="badge badge-info font-size-100">${quote.state.title}</span></p>
  <p>Date du devis: ${moment(quote.quoteDate).format('L')}</p>
  <p>Montant: ${quote.totalAmount}€</p>
  <div class="startDate">
    <p>Date de début des travaux: ${quote.startDate == null ? "Pas encore de date" : moment(quote.startDate).format('L')}</p>
    <div class="startDateControlButtons d-flex flex-wrap"></div>
  </div>
  <p>Durée des travaux: ${quote.workDuration}</p>
</div>`;

    $quoteDetail.empty().append(detail);
  }

  /**
   * set the detail of the customer in the current page
   * @param {*} customer
   */
  _createQuoteDetailClient(customer) {
    const $quoteDetailClient = this._$view.find('.detail-quote-client');

    const detail = `<div class="card-header">
  <h4>Client</h4>
</div>
<div class="card-body">
  <p>Nom: ${customer.lastName}</p>
  <p>Prenom: ${customer.firstName}</p>
  <p>Adresse: ${customer.address}</p>
  <p>Code postal: ${customer.postalCode}</p>
  <p>Ville: ${customer.city}</p>
  <p>Email: ${customer.email}</p>
  <p>Numéro de téléphone: ${customer.phoneNumber}</p>
</div>`;

    $quoteDetailClient.empty().append(detail);
  }

  /**
   * give all the development type in the current page
   * @param {*} typeList
   */
  _createQuoteDetailDevelopmentTypeList(typeList) {
    const $quoteDetailTypes = this._$view.find('.detail-quote-development-types');
    $quoteDetailTypes.empty();
    $quoteDetailTypes.append(`<div class="card-header"><h4>Types d'aménagements</h4></div>`);

    const $quoteDetailTypesList = $('<ul>', { class: 'list m-1' });

    typeList.forEach(type => {
      $quoteDetailTypesList.append($(`<li>${type.title}</li>`));
    });

    $quoteDetailTypes.append($('<div class="card-body"></div>').append($quoteDetailTypesList));
  }

  /**
   * give all the photos of the quote before development
   * @param {*} typeList
   */
  _createQuoteDetailPhotoBefore(photoList) {
    const $quoteDetailPhoto = this._$view.find('.detail-quote-photos-before');
    $quoteDetailPhoto.empty().append('<div class="card-header"><h4>Photos avant aménagements</h4></div>');
    this._createPhotoList($quoteDetailPhoto, photoList);
  }

  /**
   * give all the photos of the quote after development
   * @param {*} typeList
   */
  _createQuoteDetailPhotoAfter(photoList) {
    const $quoteDetailPhoto = this._$view.find('.detail-quote-photos-after');
    $quoteDetailPhoto.empty().append('<div class="card-header d-flex justify-content-between"><h4>Photos après aménagements</h4></div>');
    this._createPhotoList($quoteDetailPhoto, photoList, false);
  }

  _createPhotoList($container, photoList, isBefore = true) {
    const $cardBody = $('<div class="card-body"></div>');
    if (photoList.length == 0) {
      $cardBody.append(`<p class="empty">Il n'y a pas de photo d'${isBefore ? 'avant' : 'après'} aménagement !</p>`);
    } else {
      const $list = $('<div>', {class: 'list'});
      photoList.forEach(photo => this._createPhotoItem($list, photo, isBefore));
      $cardBody.append($list);
    }
    $container.append($cardBody);
  }

  _createPhotoItem($container, photo, isBefore = true) {
    const $div = $(`<div class="d-flex"><img src="${photo.base64}" alt="${photo.title}"></div>`);
    if (!isBefore) {
      const $icon = $('<i class="far fa-heart"></i>');
      $icon.on('click', () => {
        // TODO backend
        // ajax()
        const $likeIcons = $container.find('.fas').removeClass('fas').addClass('far');
        $icon.removeClass('far').addClass('fas');
        /*}, () => {
          createAlert('danger', 'La photo préférée n\'a pas été changée.');
        }*/
      });
      $div.prepend($icon);
    }
    $container.append($div);
  }

}