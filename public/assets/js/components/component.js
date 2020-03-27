'use strict';

/**
 * @module Components
 */

/**
 * Class representing a component.
 * A component object holds a part of HTML and JavaScript
 */
export class Component {

  _$view;
  _uniqueId;

  /**
   * Create a component.
   */
  constructor() {
    this._uniqueId = Component._generateUniqueId();
  }

  static _generateUniqueId() {
    Component.instanceCount = Component.instanceCount ? Component.instanceCount + 1 : 1;
    return Component.instanceCount;
  }

  /**
   * Get the content of the component that can be placed in the HTML
   *
   * @abstract
   * @returns {JQuery<HTMLElement> | jQuery | HTMLElement}
   */
  getView() {
    if (this._$view === undefined) {
      throw "Component#getView call before view initialisation";
    }
    return this._$view;
  }

  /**
   * Get the ID of this component instance. This id is unique accross all component instances
   *
   * @returns {number} A unique component ID.
   */
  getUniqueId() {
    return this._uniqueId;
  }
}