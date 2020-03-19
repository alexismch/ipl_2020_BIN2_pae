'use strict';

import {checkInputValidity, onSubmitWithAjax, onSubmitWithNavigation, verifySamePassword} from './forms.js';
import {clearAlerts, createAlert} from './alerts.js';
import {ajaxGET, ajaxPOST} from './ajax.js';
import {createUsersList} from './users-list.js';
import {createDevelopmentTypesList} from './developmentTypes-list.js';

let router;

$(() => {

  ajaxGET('/api/login', null, (data) => {
    loadHeaderForUser(data.user);
  }, () => {
    loadHeaderForUser(null);
  });

  /***********************************************************************************
   * Router sert au changment de page
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
        loadHeaderForUser(null);
        router.navigate('connexion');
        createAlert('success', 'Vous avez été deconnecté !');
      });
    },
    'connexion': () => {
      loadPageTemplate('Page de connexion', 'page-connexion', loadConnectionPage);
    },
    'inscription': () => {
      loadPageTemplate('Page d\'inscription', 'page-inscription', loadRegistrationPage);
    },
    'mes-devis': () => {
      loadPageTemplate('Mes devis', 'page-mes-devis');
    },
    'amenagements/*': () => {
      loadPageTemplate('Page de l\'aménagement X', 'page-accueil');
    },
    'amenagements': (params,query) => {
      loadPageTemplate('Page d\'aménagement', 'page-amenagements');
      loadDevelopmentTypes(query);
    },
    'clients': () => {
      loadPageTemplate('Page des clients', 'page-clients');
    },
    'utilisateurs': (params, query) => {
      loadPageTemplate('Page des utilisateurs', 'page-utilisateurs', () => {
        loadUsersPage(query)
      });
    },
    'devis': () => {
     loadPageTemplate('Page des devis', 'page-devis', () => {
       //listQuote()
      });
    },
    'nouveau-devis': () => {
      loadPageTemplate('Introduire un nouveau devis', 'page-nouveau-devis');
    },
    '*': () => {
      if (router.lastRouteResolved().url === origin) {
        loadPageTemplate('Page d\'accueil', 'page-accueil', loadHomePage);
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

/**
 * Load the template into the #content element.
 * To prevent id duplication you should prefix all ids in the template with 'template-'. This prefix is deleted in the #content section
 *
 * @param {string} title the name of the page
 * @param {string} templateId the template's id that will be loaded
 * @param {function} onLoaded Function called when the template as loaded
 */
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
  if (user != null && user.status === "c") {
    $('.nav-user, .nav-ouvrier').addClass('d-none');
    $('.nav-client').removeClass('d-none');
  } else if (user != null && user.status === "o") {
    $('.nav-user, .nav-client').addClass('d-none');
    $('.nav-ouvrier').removeClass('d-none');
  } else {
    $('.nav-client, .nav-ouvrier').addClass('d-none');
    $('.nav-user').removeClass('d-none');
  }
}

function loadHomePage() {
  console.log('des trucs page accueil');
}

function loadConnectionPage() {
  onSubmitWithAjax($('#content form'), (data) => {
    loadHeaderForUser(data.user);
    router.navigate('');
  }, (error) => {
    clearAlerts();
    createAlert('danger', error.responseJSON.error);
  });
}

function loadRegistrationPage() {
  onSubmitWithAjax($('#content form'), (data) => {
    loadHeaderForUser(data.user);
    router.navigate('');
    clearAlerts();
    createAlert('primary', "Votre demande d'inscription a été faite, vous ne pourrez pas vous connecter tant que votre inscription n'a pas été accepté.");
  }, (error) => {
    clearAlerts();
    createAlert('danger', error.responseJSON.error);
  }, undefined, () => {
    return verifySamePassword($('#page-inscription-mdp'), $('#page-inscription-mdp2'));
  });
}

function loadUsersPage(query) {
  onSubmitWithNavigation($('#content form'), (url, data) => {
    if (data !== query) {
      router.navigate(url + '?' + data);
    }
  });
  ajaxGET('/api/users-list', query, (data) => {
    if (query !== undefined && query !== null && query !== '') {
      let shouldHide = true;
      let researchMsg = 'Résultats de la recherche: ';
      const fields = query.split('&');
      for (const field of fields) {
        const entry = field.split('=');
        if (entry[1] !== '' && entry[1] !== undefined) {
          researchMsg += '<span class="badge badge-primary mr-1 font-size-100">';
          switch (entry[0]) {
            case 'name':
              researchMsg += 'Nom: ';
              break;
            case 'city':
              researchMsg += 'Ville: ';
              break;
          }
          researchMsg += entry[1] + '</span>';
          shouldHide = false;
        }
      }
      if (!shouldHide) {
        $('.user-list-search-msg').html(researchMsg).removeClass('d-none');
        $('#content form').deserialize(query);
      } else {
        $('.user-list-search-msg').addClass('d-none');
      }
    } else {
      $('.user-list-search-msg').addClass('d-none');
    }
    createUsersList(data.users);
  });
}

function loadDevelopmentTypes(query){
  ajaxGET('/api/developmentType-list',query,(data) => {
    createDevelopmentTypesList(data.developementTypesList);
  });

}


function listQuote(){
	$.ajax({
        url:'/quotes-list',
        type:'get',
        success:function(response){
          alert(response);
            var json = JSON.parse(response);
			fillTable(json);
        },
        error:function(jqXHR, textStatus, errorThrown){
            console.log('jqXHR : ' + jqXHR + '\ntextStatus : ' + textStatus + '\nerrorThrown');
        }
    });
}


function fillTable(table){
	for(var i = 0 ; i < table.length ; i++){
		$('#tableListQuotes tr:last').after('<tr> <td>'+ table[i].idQuote + '</td>'+ '<td>' +
		 table[i].idCustomer + '</td>'+ '<td>' + table[i].quoteDate + '</td>'+ '<td>' + table[i].totalAmount + '</td>'+'</tr>');
	}
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