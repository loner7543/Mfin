(function () {

  angular
    .module('frontend')
    .controller('StatisticsController', StatisticsController);

  /** @ngInject */
  function StatisticsController($scope, $http, ngDialog, UtilsFunctionsFactory,connectionService) {
    var vm = this;
    $scope.inputData={
      textAreaValues:""
    };

    $scope.calculateMainStatistics = function () {
      $http({
        method: "POST",
        url: connectionService.getUrl()+"/calculate"
      }).then(function (resp) {
          console.log("Success resp1", resp.data)
          $scope.mainStatistics = resp.data;
          drawAmplitudes(resp);
          drawIntervalChart($scope.mainStatistics);
        },
        function (result) {
          console.error(result, result.data);
          UtilsFunctionsFactory.showFlashMessage('danger', "Ошибка при расчете статистики");
        });

    };

   function drawIntervalChart(statData){
     console.log(statData);
     xBarArray=[];
     yBarArray=[];
     var gausian = {
       x: [0, 1, 2, 3, 4, 5],
       y: [1.5, 1, 1.3, 0.7, 0.8, 0.9],
       type: 'scatter'
     };

     for (var i = 0; i <statData.m.length ; i++) {
       xBarArray.push(i);
       yBarArray.push(statData.m[i]);// перейти к относительным частотам
     }

     var bar = {
       x: xBarArray,
       y: yBarArray,
       type: 'bar'
     };

     var multyLayout = {
       title: 'Интервальный статистический ряд',
       showlegend: true

     };

     $scope.data = [bar, gausian];
     $scope.multyLayout = multyLayout;
    }

    function drawAmplitudes(resp){
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
    }

    $scope.writeAmplitudes = function () {
      $http({
        method: "GET",
        url: connectionService.getUrl()+"/uploadAmplitudes",
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

    $scope.manualInputHandler = function(scope){
      $http({
        method: "POST",
        url: connectionService.getUrl()+"/manualInput",
        params: $scope.inputData
      }).then(function (resp) {
          console.log("Success resp1", resp);
          scope.closeThisDialog();
          $state.reload();
        },
        function (result) {
          UtilsFunctionsFactory.showFlashMessage('danger', "Ошибка при расчете статистики");
          console.error(result, result.data);
        });
    };

    $scope.readAmplitudes = function () {

    };

    $scope.writeHeights = function () {
      debugger;
      $http({
        method: "GET",
        url: connectionService.getUrl()+"/uploadHeights",
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
        url: connectionService.getUrl()+"/loadFiles",
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
