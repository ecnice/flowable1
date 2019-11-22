import { Modal, Form, Input, DatePicker, InputNumber } from 'antd';
import React, { PureComponent } from 'react';
import moment from 'moment/moment';
import { FormComponentProps } from 'antd/es/form';

const FormItem = Form.Item;

interface IProps extends FormComponentProps {
  loading: boolean;
  modalVisible: boolean;
  record: any;
  handleOk: any;
  handleModalVisible: any;
  modalTitle: string;
}

class LeaveListModalForm extends PureComponent<IProps, any> {
  render() {
    const {
      modalVisible,
      form,
      record,
      handleOk,
      handleModalVisible,
      modalTitle,
      loading,
    } = this.props;
    const okHandle = () => {
      form.validateFields((err: any, panelValue: any) => {
        if (err) return;
        panelValue = {
          ...panelValue,
          startTime: moment(panelValue.startTime).format('YYYY-MM-DD'),
          endTime: moment(panelValue.endTime).format('YYYY-MM-DD'),
        };
        record == null ? handleOk.add(panelValue, resetForm) : handleOk.edit(panelValue, resetForm);
      });
    };
    const resetForm = () => {
      form.resetFields();
      handleModalVisible();
    };
    const formItems = () => {
      const id = record ? record.id : null;
      form.getFieldDecorator('id', {
        initialValue: id,
      });
      return [
        <FormItem key="name" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }} label="姓名">
          {form.getFieldDecorator('name', {
            initialValue: record ? record.name : '',
            rules: [{ required: true, message: '请输入姓名' }],
          })(<Input style={{ width: 170 }} placeholder="请输入姓名" />)}
        </FormItem>,
        <FormItem key="startTime" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }} label="开始时间">
          {form.getFieldDecorator('startTime', {
            initialValue: record ? moment(record.startTime) : '',
            rules: [{ required: true, message: '请选择开始时间' }],
          })(<DatePicker format="YYYY-MM-DD" />)}
        </FormItem>,
        <FormItem key="endTime" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }} label="结束时间">
          {form.getFieldDecorator('endTime', {
            initialValue: record ? moment(record.endTime) : '',
            rules: [{ required: true, message: '请选择结束时间' }],
          })(<DatePicker format="YYYY-MM-DD" />)}
        </FormItem>,
        <FormItem key="days" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }} label="请假天数">
          {form.getFieldDecorator('days', {
            initialValue: record ? record.days : 0,
            rules: [{ required: true, message: '请输入请假天数' }],
          })(<InputNumber style={{ width: 170 }} min={0} />)}
        </FormItem>,
      ];
    };
    const onCancel = () => {
      form.resetFields();
      handleModalVisible();
    };
    return (
      <Modal
        width={400}
        bodyStyle={{ padding: '10px 15px 10px' }}
        destroyOnClose
        title={modalTitle}
        visible={modalVisible}
        okButtonProps={{ loading: loading }}
        closable={true}
        onCancel={onCancel}
        onOk={okHandle}
      >
        {formItems()}
      </Modal>
    );
  }
}

export default Form.create<IProps>()(LeaveListModalForm);
