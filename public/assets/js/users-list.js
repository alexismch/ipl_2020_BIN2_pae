function createUsersList(users) {
  $('#content .user-list').empty();
  for (const user of users) {
    createUserListItem(user);
  }
}

function createUserListItem(user) {
  const userListItem = `<li class="user-list-item border rounded mb-2">
      <p>${user.lastName} ${user.firstName}</p>
      <p>${user.email}</p>
      <p>${user.registrationDate}</p>
      <p>${user.status}</p>
      <a class="btn btn-primary w-min" href="test">Détails</a>
    </li>`;
  $('#content .user-list').append(userListItem);
}

export {createUsersList};