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
class ModulesList extends Component {
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

  componentDidMount() {
    this.doFetchData();
  }

  doDeploy(modelId) {
    const { dispatch } = this.props;
    dispatch({
      type: 'modules/deploy',
      payload: {
        modelId: modelId,
      },
    });
    // this.doFetchData();
  }

  doEdit(modelId) {
    console.log(modelId);
    router.push(`/modules/editor/${modelId}`);
  }

  render() {
    const { modules } = this.props;
    const _that = this;
    const columns = [
      {
        title: '名称',
        width: 100,
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: 'Key',
        width: 100,
        dataIndex: 'key',
        key: 'key',
      },
      {
        title: '系统',
        dataIndex: 'tenantId',
        key: 'tenantId',
        width: 150,
      },
      {
        title: '描述',
        dataIndex: 'comment',
        key: 'comment',
        width: 150,
      },
      {
        title: '创建人',
        dataIndex: 'createdBy',
        key: 'createdBy',
        width: 150,
      },
      {
        title: '创建时间',
        dataIndex: 'created',
        key: 'created',
        width: 150,
      },
      {
        title: '模板类型',
        dataIndex: 'modelType',
        key: 'modelType',
        width: 150,
      },
      {
        title: 'Action',
        key: 'operation',
        // fixed: 'right',
        width: 100,
        render: (text, record) =>
          modules.length >= 1 ? (
            <div>
              <Popconfirm title="确定要部署吗?" onConfirm={() => this.doDeploy(record.id)}>
                <a>部署</a>
              </Popconfirm>
              &nbsp;&nbsp;
              <a onClick={() => this.doEdit(record.id)}>编辑</a>
            </div>
          ) : null,
      },
    ];

    const props = {
      name: 'file',
      action: '/server/rest/model/rest/import-process-model',
      showUploadList: false,
      headers: {
        authorization: 'authorization-text',
      },
      onChange(info) {
        if (info.file.status !== 'uploading') {
          console.log(info.file, info.fileList);
        }
        if (info.file.status === 'done') {
          message.success(`${info.file.name} file uploaded successfully`);
          _that.doFetchData();
        } else if (info.file.status === 'error') {
          message.error(`${info.file.name} file upload failed.`);
        }
      },
    };

    return (
      <PageHeaderWrapper>
        <Card>
          <div className={styles.toolBar}>
            <Upload {...props}>
              <Button type="primary">
                <Icon type="upload" />
                导入模板
              </Button>
            </Upload>
          </div>
          <Table rowKey="id" columns={columns} dataSource={modules} />
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default ModulesList;
