import { message } from 'antd';
import { Effect } from 'dva';
import { Reducer } from 'redux';
import {
  pageModel,
  deleteProcessInstanceById,
} from '@/pages/processInstance/services/ProcessInstanceService.ts';

export interface ProcessInstanceModelState {
  data: [];
  total: number;
}

export interface ProcessInstanceModelType {
  namespace: 'processInstance';
  state: ProcessInstanceModelState;
  effects: {
    fetchList: Effect;
    deleteProcessInstanceById: Effect;
  };
  reducers: {
    list: Reducer<ProcessInstanceModelState>;
  };
}

const ProcessInstanceModel: ProcessInstanceModelType = {
  namespace: 'processInstance',
  state: {
    data: [],
    total: 0,
  },
  effects: {
    *fetchList({ payload }, { call, put }) {
      const response = yield call(pageModel, payload);
      yield put({
        type: 'list',
        payload: response,
      });
    },
    *deleteProcessInstanceById({ payload, callback }, { call, put }) {
      const response = yield call(deleteProcessInstanceById, payload);
      if (response.code == '100') {
        message.success(response.msg);
        callback();
      } else {
        message.error(response.msg);
      }
    },
  },
  reducers: {
    list(state, action) {
      return {
        ...state,
        data: action.payload.data || [],
        total: action.payload.total,
      };
    },
  },
};

export default ProcessInstanceModel;
