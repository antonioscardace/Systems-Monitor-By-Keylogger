let app = angular.module('show', []);
const uuid = getParamFromUrl('uuid');

app.controller('dataCtrl', ($scope, $http, $q) => {

    let info = $http.get(`/api/devices/${uuid}`);
    let logs = $http.get(`/api/logs/${uuid}`);

    $q.all([info, logs]).then(responses => {
        if (Object.keys(responses[0].data).length === 0 ||
            Object.keys(responses[1].data).length === 0)
            throw new Error('Check your parameters.');

        $scope.uuid = responses[0].data.uuid;
        $scope.device_name = responses[0].data.device_name;
        $scope.username = responses[0].data.username;
        $scope.device_type = responses[0].data.device_type;
        $scope.cpu_desc = responses[0].data.cpu_desc;
        $scope.os_name = responses[0].data.os_name;
        $scope.registered_on = normalizeDate(responses[0].data.registered_on);
        $scope.last_access = normalizeDate(responses[0].data.last_access);
        
        for (const property in responses[1].data) {
            responses[1].data[property].timestamp_begin = normalizeDate(responses[1].data[property].timestamp_begin);
            responses[1].data[property].timestamp_end = normalizeDate(responses[1].data[property].timestamp_end);
        }
        $scope.logs = responses[1].data;
        finishLoading();
    })
    .catch(error => {
        errorInLoading();
        console.error(error)
    });
    
});