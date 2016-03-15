angular.module('app')
    .provider('feedback', function () {
        this.$get = function ($rootScope) {
            var feedbacks = [];
            $rootScope.feedbacks = feedbacks;

            $rootScope.closeAlert = function (index) {
                feedbacks.splice(index, 1);
            };

            return {
                push: function (message, title, type) {
                    this.clearOlder();
                    feedbacks.push({message: message, title: title, type: type, active: true, ts: this.currentDate()});
                },
                success: function (message, title) {                    
                    this.clearAll();
                    this.push(message, title, 'success');
                },
                info: function (message, title) {
                    this.push(message, title, 'info');
                },
                warning: function (message, title) {
                    this.push(message, title, 'warning');
                },
                danger: function (message, title) {
                    this.push(message, title, 'danger');
                },
                clear: function () {
                    feedbacks = [];
                },
                currentDate: function() {
                    return new Date();
                },
                clearOlder: function () {
                    var now = this.currentDate();
                    var index = feedbacks.length;
                    while (index--) {
                        var event = feedbacks[index];
                        if (now - event.ts > 2000) {
                            feedbacks.splice(index, 1);
                        }
                    }
                },
                clearAll: function () {
                    var now = this.currentDate();
                    var index = feedbacks.length;
                    while (index--) {
                        var event = feedbacks[index];
                            feedbacks.splice(index, 1);
                    }
                },
                clearInActive: function () {
                    angular.forEach(feedbacks, function (value, key) {
                        if (!feedbacks[key].active) {
                            feedbacks.splice(key, 1);
                        } else {
                            feedbacks[key].active = false;
                        }
                    });
                }
            };
        };
    }).filter('nl2br', function ($sce) {
        return function (val) {
            return val && $sce.trustAsHtml(val.replace(/\n/g, "<br/>"));
        };
    });
 