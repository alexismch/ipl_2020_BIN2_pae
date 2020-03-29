/**
 * @module CustomWebComponents
 */

/**
 * A loading bar Web Component
 */
export class LoadBarComponent extends HTMLElement {

  /**
   *
   */
  constructor() {
    super();

    this.style.display = 'block';

    const container = document.createElement('div');

    container.setAttribute('role', 'progressbar');
    container.classList.add('load-bar');

    const spinner = document.createElement('div');

    container.appendChild(spinner);
    this.appendChild(container);

  }

}