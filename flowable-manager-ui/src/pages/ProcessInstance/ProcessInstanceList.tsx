import { Card, Button, Input, Table, Form, Row, Col, Modal, message, Icon, Popconfirm } from 'antd';
import React, { Component } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import moment from 'moment';
import { FormComponentProps } from 'antd/lib/form/Form';
import styles from './styles.less';
import { Dispatch } from 'redux';

const FormItem = Form.Item;

interface ProcessInstanceListProps extends FormComponentProps {
  data: Array<any>;
  total: number;
  loading: boolean;
  dispatch: Dispatch<any>;
}

@connect(({ processInstance, loading }: any) => ({
  loading: loading.models.processInstance,
  data: processInstance.data,
  total: processInstance.total,
}))
class ProcessInstanceList extends Component<ProcessInstanceListProps> {
  state = {
    pageNum: 1,
    pageSize: 10,
    selectedRows: [], //选择项
    selectedRowKeys: [], //选择项（key）
    formValues: {}, //查询数据
    showImageModal: false,
    imgSrc: '',
  };

  //查询列表
  componentWillMount() {
    const { dispatch } = this.props;
    dispatch({
      type: 'processInstance/fetchList',
      payload: {
        pageNum: 1,
        pageSize: 10,
      },
    });
  }

  //回掉
  callback = () => {
    const { dispatch } = this.props;
    const { pageNum, pageSize, formValues } = this.state;
    this.setState({
      selectedRows: [],
      selectedRowKeys: [],
    });
    dispatch({
      type: 'processInstance/fetchList',
      payload: { ...formValues, pageNum: pageNum, pageSize: pageSize },
    });
  };
  //分页点击
  changePage = (page: number) => {
    const { dispatch } = this.props;
    const { pageSize, formValues } = this.state;
    this.setState(
      {
        pageNum: page,
      },
      () => {
        dispatch({
          type: 'processInstance/fetchList',
          payload: { ...formValues, pageNum: page, pageSize: pageSize },
        });
      },
    );
  };
  //修改pagesize
  changePageSize = (current: number, size: number) => {
    const { dispatch } = this.props;
    const { formValues } = this.state;
    this.setState(
      {
        pageNum: current,
        pageSize: size,
      },
      () => {
        dispatch({
          type: 'processInstance/fetchList',
          payload: { ...formValues, pageNum: current, pageSize: size },
        });
      },
    );
  };

  processFile(id: string, type: string) {
    window.open(`/server/rest/processInstance/processFile/${type}/${id}`);
  }

  //查询
  handleSearch = (e: any) => {
    e.preventDefault();
    const { dispatch, form } = this.props;
    const { pageSize } = this.state;
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      const values = {
        ...fieldsValue,
        pageSize,
      };
      this.setState(
        {
          formValues: values,
          selectedRows: [],
          selectedRowKeys: [],
        },
        () => {
          dispatch({
            type: 'processInstance/fetchList',
            payload: values,
          });
        },
      );
    });
  };
  //重置
  handleFormReset = () => {
    const { form, dispatch } = this.props;
    form.resetFields();
    this.setState(
      {
        formValues: {},
        selectedRows: [],
        selectedRowKeys: [],
        pageSize: 10,
      },
      () => {
        dispatch({
          type: 'processInstance/fetchList',
          payload: { pageNum: 1, pageSize: 10 },
        });
      },
    );
  };
  renderSearchForm = () => {
    const { getFieldDecorator } = this.props.form;
    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8}>
            <FormItem label="名称">
              {getFieldDecorator('formName')(<Input placeholder="请输入名称" />)}
            </FormItem>
          </Col>
          <Col md={8}>
            <FormItem label="发起人">
              {getFieldDecorator('userName')(<Input placeholder="请输入发起人" />)}
            </FormItem>
          </Col>
          <Col md={8}>
            <span className={styles.submitButtons}>
              <Button type="primary" htmlType="submit">
                查询
              </Button>
              <Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>
                重置
              </Button>
            </span>
          </Col>
        </Row>
      </Form>
    );
  };

  //跟踪流程
  processImage = (processInstanceId: string) => {
    this.setState({
      showImageModal: true,
      imgSrc: '/server/rest/formdetail/image/' + processInstanceId,
    });
  };

  //关闭窗口
  onCancel = () => {
    this.setState({
      showImageModal: false,
      imgSrc: '',
    });
  };

  //删除流程
  deleteProcess = (processInstanceId: string) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'processInstance/deleteProcessInstanceById',
      payload: {
        processInstanceId: processInstanceId,
      },
      callback: this.callback,
    });
  };

  //终止流程
  stopProcess = (processInstanceId: string) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'processInstance/stopProcess',
      payload: {
        processInstanceId: processInstanceId,
      },
      callback: this.callback,
    });
  };
  //挂起和激活流程实例
  saProcessInstanceById = (suspensionState: number, id: string) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'processInstance/saProcessInstanceById',
      payload: {
        suspensionState: suspensionState,
        id: id,
      },
      callback: this.callback,
    });
  };

  render() {
    const { data, loading, total } = this.props;
    const { selectedRowKeys, pageNum, showImageModal, imgSrc } = this.state;
    const paginationProps = {
      showSizeChanger: true,
      showQuickJumper: true,
      current: pageNum,
      total: total,
      showTotal: (total: number) => `共 ${total} 条数据`,
      onChange: this.changePage,
      onShowSizeChange: this.changePageSize,
    };
    const rowSelection: any = {
      selectedRowKeys: selectedRowKeys,
      type: 'radio',
      onChange: (selectedRowKeys: any, selectedRows: any) => {
        this.setState({
          selectedRows: selectedRows,
          selectedRowKeys: selectedRowKeys,
        });
      },
    };

    const columns = [
      {
        title: '操作',
        key: 'action',
        width: 200,
        render: (text: string, record: any) => (
          <span>
            <a onClick={() => this.processImage(record.processInstanceId)}>跟踪</a>
            &nbsp;&nbsp;&nbsp;&nbsp;
            {record.endTime == null ? (
              <Popconfirm
                title="终止吗?"
                icon={<Icon type="question-circle-o" style={{ color: 'red' }} />}
                onConfirm={() => this.stopProcess(record.processInstanceId)}
              >
                <a>终止</a>
              </Popconfirm>
            ) : (
              ''
            )}
            &nbsp;&nbsp;&nbsp;&nbsp;
            {record.endTime == null ? (
              record.suspensionState == 1 ? (
                <Popconfirm
                  title="挂起吗?"
                  icon={<Icon type="question-circle-o" style={{ color: 'red' }} />}
                  onConfirm={() =>
                    this.saProcessInstanceById(record.suspensionState, record.processInstanceId)
                  }
                >
                  <a>挂起</a>
                </Popconfirm>
              ) : (
                <Popconfirm
                  title="激活吗?"
                  icon={<Icon type="question-circle-o" style={{ color: 'red' }} />}
                  onConfirm={() =>
                    this.saProcessInstanceById(record.suspensionState, record.processInstanceId)
                  }
                >
                  <a>激活</a>
                </Popconfirm>
              )
            ) : (
              ''
            )}
            &nbsp;&nbsp;&nbsp;&nbsp;
            <Popconfirm
              title="删除吗?"
              icon={<Icon type="question-circle-o" style={{ color: 'red' }} />}
              onConfirm={() => this.deleteProcess(record.processInstanceId)}
            >
              <a>删除</a>
            </Popconfirm>
          </span>
        ),
      },
      {
        title: '名称',
        width: 100,
        dataIndex: 'formName',
      },
      {
        title: '审批人',
        width: 100,
        dataIndex: 'approver',
      },
      {
        title: '系统',
        dataIndex: 'systemSn',
        width: 100,
      },
      {
        title: '发起人',
        dataIndex: 'starter',
        width: 100,
      },
      {
        title: '发起时间',
        dataIndex: 'startTime',
        width: 100,
        render: (val: any) => (val ? <span>{moment(val).format('YYYY-MM-DD HH:mm')}</span> : ''),
      },
      {
        title: '结束时间',
        dataIndex: 'endTime',
        width: 100,
        render: (val: any) =>
          val ? <span>{moment(val).format('YYYY-MM-DD HH:mm')}</span> : '未结束',
      },
    ];
    return (
      <PageHeaderWrapper title={''}>
        <Card bordered={false}>
          <div style={{ height: '100%' }}>
            <div className={styles.tableListForm}>{this.renderSearchForm()}</div>
            <Table
              bordered
              rowKey="id"
              loading={loading}
              dataSource={data}
              columns={columns}
              pagination={paginationProps}
            />
          </div>
        </Card>
        <Modal
          width={1200}
          bodyStyle={{ padding: '10px 15px 10px' }}
          destroyOnClose
          title="查看图片"
          visible={showImageModal}
          mask={true}
          okButtonProps={{ loading: loading }}
          closable={true}
          footer={null}
          onCancel={this.onCancel}
        >
          <img src={imgSrc} />
        </Modal>
      </PageHeaderWrapper>
    );
  }
}

export default Form.create<ProcessInstanceListProps>()(ProcessInstanceList);
