'use strict';

import {router} from '../main.js';
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
  <app-loadbar class="position-relative h-0"></app-loadbar>
  <div class="container"></div>
</div>`;

  /**
   *
   */
  constructor(userId) {
    super('Details de l\'utilisateur n°' + userId);

    this._$view = $(this._template);

    ajaxGET('/api/user', `id=${userId}`, (data) => {
      this._$view.find('app-loadbar').remove();
      this._createUserDetail(data.user);
      router.updatePageLinks();
      this.isLoading = false;
    }, () => {
      this.isLoading = false;
    });

  }

  _createUserDetail(user) {

    const container = this._$view.find('.container').empty();

    const detail = `<h2>Details de l'utilisateur n°${user.id}</h2>
<p>Nom : ${user.lastName}</p>
<p>Prénom : ${user.firstName}</p>
<p>Pseudo : ${user.pseudo}</p>
<p>Date d'inscription : ${user.registrationDate}</p>
<p>Email : ${user.email}</p>
<p>Ville : ${user.city}</p>
<p><span class="badge badge-${getUserStatusColor(user)} font-size-100">${user.status.name}</span></p>`;

    container.append($(detail));

    if (isCustomer(user)) {

      const $linkForm = $(`<form action="/api/link-cc" class="w-100 mb-3" method="post" novalidate>
  <div class="form-group select-client"></div>
  
  <input name="userId" type="hidden" value="${user.id}" />
  
  <div class="d-flex justify-content-end mt-2">
    <button class="btn btn-primary" type="submit">Lier l'utilisateur au client</button>
  </div>
</form>`);

      const $selectClient = $linkForm.find('.select-client');

      const customerInputComponent = new CustomerInputComponent('customerId', true);
      $selectClient.append(customerInputComponent.getView());

      onSubmitWithAjax($linkForm, (data) => {
        // this._createUserDetail(data.user);
        clearAlerts();
        createAlert('success', 'Le compte de l\'utilisateur ' + user.pseudo + ' a été lié au client.');
      }, () => {
        clearAlerts();
        createAlert('error', 'Le compte de l\'utilisateur ' + user.pseudo + ' n\'a pas été lié au client.');
      });

      container.append($linkForm);

    } else if (!isWorker(user)) {

      const $acceptationForm = $(`<form action="/api/user" class="w-100 mb-3" method="put" novalidate>
  <p class="text-danger">Cet utilisateur n'est pas encore confirmé!</p>
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
    <button class="btn btn-primary" type="submit">Modifier le statut</button>
  </div>
</form>`);

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

      onSubmitWithAjax($acceptationForm, (data) => {
        this._createUserDetail(data.user);
        clearAlerts();
        createAlert('success', 'Le compte de l\'utilisateur ' + user.pseudo + ' a été modifié.');
      }, () => {
        clearAlerts();
        createAlert('error', 'Le compte de l\'utilisateur ' + user.pseudo + ' n\'a pas été modifié.');
      });

      container.append($acceptationForm);
    }

  }

}