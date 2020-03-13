function createAlert(level, message) {

  $('#alerts').append(
      `<div class="alert alert-${level} alert-dismissible fade show mt-1 mx-1" role="alert">
  ${message}
  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
      <span aria-hidden="true">&times;</span>
  </button>
</div>`);

}

function clearAlerts() {
  $('#alerts').empty();
}

export {createAlert, clearAlerts};