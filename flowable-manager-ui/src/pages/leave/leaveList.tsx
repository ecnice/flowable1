import { Card, Button, Modal, Table, Form, Input, DatePicker, InputNumber, message } from 'antd';
import React, { Component, PureComponent } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import moment from 'moment/moment';
import styles from './leaveList.less';

const FormItem = Form.Item;

@Form.create()
class ModalForm extends PureComponent {
  render() {
    const { modalVisible, form, record, handleOk, handleModalVisible, modalTitle } = this.props;
    const okHandle = () => {
      form.validateFields((err, panelValue) => {
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
        <FormItem key="name" labelCol={{ span: 5 }} wrapperCol={{ span: 16 }} label="姓名">
          {form.getFieldDecorator('name', {
            initialValue: record ? record.name : '',
            rules: [{ required: true, message: '请输入姓名' }],
          })(<Input width={120} placeholder="请输入姓名" />)}
        </FormItem>,
        <FormItem key="startTime" labelCol={{ span: 5 }} wrapperCol={{ span: 16 }} label="开始时间">
          {form.getFieldDecorator('startTime', {
            initialValue: record ? moment(record.startTime) : '',
            rules: [{ required: true, message: '请选择开始时间' }],
          })(<DatePicker format="YYYY-MM-DD" />)}
        </FormItem>,
        <FormItem key="endTime" labelCol={{ span: 5 }} wrapperCol={{ span: 16 }} label="结束时间">
          {form.getFieldDecorator('endTime', {
            initialValue: record ? moment(record.endTime) : '',
            rules: [{ required: true, message: '请选择结束时间' }],
          })(<DatePicker format="YYYY-MM-DD" />)}
        </FormItem>,
        <FormItem key="days" labelCol={{ span: 5 }} wrapperCol={{ span: 16 }} label="请假天数">
          {form.getFieldDecorator('days', {
            initialValue: record ? record.days : 0,
            rules: [{ required: true, message: '请输入请假天数' }],
          })(<InputNumber min={0} />)}
        </FormItem>,
      ];
    };
    const onCancel = () => {
      form.resetFields();
      handleModalVisible();
    };
    return (
      <Modal
        width={650}
        bodyStyle={{ padding: '10px 15px 10px' }}
        destroyOnClose
        title={modalTitle}
        visible={modalVisible}
        closable={true}
        onCancel={onCancel}
        onOk={okHandle}
      >
        {formItems()}
      </Modal>
    );
  }
}

@connect(({ pm, loading }) => ({
  loading: loading.models.leave,
  data: pm.data,
}))
class LeaveList extends Component {
  state = {
    modalVisible: false, //弹框实现隐藏状态
    modalValue: null, //修改数据
    modalTitle: '', //弹框名称
    selectedRows: [], //选择项
    selectedRowKeys: [], //选择项（key）
  };

  //查询列表
  componentWillMount() {
    const { dispatch } = this.props;
    dispatch({
      type: 'leave/fetch',
    });
  }

  //打开编辑页面
  handleModalEdit = (flag, selectedRows) => {
    this.setState({
      modalVisible: !!flag,
      modalValue: selectedRows[0],
      modalTitle: '编辑请假申请',
    });
  };
  //修改modal状态
  handleModalVisible = flag => {
    this.setState({
      modalVisible: !!flag,
    });
  };
  //打开新增页面
  handleModalAdd = flag => {
    this.setState({
      modalTitle: '新增请假申请',
      modalValue: null,
      modalVisible: !!flag,
    });
  };
  //添加
  handleAdd = (panel, resetForm) => {
    this.props.dispatch({
      type: 'leave/insert',
      payload: panel,
      callback: this.callback,
      resetForm: resetForm,
    });
  };
  //修改
  handleEdit = (panel, resetForm) => {
    this.props.dispatch({
      type: 'leave/update',
      payload: panel,
      callback: this.callback,
      resetForm: resetForm,
    });
  };
  //删除
  handleDel = records => {
    message.warn('暂时无接口');
  };
  //回掉
  callback = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'leave/fetch',
    });
  };

  render() {
    const { data, loading } = this.props;
    debugger;
    const { selectedRows, selectedRowKeys, modalVisible, modalTitle, modalValue } = this.state;
    const parentMethods = {
      handleOk: { add: this.handleAdd, edit: this.handleEdit },
      handleModalVisible: this.handleModalVisible,
    };
    const paginationProps = {
      defaultPageSize: 5,
      hideOnSinglePage: true,
      showSizeChanger: true,
      showQuickJumper: true,
    };
    const rowSelection = {
      selectedRowKeys: selectedRowKeys,
      type: 'radio',
      onChange: (selectedRowKeys, selectedRows) => {
        this.setState({
          selectedRows: selectedRows,
          selectedRowKeys: selectedRowKeys,
        });
      },
    };
    const columns = [
      {
        title: '姓名',
        width: 100,
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '开始时间',
        width: 100,
        dataIndex: 'startTime',
        key: 'startTime',
        render: val => (val ? <span>{moment(val).format('YYYY-MM-DD')}</span> : ''),
      },
      {
        title: '结束时间',
        dataIndex: 'endTime',
        key: 'endTime',
        width: 150,
        render: val => (val ? <span>{moment(val).format('YYYY-MM-DD')}</span> : ''),
      },
      {
        title: '请假天数',
        dataIndex: 'days',
        key: 'days',
        width: 150,
      },
    ];
    return (
      <PageHeaderWrapper title={}>
        <Card bordered={false}>
          <div className={styles.tableList} style={{ height: '100%' }}>
            <div className={styles.tableListOperator}>
              <span>
                <Button
                  icon="plus-circle"
                  type="primary"
                  loading={loading}
                  onClick={() => this.handleModalAdd(true)}
                >
                  新建
                </Button>
                {selectedRows.length == 1 ? (
                  <span>
                    <Button
                      icon="edit"
                      loading={loading}
                      onClick={() => this.handleModalEdit(true, selectedRows)}
                    >
                      编辑
                    </Button>
                    <Button
                      icon="close-circle"
                      loading={loading}
                      type="danger"
                      onClick={() =>
                        Modal.confirm({
                          content: '确认删除这条记录么？',
                          cancelText: '取消',
                          okText: '确认',
                          onOk() {
                            this.handleDel(selectedRows);
                          },
                        })
                      }
                    >
                      删除
                    </Button>
                  </span>
                ) : (
                  ''
                )}
              </span>
            </div>
            <Table
              size="small"
              bordered
              rowKey="id"
              loading={loading}
              dataSource={data}
              columns={columns}
              rowSelection={rowSelection}
              pagination={paginationProps}
            />
          </div>
        </Card>
        <ModalForm
          {...parentMethods}
          modalVisible={modalVisible}
          record={modalValue}
          modalTitle={modalTitle}
          loading={loading}
        />
      </PageHeaderWrapper>
    );
  }
}

export default LeaveList;
