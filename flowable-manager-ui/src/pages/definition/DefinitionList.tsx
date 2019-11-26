import { Card, Button, Input, Table, Form } from 'antd';
import React, { PureComponent } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
const FormItem = Form.Item;
@connect(({ definition, loading }: any) => ({
  loading: loading.models.definition,
  data: definition.data,
  total: definition.total,
}))
class DefinitionList extends PureComponent<any, any> {
  state = {
    pageNum: 1,
    pageSize: 10,
    selectedRows: [], //选择项
    selectedRowKeys: [], //选择项（key）
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
    const { pageNum, pageSize } = this.state;
    this.setState({
      selectedRows: [],
      selectedRowKeys: [],
    });
    dispatch({
      type: 'leave/fetch',
      payload: { pageNum: pageNum, pageSize: pageSize },
    });
  };
  //分页点击
  changePage = (page: number) => {
    const { dispatch } = this.props;
    const { pageSize } = this.state;
    this.setState(
      {
        pageNum: page,
      },
      () => {
        dispatch({
          type: 'definition/fetchList',
          payload: { pageNum: page, pageSize: pageSize },
        });
      },
    );
  };
  //修改pagesize
  changePageSize = (current: number, size: number) => {
    const { dispatch } = this.props;
    this.setState(
      {
        pageNum: current,
        pageSize: size,
      },
      () => {
        dispatch({
          type: 'definition/fetchList',
          payload: { pageNum: current, pageSize: size },
        });
      },
    );
  };

  processFile(id: string, type: string) {
    window.open(`/server/rest/definition/processFile/${type}/${id}`);
  }

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
    const formItems = () => {
      return [
        <FormItem key="name" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }} label="名称">
          {<Input style={{ width: 170 }} placeholder="请输入姓名" />}
        </FormItem>,
        <FormItem key="modelKey" labelCol={{ span: 8 }} wrapperCol={{ span: 16 }} label="key">
          {<Input style={{ width: 170 }} placeholder="请输入KEY" />}
        </FormItem>,
      ];
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
            <div>
              <span>
                {formItems}
                <Button icon="plus-circle" type="primary" loading={loading}>
                  查询
                </Button>
              </span>
            </div>
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

export default DefinitionList;
