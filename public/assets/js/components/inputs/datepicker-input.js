'use strict';

import {Component} from '../component.js';

/**
 * @module Components
 */

/**
 * Component that hold a date input
 *
 * @extends module:Components.Component
 */
export class DateInputComponent extends Component {

  name;
  _$datePicker;
  _$datePickerInput;

  /**
   *
   */
  constructor(name = '', required = true, showLabel = true, showError = true) {
    super();
    this.name = name;

    this._$view = $(this._template);

    this._$datePicker = this._$view.find('#datepicker-' + this.getUniqueId());
    this._$datePicker.datetimepicker({
      format: 'L',
      widgetPositioning: {
        horizontal: 'left',
        vertical: 'auto'
      }
    });
    this._$datePickerInput = this._$datePicker.find('#datepicker-input-' + this.getUniqueId());

    this._$datePickerInput.on('blur', () => {
      this._$datePicker.datetimepicker('hide');
    });

    this.required = required;
    this.showLabel = showLabel;
    this.showError = showError;
  }

  _required;

  set required(required) {

    this._required = required;
    this._$datePickerInput.attr('required', this._required);

  }

  _showLabel;

  set showLabel(showLabel) {

    this._showLabel = showLabel;

    if (this._showLabel) {
      this._$view.find('label').removeClass('sr-only');
    } else {
      this._$view.find('label').addClass('sr-only');
    }

  }

  _showError;

  set showError(showError) {

    this._showError = showError;
    if (this._showError) {
      this._$datePickerInput.data('validator', () => {
        const $errorElement = this._$datePicker.next('.input-error');
        if (this._$datePickerInput[0].checkValidity()) {
          $errorElement.hide(100);
          return true;
        } else {
          $errorElement.show(100);
          return false;
        }
      });
    } else {
      this._$datePickerInput.data('validator', undefined);
    }

  }

  get _template() {
    return `<div>
      <label for="datepicker-input-${this.getUniqueId()}">Date<span class="text-danger">*</span></label>
      <div class="input-group date" id="datepicker-${this.getUniqueId()}" data-target-input="nearest">
        <input type="text" class="form-control" id="datepicker-input-${this.getUniqueId()}" autocomplete="off"
        data-target="#datepicker-${this.getUniqueId()}" data-toggle="datetimepicker" name="${this.name}" required/>
        <div class="input-group-append" data-target="#datepicker-${this.getUniqueId()}" data-toggle="datetimepicker">
          <div class="input-group-text"><i class="fa fa-calendar-alt"></i></div>
        </div>
      </div>
      <small class="input-error form-text text-danger">Une date est requise.</small>
    </div>`;
  }

}