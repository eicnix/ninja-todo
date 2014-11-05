/*
 * Copyright (C) 2014 Lukas Eichler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var TodoApp = angular.module("TodoApp", ["ngResource", "ngRoute"]).
    config(function ($routeProvider) {
        "use strict";
        $routeProvider.when('/task', {controller: TodoCtrl, templateUrl: 'index.html'});
    });
TodoApp.factory('Todo', function ($resource) {
    "use strict";
    return $resource(
        '/task/api',
        {},
        {
            'update': {method: 'PUT'},
            'query': {method: 'GET', isArray: true},
            'delete': {
                url: '/task/api/:id',
                method: 'DELETE'
            }
        }
    );
});

var TodoCtrl = function ($scope, Todo) {
    "use strict";
    $scope.action = "Add";
    $scope.Todos = Todo.query();
    $scope.addMenuItem = function () {
        $scope.todo.complete = false;
        Todo.update($scope.todo, function (data) {
            $scope.Todos.push(data);
            $scope.todo = {};
        });
    };
    $scope.updateItem = function () {
        var todo = this.todo;

        todo.complete = todo.complete === false;
        $('#item_' + todo.id).toggleClass('strike');
        Todo.update(todo, function (data) {
            updateByAttr($scope.Todos, 'id', todo.id, data)
        });
    };
    $scope.deleteItem = function () {
        var id = this.todo.id;
        var todo = this.todo;
        Todo.delete({id: id}, function () {
            $("#row_" + id).fadeOut();
            $scope.Todos.splice($scope.Todos.indexOf(todo), 1);
        });
    };

    var updateByAttr = function (arr, attr1, value1, newRecord) {
        if (!arr) {
            return false;
        }
        var i = arr.length;
        while (i--) {
            if (arr[i] && arr[i][attr1] && (arguments.length > 2 && arr[i][attr1] === value1)) {
                arr[i] = newRecord;
            }
        }
        return arr;
    };
};
