'use strict';

import {router} from '../main.js';
import {clearAlerts, createAlert} from './alerts.js';
import {onSubmitWithAjax} from '../utils/forms.js';
import {changeMenuForUser} from '../userUtils.js';

function getTemplate() {
  return `<div class="container">
    <h2>Connexion</h2>
    <form action="/api/login" class="w-100" method="post" novalidate>
      <div class="form-group">
        <label for="page-connexion-pseudo">Pseudo<span class="text-danger">*</span></label>
        <input class="form-control" id="page-connexion-pseudo" name="pseudo" required type="text"/>
        <small class="input-error form-text text-danger">Le pseudo est requis.</small>
      </div>
      <div class="form-group">
        <label for="page-connexion-pwd">Mot de passe<span class="text-danger">*</span></label>
        <input class="form-control" id="page-connexion-pwd" name="password" required type="password"/>
        <small class="input-error form-text text-danger">Le mot de passe est requis.</small>
      </div>
      <button class="btn btn-primary">Se Connecter</button>
    </form>
    <p class="mt-4">Pas encore de compte ? <a data-navigo href="inscription">Créer un compte</a></p>
  </div>`;
}

function createView() {
  const $page = $(getTemplate());

  onSubmitWithAjax($page.find('form'), (data) => {
    changeMenuForUser(data.user);
    router.navigate('');
  }, (error) => {
    clearAlerts();
    createAlert('danger', error.responseJSON.error);
  });

  return $page;
}

export function getLoginPage() {
  return {
    getTitle: () => 'Connexion',
    getView: createView
  }
}