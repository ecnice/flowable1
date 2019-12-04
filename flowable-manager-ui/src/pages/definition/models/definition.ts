import { Effect } from 'dva';
import { Reducer } from 'redux';
import { message } from 'antd';

import {
  pageModel,
  deleteDeployment,
  saDefinitionById,
} from '@/pages/definition/services/definition.ts';
import { ReturnCode } from '@/utils/utils';

export interface DefinitionModelState {
  data?: [];
  total?: number;
}

export interface DefinitionModelType {
  namespace: 'definition';
  state: DefinitionModelState;
  effects: {
    fetchList: Effect;
    deleteDeployment: Effect;
    saDefinitionById: Effect;
  };
  reducers: {
    list: Reducer<DefinitionModelState>;
    delete: Reducer<DefinitionModelState>;
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
      if (response.code === ReturnCode.SUCCESS) {
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
    *saDefinitionById({ payload, callback }, { call, put }) {
      const response = yield call(saDefinitionById, payload);
      if (response.code === ReturnCode.SUCCESS) {
        message.success(response.msg);
        callback();
      } else if (response.code === '101') {
        message.error(response.msg);
      } else {
        message.error('请求失败!');
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
    delete(state, action) {
      return {
        ...state,
      };
    },
  },
};

export default DefinitionModel;
