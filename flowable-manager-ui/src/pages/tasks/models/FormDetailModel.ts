import { Effect } from 'dva';
import { Reducer } from 'redux';
import { message } from 'antd';

import { commentsByProcessInstanceId, complete } from '@/pages/tasks/services/FormDetailService';

export interface FormDetailModelState {
  commentList?: [];
}

export interface FormDetailModelType {
  namespace: 'formDetail';
  state: FormDetailModelState;
  effects: {
    fetchCommentList: Effect;
  };
  reducers: {
    saveCommentList: Reducer<FormDetailModelState>;
  };
}

const FormDetailModel: FormDetailModelType = {
  namespace: 'formDetail',
  state: {
    commentList: [],
  },
  effects: {
    *fetchCommentList({ payload }, { call, put }) {
      const response = yield call(commentsByProcessInstanceId, payload);
      yield put({
        type: 'saveCommentList',
        payload: response,
      });
    },
    *fetchComplete({ payload }, { call, put }) {
      const response = yield call(complete, payload);
      if (response.code === '100') {
        message.success(response.msg);
        yield put({
          type: 'tasks/showHandleTaskModal',
          payload: {
            modalTitle: '办理任务',
            modalVisible: false,
          },
        });
      } else {
        message.error(response.msg);
      }
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
