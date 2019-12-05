import { Effect } from 'dva';
import { Reducer } from 'redux';
import { message } from 'antd';

import {
  commentsByProcessInstanceId,
  complete,
  image,
  stopProcess,
  revokeProcess,
  turnTask,
  delegateTask,
} from '@/pages/tasks/services/FormDetailService';
import { ReturnCode } from '@/utils/utils';

export interface FormDetailModelState {
  commentList?: [];
  imgSrc?: string;
}

export interface FormDetailModelType {
  namespace: 'formDetail';
  state: FormDetailModelState;
  effects: {
    fetchCommentList: Effect;
    fetchComplete: Effect;
    fetchStopProcess: Effect;
    fetchProcessImage: Effect;
    fetchRevokeProcess: Effect;
    doApprove: Effect;
  };
  reducers: {
    saveCommentList: Reducer<FormDetailModelState>;
  };
}

const FormDetailModel: FormDetailModelType = {
  namespace: 'formDetail',
  state: {
    commentList: [],
    imgSrc: '',
  },
  effects: {
    *doApprove({ payload }, { call, put }) {
      if (payload.type === 'ZB') {
        const response = yield call(turnTask, payload);
      } else if (payload.type === 'WP') {
        const response = yield call(delegateTask, payload);
      }
      // 关闭弹窗
      yield put({
        type: 'tasks/showHandleTaskModal',
        payload: {
          modalTitle: '',
          modalVisible: false,
        },
      });
      //查询待办任务
      yield put({
        type: 'tasks/fetchApplyingTasks',
        payload: {},
      });
    },
    *fetchCommentList({ payload }, { call, put }) {
      const response = yield call(commentsByProcessInstanceId, payload);
      yield put({
        type: 'saveCommentList',
        payload: response,
      });
    },
    *fetchComplete({ payload, callback }, { call, put }) {
      const response = yield call(complete, payload);
      if (response.code === ReturnCode.SUCCESS) {
        message.success(response.msg);
        callback();
      } else {
        message.error(response.msg);
      }
    },
    *fetchStopProcess({ payload, callback }, { call, put }) {
      const response = yield call(stopProcess, payload);
      if (response.code === ReturnCode.SUCCESS) {
        message.success(response.msg);
        callback();
      } else {
        message.error(response.msg);
      }
    },
    *fetchRevokeProcess({ payload, callback }, { call, put }) {
      const response = yield call(revokeProcess, payload);
      if (response.code === ReturnCode.SUCCESS) {
        message.success(response.msg);
        callback();
      } else {
        message.error(response.msg);
      }
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
    saveCommentList(state, { payload }) {
      return {
        ...state,
        commentList: payload || [],
      };
    },
  },
};

export default FormDetailModel;
