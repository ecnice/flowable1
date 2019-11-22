import { Effect } from 'dva';
import { Reducer } from 'redux';

import {
  getApplyedTasks,
  getApplyingTasks,
  getMyProcessInstances,
} from '@/pages/tasks/services/tasksservice';

export interface TasksModelState {
  applyingTasks?: [];
  applyedTasks?: [];
  myProcessInstances?: [];
  total: number;
}

export interface TasksModelType {
  namespace: 'tasks';
  state: TasksModelState;
  effects: {
    fetchApplyingTasks: Effect;
    fetchApplyedTasks: Effect;
    fetchMyProcessInstancesTasks: Effect;
  };
  reducers: {
    saveApplyingTasks: Reducer<TasksModelState>;
    saveApplyedTasks: Reducer<TasksModelState>;
    saveMyProcessInstancesTasks: Reducer<TasksModelState>;
  };
}

const TasksModel: TasksModelType = {
  namespace: 'tasks',
  state: {
    applyingTasks: [],
    applyedTasks: [],
    myProcessInstances: [],
    total: 0,
  },
  effects: {
    *fetchApplyingTasks({ payload }, { call, put }) {
      const response = yield call(getApplyingTasks, payload);
      yield put({
        type: 'saveApplyingTasks',
        payload: response,
      });
    },
    *fetchApplyedTasks({ payload, resetform, callback }, { call, put }) {
      const response = yield call(getApplyedTasks, payload);
      yield put({
        type: 'saveApplyedTasks',
        payload: response,
      });
    },
    *fetchMyProcessInstancesTasks({ payload, resetform, callback }, { call, put }) {
      const response = yield call(getMyProcessInstances, payload);
      yield put({
        type: 'saveMyProcessInstancesTasks',
        payload: response,
      });
    },
  },

  reducers: {
    saveApplyingTasks(state, { payload }) {
      return {
        ...state,
        applyingTasks: payload.data || [],
        total: payload.total,
      };
    },
    saveApplyedTasks(state, { payload }) {
      return {
        ...state,
        applyedTasks: payload.data || [],
        total: payload.total,
      };
    },
    saveMyProcessInstancesTasks(state, { payload }) {
      return {
        ...state,
        myProcessInstances: payload.data || [],
        total: payload.total,
      };
    },
  },
};

export default TasksModel;
