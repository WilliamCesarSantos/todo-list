'use strict';

var mongoose = require('mongoose'),
    Task = mongoose.model('Task');

exports.listAllTasks = function(request, response) {
    Task.find({}, function(error, tasks) {
        if(error) {response.send(error);}
        else {response.json(tasks);}
    });
};

exports.get = function(request, response) {
    Task.findById(request.params.taskId, function(error, task) {
        if(error) {response.send(error);}
        else {response.json(task);}
    });
};

exports.insert = function(request, response) {
    var newTask = new Task(request.body);
    newTask.save(function(error, task){
        if(error){response.send(error);}
        else {response.json(task);}
    });
};

exports.update = function(request, response) {
    Task.findOneAndUpdate({_id: request.params.taskId}. request.body, {new : true}, function(error, task) {
        if(error) {response.send(error);}
        else {response.json(task);}
    });
};

exports.delete = function(request, response) {
    Task.remove({_id : request.params.taskId}, function(error, task){
        if(error) {reponse.send(error);}
        else {response.json({message : 'Delete with success'})}
    });
};