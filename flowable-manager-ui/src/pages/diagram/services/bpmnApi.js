import request from '@/utils/request';

// 获取字典信息
export async function getPageUseDict(params) {
  return request(`/api/user/dict`, {
    method: 'POST',
    body: params,
  });
}

// 流程信息列表
export async function getFlowList(params) {
  return request(`/api/activiti/list`, {
    method: 'POST',
    body: params,
  });
}
// 新增编辑流程
export async function updateFlow(params) {
  return request(`/api/activiti/save`, {
    method: 'POST',
    body: params,
  });
}
// 复制流程
export async function copyFlow(params) {
  return request(`/api/activiti/copy`, {
    method: 'POST',
    body: params,
  });
}
// 删除流程
export async function deleteFlow(params) {
  return request(`/api/activiti/delete`, {
    method: 'POST',
    body: params,
  });
}
// 查询某个现有的流程设计模板
export async function findBPMNById(params) {
  // return request(`/api/activiti/find/bpmn`, {
  //     method: 'POST',
  //     body: params,
  // });
  // /server/rest/definition/processFile/xml/leave:1:927547b517f411ea96b4dc8b287b3603
  // 流程模板编辑使用此请求：/server/rest/model/loadXmlByModelId/{id}
  // 流程定义使用此请求：/server/rest/definition/processFile/xml/{id}
  return request(`/server/rest/model/loadXmlByModelId/${params.id}`);

  // return {
  //   code: 200,
  //   msg: 'success',
  //   data:
  //     '<bpmn2:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:camunda="http://camunda.org/schema/1.0/bpmn" id="sample-diagram" targetNamespace="http://bpmn.io/schema/bpmn" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd">\n  <bpmn2:process id="Process_1" isExecutable="true">\n    <bpmn2:startEvent id="StartEvent_1" name="开始">\n      <bpmn2:outgoing>SequenceFlow_1ppkpq3</bpmn2:outgoing>\n    </bpmn2:startEvent>\n    <bpmn2:sequenceFlow id="SequenceFlow_1ppkpq3" sourceRef="StartEvent_1" targetRef="Task_1lkfsqj" />\n    <bpmn2:exclusiveGateway id="ExclusiveGateway_1xtfjke">\n      <bpmn2:incoming>SequenceFlow_1gevrbq</bpmn2:incoming>\n      <bpmn2:outgoing>SequenceFlow_1l4ns00</bpmn2:outgoing>\n      <bpmn2:outgoing>SequenceFlow_187attt</bpmn2:outgoing>\n    </bpmn2:exclusiveGateway>\n    <bpmn2:sequenceFlow id="SequenceFlow_1gevrbq" sourceRef="Task_1lkfsqj" targetRef="ExclusiveGateway_1xtfjke" />\n    <bpmn2:sequenceFlow id="SequenceFlow_1l4ns00" name="条件1" sourceRef="ExclusiveGateway_1xtfjke" targetRef="Task_0o2mqeu">\n      <bpmn2:conditionExpression xsi:type="bpmn2:tFormalExpression">${x===1}</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:userTask id="Task_1lkfsqj" name="发起人">\n      <bpmn2:incoming>SequenceFlow_1ppkpq3</bpmn2:incoming>\n      <bpmn2:outgoing>SequenceFlow_1gevrbq</bpmn2:outgoing>\n    </bpmn2:userTask>\n    <bpmn2:userTask id="Task_0o2mqeu" name="用户1" camunda:candidateUsers="11af0f0d9aa140bcb73b2a515277d67d" camunda:candidateGroups="ICPR" camunda:node_belong="2" camunda:node_type="2">\n      <bpmn2:incoming>SequenceFlow_1l4ns00</bpmn2:incoming>\n      <bpmn2:outgoing>SequenceFlow_14btawc</bpmn2:outgoing>\n    </bpmn2:userTask>\n    <bpmn2:sequenceFlow id="SequenceFlow_187attt" name="条件2" sourceRef="ExclusiveGateway_1xtfjke" targetRef="Task_1w1khln">\n      <bpmn2:conditionExpression xsi:type="bpmn2:tFormalExpression">${x!==1}</bpmn2:conditionExpression>\n    </bpmn2:sequenceFlow>\n    <bpmn2:userTask id="Task_1w1khln" name="用户2" camunda:candidateUsers="01d34faef5a34f7986057b57e7418f17" camunda:candidateGroups="ICPR" camunda:node_type="1" camunda:node_belong="1">\n      <bpmn2:incoming>SequenceFlow_187attt</bpmn2:incoming>\n      <bpmn2:outgoing>SequenceFlow_04676ky</bpmn2:outgoing>\n    </bpmn2:userTask>\n    <bpmn2:endEvent id="EndEvent_129o9ek" name="结束">\n      <bpmn2:incoming>SequenceFlow_14btawc</bpmn2:incoming>\n      <bpmn2:incoming>SequenceFlow_04676ky</bpmn2:incoming>\n    </bpmn2:endEvent>\n    <bpmn2:sequenceFlow id="SequenceFlow_14btawc" sourceRef="Task_0o2mqeu" targetRef="EndEvent_129o9ek" />\n    <bpmn2:sequenceFlow id="SequenceFlow_04676ky" sourceRef="Task_1w1khln" targetRef="EndEvent_129o9ek" />\n  </bpmn2:process>\n  <bpmndi:BPMNDiagram id="BPMNDiagram_1">\n    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">\n      <bpmndi:BPMNShape id="_BPMNShape_StartEvent_2" bpmnElement="StartEvent_1">\n        <dc:Bounds x="412" y="240" width="36" height="36" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x="419" y="283" width="22" height="14" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge id="SequenceFlow_1ppkpq3_di" bpmnElement="SequenceFlow_1ppkpq3">\n        <di:waypoint x="448" y="258" />\n        <di:waypoint x="500" y="258" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id="ExclusiveGateway_1xtfjke_di" bpmnElement="ExclusiveGateway_1xtfjke" isMarkerVisible="true">\n        <dc:Bounds x="655" y="233" width="50" height="50" />\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge id="SequenceFlow_1gevrbq_di" bpmnElement="SequenceFlow_1gevrbq">\n        <di:waypoint x="600" y="258" />\n        <di:waypoint x="655" y="258" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id="SequenceFlow_1l4ns00_di" bpmnElement="SequenceFlow_1l4ns00">\n        <di:waypoint x="680" y="233" />\n        <di:waypoint x="680" y="160" />\n        <di:waypoint x="720" y="160" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x="683" y="194" width="28" height="14" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id="UserTask_0abwwwf_di" bpmnElement="Task_1lkfsqj">\n        <dc:Bounds x="500" y="218" width="100" height="80" />\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id="UserTask_1ogim57_di" bpmnElement="Task_0o2mqeu">\n        <dc:Bounds x="720" y="120" width="100" height="80" />\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge id="SequenceFlow_187attt_di" bpmnElement="SequenceFlow_187attt">\n        <di:waypoint x="680" y="283" />\n        <di:waypoint x="680" y="350" />\n        <di:waypoint x="720" y="350" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x="681" y="314" width="28" height="14" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id="UserTask_1e63bml_di" bpmnElement="Task_1w1khln">\n        <dc:Bounds x="720" y="310" width="100" height="80" />\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id="EndEvent_129o9ek_di" bpmnElement="EndEvent_129o9ek">\n        <dc:Bounds x="912" y="240" width="36" height="36" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x="920" y="283" width="22" height="14" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge id="SequenceFlow_14btawc_di" bpmnElement="SequenceFlow_14btawc">\n        <di:waypoint x="820" y="160" />\n        <di:waypoint x="866" y="160" />\n        <di:waypoint x="866" y="258" />\n        <di:waypoint x="912" y="258" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id="SequenceFlow_04676ky_di" bpmnElement="SequenceFlow_04676ky">\n        <di:waypoint x="820" y="350" />\n        <di:waypoint x="866" y="350" />\n        <di:waypoint x="866" y="258" />\n        <di:waypoint x="912" y="258" />\n      </bpmndi:BPMNEdge>\n    </bpmndi:BPMNPlane>\n  </bpmndi:BPMNDiagram>\n</bpmn2:definitions>\n',
  // };
}
// 新增编辑流程设计模板
export async function updateBPMN(params) {
  return request(`/server/rest/model/addModel`, {
    method: 'POST',
    requestType: 'json',
    data: params,
  });
}
// 初始化流程下拉框
export async function initFlowSelect(params) {
  // return request(`/api/activiti/init`, {
  //     method: 'POST',
  //     body: params,
  // });
  return {
    code: 200,
    msg: 'success',
    data: {
      condition: [
        {
          id: '1',
          name: '融资金额',
          script: '${ amount #opt# #value# }',
        },
        {
          id: '2',
          name: '审批结果',
          script: "${ result #opt# '#value#' }",
        },
      ],
      node_type: [
        {
          id: '92776258b82211e99ceb00155d018024',
          code: 'node_type',
          value: '1',
          label: '审批节点',
          rank: 1,
        },
        {
          id: '92776258b82211e99ceb00155d018025',
          code: 'node_type',
          value: '2',
          label: '提交节点',
          rank: 2,
        },
      ],
      node_belong: [
        {
          id: '92776258b82211e99ceb00155d018024',
          code: 'node_belong',
          value: '1',
          label: '业务',
          rank: 1,
        },
        {
          id: '92776258b82211e99ceb00155d018025',
          code: 'node_belong',
          value: '2',
          label: '合同',
          rank: 2,
        },
      ],
      rule_operator: [
        {
          id: '92776258b82211e99ceb00155d018018',
          code: 'rule_operator',
          value: 'eq',
          label: '等于',
          rank: 1,
        },
        {
          id: '92776258b82211e99ceb00155d018019',
          code: 'rule_operator',
          value: 'ne',
          label: '不等于',
          rank: 2,
        },
      ],
      roles: [
        {
          id: 'ASMG',
          name: '资产管理专员',
        },
        {
          id: 'ICPR',
          name: '业务人员',
        },
      ],
      users: [
        {
          id: '01d34faef5a34f7986057b57e7418f17',
          name: '梓悦-资管',
        },
        {
          id: '11af0f0d9aa140bcb73b2a515277d67d',
          name: '韩少卯',
        },
      ],
    },
  };
}
// 流程跟踪数据
export async function getProcessData(params) {
  return request(`/api/order/history`, {
    method: 'POST',
    body: params,
  });
}
