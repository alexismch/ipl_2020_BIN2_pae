'use strict';

import {ajax} from './ajax.js';

/**
 * Check if the form is valid. If he is not, error message can be displayed for each field according to the method {@link checkInputValidity}
 *
 * @param {Jquery} $form The form to check
 * @returns {boolean} true If the form is valid
 */
function checkFormValidity($form) {

  if ($form[0].checkValidity()) {
    return true;
  } else {
    $form.find('input, textarea, select').each((i, input) => {
      checkInputValidity($(input));
    });
    return false;
  }

}

/**
 * Check if a field (input, textarea, select, ...) is valid.
 * If the element is directly followed by an element with the class 'input-error'
 * this error element will be displayed if the field is invalid.
 *
 * @param {Jquery} Element (input, textarea, select, ...) to validate
 * @returns {boolean} true if the input is valid
 */
function checkInputValidity($element) {

  const errorElement = $element.next('.input-error');
  if ($element[0].checkValidity()) {
    errorElement.hide(100);
    return true;
  } else {
    errorElement.show(100);
    return false;
  }

}

/**
 * Add a submit event listener to the form.
 * This listener will validate the form with the function {@link checkFormValidity}.
 * Then the listener will send the form through an ajax request.
 *
 * @param {Jquery} $form The form to which the event listener must be added
 * @param {function} onSuccess A function called when the form is correctly send
 * @param {function} onError A function called if an error occured
 * @param {function} onInvalid A function called if the form is invalid
 * @param {function} onCheckValidity A function used to make an extra validity check.
 *                                   This function should return true is form is valid and false otherwise
 */
function onSubmitWithAjax($form, onSuccess, onError, onInvalid, onCheckValidity) {

  $form.on('submit', function (e) {
    e.preventDefault();

    if (!checkFormValidity($form) || (onCheckValidity !== undefined && !onCheckValidity())) {
      if (onInvalid !== undefined) {
        onInvalid();
      }
      return;
    }

    disableButtoms($form);

    const method = $form.attr('method');
    const url = $form.attr('action');

    const data = $form.serialize();

    ajax(method, url, data, (data) => {
      enableButtoms($form);
      if (onSuccess !== undefined) {
        onSuccess(data);
      }
    }, (error) => {
      enableButtoms($form);
      if (onError !== undefined) {
        onError(error);
      }
    });

  });

}

/**
 * Add a submit event listener to the form.
 * This listener will validate the form with the function {@link checkFormValidity}.
 * Then the listener will serialize the form to navigate to a URL with GET parameters
 *
 * @param {Jquery} $form The form to which the event listener must be added
 * @param {function} onInvalid A function called if the form is invalid
 * @param {function} onCheckValidity A function used to make an extra validity check.
 *                                   This function should return true is form is valid and false otherwise
 */
function onSubmitWithNavigation($form, navigation, onInvalid, onCheckValidity) {

  $form.on('submit', function (e) {
    e.preventDefault();

    if (!checkFormValidity($form) || (onCheckValidity !== undefined && !onCheckValidity())) {
      if (onInvalid !== undefined) {
        onInvalid();
      }
      return;
    }

    disableButtoms($form);

    const url = $form.attr('action');
    const data = $form.find(':input').filter(function (index, element) {
      return $(element).val() != '';
    }).serialize();

    if (navigation !== undefined) {
      navigation(url, data);
    }

    enableButtoms($form);

  });

}

function enableButtoms($form) {
  $form.find('button').each((i, button) => {
    const $button = $(button);
    $button.attr('type', 'submit')
    .removeClass('disabled')
    .html($button.data('content'));
  });
}

function disableButtoms($form) {
  $form.find('button').each((i, button) => {
    const $button = $(button);
    $button.attr('type', 'button')
    .addClass('disabled')
    .data('content', $button.html())
    .html('<i class="fas fa-circle-notch fa-spin"></i>');
  });
}

function verifySamePassword($input1, $input2) {
  if ($input1.val() === $input2.val()) {
    $("#content .notSamePassword").hide(100);
    return true;
  }
  $("#content .notSamePassword").show(100);
  return false;
}

export {verifySamePassword, checkFormValidity, checkInputValidity, onSubmitWithAjax, onSubmitWithNavigation};