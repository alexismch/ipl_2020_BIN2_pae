'use strict';

import {checkInputValidity, onSubmit} from './forms.js';
import {clearAlerts, createAlert} from './alerts.js';
import {ajaxGET, ajaxPOST} from './ajax.js';

let router;

$(() => {

  ajaxGET('/api/connexion', null, (user) => {
    console.log({user});
    chargerHeader(user);
  });

  /***********************************************************************************
   * Router
   * @see https://github.com/krasimir/navigo
   ***********************************************************************************/

  router = new Navigo('/', false);
  router.on({
    'deconnexion': deconnexion,
    'connexion': () => {
      chargerTemplate('Page de connexion', 'page-connexion', pageConnexion);
    },
    'inscription': () => {
      chargerTemplate('Page d\'inscription', 'page-inscription');
    },
    'mes-devis': () => {
      chargerTemplate('Mes devis', 'page-mes-devis');
    },
    'amenagements/*': () => {
      chargerTemplate('Page de l\'aménagement X', 'page-accueil');
    },
    'clients': () => {
      chargerTemplate('Page des clients', 'page-clients');
    },
    'utilisateurs': () => {
      chargerTemplate('Page des utilisateurs', 'page-utilisateurs');
    },
    'nouveau-devis': () => {
      chargerTemplate('Introduire un nouveau devis', 'page-nouveau-devis');
    },
    '*': () => {
      if (router.lastRouteResolved().url === origin) {
        chargerTemplate('Page d\'accueil', 'page-accueil', pageAccueil);
      } else {
        chargerTemplate('Page not found', 'page-error-404');
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

function chargerTemplate(nom, idTemplate, onLoaded) {

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
  if (user != null && user.statut === "client") {
    $('.nav-user, .nav-ouvrier').addClass('d-none');
    $('.nav-client').removeClass('d-none');
  } else if (user != null && user.statut === "ouvrier") {
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
  onSubmit($('#content').find('form'), (user) => {
    console.log({user});
    chargerHeader(user);
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
