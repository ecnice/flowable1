import { Card, Button, Input, Table, Form, Row, Col, Modal, message, Icon, Popconfirm } from 'antd';
import React, { Component } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import { FormComponentProps } from 'antd/lib/form/Form';
import styles from './styles.less';
import { Dispatch } from 'redux';
import moment from 'moment';
import PurchaseForm from '@/pages/purchase/PurchaseForm';

const FormItem = Form.Item;

/**
 * 定义一个props的form接口
 */
interface PurchaseListProps extends FormComponentProps {
  data: Array<any>;
  total: number;
  loading: boolean;
  dispatch: Dispatch<any>;
}

/**
 * 连接model
 */
@connect(({ purchase, loading }: any) => ({
  loading: loading.models.loading,
  total: purchase.total,
  data: purchase.data,
}))
class PurchaseList extends Component<PurchaseListProps> {
  //组件的state
  state = {
    pageNum: 1,
    pageSize: 10,
    selectedRows: [], //选择项
    selectedRowKeys: [], //选择项（key）
    formValues: {}, //查询数据
    modalVisible: false, //弹框显示隐藏状态
    modalValue: null, //修改数据
    modalTitle: '', //弹框名称
  };

  //组件渲染先把数据查询出来
  componentWillMount() {
    const { dispatch } = this.props;
    dispatch({
      type: 'purchase/fetchList',
      payload: {
        pageNum: this.state.pageNum,
        pageSize: this.state.pageSize,
      },
    });
  }

  //修改页码
  changePage = (pageNum: number) => {
    const { dispatch } = this.props;
    const { pageSize, formValues } = this.state;
    this.setState(
      {
        pageNum: pageNum,
      },
      () => {
        dispatch({
          type: 'purchase/fetchList',
          payload: {
            ...formValues,
            pageNum: pageNum,
            pageSize: pageSize,
          },
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
          type: 'purchase/fetchList',
          payload: { ...formValues, pageNum: current, pageSize: size },
        });
      },
    );
  };

  //回调函数
  callback = () => {
    const { dispatch } = this.props;
    const { pageNum, pageSize } = this.state;
    this.setState({
      selectedRows: [],
      selectedRowKeys: [],
    });
    dispatch({
      type: 'purchase/fetchList',
      payload: { pageNum: pageNum, pageSize: pageSize },
    });
  };

  //修改
  handleEdit = (panel: any, resetform: any) => {
    const { dispatch } = this.props;
    debugger;
    dispatch({
      type: 'purchase/fetchUpdate',
      payload: panel,
      callback: this.callback,
      resetform: resetform,
    });
  };

  //删除
  handleDel = (records: any) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'purchase/fetchDelete',
      payload: records,
      callback: this.callback,
    });
  };

  //添加
  handleAdd = (panel: any, resetform: any) => {
    const { dispatch } = this.props;
    debugger;
    dispatch({
      type: 'purchase/fetchAdd',
      payload: panel,
      callback: this.callback,
      resetform: resetform,
    });
  };

  //修改modal状态
  handleModalVisible = (flag: boolean) => {
    this.setState({
      modalVisible: !!flag,
    });
  };

  //打开添加界面
  handleModalAdd = (flag: boolean) => {
    this.setState({
      modalTitle: '采购申请',
      modalValue: null,
      modalVisible: !!flag,
    });
  };

  //打开编辑页面
  handleModalEdit = (flag: boolean, selectedRows: any) => {
    this.setState({
      modalVisible: !!flag,
      modalValue: selectedRows[0],
      modalTitle: '采购申请',
    });
  };

  //组件渲染
  render() {
    //获取props的数据
    const { loading, total, data } = this.props;
    //获取state的数据
    const {
      pageNum,
      pageSize,
      selectedRowKeys,
      selectedRows,
      modalVisible,
      modalValue,
      modalTitle,
    } = this.state;
    //列头
    const columns = [
      {
        title: '标题',
        width: 100,
        dataIndex: 'title',
      },
      {
        title: '金额',
        width: 50,
        dataIndex: 'money',
      },
      {
        title: '申请时间',
        width: 100,
        dataIndex: 'applyTime',
        render: (val: any) => (val ? <span>{moment(val).format('YYYY-MM-DD HH:mm')}</span> : ''),
      },
      {
        title: '详情',
        width: 400,
        dataIndex: 'content',
      },
    ];
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
      type: 'checkbox',
      columnWidth: 20,
      onChange: (selectedRowKeys: any, selectedRows: any) => {
        this.setState({
          selectedRows: selectedRows,
          selectedRowKeys: selectedRowKeys,
        });
      },
    };

    const parentMethods = {
      handleOk: { add: this.handleAdd, edit: this.handleEdit },
      handleModalVisible: this.handleModalVisible,
    };

    const handleDel = this.handleDel;
    //返回渲染的页面内容
    return (
      <PageHeaderWrapper title={''}>
        <Card bordered={false}>
          <div style={{ height: '100%' }}>
            <div className={styles.tableListOperator}>
              <span>
                <Button
                  icon="plus-circle"
                  type="primary"
                  loading={loading}
                  onClick={() => this.handleModalAdd(true)}
                >
                  我要采购
                </Button>
                {selectedRows.length >= 1 ? (
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
                          content: '确认删除？',
                          cancelText: '取消',
                          okText: '确认',
                          onOk() {
                            handleDel(selectedRowKeys);
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
              bordered
              rowKey="id"
              loading={loading}
              dataSource={data}
              columns={columns}
              rowSelection={rowSelection}
              pagination={paginationProps}
              size="small"
            />
          </div>
        </Card>
        <PurchaseForm
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
export default PurchaseList;
