import { Tabs, Card, Table } from 'antd';
import React, { Component } from 'react';
const { TabPane } = Tabs;
import { PageHeaderWrapper } from '@ant-design/pro-layout';

class TaskList extends Component {
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
          <div className="card-container">
            <Tabs type="card">
              <TabPane tab="待办任务" key="1">
                <Table rowKey="id" columns={columns} dataSource={modules} />
              </TabPane>
              <TabPane tab="已办任务" key="2">
                <Table rowKey="id" columns={columns} dataSource={modules} />
              </TabPane>
              <TabPane tab="我发起任务" key="3">
                <Table rowKey="id" columns={columns} dataSource={modules} />
              </TabPane>
            </Tabs>
          </div>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default TaskList;
