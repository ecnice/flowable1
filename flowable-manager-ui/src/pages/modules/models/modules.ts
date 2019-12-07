import { Effect } from 'dva';
import { Reducer } from 'redux';
import { message } from 'antd';

import { queryCurrent, queryModules, deployModules } from '../services/modules';
import { ReturnCode } from '@/utils/utils';

export interface CurrentUser {
  modules?: Array<any>;
  avatar?: string;
  name?: string;
  title?: string;
  group?: string;
  signature?: string;
  tags?: {
    key: string;
    label: string;
  }[];
  userid?: string;
  unreadCount?: number;
}

export interface UserModelState {
  currentUser?: CurrentUser;
  modules?: [];
}

export interface ModulesModelType {
  namespace: 'modules';
  state: UserModelState;
  effects: {
    fetch: Effect;
    fetchCurrent: Effect;
    deploy: Effect;
  };
  reducers: {
    saveList: Reducer<UserModelState>;
    saveCurrentUser: Reducer<UserModelState>;
    changeNotifyCount: Reducer<UserModelState>;
  };
}

const ModulesModel: ModulesModelType = {
  namespace: 'modules',

  state: {
    currentUser: {},
  },

  effects: {
    *fetch(_, { call, put }) {
      const response = yield call(queryModules);
      yield put({
        type: 'saveList',
        payload: response,
      });
    },
    *deploy({ payload }, { call, put }) {
      const response = yield call(deployModules, payload);
      if (response.code === ReturnCode.SUCCESS) {
        message.success(response.msg);
        yield put({
          type: 'fetch',
          payload: {},
        });
      } else {
        message.error(response.msg);
      }
    },
    *fetchCurrent(_, { call, put }) {
      const response = yield call(queryCurrent);
      yield put({
        type: 'saveCurrentUser',
        payload: response,
      });
    },
  },

  reducers: {
    saveList(state, action) {
      return {
        ...state,
        modules: action.payload.data.data || [],
      };
    },
    saveCurrentUser(state, action) {
      return {
        ...state,
        currentUser:
          { userid: action.payload.id, name: action.payload.firstName, ...action.payload } || {},
      };
    },
    changeNotifyCount(
      state = {
        currentUser: {},
      },
      action,
    ) {
      return {
        ...state,
        currentUser: {
          ...state.currentUser,
          notifyCount: action.payload.totalCount,
          unreadCount: action.payload.unreadCount,
        },
      };
    },
  },
};

export default ModulesModel;
