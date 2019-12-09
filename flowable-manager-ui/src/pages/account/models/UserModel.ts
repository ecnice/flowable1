import { message } from 'antd';
import { Effect } from 'dva';
import { Reducer } from 'redux';
import {
  pageModel,
  saveUser,
  deleteUser,
  addUserGroup,
} from '@/pages/account/services/UserService.ts';
import { ReturnCode } from '@/utils/utils';

export interface UserModelState {
  data: [];
  total: number;
}

export interface UserModelType {
  namespace: 'account';
  state: UserModelState;
  effects: {
    fetchList: Effect;
    deleteProcessInstanceById: Effect;
    saProcessInstanceById: Effect;
    stopProcess: Effect;
  };
  reducers: {
    list: Reducer<UserModelState>;
  };
}

const UserModel: UserModelType = {
  namespace: 'account',
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
    *saveUser({ payload, callback }, { call, put }) {
      const response = yield call(saveUser, payload);
      if (response.code == ReturnCode.SUCCESS) {
        message.success(response.msg);
        callback();
      } else {
        message.error(response.msg);
      }
    },
    *deleteUser({ payload, callback }, { call, put }) {
      const response = yield call(deleteUser, payload);
      if (response.code == ReturnCode.SUCCESS) {
        message.success(response.msg);
        callback();
      } else {
        message.error(response.msg);
      }
    },
    *addUserGroup({ payload, callback }, { call, put }) {
      const response = yield call(addUserGroup, payload);
      if (response.code == ReturnCode.SUCCESS) {
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

export default UserModel;
