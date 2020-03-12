'use strict';

import {checkInputValidity, onSubmit} from './forms.js';
import {clearAlerts, createAlert} from './alerts.js';
import {ajaxGET, ajaxPOST} from './ajax.js';

let router;

$(() => {

  ajaxGET('/api/connexion', null, (data) => {
    chargerHeader(data.utilisateur);
  }, () => {
    chargerHeader(null);
  });

  /***********************************************************************************
   * Router
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
      loadPageTemplate('Page d\'inscription', 'page-inscription');
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
    checkInputValidity(this);
  });

});

function loadPageTemplate(nom, idTemplate, onLoaded) {

  console.log('Navigation vers : ' + nom);

  $('title').text(nom + ' | La Rose et Le Lias');

  const contenu = $($(`#${idTemplate}`).html());

  contenu.add(contenu.find('*')).each((i, e) => {
    for (const attr of ['id', 'for']) {
      let attrValue = e.getAttribute(attr);
      if (attrValue !== null) {
        attrValue = attrValue.replace(/^template-/, '');
        e.setAttribute(attr, attrValue);
      }
    }
    for (const attr of ['href', 'data-target']) {
      let attrValue = e.getAttribute(attr);
      if (attrValue !== null && attrValue.match(/^#template-/)) {
        attrValue = attrValue.replace(/^#template-/, '');
        e.setAttribute(attr, `#${attrValue}`);
      }
    }
  });

  $('#content').html(contenu);
  router.updatePageLinks();

  if (onLoaded != undefined) {
    onLoaded();
  }
}

function chargerHeader(user) {
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
    chargerHeader(data.utilisateur);
    router.navigate('');
  }, (error) => {
    console.log(error);
    clearAlerts();
    createAlert('danger', error.responseJSON.error);
  });
}

function deconnexion() {
  ajaxPOST('/api/deconnexion', null, () => {
    chargerHeader(null);
    router.navigate('connexion');
  });
}
