'use strict';

import {ajax} from './ajax.js';

/**
 * Valide le formulaire. En cas d'erreur affiche des messages d'erreur pour chaque entrée conformément à la methode {@link checkInputValidity}
 *
 * @param {HTMLFormElement} form Formulaire à valider
 * @returns {boolean} true si le formulaire est valide
 */
function checkFormValidity(form) {

  if (form.checkValidity()) {
    return true;
  } else {
    $(form).find('input, textarea, select').each((i, input) => {
      checkInputValidity(input);
    });
    return false;
  }

}

/**
 * Valide un element de type input, textarea, select
 * Si l'element à valider est directement suivit d'un element qui a comme attribut class="input-error"
 * alors cet element est affiché en cas d'erreur et caché si l'entré est valide
 *
 * @param {HTMLInputElement|HTMLTextAreaElement|HTMLSelectElement} element Input à valider
 * @returns {boolean} true si l'input est valide
 */
function checkInputValidity(element) {

  const errorElement = $(element).next('.input-error');
  if (element.checkValidity()) {
    errorElement.hide(100);
    return true;
  } else {
    errorElement.show(100);
    return false;
  }

}

/**
 * Ajoute un écouteur d'événement quand le formulaire est soumis.
 * Cet écouteur d'événement se chargera de valider le fumulaire avec la methode {@link checkFormValidity}
 * Il enverra ensuite le formulaire via une requête ajax en traduisant les parametres method et action
 *
 * @param {Jquery} $form Formulaire è envoyé
 * @param {function} onSuccess Fonction appelée lorsque le formulaire est envoyé correctement
 * @param {function} onError Fonction appellé en cas d'erreur d'envoi du formulaire
 * @param {function} onInvalid Fonction appellé si de formulaire n'est pas valide
 */
function onSubmit($form, onSuccess, onError, onInvalid) {

  $form.on('submit', function (e) {
    e.preventDefault();

    if (!checkFormValidity($form[0])) {
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

export {checkFormValidity, checkInputValidity, onSubmit};