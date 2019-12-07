/*
 * @Author: wqjiao
 * @Date: 2019-06-11 10:59:12
 * @Last Modified by: wqjiao
 * @Last Modified time: 2019-06-11 11:03:17
 * @Description: BtnLink 列表按钮box
 * @use: <BtnLink><a>按钮</a></BtnLink>
 */
import React, { PureComponent } from 'react';
import styles from './index.less';

class BtnLink extends PureComponent {
  render() {
    return <div className={styles.btnLink}>{this.props.children}</div>;
  }
}

export default BtnLink;
