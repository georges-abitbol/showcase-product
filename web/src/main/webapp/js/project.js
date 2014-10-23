/**
 * Created by anthony on 2014-10-22.
 */


angular.module('showcaseApp',[
  'ngRoute'
])

  .config(function($routeProvider) {
    $routeProvider
      .when('/', {
        controller:'ListCtrl',
        templateUrl:'list.html'
      })
//      .when('/edit/:projectId', {
//        controller:'EditCtrl',
//        templateUrl:'detail.html'
//      })
//      .when('/new', {
//        controller:'CreateCtrl',
//        templateUrl:'detail.html'
//      })
      .otherwise({
        redirectTo:'/'
      });
  })

  .controller('ListCtrl', function($scope, $http) {
    $http.get('api/accounts').success(function(data) {
      $scope.accounts = data;
    });

    $scope.orderProp = 'id';
  });



//  .controller('CreateCtrl', function($scope, $location, $timeout, Projects) {
//    $scope.save = function() {
//      Projects.$add($scope.project).then(function(data) {
//        $location.path('/');
//      });
//    };
//  })

//  .controller('EditCtrl',
//  function($scope, $location, $routeParams, Projects) {
//    var projectId = $routeParams.projectId,
//      projectIndex;
//
//    $scope.projects = Projects;
//    projectIndex = $scope.projects.$indexFor(projectId);
//    $scope.project = $scope.projects[projectIndex];
//
//    $scope.destroy = function() {
//      $scope.projects.$remove($scope.project).then(function(data) {
//        $location.path('/');
//      });
//    };
//
//    $scope.save = function() {
//      $scope.projects.$save($scope.project).then(function(data) {
//        $location.path('/');
//      });
//    };
//  });

