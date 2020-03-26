'use strict';

import {checkInputValidity} from './utils/forms.js';
import {ajaxGET, ajaxPOST} from './utils/ajax.js';
import {clearAlerts, createAlert} from './components/alerts.js';
import {changeMenuForUser, isClient, isOuvrier} from './userUtils.js';
import {getUsersListPage} from './components/users-list.js';
import {getLoginPage} from './components/login.js';
import {getRegisterPage} from './components/register.js';
import {getCreateCustommerPage} from './components/customer-add.js';
import {getHomePage} from './components/home.js';
import {getErrorPage} from './components/error.js';
import {getDeveloppementTypePage} from './components/developmentTypes-list.js';
import {getQuotesPage} from './components/quotes-list.js';
import {getAddDevisPage} from './components/quote-add.js';
import {getCustomersListPage} from './components/customers-list.js';

let router;

$(() => {

  ajaxGET('/api/login', null, (data) => {
    changeMenuForUser(data.user);
    initRouter();
  }, () => {
    changeMenuForUser(null);
    initRouter();
  });

  // Validation de n'importe quel element de type input, textarea, select se trouvant dans #content
  // @see checkInputValidity
  $('#content').on('focusout input', 'input, textarea, select', function () {
    checkInputValidity($(this));
  });

  $('#content').on('change', '.custom-file-input', function () {
    const $element = $(this);
    const id = $element.attr('id');
    const fileName = $(this).val().split(/(\\|\/)/g).pop();
    $('label[for="' + id + '"]').text(fileName);
  });

});

function initRouter() {
  /***********************************************************************************
   * Router used to navigate bewteen pages
   * @see https://github.com/krasimir/navigo
   ***********************************************************************************/

  router = new Navigo('/', false);
  router.hooks({
    before: function (done) {
      clearAlerts();
      done();
    },
  });
  router.on('deconnexion', () => {
    ajaxPOST('/api/logout', null, () => {
      changeMenuForUser(null);
      router.navigate('connexion');
      createAlert('success', 'Vous avez été deconnecté !');
    });
  })
  .on('connexion', () => {
    loadPage(getLoginPage());
  }, routeNoUserChecker())
  .on('inscription', () => {
    loadPage(getRegisterPage());
  }, routeNoUserChecker())
  .on('amenagements', () => {
    loadPage(getDeveloppementTypePage());
  })
  .on('amenagements/:id', (params) => {
    // TODO
    loadPage(getErrorPage(404, 'Page introuvable'));
  })
  .on('mes-devis', () => {
    // TODO
    loadPage(getErrorPage(404, 'Page introuvable'));
  }, routeClientChecker())
  .on('clients', (params, query) => {
    loadPage(getCustomersListPage(query));
  }, routeOuvrierChecker())
  .on('clients/ajouter', () => {
    loadPage(getCreateCustommerPage());
  }, routeOuvrierChecker())
  .on('clients/:id', (params) => {
    // TODO
    loadPage(getErrorPage(404, 'Page introuvable'));
  }, routeOuvrierChecker())
  .on('utilisateurs', (params, query) => {
    loadPage(getUsersListPage(query));
  }, routeOuvrierChecker())
  .on('utilisateurs/:id', (params) => {
    // TODO
    loadPage(getErrorPage(404, 'Page introuvable'));
  }, routeOuvrierChecker())
  .on('devis', (params, query) => {
    loadPage(getQuotesPage(query));
  }, routeOuvrierChecker())
  .on('devis/nouveau', () => {
    loadPage(getAddDevisPage());
  }, routeOuvrierChecker())
  .on('devis/:id', (params) => {
    // TODO
    loadPage(getErrorPage(404, 'Page introuvable'));
  }, routeOuvrierChecker())
  .on('*', () => {
    if (router.lastRouteResolved().url === origin) {
      loadPage(getHomePage());
    } else {
      loadPage(getErrorPage(404, 'Page introuvable'));
    }
  })
  .resolve();
}

function checkNoUser(done) {
  if (isClient() || isOuvrier()) {
    done(false);
    router.navigate('');
    return;
  }
  done();
}

function checkClient(done) {
  if (!isClient()) {
    done(false);
    router.navigate('');
    return;
  }
  done();
}

function checkOuvrier(done) {
  if (!isOuvrier()) {
    done(false);
    router.navigate('');
    return;
  }
  done();
}

function routeNoUserChecker() {
  return {
    before: checkNoUser
  }
}

function routeClientChecker() {
  return {
    before: checkClient
  }
}

function routeOuvrierChecker() {
  return {
    before: checkOuvrier
  }
}

/**
 * Load a page into the #content element
 *
 * @param {Page} page The page that will be loaded. A Page object as at least a getTitle and a getView method
 */
function loadPage(page) {
  const title = page.getTitle();
  console.log(`Navigation vers : ${title}`);
  $('title').text(`${title} | La Rose et Le Lilas`);
  $('#content').html(page.getView());
  router.updatePageLinks();
}

export {router};