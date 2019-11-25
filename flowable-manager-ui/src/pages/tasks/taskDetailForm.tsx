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
} from 'antd';
import React, { PureComponent } from 'react';
import { FormComponentProps } from 'antd/es/form';
import styles from './style.less';

const FormItem = Form.Item;
const { Title } = Typography;
const { TextArea } = Input;
import moment from 'moment';
import { connect } from 'dva';

interface IProps extends FormComponentProps {
  loading: boolean;
  modalVisible: boolean;
  canHandel: boolean;
  record: any;
  handle: any;
  handleModalVisible: any;
  modalTitle: string;
  commentList: [];
  dispatch?: any;
}

@connect(({ formDetail, loading }: any) => ({
  loading: loading.models.formDetail,
  commentList: formDetail.commentList,
}))
class TaskDetailForm extends PureComponent<IProps, any> {
  state = {
    note: '',
  };

  //加载完成查询
  componentWillMount() {
    const processInstanceId = this.props.record ? this.props.record.processInstanceId : null;
    if (processInstanceId != null) {
      debugger;
      this.getCommentList({ processInstanceId: processInstanceId });
    }
  }

  //查询审批意见
  getCommentList = (payload: any) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'formDetail/fetchCommentList',
      payload: payload,
    });
  };

  render() {
    const {
      modalVisible,
      form,
      record,
      handle,
      canHandel,
      handleModalVisible,
      modalTitle,
      loading,
      commentList,
    } = this.props;
    debugger;
    const commentColumns = [
      {
        title: '类型',
        width: 100,
        dataIndex: 'typeName',
        key: 'typeName',
      },
      {
        title: '意见',
        width: 100,
        dataIndex: 'message',
        key: 'message',
      },
      {
        title: '时间',
        width: 100,
        dataIndex: 'time',
        key: 'time',
        render: (val: any) => (val ? <span>{moment(val).format('YYYY-MM-DD HH:mm')}</span> : ''),
      },
    ];
    const data = [
      {
        author: '张三',

        content: <p>审批意见测试数据</p>,
        datetime: (
          <Tooltip
            title={moment()
              .subtract(1, 'days')
              .format('YYYY-MM-DD HH:mm:ss')}
          >
            <span>
              {moment()
                .subtract(1, 'days')
                .fromNow()}
            </span>
          </Tooltip>
        ),
      },
      {
        author: '王五',

        content: <p>审批意见测试数据</p>,
        datetime: (
          <Tooltip
            title={moment()
              .subtract(2, 'days')
              .format('YYYY-MM-DD HH:mm:ss')}
          >
            <span>
              {moment()
                .subtract(2, 'days')
                .fromNow()}
            </span>
          </Tooltip>
        ),
      },
    ];

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
    const onCancel = () => {
      handleModalVisible();
    };
    return (
      <Modal
        width={800}
        bodyStyle={{ padding: '10px 15px 10px' }}
        destroyOnClose
        title={modalTitle}
        visible={modalVisible}
        okButtonProps={{ loading: loading }}
        closable={true}
        footer={null}
        onCancel={onCancel}
      >
        <Card title={'表单详情'}>{formItems()}</Card>
        {canHandel && (
          <React.Fragment>
            <Divider />
            <Card title={'审批'}>
              <TextArea
                onChange={e => {
                  this.setState({ note: e.target.value });
                }}
                placeholder="审批意见填写"
                autosize={{ minRows: 2, maxRows: 8 }}
              />
              <div className={styles.handelTask}>
                <Button>审批</Button>
                <Button>转办</Button>
                <Button>委派</Button>
                <Button>驳回</Button>
                <Button>终止</Button>
              </div>
            </Card>
          </React.Fragment>
        )}
        <Divider />
        <Card title={'审批意见列表'}>
          <List
            className="comment-list"
            itemLayout="horizontal"
            dataSource={commentList}
            renderItem={(item: any) => (
              <li>
                <Comment author={item.typeName} content={item.message} datetime={item.time} />
              </li>
            )}
          />
        </Card>
      </Modal>
    );
  }
}

export default Form.create<IProps>()(TaskDetailForm);
