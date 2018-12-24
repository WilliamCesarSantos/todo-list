import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Form, Input } from 'reactstrap';
const axios = require('axios');

class FormTask extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      show: false,
      title: null,
      description: null,
      id: null,
      status: 'OPEN'
    };

    this.save = this.save.bind(this);
    this.toggle = this.toggle.bind(this);
  }

  setTitle(title) {
    this.setState({ title: title });
  }

  setDescription(description) {
    this.setState({ description: description });
  }

  toggle() {
    this.setState({
      show: !this.state.show,
      title: null,
      description: null,
      id: null,
      status: 'OPEN'
    });
  }

  save() {
    let task = { id: this.state.id, description: this.state.description, title: this.state.title, status: 'OPEN' };
    let action = task.id ? axios.put : axios.post;
    action('/tasks/', task)
      .then(response => {
        if (response.status === 200) {
          this.toggle();
        } else {
          alert('Server error');
          console.log(response);
        }
      }).catch(error => {
        alert('Server error');
        console.log(error);
      });
  }

  render() {
    return (
      <div>
        <Button color="primary" onClick={this.toggle}>New task</Button>
        <Modal isOpen={this.state.show} toggle={this.toggle} className="new-task">
          <ModalHeader toggle={this.toggle}>Add a new task</ModalHeader>
          <ModalBody>
            <Form>
              <Input type="text" onChange={({ target }) => this.setState({ title: target.value })} name="title" id="title" placeholder="Title" value={this.state.title} />
              <Input type="text" onChange={({ target }) => this.setState({ description: target.value })} name="description" id="description" placeholder="Description" value={this.state.description} />
            </Form>
          </ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={this.save}>Save</Button>{' '}
            <Button color="secondary" onClick={this.toggle}>Cancel</Button>
          </ModalFooter>
        </Modal>
      </div>
    );
  }
}

export default FormTask;
