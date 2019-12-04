import React, { Component } from 'react';
import { message, Card, Table, Button, Upload, Icon, Popconfirm } from 'antd';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import styles from './modules.less';
import router from 'umi/router';
import moment from 'moment';

@connect(({ modules, loading }: any) => ({
  modules: modules.modules,
  fetchingData: loading.effects['modules/fetch'],
}))
class ModulesList extends Component<any, any> {
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

  doDeploy(modelId: string) {
    const { dispatch } = this.props;
    dispatch({
      type: 'modules/deploy',
      payload: {
        modelId: modelId,
      },
    });
    // this.doFetchData();
  }

  doEdit(modelId: string) {
    router.push(`/modules/editor/${modelId}`);
  }

  doLoadXmlByModelId(modelId) {
    window.open(`/server/rest/model/loadXmlByModelId/${modelId}`);
  }

  doLoadPngByModelId(modelId) {
    window.open(`/server/rest/model/loadPngByModelId/${modelId}`);
  }

  render() {
    const { modules } = this.props;
    const _that = this;
    const modelTypes = { 0: 'BPMN', 2: 'FORM', 3: 'APP', 4: 'DECISION_TABLE', 5: 'CMMN' };
    const columns = [
      {
        title: '操作',
        key: 'operation',
        // fixed: 'right',
        width: 250,
        render: (text, record) =>
          modules.length >= 1 ? (
            <div>
              <Popconfirm title="确定要部署吗?" onConfirm={() => this.doDeploy(record.id)}>
                <a>部署</a>
              </Popconfirm>
              &nbsp;&nbsp;
              <a onClick={() => this.doLoadXmlByModelId(record.id)}>查看XML</a>
              &nbsp;&nbsp;
              <a onClick={() => this.doLoadPngByModelId(record.id)}>查看图片</a>
            </div>
          ) : null,
      },
      {
        title: '类型',
        dataIndex: 'modelType',
        key: 'modelType',
        render: (text, record) => modelTypes[text],
        width: 100,
      },
      {
        title: '名称',
        width: 100,
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: 'Key',
        width: 80,
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
        width: 100,
      },
      {
        title: '创建人',
        dataIndex: 'createdBy',
        key: 'createdBy',
        width: 80,
      },
      {
        title: '创建时间',
        dataIndex: 'created',
        key: 'created',
        render: text => <div>{moment(text).format('YYYY-MM-DD HH:MM:SS')} </div>,
        width: 150,
      },
    ];

    const props = {
      name: 'file',
      action: '/server/rest/model/import-process-model',
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
    const paginationProps = {
      showSizeChanger: true,
      showQuickJumper: true,
      showTotal: (total: number) => `共 ${total} 条数据`,
    };
    return (
      <PageHeaderWrapper title={null}>
        <Card>
          <div className={styles.toolBar}>
            <Upload {...props}>
              <Button type="primary">
                <Icon type="upload" />
                导入模板
              </Button>
            </Upload>
          </div>
          <Table
            bordered
            rowKey="id"
            columns={columns}
            dataSource={modules}
            pagination={paginationProps}
          />
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default ModulesList;
