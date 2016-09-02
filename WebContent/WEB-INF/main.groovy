import de.michaelkuerbis.presenter.servlets.SettingsServlet;
import de.michaelkuerbis.presenter.utils.CastConnection;

html.div {
	div( class:"container", id:"main"){
		div(class:"col-xs-12"){
			div( class:"page-header"){
				h1('Overview') {
					//TODO Modal dialog
					button(class:"btn btn-default", 'data-toggle':"modal", 'data-target':"#addchromecast", "+")
					button(class:"btn btn-default", id:"refreshall", style:"height: 34px;"){
						span(class:"glyphicon glyphicon-refresh", " ")
					}
					button(class:"btn btn-default",id:"searchcasts", 'data-toggle':"modal", 'data-target':"#discoverchromecast", style:"height: 34px;"){
						span(class:"glyphicon glyphicon-search", " ")
					}
				}
			}
		}

		if(SettingsServlet.getConnections().size() == 0){
			div(class:"col-xs-12 alert alert-warning"){
				p("There are no chromecasts defined."){
					a(href:"#",'data-toggle':"modal", 'data-target':"#addchromecast", "add one.")
				}
			}
		}else{
			for(CastConnection con: SettingsServlet.getConnections()){
				div(class:"col-xs-3 chromecastpanel", 'data-ip': con.getIp()){
					div(class:"panel panel-default"){
						div(class:"panel-heading"){
							h3(class:"panel-title", con.getName()){
								button(class:"btn btn-default btn-xs panelrefresh", style:"min-height: 28px;"){
									span(class:"glyphicon glyphicon-refresh", 'aria-hidden':"true")
								}

								button(class:"btn btn-default btn-xs paneltrash btn-warning pull-right", style:"min-height: 28px;"){
									span(class:"glyphicon glyphicon-trash", 'aria-hidden':"true")
								}
							}
						}
						div(class:"panel-body"){
							p("IP: "+con.getIp())
							div{
								p("Status: ")
								p(class:"status")
								p("App:")
								p(class:"application")
								div(class:"btn-group toogle-option", 'data-toggle':"buttons"){
									a(href:"#",class:"btn btn-primary option-default "+(con.isDefault()?"active":""),'data-ip':con.getIp(),"default"){
										if(con.isDefault()){
											input( type:"radio", name:"options", autocomplete:"off", checked:"")
										}else{
											input( type:"radio", name:"options", autocomplete:"off")
										}
									}
									a(href:"#",class:"btn btn-primary option-info "+(!con.isDefault()?"active":""),'data-ip':con.getIp(),"info"){
										if(!con.isDefault()){
											input( type:"radio", name:"options", autocomplete:"off", checked:"")
										}else{
											input( type:"radio", name:"options", autocomplete:"off")
										}
									}
								}
							}
							div(class:"alert alert-warning trashmsg", style:"display:none;"){
								p(class:"text-center", "Remove this chromecast?")
								p(class:"text-center"){
									a(href:"#", class:"btn btn-danger yestrash",'data-ip':con.getIp(), "yes")
									a(href:"#", class:"btn btn-default notrash", "no")
								}
							}
						}
					}
				}
			}
		}

		div( class:"modal fade", id:"addchromecast", tabindex:"-1", role:"dialog", 'aria-labelledby':"myModalLabel"){
			div (class:"modal-dialog", role:"document"){
				div (class:"modal-content"){
					div (class:"modal-header"){
						button( type:"button", class:"close", 'data-dismiss':"modal", 'aria-label':"Close"){
							span( 'aria-hidden':"true", "x")
						}
						h4( class:"modal-title", id:"myModalLabel", "Add Chromecast")
					}
					div (class:"modal-body"){
						div(class:"form-horizontal"){
							div( class:"form-group"){
								label(class:"col-sm-2 control-label", "Name:")
								div( class:"col-sm-10"){
									input( type:"text",class:"form-control", id:"addname", placeholder:"Chromecast #1")
								}
							}
							div( class:"form-group"){
								label(class:"col-sm-2 control-label", "Ip:")
								div( class:"col-sm-10"){
									input( type:"text",class:"form-control", id:"addip", placeholder:"127.0.0.1")
								}
							}
						}
					}
					div (class:"modal-footer"){
						button( type:"button", id:"addcastbutton", class:"btn btn-primary", "add Chromecast")
					}
				}
			}
		}
		div( class:"modal fade", id:"discoverchromecast", tabindex:"-1", role:"dialog", 'aria-labelledby':"myModalLabel"){
			div (class:"modal-dialog", role:"document"){
				div (class:"modal-content"){
					div (class:"modal-header"){
						button( type:"button", class:"close", 'data-dismiss':"modal", 'aria-label':"Close"){
							span( 'aria-hidden':"true", "x")
						}
						h4( class:"modal-title", id:"myModalLabel", "discovered Chromecasts")
					}
					div (class:"modal-body"){ h1("fooo") }
				}
			}
		}

	}
}