$(document).ready(function () {
    let usersList;
    let allRoles;

    //запрос всех ролей с сервера
    $.ajax({
        url: '/roles', dataType: "json", success: function (allRolesList) {
            allRoles = allRolesList
        }
    })

    //постройка таблицы со всеми юзерами
    buildUsersTable();

    function buildUsersTable() {
        $('#users-tbody').empty();
        $.ajax({
            url: '/users',
            dataType: "json",
            success: function (data, textStatus) {
                $.each(data, function (i) {
                    usersList = data;
                    let userRoles = '';
                    for (let j = 0; j < data[i].roles.length; j++) {
                        userRoles += data[i].roles[j].roleName + ' ';
                    }
                    let row = `<tr>
                    <th>${data[i].id}</th>
                    <td>${data[i].firstName}</td>
                    <td>${data[i].lastName}</td>
                    <td>${data[i].age}</td>
                    <td>${data[i].email}</td>
                    <td>${userRoles}</td>
                    <td><button type="button" data-user-id=${data[i].id}
                        class="btn btn-primary btn-edit">Edit</button></td>
                    <td><button type="button" data-user-id=${data[i].id}
                        class="btn btn-danger btn-del">Delete</button></td>
                    </tr>`
                    $('#users-tbody').append(row)
                });
            }
        });
    };

//открытие модального окна редактирования пользователя
    $('#v-pills-tabContent').on('click', '.btn-edit', function () {
        $("#edituserform").trigger('reset') //без этой строки творится чертовщина на этапе попытки редактирования второго и последующих юзеров - в поля формы подставляются неактуальные значения. Это нормально?
        let user = usersList.find(item => item.id == $(this).attr('data-user-id'));
        $('#edituserid').attr('value', user.id);
        $('#edituserfirstname').attr('value', user.firstName);
        $('#edituserlastname').attr('value', user.lastName);
        $('#edituserage').attr('value', user.age);
        $('#edituseremail').attr('value', user.email);
        $('#edituserpassword').attr('value', user.password);
        // $('#edituserroles').attr('value', user.roles);
        $('#edituserroles').empty();
        allRoles.forEach(function (item) {
            $('#edituserroles').append(`<option value="${item.id}">${item.roleName}</option>`);
        });
        user.roles.forEach(function (role) {
            $(`#edituserroles option[value=${role.id}]`).prop('selected', true);
        });
        $('#edituser').modal('show');
    });

//отправка данных из формы редактирования пользователя
    $('#edituserform-save-button').click(
        function () {
            let userRoles = [];
            $('#edituserroles').val().forEach(function (v) {
                userRoles.push(allRoles.find(role => role.id == v));
            });
            let jsonForResponse = {
                id: $('#edituserid').val(),
                firstName: $('#edituserfirstname').val(),
                lastName: $('#edituserlastname').val(),
                age: $('#edituserage').val(),
                email: $('#edituseremail').val(),
                password: $('#edituserpassword').val(),
                roles: userRoles
            };
            $.ajax('/user', {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "put",
                dataType: "json",
                data: JSON.stringify(jsonForResponse),
                complete: function () {
                    buildUsersTable(); //перерисовка таблицы с юзерами
                }
            })
        }
    )

// открытие модального окна удаления пользователя
    $('#v-pills-tabContent').on('click', '.btn-del', function () {
        $("#deleteuserform").trigger('reset')
        let user = usersList.find(item => item.id == $(this).attr('data-user-id'));
        $('#deleteuserid').attr('value', user.id);
        $('#deleteuserfirstname').attr('value', user.firstName);
        $('#deleteuserlastname').attr('value', user.lastName);
        $('#deleteuserage').attr('value', user.age);
        $('#deleteuseremail').attr('value', user.email);
        // $('#edituserroles').attr('value', user.roles);
        $('#deleteuserroles').empty();
        user.roles.forEach(function (item) {
            $('#deleteuserroles').append(`<option value="${item.id}">${item.roleName}</option>`);
        });
        $('#deleteuser').modal('show');
    });

//отправка данных из формы удаления пользователя
    $('#deleteuserform-save-button').click(
        function () {
            $.ajax('/user/' + $('#deleteuserid').val(), {
                method: "delete",
                dataType: "json",
                data: {},
                complete: function () {
                    buildUsersTable(); //перерисовка таблицы с юзерами
                }
            })
        }
    )

//открытие формы создания нового пользователя
    $('#v-pills-tabContent').on('click', '#nav-newuser-tab', function () {
        $("#newuserform").trigger('reset')
        $('#newuserroles').empty();
        allRoles.forEach(function (item) {
            $('#newuserroles').append(`<option value="${item.id}">${item.roleName}</option>`);
        });
    });

//отправка данных из формы создания нового пользователя
    $('#newuser-sendform-button').click(
        function () {
            let userRoles = [];
            $('#newuserroles').val().forEach(function (v) {
                userRoles.push(allRoles.find(role => role.id == v));
            });
            let jsonForResponse = {
                id: null,
                firstName: $('#newuserfirstname').val(),
                lastName: $('#newuserlastname').val(),
                age: $('#newuserage').val(),
                email: $('#newuseremail').val(),
                password: $('#newuserpassword').val(),
                roles: userRoles
            };
            $.ajax('/user', {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "post",
                dataType: "json",
                data: JSON.stringify(jsonForResponse),
                complete: function () {
                    $('#nav-home-tab')[0].click();
                    buildUsersTable();//перерисовка таблицы с юзерами
                }
            })
        }
    )
})
