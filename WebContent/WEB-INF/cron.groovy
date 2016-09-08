import de.michaelkuerbis.presenter.servlets.SettingsServlet;
import de.michaelkuerbis.presenter.utils.CastConnection;

html.div {
	div( class:"container", id:"cron", style:"display:none;"){
		div(class:"col-xs-12"){
			div( class:"page-header"){ h1('add cronjobs') }
		}
		div(class:"col-xs-12"){
			div(class:"form-horizontal"){

				div( class:"form-group"){
					label(class:"col-sm-2 control-label", "Description:")
					div( class:"col-sm-10"){
						input( type:"text",class:"form-control", id:"name", placeholder:"description")
					}
				}

				div( class:"form-group"){
					label(class:"col-sm-2 control-label", "Receiver:")
					div( class:"col-sm-10"){
						select(id:"receiver-ip", class:"form-control"){
							for(CastConnection con: SettingsServlet.getConnections()){
								option(value:con.getIp(),"data-default":con.isDefault,con.getName())
							}
						}
					}
				}

				div( class:"form-group"){
					label(class:"col-sm-2 control-label", "Execution pattern:")
					div( class:"col-sm-10"){
						input( type:"text",class:"form-control", id:"pattern", placeholder:"* * * * *")
					}
				}

				div( class:"form-group"){
					label(class:"col-sm-2 control-label", "Url:")
					div( class:"col-sm-10"){
						input( type:"text",class:"form-control", id:"url", placeholder:"http://www....")
					}
				}
				div( class:"form-group"){
					div( class:"col-sm-offset-2 col-sm-10"){
						button( id:"add", class:"btn btn-default","add")
					}
				}
			}
			div(class:"form-horizontal"){
				div( class:"form-group"){
					label(class:"col-sm-2 control-label", "Reload page after sec.")
					div( class:"col-sm-10"){
						input( type:"number",class:"form-control", id:"refresh", min:"0", value:"0")
					}
					label(class:"col-sm-offset-2 col-sm-10 pull-left", "0 means no reload at all")
				}
			}
			button( id:"kill", style:"display: none",disabled:"disabled","Stop casting")
			p( id:"post-note", style:"display: none",
			"""If the page does not load please be sure HTTP header X-Frame-Options
			  allows the page to be loaded inside a frame not on the same origin.""")
		}
		div(class:"col-xs-12"){
			div( class:"page-header"){
				h1('current cronjobs'){
					button(class:"btn btn-default", id:"refreshjobs", style:"height: 34px;"){
						span(class:"glyphicon glyphicon-refresh", " ")
					}
				}
			}
		}
		div(id:"currentjobs",class:"col-xs-12"){
		}
	}
	script(src:"assets/js/cron.js")
}