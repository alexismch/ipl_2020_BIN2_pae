'use strict';

function getTemplate(code, message) {
  return `<div class="container">
    <h2>Erreur ${code}</h2>
    <p>${message}</p>
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