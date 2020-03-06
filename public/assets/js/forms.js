'use strict';

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

export {checkFormValidity, checkInputValidity};