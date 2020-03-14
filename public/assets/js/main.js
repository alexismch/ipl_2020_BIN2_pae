'use strict';

import {verifySamePassword, checkInputValidity, onSubmit} from './forms.js';
import {clearAlerts, createAlert} from './alerts.js';
import {ajaxGET, ajaxPOST} from './ajax.js';

let router;

$(() => {

  ajaxGET('/api/connexion', null, (data) => {
    loadHeaderForUser(data.utilisateur);
  }, () => {
    loadHeaderForUser(null);
  });

  /***********************************************************************************
   * Router sert au changment de page
   * @see https://github.com/krasimir/navigo
   ***********************************************************************************/

  router = new Navigo('/', false);
  router.hooks({
    before: function (done, params) {
      clearAlerts();
      done(true);
    },
  });
  router.on({
    'deconnexion': deconnexion,
    'connexion': () => {
      loadPageTemplate('Page de connexion', 'page-connexion', pageConnexion);
    },
    'inscription': () => {
      loadPageTemplate('Page d\'inscription', 'page-inscription', pageInscription);
    },
    'mes-devis': () => {
      loadPageTemplate('Mes devis', 'page-mes-devis');
    },
    'amenagements/*': () => {
      loadPageTemplate('Page de l\'aménagement X', 'page-accueil');
    },
    'clients': () => {
      loadPageTemplate('Page des clients', 'page-clients');
    },
    'utilisateurs': () => {
      loadPageTemplate('Page des utilisateurs', 'page-utilisateurs');
    },
    'devis': () => {
      loadPageTemplate('Page des devis', 'page-devis');
    },
    'nouveau-devis': () => {
      loadPageTemplate('Introduire un nouveau devis', 'page-nouveau-devis');
    },
    '*': () => {
      if (router.lastRouteResolved().url === origin) {
        loadPageTemplate('Page d\'accueil', 'page-accueil', pageAccueil);
      } else {
        loadPageTemplate('Page not found', 'page-error-404');
      }
    }
  })
  .resolve();

  // Validation de n'importe quel element de type input, textarea, select se trouvant en #content
  // @see checkInputValidity
  $('#content').on('focusout input', 'input, textarea, select', function () {
    checkInputValidity($(this));
  });

});

function loadPageTemplate(title, templateId, onLoaded) {

  console.log('Navigation vers : ' + title);

  $('title').text(title + ' | La Rose et Le Lias');

  const contenu = $($(`#${templateId}`).html());

  contenu.add(contenu.find('*')).each((i, e) => {
    const $e = $(e);
    for (const attr of ['id', 'for']) {
      let attrValue = $e.attr(attr);
      if (attrValue !== undefined && attrValue !== null) {
        attrValue = attrValue.replace(/^template-/, '');
        $e.attr(attr, attrValue);
      }
    }
    for (const attr of ['href', 'data-target']) {
      let attrValue = $e.attr(attr);
      if (attrValue !== undefined && attrValue !== null && attrValue.match(
          /^#template-/)) {
        attrValue = attrValue.replace(/^#template-/, '');
        $e.attr(attr, `#${attrValue}`);
      }
    }
  });

  $('#content').html(contenu);
  router.updatePageLinks();

  if (onLoaded != undefined) {
    onLoaded();
  }
}

function loadHeaderForUser(user) {
  if (user != null && user.statut === "c") {
    $('.nav-user, .nav-ouvrier').addClass('d-none');
    $('.nav-client').removeClass('d-none');
  } else if (user != null && user.statut === "o") {
    $('.nav-user, .nav-client').addClass('d-none');
    $('.nav-ouvrier').removeClass('d-none');
  } else {
    $('.nav-client, .nav-ouvrier').addClass('d-none');
    $('.nav-user').removeClass('d-none');
  }
}

function pageAccueil() {
  console.log('des trucs page accueil');
}

function pageConnexion() {
  onSubmit($('#content').find('form'), (data) => {
    loadHeaderForUser(data.utilisateur);
    router.navigate('');
  }, (error) => {
    console.log(error);
    clearAlerts();
    createAlert('danger', error.responseJSON.error);
  });
}

function pageInscription() {
  onSubmit($('#content').find('form'), (data) => {
    // loadHeaderForUser(data.utilisateur);
    // router.navigate('');
  }, (error) => {
    console.log(error);
    clearAlerts();
    createAlert('danger', error.responseJSON.error);
  }, undefined, () => {
    // TODO verifier mots de passe
    var test = new Boolean(verifySamePassword($("#page-inscription-mdp"), $("#page-inscription-mdp2")));
    console.log("test = " + test);
    return test;
  });
}

function deconnexion() {
  ajaxPOST('/api/logout', null, () => {
    loadHeaderForUser(null);
    router.navigate('connexion');
    createAlert('success', 'Vous avez été deconnecté !');
  });
}
