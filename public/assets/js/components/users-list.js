import {router} from '../main.js';
import {onSubmitWithNavigation} from '../utils/forms.js';
import {ajaxGET} from '../utils/ajax.js';

function getTemplate() {
  return `<div>
  <form action="utilisateurs" class="form-inline p-1 elevation-1 bg-light" method="get">
    <input class="form-control form-control-sm" name="name" placeholder="Nom de l'utilisateur" type="text">
    <input class="form-control form-control-sm ml-1" name="city" placeholder="Ville de l'utilisateur" type="text">
    <div class="input-group input-group-sm m-1">
      <button class="btn btn-primary btn-sm w-100">Rechercher</button>
    </div>
  </form>
  <p class="users-list-search-msg d-none m-0 p-2 alert alert-primary"></p>
  <ul class="users-list m-2 p-0"></ul>
</div>`;
}

function createUsersList($page, users) {
  const $usersList = $page.find('.users-list');
  $usersList.empty();
  for (const user of users) {
    createUsersListItem($usersList, user);
  }
}

function createUsersListItem($usersList, user) {

  let badgeColorUserStatus;
  switch (user.status.id) {
    case 'WORKER':
      badgeColorUserStatus = 'info';
      break;
    case 'CUSTOMER':
      badgeColorUserStatus = 'success';
      break;
    case 'NOT_ACCEPTED':
      badgeColorUserStatus = 'secondary';
      break;
  }

  const userListItem = `<li class="users-list-item border rounded mb-2">
      <p>${user.lastName} ${user.firstName}</p>
      <p>${user.email}</p>
      <p>${user.registrationDate}</p>
      <p><span class="badge badge-${badgeColorUserStatus} font-size-100">${user.status.name}</span></p>
      <a class="btn btn-primary w-min" href="utilisateurs/${user.id}">Détails</a>
    </li>`;
  $usersList.append(userListItem);
}

function createView(query) {
  const $page = $(getTemplate());

  onSubmitWithNavigation($page.find('form'), (url, data) => {
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
        $page.find('.users-list-search-msg').html(researchMsg).removeClass('d-none');
        $page.find('form').deserialize(query);
      } else {
        $page.find('.users-list-search-msg').addClass('d-none');
      }
    } else {
      $page.find('.users-list-search-msg').addClass('d-none');
    }
    createUsersList($page, data.users);
  });
  return $page;
}

export function getUsersListPage(query) {
  return {
    getTitle: () => 'Utilisateurs',
    getView: () => createView(query)
  }
}