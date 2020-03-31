'use strict';

import {router} from '../main.js';
import {clearAlerts, createAlert} from '../utils/alerts.js';
import {onSubmitWithAjax} from '../utils/forms.js';
import {Page} from './page.js';

/**
 * @module Components
 */

/**
 * Component that hold the register page
 *
 * @extends module:Components.Page
 */
export class RegisterPage extends Page {

  _template = `<div class="container">
  <h2>Inscription</h2>
  <form action="/api/register" class="w-100 mb-3" method="post" novalidate>
    <div class="form-group">
      <label for="page-inscription-pseudo">Pseudo<span class="text-danger">*</span></label>
      <input class="form-control" id="page-inscription-pseudo" name="pseudo" required type="text"/>
      <small class="input-error form-text text-danger">Le pseudo est requis.</small>
    </div>
    <div class="row m-0 p-0">
      <div class="col-lg px-0 pr-lg-2">
        <div class="form-group">
          <label for="page-inscription-nom">Nom<span class="text-danger">*</span></label>
          <input class="form-control" id="page-inscription-nom" name="nom" required type="text"/>
          <small class="input-error form-text text-danger">Le nom est requis</small>
        </div>
      </div>
      <div class="col-lg px-0 pl-lg-2">
      <div class="form-group">
        <label for="page-inscription-prenom">Prenom<span class="text-danger">*</span></label>
        <input class="form-control" id="page-inscription-prenom" name="prenom" required type="text"/>
        <small class="input-error form-text text-danger">Le prenom est requis</small>
      </div>
      </div>
    </div>
    <div class="form-group">
      <label for="page-inscription-ville">Ville<span class="text-danger">*</span></label>
      <input class="form-control" id="page-inscription-ville" name="ville" required type="text"/>
      <small class="input-error form-text text-danger">La ville est requis</small>
    </div>
    <div class="form-group">
      <label for="page-inscription-email">Email<span class="text-danger">*</span></label>
      <input class="form-control" id="page-inscription-email" name="email" required type="email"/>
      <small class="input-error form-text text-danger">Un email valide est requis.</small>
    </div>
    <div class="row m-0 p-0">
      <div class="col-lg px-0 pr-lg-2">
        <div class="form-group">
          <label for="page-inscription-pwd">Mot de passe<span class="text-danger">*</span></label>
          <input class="form-control" id="page-inscription-pwd" name="mdp" required type="password"/>
          <small class="input-error form-text text-danger">Le mot de passe est requis.</small>
        </div>
      </div>
      <div class="col-lg px-0 pl-lg-2">
        <div class="form-group">
          <label for="page-inscription-pwd2">Confirmer mot de passe<span class="text-danger">*</span></label>
          <input class="form-control" id="page-inscription-pwd2" name="mdp2" required type="password"/>
          <small class="input-error form-text text-danger">La confirmation du mot de passe est requise.</small>
          <small class="form-text text-danger notSamePassword">Vous n'avez pas écrit deux fois le même mot de passe!</small>
        </div>
      </div>
    </div>
    <button class="btn btn-primary">S'inscrire</button>
  </form>
</div>`;

  /**
   *
   */
  constructor() {
    super('Inscription');

    this._$view = $(this._template);

    const $passwordsInputs = this._$view.find('#page-inscription-pwd, #page-inscription-pwd2');

    $passwordsInputs.data('validator', () => {
      return this._verifySamePassword($passwordsInputs.filter('#page-inscription-pwd'), $passwordsInputs.filter('#page-inscription-pwd2'));
    });

    onSubmitWithAjax(this._$view.find('form'), (data) => {
      router.navigate('');
      clearAlerts();
      createAlert('primary',
          "Votre demande d'inscription a été faite, vous ne pourrez pas vous connecter tant que votre inscription n'a pas été accepté.");
    }, (error) => {
      clearAlerts();
      createAlert('danger', error.responseJSON.error);
    });
  }

  _verifySamePassword($input1, $input2) {
    if ($input1.val() === $input2.val()) {
      $input2.next().next('.notSamePassword').hide(100);
      return true;
    }
    $input2.next('.input-error').hide();
    $input2.next().next('.notSamePassword').show(100);
    return false;
  }

}
