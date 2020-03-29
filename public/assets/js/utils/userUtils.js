'use strict';

let user = null;

function getUser() {
  return user;
}

function isOuvrier() {
  return user === null ? false : user.status.id === "WORKER";
}

function isClient() {
  return user === null ? false : user.status.id === "CUSTOMER";
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

function getUserStatusColor(currentUser) {

  if (currentUser === undefined) {
    currentUser = user;
  }

  switch (currentUser.status.id) {
    case 'WORKER':
      return 'info';
    case 'CUSTOMER':
      return 'success';
    case 'NOT_ACCEPTED':
      return 'secondary';
  }

}

export {getUser, isClient, isOuvrier, changeMenuForUser, getUserStatusColor};