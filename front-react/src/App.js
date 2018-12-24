import React, { Component } from 'react';
import './App.css';
import Cards from './Cards'
import FormTask from './FormTask'
const axios = require('axios');

class App extends Component {

  constructor(props) {
    super(props);
    this.state = { taskList: [] };

    this.updateTaskList = this.updateTaskList.bind(this);
  }

  updateTaskList(tasks) {
    this.setState({ taskList: tasks });
  }

  render() {
    axios.defaults.baseURL = 'http://localhost:8090/';

    axios.get('tasks/').then(response => {
      this.updateTaskList(response.data)
    }).catch(error => {
      alert('Server error');
      console.log(error);
      return null;
    });
    return (
      <div className="App">
        <Cards tasks={this.state.taskList} />
        <FormTask />
      </div>
    );
  }

}

export default App;
