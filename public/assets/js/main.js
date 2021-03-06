'use strict';

import {checkInputValidity} from './utils/forms.js';
import {ajaxGET, ajaxPOST} from './utils/ajax.js';
import {clearAlerts, createAlert} from './utils/alerts.js';
import {changeMenuForUser, isCustomer, isWorker} from './utils/userUtils.js';
import {HomePage} from './components/home.js';
import {ErrorPage} from './components/error.js';
import {LoginPage} from './components/login.js';
import {RegisterPage} from './components/register.js';
import {DevelopmentTypesListPage} from './components/developmentTypes-list.js';
import {DevelopmentTypeFormPage} from './components/developmentType-form.js';
import {DevelopmentTypePage} from './components/developmentType.js';
import {QuotesListPage} from './components/quotes-list.js';
import {QuoteDetailPage} from './components/quote-detail.js';
import {QuoteFormPage} from './components/quote-form.js';
import {UsersListPage} from './components/users-list.js';
import {UserDetailPage} from './components/user-detail.js';
import {CustomersListPage} from './components/customers-list.js';
import {CustomerFormPage} from './components/customer-form.js';
import {LoadBarComponent} from './components/loadBar.js';

customElements.define('app-loadbar', LoadBarComponent);

let router;

$(() => {

  ajaxGET('/api/login', null, (data) => {
    changeMenuForUser(data.user);
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
      $('#load-bar').show();
      clearAlerts();
      done();
    },
  });
  router.on('deconnexion', () => {
    ajaxPOST('/api/logout', null, () => {
      changeMenuForUser(null);
      router.navigate('connexion');
      createAlert('success', 'Vous avez été déconnecté');
    });
  })
  .on('connexion', () => {
    loadPage(new LoginPage());
  }, routeNoUserChecker())
  .on('inscription', () => {
    loadPage(new RegisterPage());
  }, routeNoUserChecker())
  .on('amenagements', () => {
    loadPage(new DevelopmentTypesListPage());
  })
  .on('amenagements/ajouter', () => {
    loadPage(new DevelopmentTypeFormPage());
  })
  .on('amenagements/:id', (params) => {
    loadPage(new DevelopmentTypePage(params.id));
  })
  .on('mes-devis', (params, query) => {
    loadPage(new QuotesListPage(query));
  }, routeCustomerChecker())
  .on('clients', (params, query) => {
    loadPage(new CustomersListPage(query));
  }, routeWorkerChecker())
  .on('clients/ajouter', () => {
    loadPage(new CustomerFormPage());
  }, routeWorkerChecker())
  .on('clients/:id', (params, query) => {
    loadPage(new QuotesListPage(query, params.id));
  }, routeWorkerChecker())
  .on('utilisateurs', (params, query) => {
    loadPage(new UsersListPage(query));
  }, routeWorkerChecker())
  .on('utilisateurs/:id', (params) => {
    loadPage(new UserDetailPage(params.id));
  }, routeWorkerChecker())
  .on('devis', (params, query) => {
    loadPage(new QuotesListPage(query));
  }, routeWorkerChecker())
  .on('devis/nouveau', () => {
    loadPage(new QuoteFormPage());
  }, routeWorkerChecker())
  .on('devis/:id', (params) => {
    loadPage(new QuoteDetailPage(params.id));
  }, routeCustomerOrWorkerChecker())
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
      if (isCustomer() || isWorker()) {
        done(false);
        router.navigate('');
        return;
      }
      done();
    }
  }
}

function routeCustomerOrWorkerChecker() {
  return {
    before: (done) => {
      if (!isCustomer() && !isWorker()) {
        done(false);
        router.navigate('');
        return;
      }
      done();
    }
  }
}

function routeCustomerChecker() {
  return {
    before: (done) => {
      if (!isCustomer()) {
        done(false);
        router.navigate('');
        return;
      }
      done();
    }
  }
}

function routeWorkerChecker() {
  return {
    before: (done) => {
      if (!isWorker()) {
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