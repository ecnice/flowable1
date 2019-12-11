import { Input, Modal, Table, Tag, message } from 'antd';
import React, { Component } from 'react';
import Search from 'antd/lib/input/Search';
import TextArea from 'antd/lib/input/TextArea';
import { connect } from 'dva';
import styles from './index.less';

export interface ApproveModalProps {
  onApprove: (value: object) => void;
  open?: boolean;
  placeholder?: string;
  title?: string;
  multiSelect?: boolean;
  approveMsg?: string;
  onCancel: () => void;
  type: string;
  dispatch?: any;
}

interface ApproveModalState {
  value?: string;
  searchMode: boolean;
  selectedRows?: Array<object>;
  selectedRowKeys?: Array<string>;
  approveMsgTemp?: string;
  searchKey?: string;
}

@connect(({ userModel, loading }) => ({
  userList: userModel.userList,
  loading: loading.effects['userModel/fetchUserList'],
}))
export default class ApproveModal extends Component<ApproveModalProps, ApproveModalState> {
  state = {
    value: '',
    searchMode: true,
    selectedRows: [],
    selectedRowKeys: [],
    approveMsgTemp: '',
    searchKey: '',
  };

  componentDidMount() {
    this.doSearch();
  }

  doSearch() {
    const { dispatch, approveMsg } = this.props;
    dispatch({
      type: 'userModel/fetchUserList',
      payload: { keyword: this.state.searchKey },
    });
    var msg = JSON.parse(JSON.stringify(approveMsg));
    this.setState({
      approveMsgTemp: msg,
    });
  }

  onKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      this.doSearch();
    }
  };

  handleOk = e => {
    const { onApprove, type } = this.props;
    const { approveMsgTemp, selectedRows } = this.state;
    debugger;
    if (selectedRows.length == 0) {
      message.warn('请选择人员！');
      return;
    } else if (!approveMsgTemp) {
      message.warn('请输入意见！');
      return;
    }
    onApprove({ users: this.state.selectedRows, approveMsg: approveMsgTemp, type: type });
  };

  handleCancel = e => {
    this.setState({
      selectedRowKeys: [],
      selectedRows: [],
      approveMsgTemp: '',
    });
    const { onCancel } = this.props;
    onCancel();
  };

  deleteSlectedRow = (removedTag: any) => {
    const rows = this.state.selectedRows.filter(tag => tag.userId !== removedTag);
    const rowKeys = this.state.selectedRowKeys.filter(key => key !== removedTag);
    this.setState({ selectedRows: rows, selectedRowKeys: rowKeys });
  };

  //在输入框发生变化的时候修改状态的值
  handleMaxRestoreUp = (event: any) => {
    this.setState({ approveMsgTemp: event.target.value });
  };

  render() {
    const { title, userList, multiSelect, open, approveMsg } = this.props;
    const { selectedRows, selectedRowKeys } = this.state;

    const columns = [
      {
        title: '工号',
        dataIndex: 'userId',
      },
      {
        title: '用户名',
        dataIndex: 'userName',
      },
      {
        title: '邮箱',
        dataIndex: 'email',
      },
    ];
    const rowSelection: any = {
      selectedRowKeys: selectedRowKeys,
      type: multiSelect ? 'checkbox' : 'radio',
      onChange: (selectedRowKeys: any, selectedRows: any) => {
        this.setState({
          selectedRows: selectedRows,
          selectedRowKeys: selectedRowKeys,
        });
      },
    };
    // @ts-ignore
    return (
      <>
        <Modal
          width={700}
          title={title}
          visible={open}
          onOk={this.handleOk}
          onCancel={this.handleCancel}
        >
          <div className={styles.lineItem}>
            <Search
              placeholder="姓名"
              onSearch={value => {
                this.setState({ searchKey: value });
              }}
              enterButton
            />
          </div>
          <div>
            <Table
              rowKey="userId"
              size="small"
              rowSelection={rowSelection}
              columns={columns}
              dataSource={userList}
            />
          </div>
          <div className={styles.lineItem}>
            {selectedRows &&
              selectedRows.map(item => (
                <Tag
                  rowKey={item.userId}
                  closable
                  onClose={() => this.deleteSlectedRow(item.userId)}
                >
                  {item.userName}
                </Tag>
              ))}
          </div>
          <div>
            <TextArea
              onChange={event => this.handleMaxRestoreUp(event)}
              autosize={{ minRows: 3, maxRows: 8 }}
              value={this.state.approveMsgTemp}
            />
          </div>
        </Modal>
      </>
    );
  }
}
