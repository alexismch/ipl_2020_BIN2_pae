'use strict';

/**
 * Do a GET ajax request.
 * @see ajax
 */
function ajaxGET(url, data, onSuccess, onError) {
  ajax('GET', url, data, onSuccess, onError);
}

/**
 * Do a POST ajax request.
 * @see ajax
 */
function ajaxPOST(url, data, onSuccess, onError) {
  ajax('POST', url, data, onSuccess, onError);
}

/**
 * Do a PUT ajax request.
 * @see ajax
 */
function ajaxPUT(url, data, onSuccess, onError) {
  ajax('PUT', url, data, onSuccess, onError);
}

/**
 * Do a DELETE ajax request.
 * @see ajax
 */
function ajaxDELETE(url, data, onSuccess, onError) {
  ajax('DELETE', url, data, onSuccess, onError);
}

/**
 * Do an ajax request
 * This function required the response to be json formatted
 *
 * @param {String} method Method HTTP used
 * @param {String} url The requested URL
 * @param {String} data The data contained in the request
 * @param {function} onSuccess A function called when the request is successful
 * @param {function} onError A function called when an error happened during the request or an status code is in the 4xx or 5xx range
 */
function ajax(method = 'GET', url = '', requestData = null, onSuccess = null,
    onError = null) {

  console.log({requestData});
  $.ajax({
    method: method,
    url: url,
    dataType: 'json',
    data: requestData,
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
