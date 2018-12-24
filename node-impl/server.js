var express = require('express'),
    app = express(),
    port = process.env.PORT || 7979,
    mongoose = require('mongoose'),
    Task = require('./api/models/task'),
    bodyParser = require('body-parser');

mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost/todo_list_db');

app.use(bodyParser.urlencoded({extended : true}));
app.use(bodyParser.json());

var routes = require('./api/routes/taskRoutes');
routes(app);

app.listen(port);

console.log('Rest API initialized on port: ' + port);