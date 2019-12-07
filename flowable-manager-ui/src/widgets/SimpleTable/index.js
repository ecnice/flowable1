import React, { PureComponent } from 'react';
import { Table } from 'antd';
import styles from './index.less';

class SimpleTable extends PureComponent {
  handleTableChange = (pagination, filters, sorter) => {
    const { onChange } = this.props;
    if (onChange) {
      onChange(pagination, filters, sorter);
    }
  };

  render() {
    const { data = {}, pagination, scroll, rowKey, ...rest } = this.props;
    const { list = [], pageNo, pageSize, total } = data;
    const paginationProps = {
      showSizeChanger: true,
      showQuickJumper: true,
      current: pageNo,
      pageSize,
      total,
    };
    return (
      <div className={styles.simpleTable}>
        <Table
          rowKey={rowKey || 'key'}
          dataSource={list}
          pagination={pagination === false || paginationProps}
          onChange={this.handleTableChange}
          scroll={scroll || { x: '100%' }}
          {...rest}
        />
      </div>
    );
  }
}

export default SimpleTable;
