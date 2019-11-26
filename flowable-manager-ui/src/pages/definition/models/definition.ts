import { Effect } from 'dva';
import { Reducer } from 'redux';

import { pageModel } from '@/pages/definition/services/definition.ts';

export interface DefinitionModelState {
  data: [];
  total: number;
}

export interface DefinitionModelType {
  namespace: 'definition';
  state: DefinitionModelState;
  effects: {
    fetchList: Effect;
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

export default DefinitionModel;
