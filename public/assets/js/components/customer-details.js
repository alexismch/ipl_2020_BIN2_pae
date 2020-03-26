import {router} from '../main.js';
import {onSubmitWithNavigation} from '../utils/forms.js';
import {ajaxGET} from '../utils/ajax.js';

function getTemplate() {
  return `<div>
    <form action="clients" class="form-inline p-1 elevation-1 bg-light" method="get">
      <input class="form-control form-control-sm" name="name" placeholder="Nom du client" type="text">
      <input class="form-control form-control-sm ml-1" name="postalCode" placeholder="Code postal" type="number">
      <input class="form-control form-control-sm ml-1" name="city" placeholder="Ville du client" type="text">
      <div class="input-group input-group-sm m-1">
        <button class="btn btn-primary btn-sm w-100">Rechercher</button>
      </div>
    </form>
    <p class="customers-list-search-msg d-none m-0 p-2 alert alert-primary"></p>
    <ul class="customers-list m-2 p-0"></ul>
  </div>`;
}

function createCustomersList($page, customers) {
  const $customersList = $page.find('.customers-list');
  $customersList.empty();
  for (const customer of customers) {
    createCustomersListItem($customersList, customer);
  }
}

function createCustomersListItem($customersList, customer) {

  const customerListItem = `<li class="customers-list-item border rounded mb-2">
        <p>${customer.lastName} ${customer.firstName}</p>
        <p>${customer.email}</p>
        <p>${customer.postalCode}</p>
        <p>${customer.city}</p>
        <p>${customer.phoneNumber}</p>
        <a class="btn btn-primary w-min" href="clients/${customer.idcustomer}">Détails</a>
      </li>`;
  $customersList.append(customerListItem);
}

function createView(query) {
  const $page = $(getTemplate());

  onSubmitWithNavigation($page.find('form'), (url, data) => {
    if (data !== query) {
      router.navigate(url + '?' + data);
    }
  });

  ajaxGET('/api/customers-list', query, (data) => {
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
            case'postalCode':
              researchMsg += 'Code Postale: '
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
        $page.find('.customers-list-search-msg').html(researchMsg).removeClass('d-none');
        $page.find('form').deserialize(query);
      } else {
        $page.find('.customers-list-search-msg').addClass('d-none');
      }
    } else {
      $page.find('.customers-list-search-msg').addClass('d-none');
    }
    createCustomersList($page, data.customers);
  });
  return $page;
}

export function getCustomerDetail(query) {
    return {
      getTitle: () => 'Clients',
      getView: () => createView(query)
    }
  }