import { Input, Modal, Table, Tag, message, List, Radio, Row, Col } from 'antd';
import React, { Component } from 'react';
import TextArea from 'antd/lib/input/TextArea';
import { connect } from 'dva';
import styles from './index.less';
import moment from 'moment';

export interface BackStepModalProps {
  onBackStep: (value: object) => void;
  open?: boolean;
  placeholder?: string;
  title?: string;
  approveMsg?: string;
  onCancel: () => void;
  type: string;
  processInstanceId: string;
  taskId: string;
  dispatch?: any;
}

interface BackStepModalState {
  value?: string;
  selectedNodeId?: string;
  approveMsgTemp?: string;
}

@connect(({ formDetail, loading }) => ({
  backStepList: formDetail.backStepList,
  loading: loading.effects['userModel/fetchUserList'],
}))
export default class BackStepModal extends Component<BackStepModalProps, BackStepModalState> {
  state = {
    value: '',
    selectedNodeId: '',
    approveMsgTemp: '',
  };
  componentDidMount() {
    this.doSearch();
  }

  doSearch() {
    const { dispatch, processInstanceId, taskId, approveMsg } = this.props;
    dispatch({
      type: 'formDetail/fetchBackStepList',
      payload: { processInstanceId: processInstanceId, taskId: taskId },
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
    const { onBackStep, type } = this.props;
    const { approveMsgTemp } = this.state;
    if (!this.state.selectedNodeId) {
      message.warn('请选择驳回节点！');
      return;
    } else if (!approveMsgTemp) {
      message.warn('请输入驳回意见！');
      return;
    }
    onBackStep({ nodeId: this.state.selectedNodeId, approveMsg: approveMsgTemp, type: type });
  };

  handleCancel = e => {
    this.setState({
      selectedNodeId: '',
      approveMsgTemp: '',
    });
    const { onCancel } = this.props;
    onCancel();
  };

  onChange(e) {
    this.setState({
      selectedNodeId: e.target.value,
    });
  }

  //在输入框发生变化的时候修改状态的值
  handleMaxRestoreUp = (event: any) => {
    this.setState({ approveMsgTemp: event.target.value });
  };

  render() {
    const { title, open, approveMsg, backStepList } = this.props;
    const { selectedNodeId, selectedRowKeys } = this.state;
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
            <TextArea
              placeholder="请输入驳回意见！"
              onChange={event => this.handleMaxRestoreUp(event)}
              autosize={{ minRows: 3, maxRows: 8 }}
              value={this.state.approveMsgTemp}
            />
          </div>
          <div>
            <Radio.Group onChange={this.onChange.bind(this)} value={this.state.selectedNodeId}>
              <List
                size="small"
                header={<div style={{ width: '640px' }}>选择驳回节点：</div>}
                bordered={false}
                dataSource={backStepList}
                renderItem={item => (
                  <List.Item key={item.nodeId}>
                    <List.Item.Meta
                      avatar={<Radio value={item.nodeId} />}
                      title={<div>{item.nodeName}</div>}
                      description={item.userName}
                    />
                    <div>{moment(item.endTime).format('YYYY-MM-DD HH:mm')}</div>
                  </List.Item>
                )}
              />
            </Radio.Group>
          </div>
        </Modal>
      </>
    );
  }
}
