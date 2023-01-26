const URL = 'http://localhost:8080/admin/rest/users/';

let token = $("meta[name='_csrf']").attr("content");
let value = $("meta[name='_csrf_header']").attr("content");

const modalDelete = new bootstrap.Modal('#modal-delete');
const modalUpdate = new bootstrap.Modal('#modal-update');

const formUpdate = document.getElementById("update-user");
const formDelete = document.getElementById("delete-user");
const formCreate = document.getElementById("create-user");
let usersMap = new Map();


$(document).ajaxSend(function (e, xhr) {
    xhr.setRequestHeader(value, token);
});

(async function preparePage() {
    await getCurrentUserData();
    await fillUsersTable();
})();

formUpdate.addEventListener('submit', updateUser);
formCreate.addEventListener('submit', createUser);
formDelete.addEventListener('submit', deleteUser);

async function getCurrentUserData() {
    let url = URL + '0';
    let response = await fetch(url);
    if (response.ok) {
        let currentUser = await response.json();
        let nameElem = document.getElementById("current-user-name");
        let rolesElem = document.getElementById("current-user-roles");
        nameElem.textContent = currentUser.email
        for (let role of currentUser.roles) {
            rolesElem.textContent += role + " ";
        }
    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}

async function fillUsersTable() {
    let response = await fetch(URL);
    if (response.ok) {
        let usersList = await response.json();
        let rows = document.getElementById("all-users");
        rows.innerHTML = '';
        usersMap.clear();
        for (let user of usersList) {
            usersMap.set(user.id, user);
            rows.innerHTML += prepareUserRow(user);
        }
    }
}

function prepareUserRow(user) {
    return `<tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.email}</td>
                <td>${user.name || ""}</td>
                <td>${user.lastName || ""}</td>
                <td>${user.age}</td>
                <td>${userRolesToString(user.roles)}</td>
                <td>${prepareEditButton(user.id)}</td>
                <td>${prepareDeleteButton(user.id)}</td>
            </tr>`;
}

function userRolesToString(roles) {
    let res = '';
    for (let role of roles) {
        res += role;
        res += ' ';
    }
    return res;
}

function prepareEditButton(id) {
    return `<button type="button" class="btn btn-info text-white" onclick="openUpdateUserModal(${id})">
                                                  Edit
            </button>`;
}

function prepareDeleteButton(id) {
    return `<button type="button" class="btn btn-danger text-white" onclick="openDeleteUserModal(${id})">
                                                  Delete
            </button>`;
}

function openUpdateUserModal(id) {
    fillForm(formUpdate, usersMap.get(id));
    modalUpdate.show();
}

function openDeleteUserModal(id) {
    fillForm(formDelete, usersMap.get(id));
    modalDelete.show();
}

function fillForm(form, user) {
    console.log("filling table");
    console.log(user);
    form.elements["id"].value = user.id;
    form.elements["showId"].value = user.id;
    form.elements["login"].value = user.login;
    form.elements["email"].value = user.email;
    form.elements["name"].value = user.name;
    form.elements["lastName"].value = user.lastName;
    form.elements["age"].value = user.age;
    for (let option of form.elements["roles"].options) {
        option.selected = user.roles.includes(option.value);
    }
}


function deleteUser(event) {
    event.preventDefault();
    let json = serializeForm(event);
    $.ajax({
        url: URL + json.id,
        method: 'DELETE',
        success: function (result) {
            console.log(result);
            modalDelete.hide();
            fillUsersTable();
        },
        error: function (result) {
            console.log(result);
        }
    });
}

function createUser(event) {
    event.preventDefault();
    let json = serializeForm(event);
    console.log("create");
    console.log(JSON.stringify(json));
    $.ajax({
        url: URL,
        method: 'POST',
        data: JSON.stringify(json),
        contentType: "application/json",
        success: function (result) {
            console.log(result);
            event.target.reset();
            fillUsersTable();
            $("#users-tab-btn").click();
        },
        error: function (result) {
            console.log(result);
        }
    });
}

function updateUser(event) {
    event.preventDefault();
    let json = serializeForm(event);
    $.ajax({
        url: URL,
        method: 'PUT',
        data: JSON.stringify(json),
        contentType: "application/json",
        success: function (result) {
            console.log(result);
            event.target.reset();
            modalUpdate.hide();
            fillUsersTable();
        },
        error: function (result) {
            console.log(result);
        }
    });
}

function serializeForm(event) {
    let formData = new FormData(event.target);
    let json = Object.fromEntries(formData.entries());
    json.roles = formData.getAll("roles");
    return json;
}
