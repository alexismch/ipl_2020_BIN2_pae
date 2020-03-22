'use strict';

import {router} from '../main.js';
import {clearAlerts, createAlert} from './alerts.js';
import {onSubmitWithAjax, verifySamePassword} from '../utils/forms.js';

function getTemplate() {
  return `<div class="container">
    <h2>Inscription</h2>
    <form action="/api/register" class="w-100 mb-3" method="post" novalidate>
      <div class="form-group">
        <label for="page-inscription-pseudo">Pseudo<span class="text-danger">*</span></label>
        <input class="form-control" id="page-inscription-pseudo" name="pseudo" required type="text"/>
        <small class="input-error form-text text-danger">Le pseudo est requis.</small>
      </div>
      <div class="form-group">
        <label for="page-inscription-mdp">Mot de passe<span class="text-danger">*</span></label>
        <input class="form-control" id="page-inscription-mdp" name="mdp" required type="password"/>
        <small class="input-error form-text text-danger">Le mot de passe est requis.</small>
      </div>
      <div class="form-group">
        <label for="page-inscription-mdp2">Confirmer mot de passe<span class="text-danger">*</span></label>
        <input class="form-control" id="page-inscription-mdp2" name="mdp2" required type="password"/>
        <small class="input-error form-text text-danger">La confirmation du mot de passe est requise.</small>
        <small class="form-text text-danger notSamePassword">Vous n'avez pas écrit deux fois le même mot de passe!</small>
      </div>
      <div class="form-group">
        <label for="page-inscription-nom">Nom<span class="text-danger">*</span></label>
        <input class="form-control" id="page-inscription-nom" name="nom" required type="text"/>
        <small class="input-error form-text text-danger">Le nom est requis</small>
      </div>
      <div class="form-group">
        <label for="page-inscription-prenom">Prenom<span class="text-danger">*</span></label>
        <input class="form-control" id="page-inscription-prenom" name="prenom" required type="text"/>
        <small class="input-error form-text text-danger">Le prenom est requis</small>
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
      <button class="btn btn-primary">S'inscrire</button>
    </form>
  </div>`;
}

function createView() {
  const $page = $(getTemplate());

  onSubmitWithAjax($page.find('form'), (data) => {
    router.navigate('');
    clearAlerts();
    createAlert('primary',
        "Votre demande d'inscription a été faite, vous ne pourrez pas vous connecter tant que votre inscription n'a pas été accepté.");
  }, (error) => {
    clearAlerts();
    createAlert('danger', error.responseJSON.error);
  }, undefined, () => {
    return verifySamePassword($('#page-inscription-mdp'), $('#page-inscription-mdp2'));
  });

  return $page;
}

export function getRegisterPage() {
  return {
    getTitle: () => 'Inscription',
    getView: createView
  }
}