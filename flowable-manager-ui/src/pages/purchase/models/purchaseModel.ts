import { Effect } from 'dva';
import { Reducer } from 'redux';
import {
  updatePurchase,
  delPurchase,
  addPurchase,
  listPurchase,
} from '@/pages/purchase/services/PurchaseService.ts';
import { ReturnCode } from '@/utils/utils';
import { message } from 'antd';

/**
 * 定义state接口
 */
export interface PurchaseModelState {
  total: number;
  data: [];
}

/**
 * 定义model的接口
 */
export interface PurchaseModelType {
  namespace: 'purchase';
  state: PurchaseModelState;
  effects: {
    fetchList: Effect;
    fetchAdd: Effect;
    fetchUpdate: Effect;
    fetchDelete: Effect;
  };
  reducers: {
    list: Reducer<PurchaseModelState>;
  };
}

/**
 * 创建model实现类
 */
const PurchseModel: PurchaseModelType = {
  namespace: 'purchase',
  state: {
    total: 0,
    data: [],
  },
  effects: {
    *fetchList({ payload }, { call, put }) {
      const response = yield call(listPurchase, payload);
      yield put({
        type: 'list',
        payload: response,
      });
    },
    *fetchAdd({ payload, resetform, callback }, { call }) {
      debugger;
      const response = yield call(addPurchase, payload);
      if (response.code === ReturnCode.SUCCESS) {
        message.success(response.msg);
        resetform();
        callback();
      } else {
        message.error(response.msg);
      }
    },
    *fetchUpdate({ payload, resetform, callback }, { call }) {
      debugger;
      const response = yield call(updatePurchase, payload);
      if (response.code === ReturnCode.SUCCESS) {
        message.success(response.msg);
        resetform();
        callback();
      } else {
        message.error(response.msg);
      }
    },
    *fetchDelete({ payload, callback }, { call }) {
      debugger;
      const response = yield call(delPurchase, payload);
      if (response.code === ReturnCode.SUCCESS) {
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
//执行model
export default PurchseModel;
