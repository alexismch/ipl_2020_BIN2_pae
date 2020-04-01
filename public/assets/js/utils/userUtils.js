'use strict';

let connectedUser = null;

function getUser() {
  return connectedUser;
}

function isWorker(user) {
  if (user === undefined) {
    user = connectedUser;
  }
  return user === null ? false : user.status.id === "WORKER";
}

function isCustomer(user) {
  if (user === undefined) {
    user = connectedUser;
  }
  return user === null ? false : user.status.id === "CUSTOMER";
}

function changeMenuForUser(newUser) {
  connectedUser = newUser;
  if (isCustomer()) {
    $('.nav-user, .nav-ouvrier').addClass('d-none');
    $('.nav-client').removeClass('d-none');
  } else if (isWorker()) {
    $('.nav-user, .nav-client').addClass('d-none');
    $('.nav-ouvrier').removeClass('d-none');
  } else {
    $('.nav-client, .nav-ouvrier').addClass('d-none');
    $('.nav-user').removeClass('d-none');
  }
}

function getUserStatusColor(user) {

  if (user === undefined) {
    user = connectedUser;
  }

  switch (user.status.id) {
    case 'WORKER':
      return 'info';
    case 'CUSTOMER':
      return 'success';
    case 'NOT_ACCEPTED':
      return 'secondary';
  }

}

export {getUser, isCustomer, isWorker, changeMenuForUser, getUserStatusColor};