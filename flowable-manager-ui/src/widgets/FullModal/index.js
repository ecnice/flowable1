/*
 * @Author: wqjiao
 * @Date: 2019-08-23 11:11:27
 * @Last Modified by: wqjiao
 * @Last Modified time: 2019-09-05 11:54:27
 * @Description: 基础设置 -> 流程管理 -> 流程设计
 */
import React, { Component } from 'react';
import { Modal } from 'antd';
import styles from './index.less';

class FullModal extends Component {
  render() {
    const { visible, title = '', onCancel, children } = this.props;

    return (
      <Modal
        title={title}
        visible={visible}
        onCancel={onCancel}
        width={'calc(100% - 20px)'}
        height={'calc(100% - 10px)'}
        footer={null}
        className={styles.fullModal}
      >
        {children}
      </Modal>
    );
  }
}

export default FullModal;
