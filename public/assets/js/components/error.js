'use strict';

function getTemplate(code, message) {
  return `<div class="container-fluid mt-3">
  <div class="text-center">
    <div class="error mx-auto" data-text="404">${code}</div>
    <p class="lead text-gray-800 mb-5">${message}</p>
    <a data-navigo href="">← Retour à la page d'accueil</a>
  </div>
</div>`;
}

function createView(code, message) {
  const $page = $(getTemplate(code, message));

  return $page;
}

export function getErrorPage(code, message) {
  return {
    getTitle: () => `Erreur ${code}`,
    getView: () => {
      return createView(code, message);
    }
  }
}