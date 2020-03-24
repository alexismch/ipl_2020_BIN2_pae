'use strict';

/**
 * Do a GET ajax request.
 * @see ajax
 */
function ajaxGET(url, data, onSuccess, onError, isJson) {
  ajax('GET', url, data, onSuccess, onError, isJson);
}

/**
 * Do a POST ajax request.
 * @see ajax
 */
function ajaxPOST(url, data, onSuccess, onError, isJson) {
  ajax('POST', url, data, onSuccess, onError, isJson);
}

/**
 * Do a PUT ajax request.
 * @see ajax
 */
function ajaxPUT(url, data, onSuccess, onError, isJson) {
  ajax('PUT', url, data, onSuccess, onError, isJson);
}

/**
 * Do a DELETE ajax request.
 * @see ajax
 */
function ajaxDELETE(url, data, onSuccess, onError, isJson) {
  ajax('DELETE', url, data, onSuccess, onError, isJson);
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
 * @param {boolean} isJson If true, it's mean that data should be send as JSON using JSON.stringify(data) instaed of default urlencoded
 */
function ajax(method = 'GET', url = '', requestData = null, onSuccess = null, onError = null, isJson = false) {

  console.log({isJson, requestData});

  $.ajax({
    method: method,
    url: url,
    dataType: 'json',
    data: isJson ? JSON.stringify(requestData) : requestData,
    contentType: isJson ? 'application/json; charset=UTF-8' : 'application/x-www-form-urlencoded; charset=UTF-8',
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
