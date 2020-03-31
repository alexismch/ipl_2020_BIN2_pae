'use strict';

import {router} from '../main.js';
import {Page} from './page.js';
import {ajaxGET} from '../utils/ajax.js';
import {getUserStatusColor, isClient, isOuvrier} from '../utils/userUtils.js'
import {clearAlerts, createAlert} from '../utils/alerts.js';
import {onSubmitWithAjax} from '../utils/forms.js';

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

  _$selectClient;

  /**
   *
   */
  constructor(userId) {
    super('Details de l\'utilisateur n°' + userId);

    this._$view = $(this._template);

    ajaxGET('/api/user', `id=${userId}`, (data) => {
      this._$view.find('app-loadbar').remove();
      this._createUserDetail(data.userDetail);

    }, (error) => {
      clearAlerts();
      createAlert('danger', error.responseJSON.error);
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
  <p>Veuillez selectionner son statut.</p>
  <select class="custom-select" name="statusChoice" >
    <option value="Client">Client</option> 
    <option value="Ouvrier">Ouvrier</option> 
  </select>
  <input name="pseudo" type="hidden" value="${user.pseudo}">
  <div class="form-group">
      <label for="customerLink">Client<span class="text-danger">*</span></label>
      <select id="customerLink" name="customerId" class="form-control" data-placeholder="Choisissez un client">
        <option value="test">test</option>
      </select>
      <small class="input-error form-text text-danger">Un client doit être selectionné.</small>
      <p class="d-flex align-items-center mx-3 mt-1">
        Client inexistant ?
        <a class="btn btn-sm btn-secondary ml-3" data-navigo href="../clients/ajouter">Creer un nouveau client</a>
      </p>
    </div>
  <div class="d-flex justify-content-end mt-2">
    <button class="btn btn-primary" id="btntest" type="submit">Modifier le statut</button>
  </div>
</form>`);


       this._$selectClient = acceptationForm.find('#customerLink');
this._getCustomerList();
onSubmitWithAjax(acceptationForm, () => {
        router.navigate('utilisateurs');
        clearAlerts();
        createAlert('success', 'Le compte de l\'utilisateur ' + user.pseudo + ' a bien été modifié.' + user.status);
      });
      container.append(acceptationForm);

    }

  }
    _getCustomerList() {
    ajaxGET('/api/customers-list', null, (data) => {
      for (const customer of data.customers) {
        
        $(`<option value="${customer.idCustomer}">${customer.lastName} ${customer.firstName}</option>`).appendTo(this._$selectClient);
      }
      this._$selectClient.trigger('chosen:updated');
    });
  }

}