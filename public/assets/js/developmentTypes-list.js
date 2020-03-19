function createDevelopmentTypesList(developementTypesList) {
    $('#content').empty();
    for (const developmentType of developementTypesList) {
        const developmentListItem =`<li class="page-amenagements-item border rounded mb-2">
        <p>${developmentType.title}</p>
        <a class="btn btn-primary w-min" href="plusTard">Sélectionner</a>
        </li>`;
        $('#content').append(developmentListItem);
    }
  }
  
  export {createDevelopmentTypesList};