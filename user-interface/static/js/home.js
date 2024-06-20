let app = angular.module('panel', []);

app.controller('devicesCtrl', ($scope, $http) => {

    $scope.redirect = (uuid) => {
        location.href='show.html?uuid=' + uuid;
    };

    $scope.clean_records = (uuid) => {
        $http.delete(`http://localhost:8888/records/${uuid}`)
        .then(location.reload())
        .catch(error => console.error(error));
    };

    $scope.delete_device = (uuid) => {
        $http.delete(`http://localhost:8888/devices/${uuid}`)
        .then(location.reload())
        .catch(error => console.error(error));
    };

    $http.get('http://localhost:8888/devices/?withRecords=true').then(res => {
        for (const property in res.data) {
            res.data[property].registeredOn = normalizeDate(res.data[property].registeredOn);
            res.data[property].lastAccess = normalizeDate(res.data[property].lastAccess);
        }
        $scope.devices = res.data;
    })
    .catch(error => console.error(error));

});