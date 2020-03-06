'use strict';

import {ajaxPOST} from './ajax.js'
import {checkFormValidity, checkInputValidity} from './forms.js'

$(() => {

  /***********************************************************************************
   * Router
   * @see https://github.com/krasimir/navigo
   ***********************************************************************************/

  let router = new Navigo('/', false);
  router.on({
    'deconnexion': () => {
      console.log('deco');
    },
    'connexion': () => {
      chargerTemplate('Page de connexion', 'page-connexion');
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

});

function chargerTemplate(nom, idTemplate, onLoaded) {

  console.log('Navigation vers : ' + nom);

  $('title').text(nom + ' | La Rose et Le Lias');

  const contenu = $($(`#${idTemplate}`).html());

  contenu.add(contenu.find('*')).each((i, e) => {
    for (const attr of ['id', 'for']) {
      if (e.getAttribute(attr) !== null) {
        e.setAttribute(attr, e.getAttribute(attr).replace(/^template-/, ''));
      }
    }
    for (const attr of ['href', 'data-target']) {
      if (e.getAttribute(attr) !== null) {
        e.setAttribute(attr,
            '#' + e.getAttribute(attr).replace(/^#template-/, ''));
      }
    }
  });

  $('#content').html(contenu);

  if (onLoaded != undefined) {
    onLoaded();
  }
}

function pageAccueil() {
  console.log('des trucs page accueil');
}

$('#content').on('focusout input', 'input', function () {
  checkInputValidity(this);
});

$('#content').on('submit', 'form', function (e) {
  e.preventDefault();

  if (!checkFormValidity(this)) {
    return;
  }

  const form = $(this);
  const data = form.serialize();

  ajaxPOST('/api/connexion', data, () => {
    console.log('connecté');
  });

});
