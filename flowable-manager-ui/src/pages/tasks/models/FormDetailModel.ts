import { Effect } from 'dva';
import { Reducer } from 'redux';
import { message } from 'antd';

import {
  commentsByProcessInstanceId,
  image,
  stopProcess,
  revokeProcess,
  turnTask,
  delegateTask,
  beforeAddSignTask,
  afterAddSignTask,
  completeTask,
  claimTask,
  unClaimTask,
  getBackStepList,
  doBackStep,
} from '@/pages/tasks/services/FormDetailService';
import { ReturnCode } from '@/utils/utils';
import { TasksModelState } from '@/pages/tasks/models/tasksmodel';

export interface FormDetailModelState {
  commentList?: [];
  backStepList?: [];
  imgSrc?: string;
}

export interface FormDetailModelType {
  namespace: 'formDetail';
  state: FormDetailModelState;
  effects: {
    fetchBackStepList: Effect;
    fetchCommentList: Effect;
    // fetchComplete: Effect;
    // fetchStopProcess: Effect;
    fetchProcessImage: Effect;
    // fetchRevokeProcess: Effect;
    doApprove: Effect;
    doApproveNoModel: Effect;
    doBackStep: Effect;
  };
  reducers: {
    saveBackStepList: Reducer<TasksModelState>;
    saveCommentList: Reducer<FormDetailModelState>;
  };
}

const FormDetailModel: FormDetailModelType = {
  namespace: 'formDetail',
  state: {
    commentList: [],
    backStepList: [],
    imgSrc: '',
  },
  effects: {
    *fetchBackStepList({ payload }, { call, put }) {
      const response = yield call(getBackStepList, payload);
      yield put({
        type: 'saveBackStepList',
        payload: response,
      });
    },

    *doBackStep({ payload, callback }, { call, put }) {
      const response = yield call(doBackStep, payload);
      debugger;
      if (response.code === ReturnCode.SUCCESS) {
        callback();
        yield put({
          type: 'tasks/showHandleTaskModal',
          payload: {
            modalTitle: '',
            modalVisible: false,
          },
        });
      } else {
        message.error(response.msg);
      }
    },

    *doApprove({ payload }, { call, put }) {
      let response = {
        code: ReturnCode.FAIL,
        msg: '操作失败',
      };
      // 关闭弹窗
      const closeUserWindow = () => {
        return put({
          type: 'tasks/showHandleTaskModal',
          payload: {
            modalTitle: '',
            modalVisible: false,
          },
        });
      };
      //执行操作
      switch (payload.type) {
        case 'ZB':
          response = yield call(turnTask, payload);
          break;
        case 'WP':
          response = yield call(delegateTask, payload);
          break;
        case 'QJQ':
          response = yield call(beforeAddSignTask, payload);
          break;
        case 'HJQ':
          response = yield call(afterAddSignTask, payload);
          break;
        default:
          break;
      }
      if (response.code === ReturnCode.SUCCESS) {
        message.success(response.msg);
        yield closeUserWindow();
        //查询待办任务
        yield put({
          type: 'tasks/fetchApplyingTasks',
          payload: {},
        });
      } else {
        message.error(response.msg);
      }
    },
    *doApproveNoModel({ payload, callback }, { call, put }) {
      if (!payload.message) {
        message.warn('请填写审批意见!');
        return;
      }
      let response = {
        code: ReturnCode.FAIL,
        msg: '操作失败',
      };
      //执行操作
      switch (payload.type) {
        case 'SP':
          response = yield call(completeTask, payload);
          break;
        case 'CH':
          response = yield call(revokeProcess, payload);
          break;
        case 'ZZ':
          response = yield call(stopProcess, payload);
          break;
        case 'QS':
          response = yield call(claimTask, payload);
          break;
        case 'FQS':
          response = yield call(unClaimTask, payload);
          break;
        default:
          break;
      }
      if (response.code === ReturnCode.SUCCESS) {
        message.success(response.msg);
        callback();
        //查询待办任务
        yield put({
          type: 'tasks/fetchApplyingTasks',
          payload: {},
        });
      } else {
        message.error(response.msg);
      }
    },
    *fetchCommentList({ payload }, { call, put }) {
      const response = yield call(commentsByProcessInstanceId, payload);
      yield put({
        type: 'saveCommentList',
        payload: response,
      });
    },
    *fetchProcessImage({ payload }, { call, put }) {
      const response = yield call(image, payload);
      yield put({
        type: 'processImage',
        payload: response,
      });
    },
  },

  reducers: {
    saveBackStepList(state, { payload }) {
      return {
        ...state,
        backStepList: payload.datas || [],
      };
    },
    saveCommentList(state, { payload }) {
      return {
        ...state,
        commentList: payload || [],
      };
    },
  },
};

export default FormDetailModel;
