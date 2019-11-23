import { Tabs, Card, Table } from 'antd';
import React, { PureComponent } from 'react';

const { TabPane } = Tabs;
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import moment from 'moment';

@connect(({ tasks, loading }: any) => ({
  loading: loading.models.tasks,
  applyingTasks: tasks.applyingTasks,
  applyedTasks: tasks.applyedTasks,
  myProcessInstances: tasks.myProcessInstances,
  total: tasks.total,
}))
class TaskList extends PureComponent<any, any> {
  state = {
    key: '1',
    pageIndex: 1,
    formName: '',
  };
  //查询待办
  applying = (payload: any) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'tasks/fetchApplyingTasks',
      payload: payload,
    });
    debugger;
  };
  //查询已办
  applyed = (payload: any) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'tasks/fetchApplyedTasks',
      payload: payload,
    });
  };
  //查询我的流程
  myProcessInstance = (payload: any) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'tasks/fetchMyProcessInstancesTasks',
      payload: payload,
    });
    debugger;
  };

  //tabs切换
  onChange = (key: string) => {
    this.setState({
      key: key,
      pageIndex: 0,
      formName: '',
    });
    switch (key) {
      case '1': {
        this.applying({ pageIndex: 0 });
        break;
      }
      case '2': {
        this.applyed({ pageIndex: 0 });
        break;
      }
      case '3': {
        this.myProcessInstance({ pageIndex: 0 });
        break;
      }
      default: {
      }
    }
  };

  render() {
    const { loading, applyingTasks, applyedTasks, myProcessInstances, total } = this.props;
    const applyingColumns = [
      {
        title: '名称',
        width: 100,
        dataIndex: 'formName',
        key: 'formName',
      },
      {
        title: '任务名称',
        width: 100,
        dataIndex: 'taskName',
      },
      {
        title: '系统',
        width: 100,
        dataIndex: 'systemSn',
      },
      {
        title: '创建时间',
        dataIndex: 'startTime',
        width: 150,
        render: (val: any) => (val ? <span>{moment(val).format('YYYY-MM-DD HH:mm')}</span> : ''),
      },
    ];
    const applyedColumns = [
      {
        title: '名称',
        width: 100,
        dataIndex: 'formName',
        key: 'formName',
      },
      {
        title: '任务名称',
        width: 100,
        dataIndex: 'taskName',
      },
      {
        title: '系统',
        width: 100,
        dataIndex: 'systemSn',
      },
      {
        title: '创建时间',
        dataIndex: 'startTime',
        width: 150,
        render: (val: any) => (val ? <span>{moment(val).format('YYYY-MM-DD HH:mm')}</span> : ''),
      },
      {
        title: '处理时间',
        dataIndex: 'endTime',
        width: 150,
        render: (val: any) => <span>{moment(val).format('YYYY-MM-DD HH:mm')}</span>,
      },
    ];
    const myProcessInstancesColumns = [
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
        width: 150,
      },
      {
        title: '发起时间',
        dataIndex: 'startTime',
        width: 150,
        render: (val: any) => (val ? <span>{moment(val).format('YYYY-MM-DD HH:mm')}</span> : ''),
      },
      {
        title: '结束时间',
        dataIndex: 'endTime',
        width: 150,
        render: (val: any) =>
          val ? <span>{moment(val).format('YYYY-MM-DD HH:mm')}</span> : '未结束',
      },
    ];
    return (
      <PageHeaderWrapper title={''}>
        <Card>
          <div className="card-container">
            <Tabs type="card" defaultActiveKey={'1'} onChange={this.onChange}>
              <TabPane tab="待办任务" key="1">
                <Table
                  loading={loading}
                  rowKey="id"
                  columns={applyingColumns}
                  dataSource={applyingTasks}
                />
              </TabPane>
              <TabPane tab="已办任务" key="2">
                <Table
                  loading={loading}
                  rowKey="id"
                  columns={applyedColumns}
                  dataSource={applyedTasks}
                />
              </TabPane>
              <TabPane tab="我发起任务" key="3">
                <Table
                  loading={loading}
                  rowKey="id"
                  columns={myProcessInstancesColumns}
                  dataSource={myProcessInstances}
                />
              </TabPane>
            </Tabs>
          </div>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default TaskList;
