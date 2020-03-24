'use strict';

import {ajaxGET} from '../utils/ajax.js';

function getTemplate(id) {
  return `<div class="modal fade" id="${id}" tabindex="-1" role="dialog" aria-labelledby="${id}ModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="${id}ModalLabel">Ajouter une photo</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <p class="text-warning">PAS FINI</p>
        <div class="form-group">
          <label for="add-picutre-component-picture-title">Titre de la photo<span class="text-danger">*</span></label>
          <input class="form-control" id="add-picutre-component-picture-title" name="pictureTitle" type="text" />
          <small class="input-error form-text text-danger">Le titre de la photo est requis.</small>
        </div>
        <div class="form-group">
          <div class="input-group mb-3">
            <div class="custom-file">
              <input type="file" class="custom-file-input" id="add-picutre-component-picture-upload">
              <label class="custom-file-label" for="add-picutre-component-picture-upload">Chosissez le fichier de la photo</label>
            </div>
          </div>
        </div>
        <div class="form-group">
          <select id="add-picutre-component-types" name="type" class="form-control" data-placeholder="Choisissez le type d'aménagement correspondant">
            <option value=""></option>
          </select>
          <small class="input-error form-text text-danger">Choisissez un type d'aménagement.</small>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
        <button type="button" class="btn btn-primary">Sauver</button>
      </div>
    </div>
  </div>
</div>`;
}

function createView(id) {
  const $component = $(getTemplate(id));

  const $selectType = $component.find('#add-picutre-component-types');

  $selectType.chosen({
    width: '100%',
    no_results_text: 'Cet aménagement n\'existe pas !',
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