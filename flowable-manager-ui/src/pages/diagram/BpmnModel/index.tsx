import React, { Component } from 'react';
import { Card } from 'antd';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import ReactBpmn from 'react-bpmn';
import BpmnJS from 'bpmn-js';
import myProcess from '../../../assets/test-getway.bpmn';

class BpmnModel extends Component<any, any> {
  componentDidMount() {
    // this.viewer = new BpmnJS({
    //   container: '#canvas',
    //   height: 400,
    // });
    //
    // debugger;
    // this.viewer.importXML(myProcess, function(err) {
    //
    //   if (err) {
    //     console.log('error rendering: ', err)
    //   } else {
    //     console.log('rendered:')
    //   }
    // });
  }

  onShown(e) {
    debugger;
  }

  onLoading(e) {
    debugger;
  }

  onError() {}

  render() {
    return (
      <PageHeaderWrapper title={null}>
        <Card>Header</Card>
        <Card>
          <ReactBpmn
            height="1000"
            url={myProcess}
            onShown={this.onShown}
            onLoading={this.onLoading}
            onError={this.onError}
          />
        </Card>
      </PageHeaderWrapper>
    );
  }
}

export default BpmnModel;
