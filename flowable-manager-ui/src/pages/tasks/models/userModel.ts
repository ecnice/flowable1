import { Effect } from 'dva';
import { Reducer } from 'redux';

import { pageModel } from '@/pages/account/services/UserService';
export interface UserModelState {
  userList?: [];
}

export interface UserModelType {
  namespace: 'userModel';
  state: UserModelState;
  effects: {
    fetchUserList: Effect;
  };
  reducers: {
    saveUserList: Reducer<UserModelState>;
  };
}

const UserModel: UserModelType = {
  namespace: 'userModel',
  state: {
    userList: [],
  },
  effects: {
    *fetchUserList({ payload }, { call, put }) {
      const response = yield call(pageModel, { name: payload.keyword });
      yield put({
        type: 'saveUserList',
        payload: response,
      });
    },
  },

  reducers: {
    saveUserList(state, { payload }) {
      payload.data.filter(obj => {
        obj['userId'] = obj.id;
        obj['userName'] = obj.displayName;
        obj['email'] = obj.email;
      });
      return {
        ...state,
        userList: payload.data || [],
      };
    },
  },
};

export default UserModel;
