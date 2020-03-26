'use strict';

import {router} from '../main.js';
import {ajaxGET} from '../utils/ajax.js'

function getTemplate() {
  return `<ul class="amenagements-list m-2 p-0"></ul>`;
}

function createDevelopmentTypesList($page, developementTypesList) {
  $page.empty();
  for (const developmentType of developementTypesList) {
    const developmentListItem = `<li>
  <a class="card scale shadow border-left-primary" data-navigo href="amenagements/${developmentType.idType}">
    <span class="card-body">${developmentType.title}</span>
  </a>
</li>`;
    $page.append(developmentListItem);
  }
}

function createView() {
  const $page = $(getTemplate());

  ajaxGET('/api/developmentType-list', null, (data) => {
    createDevelopmentTypesList($page, data.developementTypesList);
    router.updatePageLinks();
  });

  return $page;
}

export function getDeveloppementTypePage() {
  return {
    getTitle: () => 'Aménagments',
    getView: createView
  }
}