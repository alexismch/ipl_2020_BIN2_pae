'use strict';

import {ajaxGET} from '../utils/ajax.js';

function getTemplate(id) {
  return `<div class="card p-2 mb-1">
  <div class="row">
    <div class="col-md-7">
      <div class="form-group">
        <label for="add-picutre-component-picture-title-${id}">Titre de la photo<span class="text-danger">*</span></label>
        <input class="form-control" id="add-picutre-component-picture-title-${id}" name="pictureTitle" type="text" />
        <small class="input-error form-text text-danger">Le titre de la photo est requis.</small>
      </div>
      <div class="form-group">
        <div class="input-group mb-3">
          <div class="custom-file">
            <input type="file" class="custom-file-input" id="add-picutre-component-picture-upload-${id}">
            <label class="custom-file-label" for="add-picutre-component-picture-upload-${id}">Chosissez le fichier de la photo</label>
          </div>
        </div>
      </div>
      <div class="form-group">
        <select id="add-picutre-component-types-${id}" name="type" class="form-control" data-placeholder="Choisissez le type d'aménagement correspondant">
          <option value=""></option>
        </select>
        <small class="input-error form-text text-danger">Choisissez un type d'aménagement.</small>
      </div>
      <button class="btn btn-sm btn-danger mb-1" type="button">Enlever cette photo</button>
    </div>
    <div class="col-md-5">
      <img class="w-100 rounded" alt="vide" src="/assets/img/img-placeholder.jpg">
    </div>  
  </div>
</div>`;
}

function createView(id) {
  const $component = $(getTemplate(id));

  const $selectType = $component.find('#add-picutre-component-types-' + id);

  $selectType.chosen({
    width: '100%',
    no_results_text: 'Cet aménagement n\'existe pas !',
    disable_search: true,
    allow_single_deselect: true
  });

  ajaxGET('/api/developmentType-list', null, (data) => {
    for (const developementType of data.developementTypesList) {
      $(`<option value="${developementType.idType}">${developementType.title}</option>`).appendTo($selectType);
    }
    $selectType.trigger('chosen:updated');
  });

  $selectType.data('validator', () => {
    const errorElement = $selectType.next().next('.input-error');
    if ($selectType.val()[0] === undefined) {
      errorElement.show(100);
      return false;
    } else {
      errorElement.hide(100);
      return true;
    }
  });

  return $component;
}

export function getAddPictureComponent(id) {
  return {
    getTitle: () => 'Ajouter une photo',
    getView: () => createView(id)
  }
}