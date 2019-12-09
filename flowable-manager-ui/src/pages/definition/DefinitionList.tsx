import { Card, Button, Input, Table, Form, Row, Col, Popconfirm, Icon } from 'antd';
import React, { Component } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import { FormComponentProps } from 'antd/lib/form/Form';
import styles from './styles.less';
import { Dispatch } from 'redux';

const FormItem = Form.Item;

interface DefinitionListProps extends FormComponentProps {
  data: Array<any>;
  total: number;
  loading: boolean;
  dispatch: Dispatch<any>;
}

@connect(({ definition, loading }: any) => ({
  loading: loading.models.definition,
  data: definition.data,
  total: definition.total,
}))
class DefinitionList extends Component<DefinitionListProps> {
  state = {
    pageNum: 1,
    pageSize: 10,
    selectedRows: [], //选择项
    selectedRowKeys: [], //选择项（key）
    formValues: {}, //查询数据
  };

  //查询列表
  componentWillMount() {
    const { dispatch } = this.props;
    dispatch({
      type: 'definition/fetchList',
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
      type: 'definition/fetchList',
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
          type: 'definition/fetchList',
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
          type: 'definition/fetchList',
          payload: { ...formValues, pageNum: current, pageSize: size },
        });
      },
    );
  };

  processFile(id: string, type: string) {
    window.open(`/server/rest/definition/processFile/${type}/${id}`);
  }

  //删除流程定义
  deleteDeployment = (deploymentId: string) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'definition/deleteDeployment',
      payload: {
        deploymentId: deploymentId,
      },
      callback: this.callback,
    });
  };
  //挂起激活
  saDefinitionById = (suspensionState: number, id: string) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'definition/saDefinitionById',
      payload: {
        suspensionState: suspensionState,
        id: id,
      },
      callback: this.callback,
    });
  };

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
            type: 'definition/fetchList',
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
          type: 'definition/fetchList',
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
              {getFieldDecorator('name')(<Input placeholder="请输入名称" />)}
            </FormItem>
          </Col>
          <Col md={8}>
            <FormItem label="key">
              {getFieldDecorator('modelKey')(<Input placeholder="请输入key" />)}
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

  render() {
    const { data, loading, total } = this.props;
    const { selectedRowKeys, pageNum } = this.state;
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
        width: 100,
        render: (text: string, record: any) => (
          <span>
            <a onClick={() => this.processFile(record.id, 'xml')}>XML</a>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <a onClick={() => this.processFile(record.id, 'img')}>流程图</a>
            &nbsp;&nbsp;&nbsp;&nbsp;
            {record.suspensionState == 1 ? (
              <Popconfirm
                title="挂起吗?"
                icon={<Icon type="question-circle-o" style={{ color: 'red' }} />}
                onConfirm={() => this.saDefinitionById(record.suspensionState, record.id)}
              >
                <a>挂起</a>
              </Popconfirm>
            ) : (
              <Popconfirm
                title="激活吗?"
                icon={<Icon type="question-circle-o" style={{ color: 'red' }} />}
                onConfirm={() => this.saDefinitionById(record.suspensionState, record.id)}
              >
                <a>激活</a>
              </Popconfirm>
            )}
            &nbsp;&nbsp;&nbsp;&nbsp;
            <Popconfirm
              title="删除吗?"
              icon={<Icon type="question-circle-o" style={{ color: 'red' }} />}
              onConfirm={() => this.deleteDeployment(record.deploymentId)}
            >
              <a>删除</a>
            </Popconfirm>
            &nbsp;&nbsp;&nbsp;&nbsp;
          </span>
        ),
      },
      {
        title: '名称',
        width: 100,
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: 'KEY',
        width: 100,
        dataIndex: 'modelKey',
        key: 'modelKey',
      },
      {
        title: '版本',
        width: 80,
        dataIndex: 'version',
        key: 'version',
      },
      {
        title: '系统',
        width: 80,
        dataIndex: 'tenantId',
        key: 'tenantId',
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
      </PageHeaderWrapper>
    );
  }
}

export default Form.create<DefinitionListProps>()(DefinitionList);
