import { Card, Button, Input, Table, Form, Row, Col, Modal, message, Icon, Popconfirm } from 'antd';
import React, { Component } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import { FormComponentProps } from 'antd/lib/form/Form';
import styles from './styles.less';
import { Dispatch } from 'redux';

const FormItem = Form.Item;

interface UserListProps extends FormComponentProps {
  data: Array<any>;
  total: number;
  loading: boolean;
  dispatch: Dispatch<any>;
}

@connect(({ account, loading }: any) => ({
  loading: loading.models.account,
  data: account.data,
  total: account.total,
}))
class UserList extends Component<UserListProps> {
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
      type: 'account/fetchList',
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
      type: 'account/fetchList',
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
          type: 'account/fetchList',
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
          type: 'account/fetchList',
          payload: { ...formValues, pageNum: current, pageSize: size },
        });
      },
    );
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
            type: 'account/fetchList',
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
          type: 'account/fetchList',
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
            <FormItem label="姓名">
              {getFieldDecorator('userName')(<Input placeholder="请输入姓名" />)}
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

  //删除流程
  deleteUser = (id: string) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'account/deleteProcessInstanceById',
      payload: {
        processInstanceId: id,
      },
      callback: this.callback,
    });
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
        width: 200,
        render: (text: string, record: any) => (
          <span>
            <a onClick={() => this.processImage(record.processInstanceId)}>跟踪</a>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <Popconfirm
              title="删除吗?"
              icon={<Icon type="question-circle-o" style={{ color: 'red' }} />}
              onConfirm={() => this.deleteUser(record.id)}
            >
              <a>删除</a>
            </Popconfirm>
          </span>
        ),
      },
      {
        title: '工号',
        width: 100,
        dataIndex: 'id',
      },
      {
        title: '所属组',
        width: 100,
        dataIndex: 'groups',
      },
      {
        title: '姓名',
        width: 100,
        dataIndex: 'firstName',
      },
      {
        title: '邮箱',
        dataIndex: 'email',
        width: 100,
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
export default Form.create<UserListProps>()(UserList);
