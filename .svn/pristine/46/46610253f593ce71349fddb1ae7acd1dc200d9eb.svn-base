<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <title>ENIO API</title>
  <!-- iCheck -->
  <link href="/webjars/gentelella/1.4.0/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
  <!-- Datatables -->
  <link href="/webjars/gentelella/1.4.0/vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
  <link href="/webjars/gentelella/1.4.0/vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
  <link href="/webjars/gentelella/1.4.0/vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
  <link href="/webjars/gentelella/1.4.0/vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
  <link href="/webjars/gentelella/1.4.0/vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
  <style type="text/css">
    td.details-control {
      background: url('/images/details_open.png') no-repeat center center;
      cursor: pointer;
    }
    
    tr.shown td.details-control {
      background: url('/images/details_close.png') no-repeat center center;
    }
    
    tr.loading td {
      text-align: center;
    }
  </style>
</head>
<body>
  <!-- page content -->
  <div class="right_col" role="main">
    <div class="">
      <div class="page-title">
        <div class="title_left">
          <h3>송수신 이벤트 로그 <small></small></h3>
        </div>

        <!-- <div class="title_right">
          <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
            <div class="input-group">
              <input type="text" class="form-control" placeholder="Search for...">
              <span class="input-group-btn">
                <button class="btn btn-default" type="button">Go!</button>
              </span>
            </div>
          </div>
        </div> -->
      </div>

      <div class="clearfix"></div>

      <div class="row">
        <div class="col-md-12 col-sm-12 col-xs-12">
          <div class="x_panel">
            <div class="x_title">
              <h2>송수신 이벤트 로그 <small>데이터 송신 및 수신시 요청 및 응답 정보를 기록하는 로그정보입니다.</small></h2>
              <ul class="nav navbar-right panel_toolbox">
                <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                </li>
                <li><a href="javascript:location.reload(true);"><i class="fa fa-refresh"></i></a>
                <li><a class="close-link"><i class="fa fa-close"></i></a>
                </li>
              </ul>
              <div class="clearfix"></div>
            </div>
            <div class="x_content">
              <div class="well" style="overflow: auto">
                <div class="form-horizontal">
                  <div class="form-group">
                    <label class="control-label col-md-2 col-sm-2 col-xs-12">구분 : </label>
                    <div class="col-md-4 col-sm-4 col-xs-12">
                      <select id="optEvtIoType" class="form-control">
                        <option value="">전체</option>
                        <c:forEach var="evtIOType" items="${evtIOTypes}">
                          <option value="${evtIOType }"><c:out value="${evtIOType.codeNm}" /></option>
                        </c:forEach>
                      </select>
                    </div>
                    <label class="control-label col-md-2 col-sm-2 col-xs-12">요청상태 : </label>
                    <div class="col-md-4 col-sm-4 col-xs-12">
                      <select id="optEvtSt" class="form-control">
                        <option value="">전체</option>
                        <c:forEach var="evtSt" items="${evtSts}">
                          <option value="${evtSt }"><c:out value="${evtSt.codeNm}" /></option>
                        </c:forEach>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="control-label col-md-2 col-sm-2 col-xs-12">에이전트 : </label>
                    <div class="col-md-4 col-sm-4 col-xs-12">
                      <select id="optAgtCd" class="form-control">
                        <option value="">전체</option>
                        <c:forEach var="agent" items="${agents}">
                          <option value="${agent.agtCd }"><c:out value="${agent.installOrgNm}" /></option>
                        </c:forEach>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
              <table id="tblEvents" class="table table-striped table-bordered table-hover" style="width: 100%">
                <thead>
                  <tr>
                    <th></th>
                    <th>No.</th>
                    <th>구분</th>
                    <th>요청 URL</th>
                    <th>요청상태</th>
                    <th>요청일시</th>
                    <th>처리결과</th>
                    <th>처리메시지</th>
                    <th>수신일시</th>
                    <th>에이전트코드</th>
                    <th>타입</th>
                  </tr>
                </thead>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- /page content -->
  <content tag="script-external">
    <!-- Datatables -->
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/jszip/dist/jszip.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="/webjars/gentelella/1.4.0/vendors/pdfmake/build/vfs_fonts.js"></script>
    <script src="/js/jquery.spring-friendly.js"></script>
    <script src="/js/jsrender/jsrender.min.js"></script>
  </content>
  <script id="eventDetailTmpl" type="text/x-jsrender">
    <div class="x_panel">
      <table class="table table-bordered">
        <colgroup>
          <col style="width: 100px;">
          <col>
        </colgroup>
        <tbody>
          <tr>
            <th class="active text-right" style="vertical-align: middle;">요청상세</th>
            <td><pre>{{:requestDetails}}</pre></td>
          </tr>
          <tr>
            <th class="active text-right" style="vertical-align: middle;">응답상세</th>
            <td><pre>{{:responseDetails}}</pre></td>
          </tr>
        </tbody>
      </table>
    </div>
  </script>
  <content tag="script">
    <script>
      (function(enio, $, window, document) {
        var page = {
          load: function() {
            var $dtEvents = (function() {
              var $table = $("#tblEvents");
              var $template = $.templates("#eventDetailTmpl");
              var $optEvtIoType = $("#optEvtIoType");
              var $optEvtSt = $("#optEvtSt");
              var $optAgtCd = $("#optAgtCd");
              var $dataTable = $table.DataTable({
                processing: true,
                serverSide: true,
                ajax: {
                  url: "/events",
                  type: "GET",
                  data: function(d) {
                    d.evtIoType = $optEvtIoType.val();
                    d.evtSt = $optEvtSt.val();
                    d.agtCd = $optAgtCd.val();
                  }
                },
                order: [
                  [5, "desc"]
                ],
                columns: [{
                  className: "details-control",
                  orderable: false,
                  data: null,
                  defaultContent: "",
                  width: "25"
                }, {
                  data: "evtNo",
                  name: "evt_No",
                  visible: false
                }, {
                  data: "evtIoType",
                  name: "evt_io_type",
                  render: function(data, type, row) {
                    var result = "수신";
                    if ("OUT" === data) {
                      result = "송신";
                    }
                    return result;
                  },
                  className: "text-center"
                }, {
                  data: "evtCmdParam",
                  name: "evt_cmd_param",
                  render: function(data, type, row) {
                    var data = JSON.parse(data);
                    return data.uri;
                  }
                }, {
                  data: "evtSt",
                  name: "evt_st",
                  render: function(data, type, row) {
                    var result = "처리완료";
                    if ("R" === data) {
                      result = "대기";
                    } else if ("W" === data) {
                      result = "실행";
                    }
                    return result;
                  },
                  className: "text-center"
                }, {
                  data: "regDt",
                  name: "reg_dt",
                  className: "text-center"
                }, {
                  data: "rsltCd",
                  name: "rslt_cd",
                  render: function(data, type, row) {
                    var result = "정상";
                    if ("ERR0000" === data) {
                      result = "처리불가";
                    } else if ("ERR1000" === data) {
                      result = "실행시간오류";
                    } else if ("ERR2000" === data) {
                      result = "내부DB관련오류";
                    } else if ("ERR3000" === data) {
                      result = "네트워크에러";
                    } else if ("ERR9999" === data) {
                      result = "알수없는오류";
                    } else if ("UNKNOWN" === data) {
                      result = "알수없는응답";
                    }
                    return result;
                  },
                  className: "text-center"
                }, {
                  data: "rsltMsg",
                  name: "rslt_msg",
                  visible: false
                }, {
                  data: "rcvDt",
                  name: "rcv_dt",
                  className: "text-center"
                }, {
                  data: "agtCd",
                  name: "agt_cd",
                  visible: false
                }, {
                  data: "scheKind",
                  name: "sche_kind",
                  render: function(data, type, row) {
                    var result = "스케쥴";
                    if ("R" === data) {
                      result = "런타임";
                    }
                    return result;
                  },
                  className: "text-center"
                }],
                dom: "<'row'<'col-sm-6'l><'col-sm-6'f>>" +
                "<'row'<'col-sm-12'B>>" +
                "<'row'<'col-sm-12'tr>>" +
                "<'row'<'col-sm-5'i><'col-sm-7'p>>",
                buttons: [{
                  extend: "reload",
                  className: "btn-sm"
                }]
              });
  
              function format(d) {
                var $htmlOutput = $('<div/>').addClass('loading').text('Loading...');
                $.ajax({
                  url: '/events/' + d.evtNo,
                  type: "GET",
                  dataType: 'json',
                  success: function(data) {
                    data.requestDetails = JSON.stringify($.extend({}, JSON.parse(data.evtCmdParam), JSON.parse(data.reqCntn)), undefined, 2);
                    data.responseDetails = JSON.stringify(JSON.parse(data.rcvCntn), undefined, 2);
                    $htmlOutput.html($template.render(data)).removeClass('loading');
                  }
                });
                return $htmlOutput;
              }
  
              $table.find("tbody").on('click', 'td.details-control', function() {
                var tr = $(this).closest('tr');
                var row = $dataTable.row(tr);
  
                if (row.child.isShown()) {
                  // This row is already open - close it
                  row.child.hide();
                  tr.removeClass('shown');
                } else {
                  // Open this row
                  row.child(format(row.data())).show();
                  tr.addClass('shown');
                }
              });
              
              $optEvtIoType.on("change", function() {
                $dataTable.draw();
              });
              
              $optEvtSt.on("change", function() {
                $dataTable.draw();
              });
              
              $optAgtCd.on("change", function() {
                $dataTable.draw();
              });
  
              return $dataTable;
            }());
          }
        };
        $(page.load);
      }(window.enio || {}, window.jQuery, window, document));
    </script>
  </content>
</body>
</html>