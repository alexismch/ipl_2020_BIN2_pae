'use strict';

import {Component} from './component.js';

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
        <select id="add-picutre-component-types-${this.getUniqueId()}" name="pictureDevelopementType" class="form-control" data-placeholder="Choisissez le type d'aménagement correspondant" required>
          <option value=""></option>
        </select>
        <small class="input-error form-text text-danger">Choisissez un type d'aménagement.</small>
      </div>
    </div>
    <div class="col-md-5">
      <img id="add-picutre-component-picture-${this.getUniqueId()}" class="w-100 rounded" alt="vide" src="/assets/img/img-placeholder.jpg" />
    </div>  
  </div>
</div>`;

  _developmentTypeListOptions;
  $selectType;

  /**
   *
   */
  constructor() {
    super();
    this._developmentTypeListOptions = new Map();
  }

  /**
   * @inheritDoc
   */
  getView() {
    const $component = $(this._template);

    const $fileInput = $component.find('#add-picutre-component-picture-upload-' + this.getUniqueId());
    const $fileOutputImg = $component.find('#add-picutre-component-picture-' + this.getUniqueId());
    const $fileOutputData = $component.find('#add-picutre-component-picture-upload-data-' + this.getUniqueId());

    $fileInput.on('change', (e) => {
      const file = e.target.files[0];
      const reader = new FileReader();

      reader.onloadend = () => {
        $fileOutputImg.attr('src', reader.result);
        $fileOutputData.val(reader.result);
      }

      reader.readAsDataURL(file);

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

    this.$selectType = $component.find('#add-picutre-component-types-' + this.getUniqueId());

    this.$selectType.chosen({
      width: '100%',
      no_results_text: 'Cet aménagement n\'existe pas !',
      disable_search: true,
      allow_single_deselect: true
    });

    this.$selectType.data('validator', () => {
      const errorElement = this.$selectType.next().next('.input-error');
      if (this.$selectType.val()[0] === undefined) {
        errorElement.show(100);
        return false;
      } else {
        errorElement.hide(100);
        return true;
      }
    });

    return $component;
  }

  /**
   * Set the list of DevelopementType that can be selected
   *
   * @param {DevelopementType[]} developementTypesList - List of DevelopementType
   */
  setDevelopmentTypesList(developementTypesList) {

    for (const developementType of developementTypesList) {
      if (this._developmentTypeListOptions.get(developementType.idType) === undefined) {
        const option = $(`<option value="${developementType.idType}">${developementType.title}</option>`);
        option.appendTo(this.$selectType);
        this._developmentTypeListOptions.set(developementType.idType, option);
      }
    }

    this._developmentTypeListOptions.forEach((value, key, map) => {

      if (!developementTypesList.find(developementType => developementType.idType == key)) {
        value.remove();
      }

    });

    this.$selectType.trigger('chosen:updated');
  }

}
