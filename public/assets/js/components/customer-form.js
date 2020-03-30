'use strict';

import {router} from '../main.js';
import {clearAlerts, createAlert} from '../utils/alerts.js';
import {onSubmitWithAjax} from '../utils/forms.js';
import {Page} from './page.js';

/**
 * @module Components
 */

/**
 * Component that hold a form to add a customer
 *
 * @extends module:Components.Page
 */
export class CustomerFormPage extends Page {

  _template = `<div class="container">
    <h2>Ajouter un client</h2>
    <form action="/api/customer" class="w-100 mb-3" method="post" novalidate>
      <div class="form-group">
        <label for="page-creerClient-nom">Nom<span class="text-danger">*</span></label>
        <input class="form-control" id="page-creerClient-firstname" name="lastname" required type="text"/>
        <small class="input-error form-text text-danger">Le nom est requis.</small>
      </div>
      <div class="form-group">
        <label for="page-creerClient-prenom">Prenom<span class="text-danger">*</span></label>
        <input class="form-control" id="page-creerClient-prenom" name="firstname" required type="text"/>
        <small class="input-error form-text text-danger">Le prénom est requis.</small>
      </div>
      <div class="form-group">
        <label for="page-creerClient-nom">Adresse<span class="text-danger">*</span></label>
        <input class="form-control" id="page-creerClient-adresse" name="address" required type="text"/>
        <small class="input-error form-text text-danger">Veuillez completer votre adresse</small>
      </div>
      <div class="form-group">
        <label for="page-creerClient-codePostal">Code Postal<span class="text-danger">*</span></label>
        <input class="form-control" id="page-creerClient-prenom" name="postalCode" required type="text"/>
        <small class="input-error form-text text-danger">Veuillez completer votre Code Postal</small>
      </div>
      <div class="form-group">
        <label for="page-creerClient-ville">Ville<span class="text-danger">*</span></label>
        <input class="form-control" id="page-creerClient-ville" name="city" required type="text"/>
        <small class="input-error form-text text-danger">Veuillez completer votre villet</small>
      </div>
      <div class="form-group">
        <label for="page-creerClient-email">Email<span class="text-danger">*</span></label>
        <input class="form-control" id="page-creerClient-email" name="email" required type="email"/>
        <small class="input-error form-text text-danger">Un email valide est requis.</small>
	    </div>
	    <div class="form-group">
        <label for="page-creerClient-phoneNumber">Telephone<span class="text-danger">*</span></label>
        <input class="form-control" id="page-creerClient-phoneNumber" name="phoneNumber" required type="text"/>
        <small class="input-error form-text text-danger">Un phoneNumber valide est requis.</small>
      </div>
      <button class="btn btn-primary">Ajouter le client</button>
    </form>
  </div>`;

  /**
   *
   */
  constructor() {
    super('Ajouter un client');

    this._$view = $(this._template);

    onSubmitWithAjax(this._$view.find('form'), (data) => {
      router.navigate('clients');
      clearAlerts();
      createAlert('success', 'Le client a bien été ajouté !');
    }, (error) => {
      clearAlerts();
      createAlert('danger', error.responseJSON.error);
    });

  }

}