'use strict';

import {checkInputValidity} from './utils/forms.js';
import {ajaxGET, ajaxPOST} from './utils/ajax.js';
import {clearAlerts, createAlert} from './utils/alerts.js';
import {changeMenuForUser, isClient, isOuvrier} from './utils/userUtils.js';
import {HomePage} from './components/home.js';
import {ErrorPage} from './components/error.js';
import {LoginPage} from './components/login.js';
import {RegisterPage} from './components/register.js';
import {DevelopmentTypePage} from './components/developmentTypes-list.js';
import {QuotesListPage} from './components/quotes-list.js';
import {QuoteDetailPage} from './components/quote-detail.js';
import {QuoteFormPage} from './components/quote-form.js';
import {UsersListPage} from './components/users-list.js';
import {UserDetailPage} from './components/user-detail.js';
import {CustomersListPage} from './components/customers-list.js';
import {CustomerDetailPage} from './components/customer-detail.js';
import {CustomerFormPage} from './components/customer-form.js';
import {LoadBarComponent} from './components/loadBar.js';

customElements.define('app-loadbar', LoadBarComponent);

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
    loadPage(new LoginPage());
  }, routeNoUserChecker())
  .on('inscription', () => {
    loadPage(new RegisterPage());
  }, routeNoUserChecker())
  .on('amenagements', () => {
    loadPage(new DevelopmentTypePage());
  })
  .on('amenagements/:id', (params) => {
    // TODO
    loadPage(new ErrorPage(404));
  })
  .on('mes-devis', () => {
    // TODO
    loadPage(new ErrorPage(404));
  }, routeClientChecker())
  .on('clients', (params, query) => {
    loadPage(new CustomersListPage(query));
  }, routeOuvrierChecker())
  .on('clients/ajouter', () => {
    loadPage(new CustomerFormPage());
  }, routeOuvrierChecker())
  .on('clients/:id', (params) => {
    loadPage(new CustomerDetailPage(params.id));
  }, routeOuvrierChecker())
  .on('utilisateurs', (params, query) => {
    loadPage(new UsersListPage(query));
  }, routeOuvrierChecker())
  .on('utilisateurs/:id', (params) => {
    loadPage(new UserDetailPage(params.id));
  }, routeOuvrierChecker())
  .on('devis', (params, query) => {
    loadPage(new QuotesListPage(query));
  }, routeOuvrierChecker())
  .on('devis/nouveau', () => {
    loadPage(new QuoteFormPage());
  }, routeOuvrierChecker())
  .on('devis/:id', (params) => {
    loadPage(new QuoteDetailPage(params.id));
  }, routeOuvrierChecker())
  .on('*', () => {
    if (router.lastRouteResolved().url === origin) {
      loadPage(new HomePage());
    } else {
      loadPage(new ErrorPage(404));
    }
  })
  .resolve();
}

function routeNoUserChecker() {
  return {
    before: (done) => {
      if (isClient() || isOuvrier()) {
        done(false);
        router.navigate('');
        return;
      }
      done();
    }
  }
}

function routeClientChecker() {
  return {
    before: (done) => {
      if (!isClient()) {
        done(false);
        router.navigate('');
        return;
      }
      done();
    }
  }
}

function routeOuvrierChecker() {
  return {
    before: (done) => {
      if (!isOuvrier()) {
        done(false);
        router.navigate('');
        return;
      }
      done();
    }
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