'use strict';

import {checkInputValidity} from './utils/forms.js';
import {ajaxGET, ajaxPOST} from './utils/ajax.js';
import {clearAlerts, createAlert} from './components/alerts.js';
import {changeMenuForUser} from './components/menu.js';
import {getUsersListPage} from './components/users-list.js';
import {getLoginPage} from './components/login.js';
import {getRegisterPage} from './components/register.js';
import {getHomePage} from './components/home.js';
import {getErrorPage} from './components/error.js';
import {getDeveloppementTypePage} from './components/developmentTypes-list.js';
import {getQuotesPage} from './components/quotes-list.js';

let router;

$(() => {

  ajaxGET('/api/login', null, (data) => {
    changeMenuForUser(data.user);
  }, () => {
    changeMenuForUser(null);
  });

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
  router.on({
    'deconnexion': () => {
      ajaxPOST('/api/logout', null, () => {
        changeMenuForUser(null);
        router.navigate('connexion');
        createAlert('success', 'Vous avez été deconnecté !');
      });
    },
    'connexion': () => {
      loadPage(getLoginPage());
    },
    'inscription': () => {
      loadPage(getRegisterPage());
    },
    'mes-devis': () => {
      // TODO
      loadPage(getErrorPage(404, 'Page introuvable'));
    },
    'amenagements': () => {
      loadPage(getDeveloppementTypePage());
    },
    'amenagements/*': () => {
      // TODO
      loadPage(getErrorPage(404, 'Page introuvable'));
    },
    'clients': () => {
      // TODO
      loadPage(getErrorPage(404, 'Page introuvable'));
    },
    'utilisateurs': (params, query) => {
      loadPage(getUsersListPage(query));
    },
    'nouveau-devis': () => {
      // TODO
      loadPage(getErrorPage(404, 'Page introuvable'));
    },
    'devis': (params, query) => {
      loadPage(getQuotesPage(query));
    },
    '*': () => {
      if (router.lastRouteResolved().url === origin) {
        loadPage(getHomePage());
      } else {
        loadPage(getErrorPage(404, 'Page introuvable'));
      }
    }
  })
  .resolve();

  // Validation de n'importe quel element de type input, textarea, select se trouvant dans #content
  // @see checkInputValidity
  $('#content').on('focusout input', 'input, textarea, select', function () {
    checkInputValidity($(this));
  });

});

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

/**
 * @see https://stackoverflow.com/questions/6992585/jquery-deserialize-form
 */
jQuery.fn.deserialize = function (data) {
  var f = this,
      map = {},
      find = function (selector) {
        return f.is("form") ? f.find(selector) : f.filter(selector);
      };
  //Get map of values
  jQuery.each(data.split("&"), function () {
    var nv = this.split("="),
        n = decodeURIComponent(nv[0]),
        v = nv.length > 1 ? decodeURIComponent(nv[1]) : null;
    if (!(n in map)) {
      map[n] = [];
    }
    map[n].push(v);
  })
  //Set values for all form elements in the data
  jQuery.each(map, function (n, v) {
    find("[name='" + n + "']").val(v);
  })
  //Clear all form elements not in form data
  find("input:text,select,textarea").each(function () {
    if (!(jQuery(this).attr("name") in map)) {
      jQuery(this).val("");
    }
  })
  find("input:checkbox:checked,input:radio:checked").each(function () {
    if (!(jQuery(this).attr("name") in map)) {
      this.checked = false;
    }
  })
  return this;
};

export {router};