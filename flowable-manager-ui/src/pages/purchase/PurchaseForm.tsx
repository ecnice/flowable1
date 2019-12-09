import { Modal, Form, Input, DatePicker, InputNumber } from 'antd';
import React, { PureComponent } from 'react';
import moment from 'moment/moment';
import { FormComponentProps } from 'antd/es/form';
import TextArea from 'antd/lib/input/TextArea';

const FormItem = Form.Item;

//定义接口
interface IProps extends FormComponentProps {
  loading: boolean;
  modalVisible: boolean;
  record: any;
  handleOk: any;
  handleModalVisible: any;
  modalTitle: string;
}
//定义组件
class PurchaseForm extends PureComponent<IProps, any> {
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
    //验证
    const okHandle = () => {
      form.validateFields((err: any, panelValue: any) => {
        debugger;
        if (err) return;
        panelValue = {
          ...panelValue,
          applyTime: moment(panelValue.applyTime).format('YYYY-MM-DD HH:mm'),
        };
        record == null ? handleOk.add(panelValue, resetForm) : handleOk.edit(panelValue, resetForm);
      });
    };
    const formItems = () => {
      const id = record ? record.id : null;
      form.getFieldDecorator('id', {
        initialValue: id,
      });
      return [
        <FormItem key="title" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }} label="标题">
          {form.getFieldDecorator('title', {
            initialValue: record ? record.title : '',
            rules: [{ required: true, message: '请输入标题' }],
          })(<Input style={{ width: 200 }} placeholder="请输入标题" />)}
        </FormItem>,
        <FormItem key="applyTime" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }} label="申请时间">
          {form.getFieldDecorator('applyTime', {
            initialValue: record ? moment(record.applyTime) : '',
            rules: [{ required: true, message: '请选择申请时间' }],
          })(<DatePicker style={{ width: 200 }} format="YYYY-MM-DD" />)}
        </FormItem>,
        <FormItem key="money" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }} label="金额">
          {form.getFieldDecorator('money', {
            initialValue: record ? record.money : 0,
            rules: [{ required: true, message: '请输入金额' }],
          })(<InputNumber style={{ width: 200 }} min={1} step={0.1} />)}
        </FormItem>,
        <FormItem key="content" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }} label="详情">
          {form.getFieldDecorator('content', {
            initialValue: record ? record.content : '',
            rules: [{ required: true, message: '请输入详情' }],
          })(<TextArea rows={4} style={{ width: 200 }} />)}
        </FormItem>,
      ];
    };
    //关闭按钮
    const onCancel = () => {
      form.resetFields();
      handleModalVisible();
    };
    //从新设置
    const resetForm = () => {
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
export default Form.create<IProps>()(PurchaseForm);
