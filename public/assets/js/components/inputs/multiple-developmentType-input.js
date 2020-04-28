'use strict';

import {Component} from '../component.js';
import {ajaxGET} from '../../utils/ajax.js';

/**
 * @module Components
 */

/**
 * Component that hold input for multiple DevelopmentType
 *
 * @extends module:Components.Component
 */
export class MutltipleDevelopmentTypeInputComponent extends Component {

  name;
  labelValue;
  placeholder;
  validator;
  _$selectTypes;
  _developmentTypeList;

  /**
   *
   */
  constructor(name = '', validator, required = true,
      labelValue = 'Type(s) d\'aménagement(s)<span class="text-danger">*</span>',
      showLabel = true, showError = true, placeholder = 'Choisissez au moins un type d\'aménagement',
      addButton = true) {
    super();
    this.name = name;
    this.labelValue = labelValue;
    this.validator = validator;
    this.placeholder = placeholder;

    this._$view = $(this._template);

    if (addButton) {
      this._$view.append(`<p class="mx-3 mt-1">
        Aménagement inexistant ? 
        <a href="/amenagements/ajouter" target="_blank">Créer un nouveau type d'aménagement</a>
      </p>`);
    }

    this._$selectTypes = this._$view.find('#multiple-developmentType-input-' + this.getUniqueId());

    this._$selectTypes.chosen({
      width: '100%',
      no_results_text: 'Cet aménagement n\'existe pas !',
      allow_single_deselect: true
    });

    this._$selectTypes.on('chosen:showing_dropdown', () => {
      this._retriveDevelopmentTypeList();
    });

    this.required = required;
    this.showLabel = showLabel;
    this.showError = showError;
  }

  _required;

  set required(required) {

    this._required = required;
    this._$selectTypes.attr('required', this._required);

  }

  _showLabel;

  set showLabel(showLabel) {

    this._showLabel = showLabel;

    if (this._showLabel) {
      this._$view.find('label').removeClass('sr-only');
      this._$view.find('input').removeAttr('placeholder');
    } else {
      this._$view.find('label').addClass('sr-only');
      this._$view.find('input').attr('placeholder', this.labelValue);
    }

  }

  _showError;

  set showError(showError) {

    this._showError = showError;
    if (this._showError) {
      this._$selectTypes.data('validator', () => {
        if (this.validator !== undefined) {
          this.validator(this._developmentTypeList.filter(
              developmentType => this._$selectTypes.val().find(selected => selected == developmentType.idType)));
        }
        const errorElement = this._$selectTypes.next().next('.input-error');
        if (this._$selectTypes.val()[0] === undefined) {
          errorElement.show(100);
          return false;
        } else {
          errorElement.hide(100);
          return true;
        }
      });
    } else {
      this._$selectTypes.data('validator', undefined);
    }

  }

  get _template() {
    return `<div>
  <label for="multiple-developmentType-input-${this.getUniqueId()}">${this.labelValue}</label>
  <select id="multiple-developmentType-input-${this.getUniqueId()}" name="${this.name}" required
          class="form-control" data-placeholder="${this.placeholder}" multiple>
    <option value=""></option>
  </select>
  <small class="input-error form-text text-danger">Au moins un type d'aménagement dois être selectionné.</small>
</div>`;
  }

  _retriveDevelopmentTypeList() {
    ajaxGET('/api/developmentType-list', null, (data) => {
      this._developmentTypeList = data.developmentTypesList;
      for (const developmentType of this._developmentTypeList) {
        if (this._$selectTypes.find('option[value="' + developmentType.idType + '"]').length == 0) {
          $(`<option value="${developmentType.idType}">${developmentType.title}</option>`).appendTo(this._$selectTypes);
        }
      }
      this.update();
    });
  }

  update() {
    this._$selectTypes.trigger('chosen:updated');
  }

}