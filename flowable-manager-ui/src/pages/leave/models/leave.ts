import { Effect } from 'dva';
import { Reducer } from 'redux';
import { message } from 'antd';

import { queryLeave, insertLeave, updateLeave } from '@/pages/leave/services/leave';
import { ReturnCode } from '@/utils/utils';

export interface UserModelState {
  data: [];
  total: number;
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
    total: 0,
  },
  effects: {
    *fetch({ payload }, { call, put }) {
      const response = yield call(queryLeave, payload);
      yield put({
        type: 'list',
        payload: response,
      });
    },
    *insert({ payload, resetform, callback }, { call, put }) {
      const response = yield call(insertLeave, payload);
      if (response.code === ReturnCode.SUCCESS) {
        message.success(response.msg);
        resetform();
        callback();
      } else {
        message.error(response.msg);
      }
    },
    *update({ payload, resetform, callback }, { call, put }) {
      const response = yield call(updateLeave, payload);
      if (response.code === ReturnCode.SUCCESS) {
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
        total: action.payload.total,
      };
    },
  },
};

export default LeaveModel;
