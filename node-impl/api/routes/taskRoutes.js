'use strict';
module.exports = function(app) {

    var controller = require('../controllers/taskController');

    app.route('/tasks')
        .get(controller.listAllTasks)
        .post(controller.insert);

    app.route('/tasks/:taskId')
        .get(controller.get)
        .put(controller.update)
        .delete(controller.delete);
        
};