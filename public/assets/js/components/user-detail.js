'use strict';

import {router} from '../main.js';
import {Page} from './page.js';
import {ajaxGET} from '../utils/ajax.js';
import {getUserStatusColor, isClient, isOuvrier} from '../utils/userUtils.js'
import {clearAlerts, createAlert} from '../utils/alerts.js';
import {onSubmitWithAjax} from '../utils/forms.js';
import {CustomerInputComponent} from './customer-input.js';

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
      this._createUserDetail(data.userDetail);
      router.updatePageLinks();
      this.isLoading = false;
    }, () => {
      this.isLoading = false;
    });

  }

  _createUserDetail(user) {

    console.log(user);

    const container = this._$view.find('.container');

    const detail = `<h2>Details de l'utilisateur n°${user.id}</h2>
<p>Nom : ${user.lastName}</p>
<p>Prénom : ${user.firstName}</p>
<p>Pseudo : ${user.pseudo}</p>
<p>Date d'inscription : ${user.registrationDate}</p>
<p>Email : ${user.email}</p>
<p>Ville : ${user.city}</p>
<p><span class="badge badge-${getUserStatusColor(user)} font-size-100">${user.status.name}</span></p>`;

    container.append($(detail));

    if (isClient(user)) {

      const clientDetail = `<p>Client details</p>`;

      container.append(clientDetail);

    } else if (!isOuvrier(user)) {

      const acceptationForm = $(`<form action="/api/confirmationStatut" class="w-100 mb-3" method="post" novalidate>
  <p class="text-danger">Cet utilisateur n'est pas encore confirmé!</p>
  <div class="form-group">
    <select name="statusChoice" class="select-status" data-placeholder="Veuillez sélectionner son statut">
      <option value=""></option>
      <option value="CUSTOMER">Client</option> 
      <option value="WORKER">Ouvrier</option> 
    </select>
  </div>
  
  <input name="pseudo" type="hidden" value="${user.pseudo}">
  <input name="userId" type="hidden" value="${user.id}">
  
  <div class="form-group select-client"></div>
  <div class="d-flex justify-content-end mt-2">
    <button class="btn btn-primary" type="submit">Modifier le statut</button>
  </div>
</form>`);

      const $selectStatus = acceptationForm.find('.select-status');
      const $selectClient = acceptationForm.find('.select-client');
      $selectClient.hide();

      $selectStatus.chosen({
        width: '100%',
        disable_search: true,
        allow_single_deselect: true
      });

      $selectStatus.data('validator', () => {
        const errorElement = $selectStatus.next().next('.input-error');
        if ($selectStatus.val()[0] === undefined) {
          acceptationForm.attr('action', '/api/confirmationStatut');
          $selectClient.hide();
          errorElement.show(100);
          return false;
        } else {

          if ($selectStatus.val() === "CUSTOMER") {
            acceptationForm.attr('action', '/api/link-cc');
            $selectClient.show();
          } else {
            acceptationForm.attr('action', '/api/confirmationStatut');
            $selectClient.hide();
          }

          errorElement.hide(100);
          return true;
        }
      });

      const customerInputComponent = new CustomerInputComponent('customerId', true);

      $selectClient.append(customerInputComponent.getView());

      onSubmitWithAjax(acceptationForm, () => {
        router.navigate('utilisateurs');
        clearAlerts();
        createAlert('success', 'Le compte de l\'utilisateur ' + user.pseudo + ' a bien été modifié.');
      });

      container.append(acceptationForm);
    }

  }

}