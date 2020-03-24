'use strict';

import {checkInputValidity} from './utils/forms.js';
import {ajaxGET, ajaxPOST} from './utils/ajax.js';
import {clearAlerts, createAlert} from './components/alerts.js';
import {changeMenuForUser, isClient, isOuvrier} from './userUtils.js';
import {getUsersListPage} from './components/users-list.js';
import {getLoginPage} from './components/login.js';
import {getRegisterPage} from './components/register.js';
import {getCreateCustommerPage} from './components/createCustommer.js';
import {getHomePage} from './components/home.js';
import {getErrorPage} from './components/error.js';
import {getDeveloppementTypePage} from './components/developmentTypes-list.js';
import {getQuotesPage} from './components/quotes-list.js';
import {getAddDevisPage} from './components/add-devis.js';
import { getCustomersListPage } from './components/customers-list.js';

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
      },
      {
        before: checkNoUser
      })
  .on('inscription', () => {
        loadPage(getRegisterPage());
      },
      {
        before: checkNoUser
      })
  .on('mes-devis', () => {
        // TODO
        loadPage(get);
      },
      {
        before: checkClient
      })
  .on('amenagements', () => {
    loadPage(getDeveloppementTypePage());
  })
  .on('amenagements/*', () => {
    // TODO
    loadPage(getErrorPage(404, 'Page introuvable'));
  })
  .on('clients', (params,query) => {
        loadPage(getCustomersListPage(query));
      },
      {
        before: checkOuvrier
      })
  .on('utilisateurs', (params, query) => {
        loadPage(getUsersListPage(query));
      },
      {
        before: checkOuvrier
      })
  .on('nouveau-devis', () => {
        loadPage(getAddDevisPage());
      },
      {
        before: checkOuvrier
      })
  .on('creerClient', () => {
        loadPage(getCreateCustommerPage());
      },
      {
        before: checkOuvrier
      })
  .on('devis', (params, query) => {
        loadPage(getQuotesPage(query));
      },
      {
        before: checkOuvrier
      })
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