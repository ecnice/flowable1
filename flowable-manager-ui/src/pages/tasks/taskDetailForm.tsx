import {
  Modal,
  Form,
  Input,
  DatePicker,
  Card,
  InputNumber,
  Divider,
  Typography,
  Button,
  Comment,
  Tooltip,
  List,
  Avatar,
  message,
} from 'antd';
import React, { PureComponent } from 'react';
import { FormComponentProps } from 'antd/es/form';
import styles from './style.less';

const FormItem = Form.Item;
const { Title } = Typography;
const { TextArea } = Input;
import moment from 'moment';
import { connect } from 'dva';
import ApproveModal from '@/pages/tasks/components/ApproveModal';
import BackStepModal from '@/pages/tasks/components/BackStepModal';

interface IProps extends FormComponentProps {
  loading?: boolean;
  canHandel?: boolean;
  record?: any;
  formInfo?: any;
  handle?: any;
  modalTitle?: string;
  commentList?: [];
  modalVisible?: boolean;
  dispatch?: any;
  formatCommentList?: [];
  callBack?: Function;
}

@connect(({ formDetail, tasks, loading }: any) => ({
  loading: loading.models.formDetail,
  commentList: formDetail.commentList,
  modalVisible: tasks.modalVisible,
}))
class TaskDetailForm extends PureComponent<IProps, any> {
  state = {
    note: '',
    approveModal: {
      title: '',
      open: false,
      multiSelect: true,
      type: '',
    },
    backStepModal: {
      open: false,
    },
  };

  //查询审批意见
  getCommentList = (payload: any) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'formDetail/fetchCommentList',
      payload: payload,
    });
  };

  //内容
  MyContent = (content: any) => {
    return <p>{content}</p>;
  };
  //时间
  MyDatetime = (time: any) => {
    return (
      <Tooltip
        title={moment(time)
          .subtract(1, 'days')
          .format('YYYY-MM-DD HH:mm:ss')}
      >
        <span>
          {moment(time)
            .subtract(1, 'days')
            .fromNow()}
        </span>
      </Tooltip>
    );
  };

  //打开办理页面
  onCancel = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'tasks/showHandleTaskModal',
      payload: {
        modalTitle: '办理任务',
        modalVisible: false,
      },
    });
  };

  //审批没有弹出框
  doApproveNoModel = item => {
    const { dispatch, formInfo, callBack } = this.props;
    const data = {
      taskId: formInfo.taskId,
      processInstanceId: formInfo.processInstanceId,
      message: this.state.note,
      type: item.type,
    };
    dispatch({
      type: 'formDetail/doApproveNoModel',
      payload: data,
      callback: callBack,
    });
  };

  //审批并选择人员
  doApprove = e => {
    const { dispatch, formInfo, callBack } = this.props;
    this.setState({ approveModal: { open: false } });

    let userIds = [];
    e.users.forEach((item, i) => {
      userIds.push(item.userId);
    });
    debugger;
    const data = {
      taskId: formInfo.taskId,
      processInstanceId: formInfo.processInstanceId,
      message: e.approveMsg,
      userCodes: userIds,
      type: e.type,
    };
    dispatch({
      type: 'formDetail/doApprove',
      payload: data,
      callback: callBack,
    });
  };

  onChange = ({ target: { value } }) => {
    this.setState({ note: value });
  };

  //驳回
  onBackStep(e) {
    const { dispatch, formInfo, callBack } = this.props;
    const data = {
      taskId: formInfo.taskId,
      distFlowElementId: e.nodeId,
      message: e.approveMsg,
      processInstanceId: formInfo.processInstanceId,
    };
    dispatch({
      type: 'formDetail/doBackStep',
      payload: data,
      callback: callBack,
    });
    this.setState({ backStepModal: { open: false } });
  }

  render() {
    const {
      modalVisible,
      form,
      record,
      handle,
      canHandel,
      modalTitle,
      loading,
      commentList,
      formInfo,
    } = this.props;
    const {
      approveModal: { title, open, multiSelect, type },
      note,
    } = this.state;
    const formItems = () => {
      const id = record ? record.id : null;
      form.getFieldDecorator('id', {
        initialValue: id,
      });
      return [
        <FormItem key="name" labelCol={{ span: 3 }} wrapperCol={{ span: 16 }} label="姓名">
          {form.getFieldDecorator('name', {
            initialValue: record ? record.name : '',
            rules: [{ required: true, message: '请输入姓名' }],
          })(<Input readOnly={true} style={{ width: 170 }} placeholder="请输入姓名" />)}
        </FormItem>,
        <FormItem key="startTime" labelCol={{ span: 3 }} wrapperCol={{ span: 16 }} label="开始时间">
          {form.getFieldDecorator('startTime', {
            initialValue: record ? moment(record.startTime) : '',
            rules: [{ required: true, message: '请选择开始时间' }],
          })(<DatePicker disabled={true} format="YYYY-MM-DD" />)}
        </FormItem>,
        <FormItem key="endTime" labelCol={{ span: 3 }} wrapperCol={{ span: 16 }} label="结束时间">
          {form.getFieldDecorator('endTime', {
            initialValue: record ? moment(record.endTime) : '',
            rules: [{ required: true, message: '请选择结束时间' }],
          })(<DatePicker disabled={true} format="YYYY-MM-DD" />)}
        </FormItem>,
        <FormItem key="days" labelCol={{ span: 3 }} wrapperCol={{ span: 16 }} label="请假天数">
          {form.getFieldDecorator('days', {
            initialValue: record ? record.days : 0,
            rules: [{ required: true, message: '请输入请假天数' }],
          })(<InputNumber readOnly={true} style={{ width: 170 }} min={0} />)}
        </FormItem>,
      ];
    };

    //没有弹出层
    const ctrlOptionsNoModel = [
      {
        buttonText: '审批',
        type: 'SP',
      },
      {
        buttonText: '签收',
        type: 'QS',
      },
      {
        buttonText: '反签收',
        type: 'FQS',
      },
      {
        buttonText: '撤回',
        type: 'CH',
      },
      {
        buttonText: '终止',
        type: 'ZZ',
      },
    ];
    //有弹出层
    const ctrlOptions = [
      {
        buttonText: '转办',
        showTitle: '转办',
        type: 'ZB',
        handelClick: e => {
          this.setState({
            approveModal: { open: true, title: e.showTitle, type: e.type, multiSelect: false },
          });
        },
      },
      {
        buttonText: '委派',
        showTitle: '委派',
        type: 'WP',
        handelClick: e => {
          this.setState({
            approveModal: { open: true, title: e.showTitle, type: e.type, multiSelect: false },
          });
        },
      },
      {
        buttonText: '前加签',
        showTitle: '前加签',
        type: 'QJQ',
        handelClick: e => {
          this.setState({
            approveModal: { open: true, title: e.showTitle, type: e.type, multiSelect: true },
          });
        },
      },
      {
        buttonText: '后加签',
        showTitle: '后加签',
        type: 'HJQ',
        handelClick: e => {
          this.setState({
            approveModal: { open: true, title: e.showTitle, type: e.type, multiSelect: true },
          });
        },
      },
      {
        buttonText: '驳回',
        showTitle: '驳回',
        type: 'BH',
        handelClick: e => {
          this.setState({
            backStepModal: { open: true },
          });
        },
      },
    ];

    return (
      <Modal
        width={950}
        bodyStyle={{ padding: '10px 15px 10px' }}
        destroyOnClose
        title={modalTitle}
        visible={modalVisible}
        okButtonProps={{ loading: loading }}
        closable={true}
        footer={null}
        onCancel={this.onCancel}
      >
        <Card title={'表单详情'}>{formItems()}</Card>
        {canHandel && (
          <React.Fragment>
            <Divider />
            <Card title={'审批'}>
              <TextArea
                onChange={this.onChange.bind(this)}
                placeholder="审批意见填写"
                autosize={{ minRows: 2, maxRows: 8 }}
              />
              <div className={styles.handelTask}>
                {/*<Button type="primary" onClick={this.complete}>
                  审批
                </Button>
                <Button type="primary" onClick={this.stopProcess}>
                  终止
                </Button>
                <Button type="primary" onClick={this.revokeProcess}>
                  撤回
                </Button>*/}
                {ctrlOptionsNoModel.map(item => {
                  return (
                    <Button
                      type="primary"
                      onClick={() => {
                        this.doApproveNoModel(item);
                      }}
                    >
                      {item.buttonText}
                    </Button>
                  );
                })}
                |&nbsp;&nbsp;
                {ctrlOptions.map(item => {
                  return (
                    <Button
                      type="primary"
                      onClick={() => {
                        item.handelClick(item);
                      }}
                    >
                      {item.buttonText}
                    </Button>
                  );
                })}
              </div>
            </Card>
          </React.Fragment>
        )}
        <Divider />
        <Card title={'审批意见'}>
          <List
            className="comment-list"
            itemLayout="horizontal"
            dataSource={commentList}
            renderItem={(item: any) => (
              <li>
                <Comment
                  author={item.userName + '-' + item.typeName}
                  avatar={
                    <Avatar
                      src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
                      alt="Han Solo"
                    />
                  }
                  content={this.MyContent(item.message)}
                  datetime={this.MyDatetime(item.time)}
                />
              </li>
            )}
          />
        </Card>
        {this.state.approveModal.open ? (
          <ApproveModal
            onCancel={() => {
              this.setState({ approveModal: { open: false } });
            }}
            onApprove={e => {
              this.doApprove(e);
            }}
            type={type}
            title={title}
            multiSelect={multiSelect}
            open={open}
            approveMsg={note}
          />
        ) : (
          ''
        )}

        {this.state.backStepModal.open ? (
          <BackStepModal
            onBackStep={e => {
              this.onBackStep(e);
            }}
            onCancel={() => {
              this.setState({ backStepModal: { open: false } });
            }}
            processInstanceId={formInfo.processInstanceId}
            taskId={formInfo.taskId}
            open={this.state.backStepModal.open}
            approveMsg={note}
            title="驳回"
            type="BH"
          />
        ) : (
          ''
        )}
      </Modal>
    );
  }
}

export default Form.create<IProps>()(TaskDetailForm);
