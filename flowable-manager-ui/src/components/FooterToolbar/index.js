import React, { Component } from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import styles from './index.less';

export default class FooterToolbar extends Component {
  static contextTypes = {
    isMobile: PropTypes.bool,
  };

  state = {
    width: undefined,
  };

  componentDidMount() {
    window.addEventListener('resize', this.resizeFooterToolbar);
    this.resizeFooterToolbar();
  }

  componentWillUnmount() {
    window.removeEventListener('resize', this.resizeFooterToolbar);
  }

  resizeFooterToolbar = () => {
    const sider = document.querySelector('.ant-layout-sider');
    if (sider == null) {
      return;
    }
    //antd这个方法无效
    // const {isMobile} = this.context;
    //当宽度小于599px展示整个底部栏599是屏幕展示左侧栏的最小宽度
    const minWidth = document.body.clientWidth > 599 ? false : true;
    const width = minWidth ? null : `calc(100% - ${sider.style.width})`;
    const { width: stateWidth } = this.state;
    if (stateWidth !== width) {
      this.setState({ width });
    }
  };

  render() {
    const { children, className, extra, ...restProps } = this.props;
    const { width } = this.state;
    return (
      <div className={classNames(className, styles.toolbar)} style={{ width }} {...restProps}>
        <div className={styles.left}>{extra}</div>
        <div className={styles.right}>{children}</div>
      </div>
    );
  }
}
