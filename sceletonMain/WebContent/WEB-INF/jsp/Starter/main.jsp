<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>Search Results - Ace Admin</title>

<meta name="description" content="" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="assets/font-awesome/4.5.0/css/font-awesome.min.css" />

<!-- page specific plugin styles -->
<link rel="stylesheet" href="assets/css/select2.min.css" />
<link rel="stylesheet" href="assets/css/jquery-ui.custom.min.css" />

<!-- text fonts -->
<link rel="stylesheet" href="assets/css/fonts.googleapis.com.css" />

<!-- ace styles -->
<link rel="stylesheet" href="assets/css/ace.min.css"
	class="ace-main-stylesheet" id="main-ace-style" />

<!--[if lte IE 9]>
			<link rel="stylesheet" href="assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->
<link rel="stylesheet" href="assets/css/ace-skins.min.css" />
<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />

<!--[if lte IE 9]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

<!-- inline styles related to this page -->

<!-- ace settings handler -->
<script src="assets/js/ace-extra.min.js"></script>

<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

<!--[if lte IE 8]>
		<script src="assets/js/html5shiv.min.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
</head>

<body class="no-skin">
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.loadState('main-container')
			} catch (e) {
			}
		</script>



		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">

					<div class="nav-search" id="nav-search">
						<form class="form-search">
							<span class="input-icon"> <input type="text"
								placeholder="Search ..." class="nav-search-input"
								id="nav-search-input" autocomplete="off" /> <i
								class="ace-icon fa fa-search nav-search-icon"></i>
							</span>
						</form>
					</div>
					<!-- /.nav-search -->
				</div>

				<div class="page-content">
					<div>
						<div class="row search-page" id="search-page-1">
							<div class="col-xs-12">
								<div class="row">
									<div class="col-xs-12 col-sm-12"></div>

									<div class="row">

										<c:forEach var="rssList" items="${rssItems}">
										
											<div class="col-xs-6 col-sm-4 col-md-3">
												<div class="thumbnail search-thumbnail">
												
												roo ${rssList.imageUrl} khm
													<img class="media-object"
														data-src="${rssList.imageUrl}" />
													<div class="caption">
														<div class="clearfix">
															<span class="pull-right label label-grey info-label">${rssList.source}</span>
														</div>

														<h3 class="search-title">
															<a href="${rssList.url}" target="_blank" class="blue">${rssList.title}</a>
														</h3>
														<p>${fn:substring(rssList.description, 0, 50)}...</p>
														
													</div>
												</div>
											</div>
										</c:forEach>


									</div>


								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="hide">
					<div class="row search-page" id="search-page-2">
						<div class="col-xs-12 col-md-10 col-md-offset-1">
							<div class="search-area well no-margin-bottom">
								<form>
									<div class="row">
										<div class="col-md-6">
											<div class="input-group">
												<input type="text" class="form-control" name="search"
													value="Hello World" />
												<div class="input-group-btn">
													<button type="button" class="btn btn-primary btn-sm">
														<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
													</button>
												</div>
											</div>
										</div>
									</div>
								</form>

								<div class="space space-6"></div>
								<span class="grey">About 263,000,000 results (0.74
									seconds)</span>
							</div>

							<div class="search-results">
								<div class="search-result">
									<h5 class="search-title">
										<a href="#">&quot;Hello, World!&quot; - Wikipedia, the
											free encyclopedia</a>
									</h5>
									<a class="text-success" href="#">en.wikipedia.org</a>

									<p class="search-content">
										A &quot; <b>Hello</b> , <b>World</b> !&quot; program is a
										computer program that outputs &quot; <b>Hello</b> , <b>World</b>!&quot;
										(or some variant thereof) on a display device. Because it is
										typically one of the ...
									</p>
								</div>

								<div class="search-result">
									<h5 class="search-title">
										<a href="#">Hello World! - GNU Project</a>
									</h5>
									<a class="text-success" href="#">www.gnu.org</a>

									<p class="search-content">
										<b>Hello World</b> ! How the way people code “ <b>Hello
											World</b>” varies depending on their age and job ...
									</p>
								</div>

								<div class="search-result">
									<h5 class="search-title">
										<a href="#">HelloWorld.java - Introduction to Programming
											in Java</a>
									</h5>
									<a class="text-success" href="#">introcs.cs.princeton.edu</a>

									<p class="search-content">
										<b>HelloWorld</b> code in Java. ... <b>HelloWorld</b> .java.
										Below is the syntax highlighted version of <b>HelloWorld</b>.java
										from ...
									</p>
								</div>

								<div class="search-result">
									<h5 class="search-title">
										<a href="#">Hello, World! - Learn Python - Free
											Interactive Python Tutorial</a>
									</h5>
									<a class="text-success" href="#">www.learnpython.org</a>

									<p class="search-content">
										<b>Hello</b> , <b>World</b>! Python is a very simple language,
										and has a very straightforward syntax. It encourages
										programmers to program without boilerplate (prepared) ...
									</p>
								</div>

								<div class="search-result">
									<h5 class="search-title">
										<a href="#">Hello World · GitHub Guides</a>
									</h5>
									<a class="text-success" href="#">guides.github.com</a>

									<p class="search-content">
										The <b>Hello World</b> project is a time-honored tradition in
										computer programming. It is a simple exercise that gets you
										started when learning something new. Let's get ...
									</p>
								</div>
							</div>




						</div>
					</div>
				</div>

				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.page-content -->
	</div>
	</div>
	<!-- /.main-content -->

	<div class="footer">
		<div class="footer-inner">
			<div class="footer-content">
				<span class="bigger-120"> <span class="blue bolder">Ace</span>
					Application &copy; 2013-2014
				</span> &nbsp; &nbsp; <span class="action-buttons"> <a href="#">
						<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
				</a> <a href="#"> <i
						class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
				</a> <a href="#"> <i
						class="ace-icon fa fa-rss-square orange bigger-150"></i>
				</a>
				</span>
			</div>
		</div>
	</div>

	<a href="#" id="btn-scroll-up"
		class="btn-scroll-up btn btn-sm btn-inverse"> <i
		class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
	</a>
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->

	<!--[if !IE]> -->
	<script src="assets/js/jquery-2.1.4.min.js"></script>

	<!-- <![endif]-->

	<!--[if IE]>
<script src="assets/js/jquery-1.11.3.min.js"></script>
<![endif]-->
	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document
					.write("<script src='assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>
	<script src="assets/js/bootstrap.min.js"></script>

	<!-- page specific plugin scripts -->
	<script src="assets/js/tree.min.js"></script>
	<script src="assets/js/select2.min.js"></script>
	<script src="assets/js/jquery-ui.custom.min.js"></script>
	<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
	<script src="assets/js/holder.min.js"></script>

	<!-- ace scripts -->
	<script src="assets/js/ace-elements.min.js"></script>
	<script src="assets/js/ace.min.js"></script>

	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		jQuery(function($) {

			//data for tree element
			var category = {
				'for-sale' : {
					text : 'For Sale',
					type : 'folder'
				},
				'vehicles' : {
					text : 'Vehicles',
					type : 'item'
				},
				'rentals' : {
					text : 'Rentals',
					type : 'item'
				},
				'real-estate' : {
					text : 'Real Estate',
					type : 'item'
				},
				'pets' : {
					text : 'Pets',
					type : 'item'
				},
				'tickets' : {
					text : 'Tickets',
					type : 'item'
				}
			}
			category['for-sale']['additionalParameters'] = {
				'children' : {
					'appliances' : {
						text : 'Appliances',
						type : 'item'
					},
					'arts-crafts' : {
						text : 'Arts & Crafts',
						type : 'item'
					},
					'clothing' : {
						text : 'Clothing',
						type : 'item'
					},
					'computers' : {
						text : 'Computers',
						type : 'item'
					},
					'jewelry' : {
						text : 'Jewelry',
						type : 'item'
					},
					'office-business' : {
						text : 'Office',
						type : 'item'
					},
					'sports-fitness' : {
						text : 'Sports & Fitness',
						type : 'item'
					}
				}
			}

			var dataSource1 = function(options, callback) {
				var $data = null
				if (!("text" in options) && !("type" in options)) {
					$data = category;//the root tree
					callback({
						data : $data
					});
					return;
				} else if ("type" in options && options.type == "folder") {
					if ("additionalParameters" in options
							&& "children" in options.additionalParameters)
						$data = options.additionalParameters.children || {};
					else
						$data = {}//no data
				}

				callback({
					data : $data
				})
			}

			$('#cat-tree')
					.ace_tree(
							{
								dataSource : dataSource1,
								multiSelect : true,
								cacheItems : true,
								'open-icon' : 'ace-icon tree-minus',
								'close-icon' : 'ace-icon tree-plus',
								'itemSelect' : true,
								'folderSelect' : false,
								'selected-icon' : 'ace-icon fa fa-check',
								'unselected-icon' : 'ace-icon fa fa-times',
								loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>'
							});

			$('.tree-container').ace_scroll({
				size : 250,
				mouseWheelLock : true
			});
			$('#cat-tree').on(
					'closed.fu.tree disclosedFolder.fu.tree',
					function() {
						$('.tree-container').ace_scroll('reset').ace_scroll(
								'start');
					});

			//select2 location element
			$('.select2').css('min-width', '150px').select2({
				allowClear : true
			});

			//jQuery ui distance slider
			$("#slider-range")
					.css('width', '150px')
					.slider(
							{
								range : true,
								min : 0,
								max : 100,
								values : [ 17, 67 ],
								slide : function(event, ui) {
									var val = ui.values[$(ui.handle).index() - 1]
											+ "";

									if (!ui.handle.firstChild) {
										$(
												"<div class='tooltip bottom in' style='display:none;left:-6px;top:14px;'><div class='tooltip-arrow'></div><div class='tooltip-inner'></div></div>")
												.prependTo(ui.handle);
									}
									$(ui.handle.firstChild).show().children()
											.eq(1).text(val);
								}
							}).find('span.ui-slider-handle').on('blur',
							function() {
								$(this.firstChild).hide();
							});

			//this is for demo only
			$('.thumbnail').on('mouseenter', function() {
				$(this).find('.info-label').addClass('label-primary');
			}).on('mouseleave', function() {
				$(this).find('.info-label').removeClass('label-primary');
			});

			//toggle display format buttons
			$('#toggle-result-format .btn').tooltip({
				container : 'body'
			}).on(
					'click',
					function(e) {
						$(this).siblings().each(
								function() {
									$(this).removeClass(
											$(this).attr('data-class'))
											.addClass('btn-grey');
								});
						$(this).removeClass('btn-grey').addClass(
								$(this).attr('data-class'));
					});

			////////////////////
			//show different search page
			$('#toggle-result-page .btn').on('click', function(e) {
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				$('.search-page').parent().addClass('hide');
				$('#search-page-' + which).parent().removeClass('hide');
			});
		});
	</script>


</body>
</html>
