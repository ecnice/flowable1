import { Card, Table } from 'antd';
import React, { Component } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
// import { connect } from 'dva';

class LeaveList extends Component {
  callback(key) {
    console.log(key);
  }
  render() {
    // const { modules } = this.props;
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
      },
      {
        title: '结束时间',
        dataIndex: 'endTime',
        key: 'endTime',
        width: 150,
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
        <Card></Card>
      </PageHeaderWrapper>
    );
  }
}

export default LeaveList;
