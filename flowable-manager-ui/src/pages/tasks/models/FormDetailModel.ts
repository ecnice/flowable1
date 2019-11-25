import { Effect } from 'dva';
import { Reducer } from 'redux';

import { commentsByProcessInstanceId } from '@/pages/tasks/services/FormDetailService';

export interface FormDetailModelState {
  commentList?: [];
}

export interface FormDetailModelType {
  namespace: 'formDetail';
  state: FormDetailModelState;
  effects: {
    commentList: Effect;
  };
  reducers: {
    commentList: Reducer<FormDetailModelState>;
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
  },

  reducers: {
    saveCommentList(state, { payload }) {
      return {
        ...state,
        commentList: payload.data || [],
      };
    },
  },
};

export default FormDetailModel;
