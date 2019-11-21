import { Card, Table } from 'antd';
import React, { Component } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';

class LeaveList extends Component {
  callback(key) {
    console.log(key);
  }
  render() {
    const { modules } = this.props;
    const columns = [
      {
        title: '名称',
        width: 100,
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: 'Key',
        width: 100,
        dataIndex: 'key',
        key: 'key',
      },
      {
        title: '系统',
        dataIndex: 'tenantId',
        key: 'tenantId',
        width: 150,
      },
      {
        title: '描述',
        dataIndex: 'comment',
        key: 'comment',
        width: 150,
      },
    ];
    return (
      <PageHeaderWrapper title={}>
        <Card>
          <Table rowKey="id" columns={columns} dataSource={modules} />
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default LeaveList;
