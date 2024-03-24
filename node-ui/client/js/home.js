let app = angular.module('panel', []);

app.controller('devicesCtrl', ($scope, $http) => {

    $scope.redirect = (uuid) => {
        location.href="show.html?uuid=" + uuid;
    };

    $scope.clean_logs = (uuid) => {
        $http.delete(`/api/logs/${uuid}`)
        .then(location.reload())
        .catch(error => console.error(error));
    };

    $scope.delete_device = (uuid) => {
        $http.delete(`/api/devices/${uuid}`)
        .then(location.reload())
        .catch(error => console.error(error));
    };

    $http.get('/api/devices/').then(res => {
        for (const property in res.data) {
            res.data[property].registered_on = normalizeDate(res.data[property].registered_on);
            res.data[property].last_access = normalizeDate(res.data[property].last_access);
        }
        $scope.devices = res.data;
    })
    .catch(error => console.error(error));

});