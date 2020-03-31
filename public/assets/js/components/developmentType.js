'use strict';

import {Page} from './page.js';
import {ajaxGET} from '../utils/ajax.js';

/**
 * @module Components
 */

/**
 * Component that hold a developmentType page
 *
 * @extends module:Components.Page
 */
export class DevelopmentTypePage extends Page {

  _template = `<div class="carousel slide d-block" data-ride="carousel" id="template-carousel">
  <app-loadbar></app-loadbar>
  <div class="carousel-inner"></div>
  <a class="carousel-control-prev" data-slide="prev" href="#template-carousel" role="button">
    <i aria-hidden="true" class="fas fa-angle-left"></i>
    <span class="sr-only">Previous</span>
  </a>
  <a class="carousel-control-next" data-slide="next" href="#template-carousel" role="button">
    <i aria-hidden="true" class="fas fa-angle-right"></i>
    <span class="sr-only">Next</span>
  </a>
</div>`;

  /**
   *
   */
  constructor(id) {
    super(id === undefined ? 'Accueil' : 'Aménagement n°' + id);

    this._$view = $(this._template);
    ajaxGET('/api/photos-list', id === undefined ? null : `typeId=${id}`, (data) => {
      this._$view.find('app-loadbar').remove();
      if (id !== undefined && data.photosList.length > 0) {
        this.setTitle(data.photosList[0].developmentType);
      }
      if (data.photosList.length === 0) {
        this._setNoPicture();
      } else {
        this._createPicturesList(data.photosList);
      }
    }, () => {
      this._$view.find('app-loadbar').remove();
    });
     


  }

  _createPicturesList(pictures) {

    let isFirst = true;
    for (const picture of pictures) {
      this._addPicture(picture, isFirst);
      isFirst = false;
    }

  }

  _setNoPicture() {

    const noPictureContainer = `<div class="carousel-item active">
  <img alt="Aucune photo disponible" src="/assets/img/img-placeholder.jpg">
  <div class="carousel-caption d-block">
    <h5>Aucune photo disponible</h5>
  </div>
</div>`;

    this._$view.find('.carousel-inner').append(noPictureContainer);

  }

  _addPicture(picture, first) {

    const pictureContainer = `<div class="carousel-item${first ? ' active' : ''}">
      <img alt="${picture.title}" src="${picture.base64}"/>
      <div class="carousel-caption d-block">
        <h5>${picture.developmentType}</h5>
      </div>
    </div>`;

    this._$view.find('.carousel-inner').append(pictureContainer);

  }

}
