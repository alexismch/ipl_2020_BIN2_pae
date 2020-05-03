'use strict';

import {Component} from './component.js';

/**
 * @module Components
 */

/**
 * Class representing a page.
 *
 * @extends module:Components.Component
 */
export class Page extends Component {

  _title;
  _isLoading = false;

  /**
   * Create a page.
   *
   * @param {string} title - The page title.
   */
  constructor(title) {
    super();
    this._title = title;
  }

  /**
   * Get the title of the page.
   *
   * @returns {string} The title of the page.
   */
  getTitle() {
    return this._title;
  }

  setTitle(title) {
    this._title = title;
    $('title').text(`${title} | La Rose et Le Lilas`);
  }

  get isLoading() {
    return this._isLoading;
  }

  set isLoading(isLoading) {
    this._isLoading = isLoading;
    if (this._isLoading) {
      $('#load-bar').show();
    } else {
      $('#load-bar').hide();
    }
  }

}