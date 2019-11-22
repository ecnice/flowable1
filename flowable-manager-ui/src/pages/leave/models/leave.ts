import { Effect } from 'dva';
import { Reducer } from 'redux';
import { message } from 'antd';

import { queryLeave, insertLeave, updateLeave } from '@/pages/leave/services/leave';

export interface UserModelState {
  data: [];
}

export interface LeaveModelType {
  namespace: 'leave';
  state: UserModelState;
  effects: {
    fetch: Effect;
    insert: Effect;
    update: Effect;
  };
  reducers: {
    list: Reducer<UserModelState>;
  };
}

const LeaveModel: LeaveModelType = {
  namespace: 'leave',
  state: {
    data: [],
  },
  effects: {
    *fetch(_, { call, put }) {
      const response = yield call(queryLeave);
      yield put({
        type: 'list',
        payload: response,
      });
    },
    *insert({ payload, resetform, callback }, { call, put }) {
      const response = yield call(insertLeave, payload);
      if (response.code === '100') {
        message.success(response.msg);
        resetform();
        callback();
      } else {
        message.error(response.msg);
      }
    },
    *update({ payload, resetform, callback }, { call, put }) {
      const response = yield call(updateLeave, payload);
      if (response.code === '100') {
        message.success(response.msg);
        resetform();
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
      };
    },
  },
};

export default LeaveModel;
