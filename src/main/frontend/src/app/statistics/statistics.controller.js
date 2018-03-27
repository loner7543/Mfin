(function () {

  angular
    .module('frontend')
    .controller('StatisticsController', StatisticsController);

  /** @ngInject */
  function StatisticsController($scope, $http, ngDialog, UtilsFunctionsFactory) {
    var vm = this;

    $scope.calculateMainStatistics = function () {
      $http({
        method: "POST",
        url: "http://localhost:8080/krugstat/rest/calculate"
      }).then(function (resp) {
          console.log("Success resp1", resp.data)
          $scope.mainStatistics = resp.data;
          var amplitudes = resp.data.amplitudes;
          var xLabelData = 1;
          var xLabelValues = [];
          for (var i = 0; i < amplitudes.length; i++) {
            xLabelValues.push('' + xLabelData);
            xLabelData++;
          }
          var layout = {
            title: 'Гистограмма амплитуд',
            showlegend: true,
            xaxis: {
              tickangle: -45
            },
            yaxis: {
              zeroline: false,
              gridwidth: 2
            },
            bargap: 0.05

          };
          $scope.graphData = [
            {
              x: xLabelValues,
              y: amplitudes,
              type: 'bar',
              name: 'Амплитуды'
            }
          ];
          $scope.layout = layout;
        },
        function (result) {
          console.error(result, result.data);
        });

    };

    $scope.writeAmplitudes = function () {
      $http({
        method: "GET",
        url: "http://localhost:8080/krugstat/rest/uploadAmplitudes",
        headers: {
          'Accept': 'application/json, */*'
        }
      }).then(function (resp) {
          console.log("Success resp1", resp)
          $scope.filrnames = resp.data;
        },
        function (result) {
          console.error(result, result.data);
        });
    };

    $scope.ujd = function () {
      ngDialog.open({
        template: 'app/statistics/inputStatisticsDialog.html',
        className: 'ngdialog-theme-default',
        scope: $scope
      });
    };

    $scope.readAmplitudes = function () {

    };

    $scope.writeHeights = function () {
      debugger;
      $http({
        method: "GET",
        url: "http://localhost:8080/krugstat/rest/uploadHeights",
        headers: {
          'Accept': 'application/json, */*'
        }
      }).then(function (resp) {
          console.log("Success resp1", resp)
          $scope.filrnames = resp.data;
        },
        function (result) {
          console.error(result, result.data);
        });
    };

    $scope.readHeights = function () {

    };

    $scope.loadFile = function () {
      $http({
        method: "GET",
        url: "http://localhost:8080/krugstat/rest/loadFiles",
        headers: {
          'Accept': 'application/json, */*'
        }
      }).then(function (resp) {

          console.log("Success resp1", resp);
          $scope.filrnames = resp.data;
          UtilsFunctionsFactory.showFlashMessage('success', "Файлы загружены");
        },
        function (result) {
          console.error(result, result.data);
          UtilsFunctionsFactory.showFlashMessage('danger', "Ошибка при загрузке файлов");
        });
    };
  }
})();
