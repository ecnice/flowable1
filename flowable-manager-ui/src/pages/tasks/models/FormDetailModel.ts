import { Effect } from 'dva';
import { Reducer } from 'redux';
import { message } from 'antd';

import {
  commentsByProcessInstanceId,
  complete,
  image,
  stopProcess,
} from '@/pages/tasks/services/FormDetailService';

export interface FormDetailModelState {
  commentList?: [];
  imgSrc?:string;
}

export interface FormDetailModelType {
  namespace: 'formDetail';
  state: FormDetailModelState;
  effects: {
    fetchCommentList: Effect;
    fetchComplete: Effect;
    fetchStopProcess: Effect;
    fetchProcessImage: Effect;
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
    *fetchCommentList({ payload }, { call, put }) {
      const response = yield call(commentsByProcessInstanceId, payload);
      yield put({
        type: 'saveCommentList',
        payload: response,
      });
    },
    *fetchComplete({ payload, callback }, { call, put }) {
      const response = yield call(complete, payload);
      if (response.code === '100') {
        message.success(response.msg);
        callback();
      } else {
        message.error(response.msg);
      }
    },
    *fetchStopProcess({ payload, callback }, { call, put }) {
      const response = yield call(stopProcess, payload);
      if (response.code === '100') {
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
