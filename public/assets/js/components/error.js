'use strict';

import {Page} from './page.js';

/**
 * @module Components
 */

/**
 * Component that hold the error page
 *
 * @extends module:Components.Page
 */
export class ErrorPage extends Page {

  code;
  message;

  /**
   * Create an error page for the specified error
   *
   * @param {number} code - The error code
   */
  constructor(code) {
    super('Erreur ' + code);

    this.code = code;

    switch (this.code) {
      case 404:
        this.message = 'Page introuvable';
      default:
        this.message = 'Une erreur est survenue';
    }

    this._$view = $(this._template);

  }

  get _template() {
    return `<div class="container-fluid mt-3">
  <div class="text-center">
    <div class="error mx-auto" data-text="${this.code}">${this.code}</div>
    <p class="lead text-gray-800 mb-5">${this.message}</p>
    <a data-navigo href="">← Retour à la page d'accueil</a>
  </div>
</div>`;
  }

}