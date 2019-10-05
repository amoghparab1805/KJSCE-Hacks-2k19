import {
    Form,
    Input,
    Tooltip,
    Icon,
    Select,
    Button,
  } from 'antd';
import React from "react"
import axios from "axios"

const { Option } = Select;

class DetailsForm extends React.Component {

constructor() {
    super()
    this.state = {
        gender: 'M',
    }
    this.onChange = this.onChange.bind(this)
}

handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((err, values) => {
    if (!err) {
        axios.post("https://red-hat-pirates.herokuapp.com/api/update-user/", 
          JSON.stringify({
            displayName: values['display_name'],
            uid: localStorage.getItem("uid"),
            age: values['age'],
            gender: this.state.gender
          }), 
          { headers: {"Content-Type": "application/json"} }
        ).then(response => {
          console.log(response.data)
          localStorage.setItem("isNewUser", false)
          window.location.reload()
        })
    }
    });
};

onChange(e) {
    this.setState({gender:e.target.value});
}

render() {
    const { getFieldDecorator } = this.props.form;

    const formItemLayout = {
    labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
    },
    wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
    },
    };
    const tailFormItemLayout = {
    wrapperCol: {
        xs: {
        span: 24,
        offset: 0,
        },
        sm: {
        span: 16,
        offset: 8,
        },
    },
    };

    return (
    <Form {...formItemLayout} onSubmit={this.handleSubmit}>

        <Form.Item
        label={
            <span>
            Display Name&nbsp;
            <Tooltip title="What do you want others to call you?">
                <Icon type="question-circle-o" />
            </Tooltip>
            </span>
        }
        >
        {getFieldDecorator('display_name', {
            rules: [{ required: true, message: 'Please input your display name!', whitespace: true }],
        })(<Input />)}
        </Form.Item>

        <Form.Item
        label={
            <span>
            Age&nbsp;
            <Tooltip title="Your Age?">
                <Icon type="question-circle-o" />
            </Tooltip>
            </span>
        }
        >
        {getFieldDecorator('age', {
            rules: [{ required: true, message: 'Please input your age!', whitespace: true }],
        })(<Input />)}
        </Form.Item>

        <Form.Item
        label={
            <span>
            Gender&nbsp;
            <Tooltip title="Your Gender?">
                <Icon type="question-circle-o" />
            </Tooltip>
            </span>
        }
        >
        <select
            style={{ width: 200 }}
            placeholder="Select a gender"
            name="gender"
            value={this.state.gender}
            onChange={this.onChange}
        >
            <option value="M">Male</option>
            <option value="F">Female</option>
            <option value="O">Other</option>
        </select>
        </Form.Item>
        
        
        
        <Form.Item {...tailFormItemLayout}>
            <Button type="primary" htmlType="submit">
                Register
            </Button>
        </Form.Item>
    </Form>
    );
}
}

const WrappedDetailsForm = Form.create({ name: 'register' })(DetailsForm);

export default WrappedDetailsForm;