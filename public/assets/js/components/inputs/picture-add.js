'use strict';

import {Component} from '../component.js';

/**
 * @module Components
 */

/**
 * Component that hold a form to add a picture
 *
 * @extends module:Components.Component
 */
export class AddPictureComponent extends Component {

  _template = `<div class="card p-2 mb-1">
  <div class="row">
    <div class="col-md-7">
      <div class="form-group">
        <label for="add-picutre-component-picture-title-${this.getUniqueId()}">Titre de la photo<span class="text-danger">*</span></label>
        <input class="form-control" id="add-picutre-component-picture-title-${this.getUniqueId()}" name="pictureTitle" type="text" required />
        <small class="input-error form-text text-danger">Le titre de la photo est requis.</small>
      </div>
      <div class="form-group">
        <div class="input-group">
          <div class="custom-file">
            <input type="file" class="custom-file-input" id="add-picutre-component-picture-upload-${this.getUniqueId()}" accept="image/*" required />
            <input type="hidden" id="add-picutre-component-picture-upload-data-${this.getUniqueId()}" name="pictureData" />
            <label class="custom-file-label" for="add-picutre-component-picture-upload-${this.getUniqueId()}">Chosissez le fichier de la photo</label>
          </div>
        </div>
        <small class="input-error form-text text-danger">Une photo est requise.</small>
      </div>
      <div class="form-group">
        <select id="add-picutre-component-types-${this.getUniqueId()}" name="pictureDevelopmentType" class="form-control" data-placeholder="Choisissez le type d'aménagement correspondant" required>
          <option value=""></option>
        </select>
        <small class="input-error form-text text-danger">Choisissez un type d'aménagement.</small>
      </div>
      <div id="add-picutre-component-picutre-visibility-${this.getUniqueId()}" class="form-group">
        <div class="custom-control custom-checkbox">
          <input type="checkbox" class="custom-control-input" id="add-picutre-component-visible-${this.getUniqueId()}" name="pictureIsVisible">
          <label class="custom-control-label" for="add-picutre-component-visible-${this.getUniqueId()}">Rendre visible</label>
        </div>
      </div>
    </div>
    <div class="col-md-5">
      <img id="add-picutre-component-picture-${this.getUniqueId()}" class="w-100 rounded" alt="Image à mettre en ligne" src="/assets/img/img-placeholder.jpg" />
    </div>  
  </div>
</div>`;

  _developmentTypeListOptions;
  _$selectType;

  /**
   *
   */
  constructor(isBefore = true) {
    super();
    this._developmentTypeListOptions = new Map();

    this._$view = $(this._template);

    if (isBefore) {
      this._$view.find('#add-picutre-component-picutre-visibility-' + this.getUniqueId()).remove();
    }

    const $fileInput = this._$view.find('#add-picutre-component-picture-upload-' + this.getUniqueId());
    const $fileOutputImg = this._$view.find('#add-picutre-component-picture-' + this.getUniqueId());
    const $fileOutputData = this._$view.find('#add-picutre-component-picture-upload-data-' + this.getUniqueId());

    $fileInput.on('change', (e) => {
      console.log(e.target.files);
      if (e.target.files.length < 1) {
        $fileOutputImg.attr('src', '/assets/img/img-placeholder.jpg');
        $fileOutputData.val('');
      } else {
        const file = e.target.files[0];
        const reader = new FileReader();

        reader.onloadend = () => {
          $fileOutputImg.attr('src', reader.result);
          $fileOutputData.val(reader.result);
        }

        reader.readAsDataURL(file);
      }
    });

    $fileInput.data('validator', () => {
      const errorElement = $fileInput.parent().parent().next('.input-error');
      if ($fileInput.val()[0] === undefined) {
        errorElement.show(100);
        return false;
      } else {
        errorElement.hide(100);
        return true;
      }
    });

    this._$selectType = this._$view.find('#add-picutre-component-types-' + this.getUniqueId());

    this._$selectType.chosen({
      width: '100%',
      no_results_text: 'Cet aménagement n\'existe pas !',
      disable_search: true,
      allow_single_deselect: true
    });

    this._$selectType.data('validator', () => {
      const errorElement = this._$selectType.next().next('.input-error');
      if (this._$selectType.val()[0] === undefined) {
        errorElement.show(100);
        return false;
      } else {
        errorElement.hide(100);
        return true;
      }
    });
  }

  /**
   * Set the list of DevelopmentType that can be selected
   *
   * @param {DevelopmentType[]} developmentTypesList - List of DevelopmentType
   */
  setDevelopmentTypesList(developmentTypesList) {

    for (const developmentType of developmentTypesList) {
      if (this._developmentTypeListOptions.get(developmentType.idType) === undefined) {
        const option = $(`<option value="${developmentType.idType}">${developmentType.title}</option>`);
        option.appendTo(this._$selectType);
        this._developmentTypeListOptions.set(developmentType.idType, option);
      }
    }

    this._developmentTypeListOptions.forEach((value, key, map) => {

      if (!developmentTypesList.find(developmentType => developmentType.idType == key)) {
        value.remove();
      }

    });

    this._$selectType.trigger('chosen:updated');
  }

}
