let app = angular.module('show', []);
const uuid = getParamFromUrl('uuid');

app.controller('dataCtrl', ($scope, $http, $q) => {

    let info = $http.get(`http://localhost:8888/devices/${uuid}`);
    let records = $http.get(`http://localhost:8888/records/?uuid=${uuid}`);

    $q.all([info, records]).then(responses => {
        if (Object.keys(responses[0].data).length === 0 ||
            Object.keys(responses[1].data).length === 0)
            throw new Error('Check your parameters.');

        $scope.uuid = responses[0].data.uuid;
        $scope.device_name = responses[0].data.deviceName;
        $scope.username = responses[0].data.username;
        $scope.device_type = responses[0].data.deviceType;
        $scope.cpu_desc = responses[0].data.cpuDesc;
        $scope.os_name = responses[0].data.osName;
        $scope.registered_on = normalizeDate(responses[0].data.registeredOn);
        $scope.last_access = normalizeDate(responses[0].data.lastAccess);
        
        for (const property in responses[1].data) {
            responses[1].data[property].id.timestampBegin = normalizeDate(responses[1].data[property].id.timestampBegin);
            responses[1].data[property].timestampEnd = normalizeDate(responses[1].data[property].timestampEnd);
        }
        $scope.records = responses[1].data;
        finishLoading();
    })
    .catch(error => {
        errorInLoading();
        console.error(error);
    });
    
});