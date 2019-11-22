import { Card, Button, Modal, Table, message } from 'antd';
import React, { PureComponent } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import moment from 'moment/moment';
import styles from './leaveList.less';
import LeaveListModalForm from './leaveListModalForm';

@connect(({ leave, loading }: any) => ({
  loading: loading.models.leave,
  data: leave.data,
}))
class LeaveList extends PureComponent<any, any> {
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
  handleModalEdit = (flag: boolean, selectedRows: any) => {
    this.setState({
      modalVisible: !!flag,
      modalValue: selectedRows[0],
      modalTitle: '编辑请假申请',
    });
  };
  //修改modal状态
  handleModalVisible = (flag: boolean) => {
    this.setState({
      modalVisible: !!flag,
    });
  };
  //打开新增页面
  handleModalAdd = (flag: boolean) => {
    this.setState({
      modalTitle: '新增请假申请',
      modalValue: null,
      modalVisible: !!flag,
    });
  };
  //添加
  handleAdd = (panel: any, resetform: any) => {
    this.props.dispatch({
      type: 'leave/insert',
      payload: panel,
      callback: this.callback,
      resetform: resetform,
    });
  };
  //修改
  handleEdit = (panel: any, resetform: any) => {
    this.props.dispatch({
      type: 'leave/update',
      payload: panel,
      callback: this.callback,
      resetform: resetform,
    });
  };
  //删除
  handleDel = (records: any) => {
    message.warn('暂时无接口');
  };
  //回掉
  callback = () => {
    const { dispatch } = this.props;
    this.setState({
      selectedRows: [],
      selectedRowKeys: [],
    });
    dispatch({
      type: 'leave/fetch',
    });
  };

  render() {
    const { data, loading } = this.props;
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
        render: (val: any) => (val ? <span>{moment(val).format('YYYY-MM-DD')}</span> : ''),
      },
      {
        title: '结束时间',
        dataIndex: 'endTime',
        key: 'endTime',
        width: 150,
        render: (val: any) => (val ? <span>{moment(val).format('YYYY-MM-DD')}</span> : ''),
      },
      {
        title: '请假天数',
        dataIndex: 'days',
        key: 'days',
        width: 150,
      },
    ];
    const handleDel = this.handleDel;
    return (
      <PageHeaderWrapper title={'请假管理'}>
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
                  我要请假
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
                            handleDel(selectedRows);
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
        <LeaveListModalForm
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
