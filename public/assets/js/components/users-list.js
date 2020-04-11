'use-strict';

import {router} from '../main.js';
import {onSubmitWithNavigation} from '../utils/forms.js';
import {ajaxGET} from '../utils/ajax.js';
import {getUserStatusColor} from '../utils/userUtils.js'
import {Page} from './page.js';

/**
 * @module Components
 */

/**
 * Component that hold the users list page
 *
 * @extends module:Components.Page
 */
export class UsersListPage extends Page {

  _template = `<div>
  <form action="utilisateurs" class="form-inline p-1 elevation-1 bg-light" method="get">
    <input class="form-control form-control-sm mx-1 mt-1" name="name" placeholder="Nom de l'utilisateur" type="text">
    <input class="form-control form-control-sm  mx-1 mt-1" name="city" placeholder="Ville de l'utilisateur" type="text">
    <div class="input-group input-group-sm  mx-1 mt-1">
      <button class="btn btn-primary btn-sm w-100">Rechercher</button>
    </div>
  </form>
  <p class="users-list-search-msg d-none m-0 p-2 alert alert-primary rounded-0"></p>
  <ul class="users-list m-2 p-0"></ul>
</div>`;

  /**
   *
   */
  constructor(query) {
    super('Utilisateurs');

    this._$view = $(this._template);

    onSubmitWithNavigation(this._$view.find('form'), (url, data) => {
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
            researchMsg += decodeURI(entry[1]) + '</span>';
            shouldHide = false;
          }
        }
        if (!shouldHide) {
          this._$view.find('.users-list-search-msg').html(researchMsg).removeClass('d-none');
          this._$view.find('form').deserialize(query);
        } else {
          this._$view.find('.users-list-search-msg').addClass('d-none');
        }
      } else {
        this._$view.find('.users-list-search-msg').addClass('d-none');
      }
      this._createUsersList(data.users);
      router.updatePageLinks();
      this.isLoading = false;
    }, () => {
      this.isLoading = false;
    });

  }

  _createUsersList(users) {
    const $usersList = this._$view.find('.users-list');
    $usersList.empty();
    for (const user of users) {
      this._createUsersListItem($usersList, user);
    }
  }

  _createUsersListItem($usersList, user) {

    const userListItem = `<li class="users-list-item shadow border border-left-primary rounded mb-2">
      <p>${user.lastName} ${user.firstName}</p>
      <p>${user.email}</p>
      <p>Inscrit le ${moment(user.registrationDate).format('L')}</p>
      <p><span class="badge badge-${getUserStatusColor(user)} font-size-100">${user.status.name}</span></p>
      <a class="btn btn-primary w-min" data-navigo href="utilisateurs/${user.id}">Détails</a>
    </li>`;
    $usersList.append(userListItem);
  }

}