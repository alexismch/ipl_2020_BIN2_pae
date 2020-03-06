'use strict';

/**
 * Fait une requête ajax de type GET.
 * @see ajax
 */
function ajaxGET(url, data, onSuccess, onError) {
  ajax('GET', url, data, onSuccess, onError);
}

/**
 * Fait une requête ajax de type POST.
 * @see ajax
 */
function ajaxPOST(url, data, onSuccess, onError) {
  ajax('POST', url, data, onSuccess, onError);
}

/**
 * Fait une requête ajax de type PUT.
 * @see ajax
 */
function ajaxPUT(url, data, onSuccess, onError) {
  ajax('PUT', url, data, onSuccess, onError);
}

/**
 * Fait une requête ajax de type DELETE.
 * @see ajax
 */
function ajaxDELETE(url, data, onSuccess, onError) {
  ajax('DELETE', url, data, onSuccess, onError);
}

/**
 * Fait une requête ajax.
 * Les données renvoyées par la requête devraient être de type json.
 *
 * @param {String} method Methode HTTP utilisée
 * @param {String} url Url vers laquelle la requete est effectuée
 * @param {String} data Les différentes données passées par methode
 * @param {function} onSuccess Fonction appelée lorsque la requête est effectuée correctement
 * @param {function} onError Fonction appelée en case d'echec de la requête
 */
function ajax(method = 'GET', url = '', data = null, onSuccess = null,
    onError = null) {

  console.log({data});
  $.ajax({
    url: url,
    dataType: 'json',
    data: data,
    beforeSend: (jqXHR, settings) => {
      // En cas de debug
      jqXHR.url = settings.url;
    },
    success: (data, statut) => {
      if (onSuccess !== null) {
        onSuccess(data, statut);
      }
    },
    error: (jqXHR, textStatus, errorThrown) => {
      console.error({
        textStatus,
        errorThrown,
        jqXHR
      });
      if (onError !== null) {
        onError(jqXHR, textStatus, errorThrown);
      }
    }
  });

}

export {ajaxGET, ajaxPOST, ajaxPUT, ajaxDELETE, ajax};
