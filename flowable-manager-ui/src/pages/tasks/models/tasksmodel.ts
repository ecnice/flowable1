import { Effect } from 'dva';
import { Reducer } from 'redux';

import {
  getApplyedTasks,
  getApplyingTasks, getFormInfoForTask,
  getMyProcessInstances,
} from '@/pages/tasks/services/tasksservice';

export interface TasksModelState {
  applyingTasks?: [];
  applyedTasks?: [];
  myProcessInstances?: [];
  total?: number;
  formInfo?:object;
}

export interface TasksModelType {
  namespace: 'tasks';
  state: TasksModelState;
  effects: {
    fetchApplyingTasks: Effect;
    fetchApplyedTasks: Effect;
    fetchMyProcessInstancesTasks: Effect;
    fetchFormInfo: Effect;
  };
  reducers: {
    saveApplyingTasks: Reducer<TasksModelState>;
    saveApplyedTasks: Reducer<TasksModelState>;
    saveMyProcessInstancesTasks: Reducer<TasksModelState>;
    saveFormInfo: Reducer<TasksModelState>;
  };
}

const TasksModel: TasksModelType = {
  namespace: 'tasks',
  state: {
    applyingTasks: [],
    applyedTasks: [],
    myProcessInstances: [],
    total: 0,
    formInfo:{}
  },
  effects: {
    *fetchApplyingTasks({ payload }, { call, put }) {
      const response = yield call(getApplyingTasks, payload);
      yield put({
        type: 'saveApplyingTasks',
        payload: response,
      });
    },
    *fetchApplyedTasks({ payload }, { call, put }) {
      const response = yield call(getApplyedTasks, payload);
      yield put({
        type: 'saveApplyedTasks',
        payload: response,
      });
    },
    *fetchMyProcessInstancesTasks({ payload }, { call, put }) {
      const response = yield call(getMyProcessInstances, payload);
      yield put({
        type: 'saveMyProcessInstancesTasks',
        payload: response,
      });
    },
    *fetchFormInfo({ payload, callback }, { call, put }) {
      const response = yield call(getFormInfoForTask, payload);
      yield put({
        type: 'saveFormInfo',
        payload: response,
      });
      callback();
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
    saveFormInfo(state, { payload }) {
      return {
        ...state,
        formInfo: payload.data || [],
      };
    },
  },
};

export default TasksModel;
