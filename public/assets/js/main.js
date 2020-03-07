'use strict';

import {checkInputValidity, onSubmit} from './forms.js'

let router;

$(() => {

  /***********************************************************************************
   * Router
   * @see https://github.com/krasimir/navigo
   ***********************************************************************************/

  router = new Navigo('/', false);
  router.on({
    'deconnexion': () => {
      console.log('deco');
    },
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

function pageAccueil() {
  console.log('des trucs page accueil');
}

function pageConnexion() {
  onSubmit($('#content').find('form'), () => {
    console.log('conecté');
    $('.nav-user').addClass('d-none');
    $('.nav-client').removeClass('d-none');
  });
}

