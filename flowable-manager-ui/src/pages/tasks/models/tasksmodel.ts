import { Effect } from 'dva';
import { Reducer } from 'redux';

import {
  getApplyedTasks,
  getApplyingTasks,
  getFormInfoForTask,
  getMyProcessInstances,
} from '@/pages/tasks/services/tasksservice';

export interface TasksModelState {
  applyingTasks?: [];
  applyedTasks?: [];
  myProcessInstances?: [];
  total?: number;
  formInfo?: object;
  modalVisible?: boolean;
  modalTitle?: string;
  showImageModal?: boolean;
  imgSrc?: string;
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
    showHandleTaskModal: Reducer<TasksModelState>;
    processImage: Reducer<TasksModelState>;
  };
}

const TasksModel: TasksModelType = {
  namespace: 'tasks',
  state: {
    applyingTasks: [],
    applyedTasks: [],
    myProcessInstances: [],
    total: 0,
    formInfo: {},
    modalVisible: false,
    modalTitle: '',
    showImageModal: false,
    imgSrc: '',
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
      //查询审批意见
      yield put({
        type: 'formDetail/fetchCommentList',
        payload: { processInstanceId: response.data.processInstanceId },
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
    showHandleTaskModal(state, { payload }) {
      return {
        ...state,
        modalTitle: payload.modalTitle,
        modalVisible: payload.modalVisible,
      };
    },
    processImage(state, { payload }) {
      return {
        ...state,
        showImageModal: payload.showImageModal,
        imgSrc: payload.showImageModal
          ? '/server/rest/formdetail/image/' + payload.processInstanceId
          : '',
      };
    },
  },
};

export default TasksModel;
