function changeMenuForUser(user) {
  if (user != null && user.status === "c") {
    $('.nav-user, .nav-ouvrier').addClass('d-none');
    $('.nav-client').removeClass('d-none');
  } else if (user != null && user.status === "o") {
    $('.nav-user, .nav-client').addClass('d-none');
    $('.nav-ouvrier').removeClass('d-none');
  } else {
    $('.nav-client, .nav-ouvrier').addClass('d-none');
    $('.nav-user').removeClass('d-none');
  }
}

export {changeMenuForUser};