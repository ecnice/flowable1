/*
 * @Author: wqjiao
 * @Date: 2019-08-14 15:03:17
 * @Last Modified by: wqjiao
 * @Last Modified time: 2019-09-11 18:06:37
 * @Description: 默认空状态，统一自定义背景图片
 */
import React, { PureComponent } from 'react';
import { Empty } from 'antd';
import EMPTYIMAGE from '@/assets/empty.png';

export default class AntdEmpty extends PureComponent {
  render() {
    const { description = '暂无数据' } = this.props;
    return (
      <Empty
        className="ant-empty-normal"
        style={{ color: 'rgba(0, 0, 0, 0.45)' }}
        image={EMPTYIMAGE}
        description={description}
      />
    );
  }
}
