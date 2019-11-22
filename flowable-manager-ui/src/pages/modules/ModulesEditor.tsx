import React, { Component } from 'react';
import { message, Card, Table, Button, Upload, Icon, Popconfirm } from 'antd';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import styles from './modules.less';
import router from 'umi/router';

@connect(({ modules, loading }) => ({
  modules: modules.modules,
  fetchingData: loading.effects['modules/fetch'],
}))
class ModulesEditor extends Component<any, any> {
  state: {};
  onTabChange = (type: string) => {
    this.setState({ type });
  };

  doFetchData() {
    const { dispatch } = this.props;
    dispatch({
      type: 'modules/fetch',
      payload: {},
    });
  }

  componentDidMount() {}

  render() {
    const { modules } = this.props;
    const _that = this;

    return (
      <PageHeaderWrapper>
        <Card>
          {/*<div className={styles.toolBar}>
            <Upload {...props}>
              <Button type="primary">
                <Icon type="upload" />导入模板
              </Button>
            </Upload>
          </div>*/}
          <div>
            <iframe className={styles.flowEditor} src="http://www.baidu.com"></iframe>
          </div>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default ModulesEditor;
