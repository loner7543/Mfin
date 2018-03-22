(function () {
  angular
    .module('frontend')
    .controller('LoginController', LoginController)

  /** @ngInject */
  function LoginController($scope, $http,$interval, $location,ngDialog,UtilsFunctionsFactory) {
    var vm = this;
    vm.UtilsFunctionsFactory = UtilsFunctionsFactory;
    $scope.onSign = function () {

    }
  }

})();
// var promise = $http.get($location.protocol() + '://' + $location.host() + ':'+ $location.port() + "/crudGoods/data/buyers.json");
