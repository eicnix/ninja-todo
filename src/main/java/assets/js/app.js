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
