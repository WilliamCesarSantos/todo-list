import React, { Component } from 'react';
import { Card, CardText, CardTitle, Button, Col } from 'reactstrap';
const axios = require('axios');

class Cards extends Component {

    constructor(props) {
        super(props);
        this.delete = this.delete.bind(this);
    }

    delete({ target }) {
        axios.delete('/tasks/' + target.value)
            .then(response => {
                if (response.status !== 204) {
                    alert('Server error');
                    console.log(response);
                }
            }).catch(error => {
                alert('Server error');
                console.log(error);
            });
    }

    createCard = () => {
        return this.props.tasks.map((task, index) => {
            let isOpen = task.status === 'OPEN';
            let color = isOpen ? 'success' : 'link';
            let text = isOpen ? 'Finish' : 'Unfinish';

            let click = function () {
                axios.put('/tasks/' + task.id, {
                    id: task.id,
                    title: task.title,
                    description: task.description,
                    status: isOpen ? "CLOSE" : "OPEN"
                }).then(response => {
                    if (response.status !== 200) {
                        alert("Server error");
                        console.log(response);
                    }
                }).catch(error => {
                    alert('Server error');
                    console.log(error);
                });
            }
            return (<Col sm="2">
                <Card>
                    <Card body>
                        <CardTitle>{task.title}</CardTitle>
                        <CardText>{task.description}</CardText>
                        <Button onClick={click} color={color} >{text}</Button>
                        <Button onClick={this.delete} color="danger" value={task.id}>Delete</Button>
                    </Card>
                </Card>
            </Col>)
        })
    }

    render() {

        return (
            this.createCard()
        );
    }
}

export default Cards;
