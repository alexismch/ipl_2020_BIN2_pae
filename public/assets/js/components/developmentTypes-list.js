'use strict';

import {ajaxGET} from '../utils/ajax.js'

function getTemplate() {
  return `<ul class="amenagements-list m-2 p-0"></ul>`;
}

function createDevelopmentTypesList($page, developementTypesList) {
  $page.empty();
  for (const developmentType of developementTypesList) {
    const developmentListItem = `<li>
  <a href="amenagements/${developmentType.idType}" class="card border-secondary">
    <!--<span class="card-header">${developmentType.title}</span>
    <span class="card-body text-secondary"></span>-->
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
  });

  return $page;
}

export function getDeveloppementTypePage() {
  return {
    getTitle: () => 'Aménagments',
    getView: createView
  }
}