let user = null;

function getUser() {
  return user;
}

function isOuvrier() {
  return user === null ? false : user.status === "o";
}

function isClient() {
  return user === null ? false : user.status === "c";
}

function changeMenuForUser(newUser) {
  user = newUser;
  if (isClient()) {
    $('.nav-user, .nav-ouvrier').addClass('d-none');
    $('.nav-client').removeClass('d-none');
  } else if (isOuvrier()) {
    $('.nav-user, .nav-client').addClass('d-none');
    $('.nav-ouvrier').removeClass('d-none');
  } else {
    $('.nav-client, .nav-ouvrier').addClass('d-none');
    $('.nav-user').removeClass('d-none');
  }
}

export {getUser, isClient, isOuvrier, changeMenuForUser};