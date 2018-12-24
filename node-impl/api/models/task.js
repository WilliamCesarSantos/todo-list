'use strict';

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var Task = new Schema({
    title : {
        type : String,
        required: 'Title of task is necessary'
    },
    description : {
        type : String
    },
    createdAt : {
        type : Date,
        default : Date.now
    },
    updateAt : {
        type : Date,
        default : Date.now
    },
    closeAt : {
        type : Date
    },
    status : {
        type : [{
            type : String,
            enum : ['OPEN', 'CLOSE']
        }],
        default : ['OPEN']
    }
});

module.exports = mongoose.model('Task', Task);