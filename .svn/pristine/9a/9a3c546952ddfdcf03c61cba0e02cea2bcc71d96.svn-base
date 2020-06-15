<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">

    <title><sitemesh:write property='title'/></title>

    <!-- Bootstrap -->
    <link href="/webjars/gentelella/1.4.0/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="/webjars/gentelella/1.4.0/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
      
    <!-- Page Head -->
    <sitemesh:write property='head'/>
    <!-- /Page Head -->
    
    <!-- Custom Theme Style -->
    <!-- <link href="/webjars/gentelella/1.4.0/build/css/custom.min.css" rel="stylesheet"> -->
    <link href="/webjars/gentelella/1.4.0/build/css/custom.min.css" rel="stylesheet">
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col menu_fixed">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <a href="/" class="site_title"><i class="fa fa-paper-plane"></i> <span>ENIO API</span></a>
            </div>

            <div class="clearfix"></div>

            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
                
                <ul class="nav side-menu">
                  <li><a href="/events"><i class="fa fa-desktop"></i> 송수신 이벤트 로그 </a></li>
                  <!-- <li><a><i class="fa fa-warning"></i> 오류 발생 현황 </a></li> -->
                  <li><a href="/logs"><i class="fa fa-folder-open"></i> 시스템 로그 파일 </a></li>
                  <li><a><i class="fa fa-gear"></i> 시스템 설정 </a></li>
                  <!-- <li><a><i class="fa fa-gear"></i> 시스템 설정 </a></li> -->
                </ul>
              </div>
            </div>
            <!-- /sidebar menu -->

            <!-- /menu footer buttons -->
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Logout">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              </a>
            </div>
            <!-- /menu footer buttons -->
          </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
          <div class="nav_menu">
            <nav class="" role="navigation">
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>

              <ul class="nav navbar-nav navbar-right">
                <li class="">
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                    ENIO API
                    <span class=" fa fa-angle-down"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-usermenu pull-right">
                    <!-- <li><a href="javascript:;"> Profile</a></li>
                    <li>
                      <a href="javascript:;">
                        <span class="badge bg-red pull-right">50%</span>
                        <span>Settings</span>
                      </a>
                    </li>
                     -->
                    <li><a href="javascript:;">Help</a></li>
                    <li><a href="javascript:;" data-id="logout"><i class="fa fa-sign-out pull-right"></i> Log Out</a></li>
                  </ul>
                </li>
              </ul>
            </nav>
          </div>
        </div>
        <!-- /top navigation -->

        <sitemesh:write property='body'/>
        <form action="/logout" method="post" id="logoutForm">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
        <!-- footer content -->
        <footer>
          <div class="pull-right">
            COPYRIGHT Ⓒ <a href="https://colorlib.com">INOGARD.</a> ALL RIGHTS RESERVED.
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
      
    </div>

    <!-- jQuery -->
    <script src="/webjars/gentelella/1.4.0/vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="/webjars/gentelella/1.4.0/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="/webjars/gentelella/1.4.0/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="/webjars/gentelella/1.4.0/vendors/nprogress/nprogress.js"></script>
    
    <!-- Page Script External -->
    <sitemesh:write property="page.script-external"/>
    <!-- /Page Script External -->
    
    <!-- Custom Theme Scripts -->
    <script src="/webjars/gentelella/1.4.0/build/js/custom.min.js"></script>
    
    <!-- enio Global -->
    <script src="/js/enio.js"></script>
    <script>
      (function(enio, $, window, document) {
        $.ajaxPrefilter(function(options, originalOptions, jqXHR) {
          var token = $("meta[name='_csrf']").attr("content");
          var header = $("meta[name='_csrf_header']").attr("content");
          jqXHR.setRequestHeader(header, token);
        });

        $.fn.dataTable.ext.buttons.reload = {
          text: 'Reload',
          action: function(e, dt, node, config) {
            dt.ajax.reload();
          }
        };

        var masterPage = {
          load: function() {
            $("a[data-id='logout']").on("click", masterPage.btnLogOutClick);
          },
          btnLogOutClick: function() {
            if (!confirm("로그아웃 하시겠습니까?")) { return false; }
            $("#logoutForm").submit();
          }
        };

        $(masterPage.load);
      }(window.enio || {}, window.jQuery, window, document));
    </script>
    <!-- Page Script -->
    <sitemesh:write property="page.script"/>
    <!-- /Page Script -->
  </body>
</html>