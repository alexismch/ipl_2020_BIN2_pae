'use strict';

import {Page} from './page.js';
import {ajaxGET} from '../utils/ajax.js';
import {getUserStatusColor, isCustomer, isWorker} from '../utils/userUtils.js'
import {clearAlerts, createAlert} from '../utils/alerts.js';
import {onSubmitWithAjax} from '../utils/forms.js';
import {CustomerInputComponent} from './inputs/customer-input.js';

/**
 * @module Components
 */

/**
 * Component that hold a user details page
 *
 * @extends module:Components.Page
 */
export class UserDetailPage extends Page {

  _template = `<div>
  <div class="container"></div>
</div>`;

  /**
   *
   */
  constructor(userId) {
    super('Details de l\'utilisateur n°' + userId);

    this._$view = $(this._template);

    ajaxGET('/api/user', `id=${userId}`, (data) => {
      this._createUserDetail(data.user);
      this.isLoading = false;
    }, () => {
      this.isLoading = false;
    });

  }

  _createUserDetail(user) {

    const container = this._$view.find('.container').empty();

    const detail = `<h2 class="mb-3">Details de l'utilisateur n°${user.id}</h2>
<div class="card mb-3">
  <div class="card-header">
    <h4>Infos</h4>
  </div>
  <div class="card-body">
    <p>Statut : <span class="badge badge-${getUserStatusColor(user)} font-size-100">${user.status.name}</span></p>
    <p>Nom : ${user.lastName}</p>
    <p>Prénom : ${user.firstName}</p>
    <p>Pseudo : ${user.pseudo}</p>
    <p>Date d'inscription : ${user.registrationDate}</p>
    <p>Email : ${user.email}</p>
    <p>Ville : ${user.city}</p>
  </div>
</div>`;

    container.append($(detail));

    if (isCustomer(user)) {

      this.isLoading = true;
      ajaxGET('/api/customer', 'userId=' + user.id, (data) => {
        if (data.customer == null) {
          this._createLinkCcFrom(container, user);
        } else {
          this._createCustomerDetails(container, data.customer);
        }
        this.isLoading = false;
      }, () => {
        this.isLoading = false;
      });

    } else if (!isWorker(user)) {
      this._createAcceptationFrom(container, user);
    }

  }

  _createAcceptationFrom(container, user) {

    const $acceptationForm = $(`<div class="card">
  <div class="card-header">
    <h4>Formulaire d'acceptation</h4>
  </div>
  <div class="card-body">
    <form action="/api/user" class="w-100 mb-3" method="put" novalidate>
      <p class="text-danger mb-2">La demande d'inscription de cet utilisateur n'as pas encore été acceptée !</p>
      <div class="form-group">
        <select name="status" class="select-status" data-placeholder="Veuillez sélectionner son statut" required>
          <option value=""></option>
          <option value="CUSTOMER">Client</option> 
          <option value="WORKER">Ouvrier</option> 
        </select>
        <small class="input-error form-text text-danger">Un statut est requis.</small>
      </div>
      
      <input name="userId" type="hidden" value="${user.id}" />
      
      <div class="d-flex justify-content-end mt-2">
        <button class="btn btn-primary" type="submit">Accepter l'utilisateur</button>
      </div>
    </form>
  </div>
</div>`);

    const $selectStatus = $acceptationForm.find('.select-status');

    $selectStatus.chosen({
      width: '100%',
      disable_search: true,
      allow_single_deselect: true
    });

    $selectStatus.data('validator', () => {
      const errorElement = $selectStatus.next().next('.input-error');
      if ($selectStatus.val()[0] === undefined) {
        errorElement.show(100);
        return false;
      } else {
        errorElement.hide(100);
        return true;
      }
    });

    onSubmitWithAjax($acceptationForm.find('form'), (data) => {
      this._createUserDetail(data.user);
      clearAlerts();
      createAlert('success', 'Le compte de l\'utilisateur ' + user.pseudo + ' a été modifié.');
    }, () => {
      clearAlerts();
      createAlert('error', 'Le compte de l\'utilisateur ' + user.pseudo + ' n\'a pas été modifié.');
    });

    container.append($acceptationForm);

  }

  _createLinkCcFrom(container, user) {

    const $linkForm = $(`<div class="card">
  <div class="card-header">
    <h4>Liaison avec un client</h4>
  </div>
  <div class="card-body">
    <form action="/api/link-cc" class="w-100 mb-3" method="post" novalidate>
      <div class="form-group select-client"></div>
      
      <input name="userId" type="hidden" value="${user.id}" />
      
      <div class="d-flex justify-content-end mt-2">
        <button class="btn btn-primary" type="submit">Lier l'utilisateur au client</button>
      </div>
    </form>
  </div>
</div>`);

    const $selectClient = $linkForm.find('.select-client');

    const customerInputComponent = new CustomerInputComponent('customerId', true);
    $selectClient.append(customerInputComponent.getView());

    onSubmitWithAjax($linkForm.find('form'), (data) => {
      this.isLoading = true;
      ajaxGET('/api/user', 'id=' + user.id, (data) => {
        this._createUserDetail(data.user);
        this.isLoading = false;
      }, () => {
        this.isLoading = false;
      });
      clearAlerts();
      createAlert('success', 'Le compte de l\'utilisateur ' + user.pseudo + ' a été lié au client.');
    }, () => {
      clearAlerts();
      createAlert('error', 'Le compte de l\'utilisateur ' + user.pseudo + ' n\'a pas été lié au client.');
    });

    container.append($linkForm);

  }

  _createCustomerDetails(container, customer) {

    const customerDetails = $(`<div class="card">
  <div class="card-header">
    <h4>Lié au client n°${customer.idCustomer}</h4>
  </div>
  <div class="card-body">
    <p>Nom : ${customer.lastName}</p>
    <p>Prénom : ${customer.firstName}</p>
    <p>Email : ${customer.email}</p>
    <p>Nuréro de téléphone : ${customer.phoneNumber}</p>
    <p>Adresse : ${customer.address}</p>
    <p>Code postal : ${customer.postalCode}</p>
    <p>Ville : ${customer.city}</p>
  </div>
</div>`);

    container.append(customerDetails);

  }

}