import {
    Form,
    Input,
    Tooltip,
    Icon,
    Select,
    Button,
  } from 'antd';
import React from "react"

  
const { Option } = Select;
  
class DetailsForm extends React.Component {

handleSubmit = e => {
    this.props.form.validateFieldsAndScroll((err, values) => {
    if (!err) {
        console.log('Received values of form: ', values);
    }
    });
};

onChange(value) {
    // this.setState({gender:value});
    console.log(value);
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
        <Select
            showSearch
            style={{ width: 200 }}
            placeholder="Select a gender"
            optionFilterProp="children"
            onChange={this.onChange}
            filterOption={(input, option) =>
                option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
        >
            <Option value="M">Male</Option>
            <Option value="F">Female</Option>
            <Option value="O">Other</Option>
        </Select>
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