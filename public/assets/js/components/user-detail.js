'use strict';

import {Page} from './page.js';
import {ajaxGET} from '../utils/ajax.js';
import {getUserStatusColor, isClient, isOuvrier} from '../utils/userUtils.js'
import {clearAlerts, createAlert} from '../utils/alerts.js';

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

      const acceptationForm = `<form action="/api/confirmationStatut" class="w-100 mb-3" method="post" novalidate>
       Cet utilisateur n'est pas encore confirmé!
       Veuillez selectionner son statut.
       <select class="selectpicker" data-style="btn-primary"  name="statusChoice" id="statusChoice">
    <option value="Client">Client</option> 
    <option value="Ouvrier">Ouvrier</option> 
    </select>
        <input name="pseudo" type="hidden" value="${user.pseudo}">
       <a> <input type="submit" value="confirmer Inscription"></a>
       </form>`;

      container.append(acceptationForm);

    }

  }

}