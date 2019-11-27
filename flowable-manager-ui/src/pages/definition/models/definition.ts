import { Effect } from 'dva';
import { Reducer } from 'redux';
import { message } from 'antd';

import { pageModel, deleteDeployment } from '@/pages/definition/services/definition.ts';

export interface DefinitionModelState {
  data: [];
  total: number;
}

export interface DefinitionModelType {
  namespace: 'definition';
  state: DefinitionModelState;
  effects: {
    fetchList: Effect;
    deleteDeployment: Effect;
  };
  reducers: {
    list: Reducer<DefinitionModelState>;
  };
}

const DefinitionModel: DefinitionModelType = {
  namespace: 'definition',
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
    *deleteDeployment({ payload, callback }, { call, put }) {
      const response = yield call(deleteDeployment, payload);
      if (response.code === '100') {
        message.success(response.msg);
        callback();
      } else {
        message.error(response.msg);
      }
      yield put({
        type: 'delete',
        payload: response,
      });
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
    delete(state, action) {
      return {
        ...state,
      };
    },
  },
};

export default DefinitionModel;
